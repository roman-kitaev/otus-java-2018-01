package ru.otus.hw141;

/**
 * Created by rel on 03.06.2018.
 */
public class Main {
    public static void main(String[] args) {
        Runnable r = () -> {
            for(int i = 0; i < 10; i++) {
                System.out.println(i);
            }
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(i);
                }
            }
        };

        Thread t = new Thread(r);
        Thread t2 = new Thread(r2);

        t.start();
        t2.start();

        Thread t3 = new MyThread();
        t3.start();
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread.run()");
    }
}
