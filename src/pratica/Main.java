package pratica;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {

        BlockingQueue<String> runnables = new ArrayBlockingQueue<>(5);

        Thread produttore = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                String ris = "Prodotto: " + i;
                System.out.println(ris);
                runnables.add(ris);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        Thread consumatore = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                String ris = "Consumo: " + i;
                System.out.println(ris);
                try {
                    runnables.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        produttore.start();
        consumatore.start();


    }
}