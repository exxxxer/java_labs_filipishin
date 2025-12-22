package com.pogoda;

import com.pogoda.CityStorage.SavedCity;
import com.pogoda.ex.ApiException;
import com.pogoda.ex.CityNotFoundException;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WeatherController {
    private static final Logger log = LoggerFactory.getLogger(WeatherController.class);

    @FXML private TextField cityField;
    @FXML private ListView<String> suggestionsList;

    @FXML private ImageView iconView;
    @FXML private Label cityLabel;
    @FXML private Label nowLabel;
    @FXML private Label moreLabel;

    @FXML private ListView<String> forecastList;

    private WeatherApi api;
    private CityStorage storage;

    private List<WeatherApi.City> lastSuggestions;

    @FXML
    public void initialize() {
        String key = System.getenv("OPENWEATHER_API_KEY");
        if (key == null || key.isBlank()) {
            showMsg("Нет API ключа. Задайте переменную OPENWEATHER_API_KEY.");
            log.error("API ключ OpenWeather не найден. Необходимо задать переменную окружения OPENWEATHER_API_KEY");
            return;
        }

        api = new WeatherApi(key);
        storage = new CityStorage();

        log.info("Приложение запущено");
        log.info("API ключ найден");

        // Подгрузить последний город
        try {
            SavedCity sc = storage.loadOrNull();
            if (sc != null) {
                log.info("Загружен сохранённый город: {}", sc.display());
                cityField.setText(sc.display());
                loadAll(sc.name, sc.country, sc.lat, sc.lon);
            }
        } catch (ApiException e) {
            log.error("Ошибка чтения сохранённого города", e);
        }

        // Подсказки (очень простая логика)
        cityField.textProperty().addListener((obs, oldV, newV) -> {
            if (newV == null) return;
            String q = newV.trim();
            if (q.length() < 2) {
                suggestionsList.getItems().clear();
                return;
            }
            suggest(q);
        });

        // Клик по подсказке
        suggestionsList.setOnMouseClicked(e -> {
            int idx = suggestionsList.getSelectionModel().getSelectedIndex();
            if (idx >= 0 && lastSuggestions != null && idx < lastSuggestions.size()) {
                WeatherApi.City c = lastSuggestions.get(idx);
                log.info("Выбран город: {}", c.display());
                cityField.setText(c.display());
                suggestionsList.getItems().clear();

                loadAll(c.name, c.country, c.lat, c.lon);

                SavedCity sc = new SavedCity();
                sc.name = c.name;
                sc.country = c.country;
                sc.lat = c.lat;
                sc.lon = c.lon;
                try {
                    storage.save(sc);
                    log.info("Город сохранён: {}", c.display());
                } catch (ApiException ex) {
                    log.error("Не сохранился город", ex);
                }
            }
        });
    }

    @FXML
    public void onSearch() {
        String q = cityField.getText() == null ? "" : cityField.getText().trim();
        log.info("Нажал Найти: {}", q);

        if (q.isBlank()) {
            showMsg("Введите город");
            return;
        }

        Task<WeatherApi.City> task = new Task<>() {
            @Override
            protected WeatherApi.City call() throws Exception {
                return api.firstCityOrThrow(q);
            }
        };

        task.setOnSucceeded(e -> {
            WeatherApi.City city = task.getValue();
            loadAll(city.name, city.country, city.lat, city.lon);

            SavedCity sc = new SavedCity();
            sc.name = city.name;
            sc.country = city.country;
            sc.lat = city.lat;
            sc.lon = city.lon;
            try {
                storage.save(sc);
                log.info("Город сохранён: {}", city.display());
            } catch (ApiException ex) {
                log.error("Не сохранился город", ex);
            }
        });

        task.setOnFailed(e -> {
            Throwable r = task.getException();
            while (r != null && r.getCause() != null) r = r.getCause();

            if (r instanceof CityNotFoundException) {
                showMsg(r.getMessage());
            } else {
                log.error("Ошибка поиска города", r);
                showMsg("Ошибка. Смотри errors.log");
            }
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    private void suggest(String q) {
        Task<List<WeatherApi.City>> task = new Task<>() {
            @Override
            protected List<WeatherApi.City> call() throws Exception {
                return api.suggest(q);
            }
        };

        task.setOnSucceeded(e -> {
            List<WeatherApi.City> list = task.getValue();
            lastSuggestions = list;
            suggestionsList.getItems().clear();
            for (WeatherApi.City c : list) {
                suggestionsList.getItems().add(c.display());
            }
        });

        task.setOnFailed(e -> log.error("Ошибка подсказок", task.getException()));

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    private void loadAll(String name, String country, double lat, double lon) {
        cityLabel.setText(name + (country == null || country.isBlank() ? "" : ", " + country));

        log.info("Загрузка погоды для города: {} ({}, {})", name, lat, lon);

        Task<Object[]> task = new Task<>() {
            @Override
            protected Object[] call() throws Exception {
                WeatherApi.Current cur = api.current(lat, lon);
                List<String> days = api.next4Days(lat, lon);
                return new Object[]{cur, days};
            }
        };

        task.setOnSucceeded(e -> {
            Object[] obj = task.getValue();

            WeatherApi.Current cur = (WeatherApi.Current) obj[0];
            @SuppressWarnings("unchecked")
            List<String> days = (List<String>) obj[1];

            nowLabel.setText(String.format("Сейчас: %.1f°C (ощущ. %.1f°C), %s",
                    cur.temp, cur.feels, cur.desc == null ? "" : cur.desc));

            String precip = "нет";
            if (cur.rain1h != null) precip = "дождь " + String.format("%.1f мм/ч", cur.rain1h);
            if (cur.snow1h != null) precip = "снег " + String.format("%.1f мм/ч", cur.snow1h);

            moreLabel.setText(String.format("Мин/макс: %.1f / %.1f, влажн. %d%%, давл. %d гПа, ветер %.1f м/с (%d°), облачн. %d%%, осадки: %s",
                    cur.min, cur.max, cur.humidity, cur.pressure, cur.windSpeed, cur.windDeg, cur.clouds, precip));

            if (cur.icon != null && !cur.icon.isBlank()) {
                String url = "https://openweathermap.org/img/wn/" + cur.icon + "@2x.png";
                iconView.setImage(new Image(url, true));
            } else {
                iconView.setImage(null);
            }

            forecastList.getItems().setAll(days);
            
            log.info("Погода успешно загружена для города: {}", name);
        });

        task.setOnFailed(e -> {
            log.error("Ошибка загрузки погоды", task.getException());
            showMsg("Ошибка погоды. Смотри errors.log");
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    private void showMsg(String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Сообщение");
        a.setHeaderText(null);
        a.setContentText(text);
        a.showAndWait();
    }
}
