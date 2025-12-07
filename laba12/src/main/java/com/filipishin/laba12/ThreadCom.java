package com.filipishin.laba12;

public class ThreadCom {
    public static void main(String[] args) {
        TikTok tt = new TikTok();

        MyThread mt1 = MyThread.createAndStart("Поток1", tt);
        MyThread mt2 = MyThread.createAndStart("Поток2", tt);

    }
}