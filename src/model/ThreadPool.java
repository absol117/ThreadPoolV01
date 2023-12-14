package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPool {
    private BlockingQueue<Runnable> taskQueue = null;
    private List<PoolThreadRunnable> runnables = new ArrayList<>();
    private boolean isStopped = false;

    public ThreadPool(int nThreads, int maxTasks) {
        taskQueue = new ArrayBlockingQueue<>(maxTasks);

        for (int i = 0; i < nThreads; i++) {
            PoolThreadRunnable poolThreadRunnable = new PoolThreadRunnable(taskQueue);
            runnables.add(poolThreadRunnable);

        }
        for (PoolThreadRunnable runnable : runnables) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

    public void execute(Runnable task) throws IllegalAccessException {
        synchronized (this) {
            if (this.isStopped) {
                throw new IllegalAccessException("ThreadPool is stopped");
            }
            this.taskQueue.offer(task);
        }
    }

    public void stop() {
        synchronized (this) {
            this.isStopped = true;
            for (PoolThreadRunnable runnable : runnables) {
                runnable.doStop();
            }
        }
    }

    public void waitUntilAllTaskFinished() {
        synchronized (this) {
            while (taskQueue.size() > 0) {
                try {
                    this.wait(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
