package school.lemon.changerequest.java.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _14ExecutorServiceShutdownExample {

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(2);
        Runnable task = new Runnable() {
            public void run() {
                System.out.println("Task executing");
            }
        };
        exec.execute(task);
        exec.shutdown();
        exec.execute(task);
    }

}
