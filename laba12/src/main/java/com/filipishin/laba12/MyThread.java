package com.filipishin.laba12;

class MyThread implements Runnable {
    Thread thrd;
    TikTok ttObj;

    MyThread(String name, TikTok tt) {
        thrd = new Thread(this, name);
        ttObj = tt;
    }

    public static MyThread createAndStart(String name, TikTok tt) {
        MyThread myThrd = new MyThread(name, tt);
        myThrd.thrd.start();
        return myThrd;
    }

    @Override
    public void run() {
        if (thrd.getName().equals("Поток1")) {
            while (true) {
                ttObj.printFirst();
            }
        } else {
            while (true) {
                ttObj.printSecond();
            }
        }
    }
}