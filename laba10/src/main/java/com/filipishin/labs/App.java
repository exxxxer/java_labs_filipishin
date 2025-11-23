package com.filipishin.labs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        TabPane tabs = new TabPane();

        Tab t1 = new Tab("№1 Перекидыватель");
        t1.setContent(new Task1Pane());
        t1.setClosable(false);

        Tab t2 = new Tab("№2 Виджеты + чекбоксы");
        t2.setContent(new Task2Pane());
        t2.setClosable(false);

        Tab t3 = new Tab("№3 Заказ в ресторане");
        t3.setContent(new Task3Pane());
        t3.setClosable(false);

        Tab t4 = new Tab("№4 Калькулятор");
        t4.setContent(new Task4Pane());
        t4.setClosable(false);

        Tab t5 = new Tab("№5 Текстовый флаг");
        t5.setContent(new Task5Pane());
        t5.setClosable(false);

        tabs.getTabs().addAll(t1, t2, t3, t4, t5);

        Scene scene = new Scene(tabs, 700, 500);
        stage.setTitle("Лабораторная 10 – JavaFX");
        stage.setScene(scene);
        stage.setResizable(false); // размер окна нельзя менять
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}