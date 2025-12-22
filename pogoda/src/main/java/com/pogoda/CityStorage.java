package com.pogoda;

import com.google.gson.Gson;
import com.pogoda.ex.ApiException;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class CityStorage {
    private final Gson gson = new Gson();
    private final Path file;

    public static class SavedCity {
        public String name;
        public String country;
        public double lat;
        public double lon;

        public String display() {
            if (country == null || country.isBlank()) return name;
            return name + ", " + country;
        }
    }

    public CityStorage() {
        Path dir = Path.of(System.getProperty("user.home"), ".pogoda");
        this.file = dir.resolve("city.json");
    }

    public void save(SavedCity city) throws ApiException {
        try {
            Files.createDirectories(file.getParent());
            Files.writeString(file, gson.toJson(city), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new ApiException("Не смог сохранить город", e);
        }
    }

    public SavedCity loadOrNull() throws ApiException {
        try {
            if (!Files.exists(file)) return null;
            String json = Files.readString(file, StandardCharsets.UTF_8);
            return gson.fromJson(json, SavedCity.class);
        } catch (Exception e) {
            throw new ApiException("Не смог загрузить город", e);
        }
    }
}
