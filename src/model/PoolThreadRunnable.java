package model;

import java.util.concurrent.BlockingQueue;

class PoolThreadRunnable implements Runnable {
    private Thread thread = null;
    private BlockingQueue<Runnable> taskQueue = null;
    private boolean isStopped = false;

    public PoolThreadRunnable(BlockingQueue<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void run() {
        this.thread = Thread.currentThread();
        System.out.println(thread.getName());
        while (!isStopped) {
            try {
                Runnable task = taskQueue.take();
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception ignored) {

            }
        }
    }

    public synchronized void doStop() {
        isStopped = true;
        this.thread.interrupt();
        System.out.println(thread.getName() + " Stoppato");
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }
}