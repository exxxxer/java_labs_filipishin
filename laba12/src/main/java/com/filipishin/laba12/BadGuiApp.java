package com.filipishin.laba12;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BadGuiApp extends Application {

    @Override
    public void start(Stage stage) {
        Button button = new Button("Запустить бесконечный цикл (плохо)");

        button.setOnAction(e -> longOperationInFxThread());

        StackPane root = new StackPane(button);
        Scene scene = new Scene(root, 400, 200);

        stage.setTitle("Плохой пример (окно зависает)");
        stage.setScene(scene);
        stage.show();
    }

    private void longOperationInFxThread() {
        while (true) {
            System.out.println("Работаю в FX-потоке...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // ничиво
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
