package com.filipishin.labs;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Task2Pane extends VBox {

    public Task2Pane() {
        setPadding(new Insets(15));
        setSpacing(10);

        Label info = new Label("Отмечайте чекбоксы, чтобы показать/скрыть элементы.");

        TextArea textArea = new TextArea();
        textArea.setPromptText("Текстовая область");
        textArea.setPrefRowCount(3);

        Slider slider = new Slider(0, 100, 50);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        DatePicker datePicker = new DatePicker();

        CheckBox cbText = new CheckBox("Показывать текстовую область");
        CheckBox cbSlider = new CheckBox("Показывать слайдер");
        CheckBox cbDate = new CheckBox("Показывать выбор даты");

        cbText.setSelected(true);
        cbSlider.setSelected(true);
        cbDate.setSelected(true);

        bindVisible(textArea, cbText);
        bindVisible(slider, cbSlider);
        bindVisible(datePicker, cbDate);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(cbText,   0, 0);
        grid.add(textArea, 1, 0);
        grid.add(cbSlider, 0, 1);
        grid.add(slider,   1, 1);
        grid.add(cbDate,   0, 2);
        grid.add(datePicker, 1, 2);

        getChildren().addAll(info, grid);
    }

    private void bindVisible(javafx.scene.Node node, CheckBox cb) {
        node.visibleProperty().bind(cb.selectedProperty());
        node.managedProperty().bind(cb.selectedProperty());
    }
}