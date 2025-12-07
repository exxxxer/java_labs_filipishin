package com.filipishin.laba12;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProgressBarApp extends Application {

    private ProgressBar progressBar;
    private Button startBtn;
    private Button pauseBtn;
    private Button stopBtn;

    private Worker worker;
    private final Object lock = new Object();

    public void start(Stage stage) {
        progressBar = new ProgressBar(0.0);

        startBtn = new Button("Старт");
        pauseBtn = new Button("Пауза");
        stopBtn  = new Button("Стоп");

        pauseBtn.setDisable(true);
        stopBtn.setDisable(true);

        startBtn.setOnAction(e -> onStart());
        pauseBtn.setOnAction(e -> onPauseOrContinue());
        stopBtn.setOnAction(e -> onStop());

        HBox buttons = new HBox(10, startBtn, pauseBtn, stopBtn);
        VBox root = new VBox(10, progressBar, buttons);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 400, 120);
        stage.setScene(scene);
        stage.setTitle("ProgressBar и потоки");
        stage.show();
    }

    private void onStart() {
        if (worker != null && worker.isAlive()) {
            worker.requestStop();
        }
        worker = new Worker("Progress-Worker");
        worker.setDaemon(true);
        worker.start();

        progressBar.setProgress(0.0);
        pauseBtn.setDisable(false);
        stopBtn.setDisable(false);
        pauseBtn.setText("Пауза");
    }

    private void onPauseOrContinue() {
        if (worker == null) return;

        if (worker.isPaused()) {
            worker.myResume();
            pauseBtn.setText("Пауза");
        } else {
            worker.mySuspend();
            pauseBtn.setText("Продолжить");
        }
    }

    private void onStop() {
        if (worker != null) {
            worker.requestStop();
            worker = null;
        }
        progressBar.setProgress(0.0);
        pauseBtn.setDisable(true);
        stopBtn.setDisable(true);
        pauseBtn.setText("Пауза");
    }

    class Worker extends Thread {
        private volatile boolean running = true;
        private boolean suspended = false;

        Worker(String name) {
            super(name);
        }

        public void requestStop() {
            running = false;
            synchronized (lock) {
                suspended = false;
                lock.notifyAll();
            }
        }

        public void mySuspend() {
            synchronized (lock) {
                suspended = true;
            }
        }

        public void myResume() {
            synchronized (lock) {
                suspended = false;
                lock.notifyAll();
            }
        }

        public boolean isPaused() {
            return suspended;
        }

        public void run() {
            final int max = 1000;
            for (int i = 0; i <= max && running; i++) {

                synchronized (lock) {
                    while (suspended && running) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }

                final double progress = i / (double) max;

                Platform.runLater(new Runnable() {
                    public void run() {
                        progressBar.setProgress(progress);
                    }
                });

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    return;
                }
            }

            Platform.runLater(new Runnable() {
                public void run() {
                    pauseBtn.setDisable(true);
                    stopBtn.setDisable(true);
                    pauseBtn.setText("Пауза");
                }
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
