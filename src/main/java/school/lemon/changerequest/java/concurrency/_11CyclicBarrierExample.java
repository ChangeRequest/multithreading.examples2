package school.lemon.changerequest.java.concurrency;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class _11CyclicBarrierExample {

    public static void main(String[] args) throws Exception {
        int threads = 3;
        final CyclicBarrier barrier = new CyclicBarrier(threads);

        for (int i = 0; i < threads; i++) {
            new Thread(new Runnable() {
                private Random r = new Random();

                public void run() {
                    try {
                        log("Starting");
                        Thread.sleep(r.nextInt(1000));
                        log("Finished Starting");

                        barrier.await();
                        log("Working");
                        Thread.sleep(r.nextInt(1000));
                        log("Finished working");

                        barrier.await();
                        log("Waiting for end");
                        Thread.sleep(r.nextInt(1000));
                        log("Finished waiting for end");

                        barrier.await();
                        log("Done");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private static void log(String msg) {
        System.out.println(System.currentTimeMillis() + ": " + Thread.currentThread().getId() + "  " + msg);
    }
}
