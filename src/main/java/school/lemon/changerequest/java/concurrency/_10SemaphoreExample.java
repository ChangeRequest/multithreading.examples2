package school.lemon.changerequest.java.concurrency;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class _10SemaphoreExample {

    private static final int CARS_NUMBER = 4;

    public static void main(String[] args) throws InterruptedException {
        SemaphoreTunnel st = new SemaphoreTunnel();
        Thread[] threads = new Thread[CARS_NUMBER];
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Thread(new Car(st, i));
            threads[i].start();
        }

        Thread.sleep(500);

        for (Thread t : threads) {
            t.interrupt();
        }
        System.out.println("Main method ending");
    }

    private static class SemaphoreTunnel {

        // size refers to the maximum number of cars that can enter the tunnel
        private static final int SIZE = 2;
        private Semaphore spaces = new Semaphore(SIZE);

        public void enter(int number) throws InterruptedException {
            spaces.acquire();
            System.out.println("Car #" + number + " enters.");

        }

        public void exit(int number) throws InterruptedException {
            System.out.println("Car #" + number + " exits");
            spaces.release();
        }

    }

    private static class Car implements Runnable {

        private SemaphoreTunnel tunnel;
        private int number;
        private Random r;

        public Car(SemaphoreTunnel tunnel, int number) {
            this.tunnel = tunnel;
            this.number = number;
            this.r = new Random();
        }

        public void run() {
            try {
                while (!Thread.interrupted()) {
                    tunnel.enter(number);
                    Thread.sleep(r.nextInt(100));
                    tunnel.exit(number);
                    Thread.sleep(r.nextInt(100));

                }
            } catch (InterruptedException e) {
            }
        }
    }

}
