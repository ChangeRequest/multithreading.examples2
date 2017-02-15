package school.lemon.changerequest.java.concurrency;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class _02ConcurrentHashMapPerformance {

    private Map<Integer, Integer> queryCounts = Collections.synchronizedMap(new HashMap<Integer, Integer>(1000));
    //private Map<Integer, Integer> queryCounts = new ConcurrentHashMap<Integer, Integer>(1000);  // concurrent

    private void incrementCount(Integer q) {
        Integer cnt = queryCounts.get(q);
        if (cnt == null) {
            queryCounts.put(q, 1);
        } else {
            queryCounts.put(q, cnt + 1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int numWorkers = 20;
        _02ConcurrentHashMapPerformance t = new _02ConcurrentHashMapPerformance();

        Runnable r = new Runnable() {
            public void run() {
                Random r = new Random();
                for (int i = 0; i < 100000; i++) {
                    t.incrementCount(r.nextInt(numWorkers - 1));
                }
            }
        };

        Thread[] workers = new Thread[numWorkers];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Thread(r);
            workers[i].start();
        }

        long startTime = System.currentTimeMillis();
        for (Thread worker : workers) {
            worker.join();
        }
        System.out.println("Elapsed Time:" + (System.currentTimeMillis() - startTime));
    }

}
