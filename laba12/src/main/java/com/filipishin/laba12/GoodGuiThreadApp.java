package com.filipishin.laba12;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GoodGuiThreadApp extends Application {

    @Override
    public void start(Stage stage) {
        Button button = new Button("Старт (Thread)");

        button.setOnAction(e -> startWorkerThread());

        StackPane root = new StackPane(button);
        Scene scene = new Scene(root, 400, 200);

        stage.setTitle("Хороший пример (Thread)");
        stage.setScene(scene);
        stage.show();
    }

    static class MyThread extends Thread {

        MyThread(String name) {
            super(name);
        }

        public void run() {
            while (true) {
                System.out.println("Работает поток: " + getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(getName() + " завершён");
                    return;
                }
            }
        }
    }

    private void startWorkerThread() {
        MyThread t = new MyThread("Worker-Thread");
        t.setDaemon(true);
        t.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
