package school.lemon.changerequest.java.concurrency;

import java.util.concurrent.CountDownLatch;

public class _08CountDownLatchExample {

    private static final int COUNT = 10;

    private static class Worker implements Runnable {

        CountDownLatch startLatch;
        CountDownLatch stopLatch;
        String name;

        Worker(CountDownLatch startLatch, CountDownLatch stopLatch, String name) {
            this.startLatch = startLatch;
            this.stopLatch = stopLatch;
            this.name = name;
        }

        public void run() {
            try {
                startLatch.await(); // wait until the latch has counted down to
                // zero
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("Running: " + name);
            stopLatch.countDown();
        }
    }

    public static void main(String args[]) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch stopSignal = new CountDownLatch(COUNT);

        for (int i = 0; i < COUNT; i++) {
            new Thread(new Worker(startSignal, stopSignal, Integer.toString(i))).start();
        }
        System.out.println("Go");
        startSignal.countDown();

        stopSignal.await();
        System.out.println("Done");
    }
}
