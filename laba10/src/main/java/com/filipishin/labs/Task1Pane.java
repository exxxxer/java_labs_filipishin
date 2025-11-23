package com.filipishin.labs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Task1Pane extends VBox {

    private boolean leftToRight = true;

    public Task1Pane() {
        setPadding(new Insets(15));
        setSpacing(10);

        Label info = new Label("Введите текст в одно из полей и нажимайте кнопку.");

        TextField left = new TextField();
        left.setPromptText("Левое поле");

        TextField right = new TextField();
        right.setPromptText("Правое поле");

        Button moveBtn = new Button("→");

moveBtn.setOnAction(e -> {
    if (leftToRight) {
        right.setText(left.getText());
        left.clear();                  
    } else {
        left.setText(right.getText());
        right.clear();                 
    }
    leftToRight = !leftToRight;
    moveBtn.setText(leftToRight ? "→" : "←");
});

        HBox row = new HBox(10, left, moveBtn, right);
        row.setAlignment(Pos.CENTER);

        getChildren().addAll(info, row);
    }
}