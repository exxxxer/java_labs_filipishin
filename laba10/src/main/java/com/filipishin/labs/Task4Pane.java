package com.filipishin.labs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Task4Pane extends VBox {

    private final TextField display = new TextField("0");
    private Double currentValue = null;
    private String pendingOp = null;
    private boolean startNewNumber = true;

    public Task4Pane() {
        setPadding(new Insets(15));
        setSpacing(10);

        Label info = new Label("Простой калькулятор. Операции выполняются последовательно.");

        display.setEditable(false);
        display.setAlignment(Pos.CENTER_RIGHT);

        GridPane buttons = new GridPane();
        buttons.setHgap(5);
        buttons.setVgap(5);

        String[][] layout = {
                {"7","8","9","/"},
                {"4","5","6","*"},
                {"1","2","3","-"},
                {"0",".","C","+"},
                {"=","","",""}
        };

        for (int r = 0; r < layout.length; r++) {
            for (int c = 0; c < layout[r].length; c++) {
                String text = layout[r][c];
                if (text.isEmpty()) continue;
                Button b = new Button(text);
                b.setPrefWidth(50);
                b.setOnAction(e -> onButton(text));
                buttons.add(b, c, r);
            }
        }

        getChildren().addAll(info, display, buttons);
    }

    private void onButton(String text) {
        if (text.matches("[0-9]") || ".".equals(text)) {
            if (startNewNumber) {
                display.setText(text.equals(".") ? "0." : text);
                startNewNumber = false;
            } else {
                if (text.equals(".") && display.getText().contains(".")) return;
                display.setText(display.getText() + text);
            }
            return;
        }

        if ("C".equals(text)) {
            display.setText("0");
            currentValue = null;
            pendingOp = null;
            startNewNumber = true;
            return;
        }

        if ("+".equals(text) || "-".equals(text) ||
            "*".equals(text) || "/".equals(text)) {
            applyOperation(text);
            return;
        }

        if ("=".equals(text)) {
            applyOperation(null); // завершить
        }
    }

    private void applyOperation(String newOp) {
        try {
            double value = Double.parseDouble(display.getText());
            if (currentValue == null) {
                currentValue = value;
            } else if (pendingOp != null) {
                if ("/".equals(pendingOp) && value == 0.0) {
                    showError("Ошибка: деление на ноль");
                    display.setText("Ошибка");
                    currentValue = null;
                    pendingOp = null;
                    startNewNumber = true;
                    return;
                }
                currentValue = switch (pendingOp) {
                    case "+" -> currentValue + value;
                    case "-" -> currentValue - value;
                    case "*" -> currentValue * value;
                    case "/" -> currentValue / value;
                    default -> currentValue;
                };
            }

            display.setText(String.valueOf(currentValue));
            pendingOp = newOp;
            startNewNumber = true;

            if (newOp == null) {
                // нажал "=" – сброс операции
                currentValue = null;
            }
        } catch (NumberFormatException ex) {
            showError("Некорректное число");
            display.setText("0");
            currentValue = null;
            pendingOp = null;
            startNewNumber = true;
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
