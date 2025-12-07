package com.filipishin.laba12;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GoodGuiRunnableApp extends Application {

    @Override
    public void start(Stage stage) {
        Button button = new Button("Старт (Runnable)");

        button.setOnAction(e -> startWorkerRunnable());

        StackPane root = new StackPane(button);
        Scene scene = new Scene(root, 400, 200);

        stage.setTitle("Хороший пример (Runnable)");
        stage.setScene(scene);
        stage.show();
    }

    static class MyRunnable implements Runnable {
        public void run() {
            while (true) {
                System.out.println("Работает поток: " +
                        Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Поток (Runnable) завершён");
                    return;
                }
            }
        }
    }

    private void startWorkerRunnable() {
        MyRunnable r = new MyRunnable();
        Thread t = new Thread(r, "Worker-Runnable");
        t.setDaemon(true);
        t.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
