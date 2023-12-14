package main;

import model.ThreadPool;

public class Main {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(3, 10);

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            try {
                threadPool.execute(() -> {
                    String message = Thread.currentThread().getName() + " Task : " + finalI;
                    System.out.println(message);
                });
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        //threadPool.waitUntilAllTaskFinished();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        threadPool.stop();
    }
}
