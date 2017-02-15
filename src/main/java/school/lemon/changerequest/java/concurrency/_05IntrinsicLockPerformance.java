package school.lemon.changerequest.java.concurrency;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class _05IntrinsicLockPerformance {

    private final Map<Integer, Integer> m = new TreeMap<Integer, Integer>();

    public synchronized Integer get(Integer key) {
        return m.get(key);
    }

    public synchronized Integer put(Integer key, Integer value) {
        return m.put(key, value);
    }

    public static void main(String[] args) throws InterruptedException {
        final int numWorkers = 20;
        final _05IntrinsicLockPerformance rwd = new _05IntrinsicLockPerformance();

        // reader
        Runnable r = new Runnable() {
            public void run() {
                Random r = new Random();
                for (int i = 0; i < 100_000; i++) {
                    rwd.get(r.nextInt(numWorkers - 1));
                }
            }
        };

        // writer
        Runnable w = new Runnable() {
            public void run() {
                Random r = new Random();
                for (int i = 0; i < 100_000; i++) {
                    rwd.put(r.nextInt(numWorkers - 1), r.nextInt(numWorkers - 1));
                }
            }
        };

        Thread writer = new Thread(w);
        writer.start();
        Thread[] readers = new Thread[numWorkers];
        for (int i = 0; i < readers.length; ++i) {
            readers[i] = new Thread(r);
            readers[i].start();
        }

        long startTime = System.currentTimeMillis();
        writer.join();
        for (Thread reader : readers) {
            reader.join();
        }
        System.out.println("Elapsed Time:" + (System.currentTimeMillis() - startTime));
    }
}
