package com.filipishin.labs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class Task3Pane extends VBox {

    private static class DishRow {
        String name;
        int price;
        CheckBox checkBox;
        Spinner<Integer> qty;

        DishRow(String name, int price) {
            this.name = name;
            this.price = price;
            this.checkBox = new CheckBox(name + " (" + price + " ₽)");
            this.qty = new Spinner<>(0, 20, 0);
            this.qty.setEditable(true);

            checkBox.selectedProperty().addListener((obs, oldV, newV) -> {
                if (newV && qty.getValue() == 0) {
                    qty.getValueFactory().setValue(1);
                }
                if (!newV) {
                    qty.getValueFactory().setValue(0);
                }
            });
        }
    }

    private final List<DishRow> dishes = new ArrayList<>();
    private final TextArea receipt = new TextArea();

    public Task3Pane() {
        setPadding(new Insets(15));
        setSpacing(10);

        Label info = new Label("Выберите блюда, задайте количество и нажмите \"Обновить чек\".");

        dishes.add(new DishRow("Борщ", 150));
        dishes.add(new DishRow("Пюре с котлетой", 220));
        dishes.add(new DishRow("Пицца", 350));
        dishes.add(new DishRow("Чай", 60));
        dishes.add(new DishRow("Кофе", 90));

        VBox dishBox = new VBox(5);
        for (DishRow d : dishes) {
            HBox row = new HBox(10, d.checkBox, new Label("Количество:"), d.qty);
            row.setAlignment(Pos.CENTER_LEFT);
            dishBox.getChildren().add(row);
        }

        Button calcBtn = new Button("Обновить чек");
        calcBtn.setOnAction(e -> updateReceipt());

        receipt.setEditable(false);
        receipt.setPrefRowCount(8);

        getChildren().addAll(info, dishBox, calcBtn, new Label("Чек:"), receipt);
    }

    private void updateReceipt() {
        StringBuilder sb = new StringBuilder();
        int total = 0;

        for (DishRow d : dishes) {
            int q = d.qty.getValue();
            if (q > 0) {
                int cost = q * d.price;
                sb.append(String.format("%s × %d = %d ₽%n", d.name, q, cost));
                total += cost;
            }
        }

        if (total == 0) {
            sb.append("Ничего не выбрано.");
        } else {
            sb.append("------------------------------\n");
            sb.append(String.format("ИТОГО: %d ₽%n", total));
        }

        receipt.setText(sb.toString());
    }
}