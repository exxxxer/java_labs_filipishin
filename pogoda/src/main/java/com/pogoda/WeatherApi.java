package com.pogoda;

import com.google.gson.*;
import com.pogoda.ex.ApiException;
import com.pogoda.ex.CityNotFoundException;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;

public class WeatherApi {
    private final String apiKey;
    private final HttpClient http = HttpClient.newHttpClient();

    public WeatherApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public static class City {
        public String name;
        public String country;
        public double lat;
        public double lon;

        public String display() {
            if (country == null || country.isBlank()) return name;
            return name + ", " + country;
        }
    }

    public static class Current {
        public double temp;
        public double feels;
        public double min;
        public double max;
        public int humidity;
        public int pressure;
        public double windSpeed;
        public int windDeg;
        public int clouds;
        public Double rain1h;
        public Double snow1h;
        public String icon;
        public String desc;
    }

    private String get(String url) throws ApiException {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() != 200) {
                throw new ApiException("HTTP " + resp.statusCode());
            }
            return resp.body();
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException("Ошибка запроса", e);
        }
    }

    public List<City> suggest(String q) throws ApiException {
        String qq = URLEncoder.encode(q, StandardCharsets.UTF_8);
        String url = "https://api.openweathermap.org/geo/1.0/direct?q=" + qq + "&limit=5&appid=" + apiKey;
        String body = get(url);

        JsonArray arr = JsonParser.parseString(body).getAsJsonArray();
        List<City> res = new ArrayList<>();
        for (JsonElement el : arr) {
            JsonObject o = el.getAsJsonObject();
            City c = new City();
            c.name = o.get("name").getAsString();
            c.country = o.has("country") ? o.get("country").getAsString() : "";
            c.lat = o.get("lat").getAsDouble();
            c.lon = o.get("lon").getAsDouble();
            res.add(c);
        }
        return res;
    }

    public City firstCityOrThrow(String q) throws ApiException, CityNotFoundException {
        List<City> list = suggest(q);
        if (list.isEmpty()) throw new CityNotFoundException("Город не найден: " + q);
        return list.get(0);
    }

    public Current current(double lat, double lon) throws ApiException {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon +
                "&appid=" + apiKey + "&units=metric&lang=ru";
        String body = get(url);

        JsonObject root = JsonParser.parseString(body).getAsJsonObject();

        Current c = new Current();

        JsonObject main = root.getAsJsonObject("main");
        c.temp = main.get("temp").getAsDouble();
        c.feels = main.get("feels_like").getAsDouble();
        c.min = main.get("temp_min").getAsDouble();
        c.max = main.get("temp_max").getAsDouble();
        c.humidity = main.get("humidity").getAsInt();
        c.pressure = main.get("pressure").getAsInt();

        JsonObject wind = root.getAsJsonObject("wind");
        c.windSpeed = wind.get("speed").getAsDouble();
        c.windDeg = wind.has("deg") ? wind.get("deg").getAsInt() : 0;

        JsonObject clouds = root.getAsJsonObject("clouds");
        c.clouds = clouds.get("all").getAsInt();

        if (root.has("rain") && root.get("rain").isJsonObject()) {
            JsonObject r = root.getAsJsonObject("rain");
            if (r.has("1h")) c.rain1h = r.get("1h").getAsDouble();
        }
        if (root.has("snow") && root.get("snow").isJsonObject()) {
            JsonObject s = root.getAsJsonObject("snow");
            if (s.has("1h")) c.snow1h = s.get("1h").getAsDouble();
        }

        JsonArray wArr = root.getAsJsonArray("weather");
        if (wArr != null && !wArr.isEmpty()) {
            JsonObject w0 = wArr.get(0).getAsJsonObject();
            c.icon = w0.get("icon").getAsString();
            c.desc = w0.get("description").getAsString();
        }

        return c;
    }

    public List<String> next4Days(double lat, double lon) throws ApiException {
        String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon +
                "&appid=" + apiKey + "&units=metric&lang=ru";
        String body = get(url);

        JsonObject root = JsonParser.parseString(body).getAsJsonObject();

        int tz = 0;
        if (root.has("city")) {
            JsonObject city = root.getAsJsonObject("city");
            if (city.has("timezone")) tz = city.get("timezone").getAsInt();
        }
        ZoneOffset off = ZoneOffset.ofTotalSeconds(tz);
        LocalDate today = Instant.now().atZone(off).toLocalDate();

        JsonArray list = root.getAsJsonArray("list");
        Map<LocalDate, DayAgg> map = new HashMap<>();

        for (JsonElement el : list) {
            JsonObject it = el.getAsJsonObject();
            long dt = it.get("dt").getAsLong();
            LocalDateTime time = Instant.ofEpochSecond(dt).atZone(off).toLocalDateTime();
            LocalDate date = time.toLocalDate();

            if (!date.isAfter(today)) continue;

            JsonObject main = it.getAsJsonObject("main");
            double tMin = main.get("temp_min").getAsDouble();
            double tMax = main.get("temp_max").getAsDouble();

            double pop = it.has("pop") ? it.get("pop").getAsDouble() : 0.0;

            double precip = 0.0;
            if (it.has("rain") && it.get("rain").isJsonObject()) {
                JsonObject r = it.getAsJsonObject("rain");
                if (r.has("3h")) precip += r.get("3h").getAsDouble();
            }
            if (it.has("snow") && it.get("snow").isJsonObject()) {
                JsonObject s = it.getAsJsonObject("snow");
                if (s.has("3h")) precip += s.get("3h").getAsDouble();
            }

            String desc = "";
            JsonArray wArr = it.getAsJsonArray("weather");
            if (wArr != null && !wArr.isEmpty()) {
                desc = wArr.get(0).getAsJsonObject().get("description").getAsString();
            }

            DayAgg agg = map.get(date);
            if (agg == null) {
                agg = new DayAgg();
                agg.min = tMin;
                agg.max = tMax;
                agg.popMax = pop;
                agg.precip = precip;
                agg.bestDesc = desc;
                agg.bestNoonDiff = Math.abs(Duration.between(time, LocalDateTime.of(date, LocalTime.NOON)).toMinutes());
                map.put(date, agg);
            } else {
                agg.min = Math.min(agg.min, tMin);
                agg.max = Math.max(agg.max, tMax);
                agg.popMax = Math.max(agg.popMax, pop);
                agg.precip += precip;

                long diff = Math.abs(Duration.between(time, LocalDateTime.of(date, LocalTime.NOON)).toMinutes());
                if (diff < agg.bestNoonDiff) {
                    agg.bestNoonDiff = diff;
                    agg.bestDesc = desc;
                }
            }
        }

        List<LocalDate> dates = new ArrayList<>(map.keySet());
        Collections.sort(dates);
        if (dates.size() > 4) dates = dates.subList(0, 4);

        List<String> out = new ArrayList<>();
        for (LocalDate d : dates) {
            DayAgg a = map.get(d);
            String s;
            if (a.precip > 0.0) {
                s = d + ": " + String.format("мин %.1f / макс %.1f, осадки %.1f мм, POP до %.0f%%",
                        a.min, a.max, a.precip, a.popMax * 100.0);
            } else {
                s = d + ": " + String.format("мин %.1f / макс %.1f, осадки нет, POP до %.0f%%",
                        a.min, a.max, a.popMax * 100.0);
            }
            if (a.bestDesc != null && !a.bestDesc.isBlank()) s += " (" + a.bestDesc + ")";
            out.add(s);
        }

        return out;
    }

    private static class DayAgg {
        double min;
        double max;
        double precip;
        double popMax;
        long bestNoonDiff;
        String bestDesc;
    }
}
