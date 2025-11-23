package com.filipishin.labs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Task5Pane extends VBox {

    private final Label resultLabel = new Label("Здесь появится описание флага.");

    public Task5Pane() {
        setPadding(new Insets(15));
        setSpacing(10);

        Label info = new Label("Выберите цвет для каждой полосы и нажмите \"Нарисовать\".");

        ToggleGroup topGroup = new ToggleGroup();
        ToggleGroup midGroup = new ToggleGroup();
        ToggleGroup bottomGroup = new ToggleGroup();

        HBox topRow = createStripe("Верхняя полоса", topGroup);
        HBox midRow = createStripe("Средняя полоса", midGroup);
        HBox bottomRow = createStripe("Нижняя полоса", bottomGroup);

        Button drawBtn = new Button("Нарисовать");
        drawBtn.setOnAction(e -> showFlag(topGroup, midGroup, bottomGroup));

        getChildren().addAll(info, topRow, midRow, bottomRow, drawBtn, resultLabel);
    }

    private HBox createStripe(String title, ToggleGroup group) {
        Label l = new Label(title + ": ");
        RadioButton r1 = new RadioButton("Красный");
        RadioButton r2 = new RadioButton("Зелёный");
        RadioButton r3 = new RadioButton("Синий");
        RadioButton r4 = new RadioButton("Белый");
        RadioButton r5 = new RadioButton("Жёлтый");

        r1.setToggleGroup(group);
        r2.setToggleGroup(group);
        r3.setToggleGroup(group);
        r4.setToggleGroup(group);
        r5.setToggleGroup(group);

        HBox box = new HBox(10, l, r1, r2, r3, r4, r5);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(5, 0, 5, 0));
        return box;
    }

    private void showFlag(ToggleGroup top, ToggleGroup mid, ToggleGroup bottom) {
        String c1 = getSelectedText(top);
        String c2 = getSelectedText(mid);
        String c3 = getSelectedText(bottom);

        if (c1 == null || c2 == null || c3 == null) {
            resultLabel.setText("Пожалуйста, выберите цвет для каждой полосы.");
        } else {
            resultLabel.setText(c1 + ", " + c2 + ", " + c3);
        }
    }

    private String getSelectedText(ToggleGroup group) {
        Toggle t = group.getSelectedToggle();
        if (t == null) return null;
        return ((RadioButton) t).getText();
    }
}