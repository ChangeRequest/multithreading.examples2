package school.lemon.changerequest.java.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _13ExecutorServiceExample {

    public static void main(String[] args) {
        Runnable task = new Runnable() {
            public void run() {
                try {
                    System.out.println("Task executing by: " + Thread.currentThread().getName());
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ExecutorService exec = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10; ++i) {
            exec.execute(task);
        }
        exec.shutdown();
    }

}
