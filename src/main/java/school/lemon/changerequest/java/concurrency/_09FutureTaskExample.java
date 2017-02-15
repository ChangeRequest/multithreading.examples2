package school.lemon.changerequest.java.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class _09FutureTaskExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> c = new Callable<String>() {
            public String call() {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ("Result of call()!");
            }
        };
        FutureTask<String> future = new FutureTask<String>(c);
        new Thread(future).start();
        System.out.println("FutureTask launched.");

        System.out.println("main is doing something else now.");

        System.out.println(future.get());
        System.out.println("Done.");
    }

}
