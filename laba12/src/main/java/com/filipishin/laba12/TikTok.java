package com.filipishin.laba12;

class TikTok {
    String state;

    TikTok() {
        state = "secondPrinted";
    }

    synchronized void printFirst() {
        try {
            while (!state.equals("secondPrinted")) {
                wait();
            }
            System.out.println("Поток1");
            state = "firstPrinted";
            notify();
        } catch (InterruptedException exc) {
            System.out.println("Прерывание потока Поток1");
        }
    }

    synchronized void printSecond() {
        try {
            while (!state.equals("firstPrinted")) {
                wait();
            }
            System.out.println("Поток2");
            state = "secondPrinted";
            notify();
        } catch (InterruptedException exc) {
            System.out.println("Прерывание потока Поток2");
        }
    }
}