package school.lemon.changerequest.java.concurrency;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class _07ReentrantRWLockPerformance {

    private final Map<Integer, Integer> m = new TreeMap<Integer, Integer>();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(false);
    //TODO: uncomment to test with fairness
    // private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public Integer get(Integer key) {
        r.lock();
        try {
            return m.get(key);
        } finally {
            r.unlock();
        }
    }

    public Integer put(Integer key, Integer value) {
        w.lock();
        try {
            return m.put(key, value);
        } finally {
            w.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int numWorkers = 20;
        final _07ReentrantRWLockPerformance rwd = new _07ReentrantRWLockPerformance();

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
