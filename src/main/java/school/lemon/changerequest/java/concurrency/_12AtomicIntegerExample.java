package school.lemon.changerequest.java.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class _12AtomicIntegerExample {

    private final static int NUM_THREADS = 10;

    interface Counter {
        void increment();
        void decrement();
        int value();
    }

    private final static Counter unsynchronizedCounter = new Counter() {
        private int c = 0;
        public void increment() {
            c++;
        }
        public void decrement() {
            c--;
        }
        public int value() {
            return c;
        }
    };

    private final static Counter synchronizedCounter = new Counter() {
        private int c = 0;
        public synchronized void increment() { c++; }
        public synchronized void decrement() {
            c--;
        }
        public synchronized int value() {
            return c;
        }
    };

    private final static Counter atomicCounter = new Counter() {
        private AtomicInteger c = new AtomicInteger(0);
        public void increment() {
            c.incrementAndGet();
        }
        public void decrement() {
            c.decrementAndGet();
        }
        public int value() {
            return c.get();
        }
    };

    private final static class CounterRunner implements Runnable {
        private Counter counter;
        public CounterRunner(Counter counter) { this.counter = counter; }
        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) { counter.increment(); }
            for (int i = 0; i < 100000; i++) { counter.decrement(); }
        }
        public Counter getCounter() { return counter; }
    }

    public static void main(String[] args) throws InterruptedException {
        executeCounters("unsynchronized", new CounterRunner(unsynchronizedCounter));
        executeCounters("synchronized", new CounterRunner(synchronizedCounter));
        executeCounters("atomic", new CounterRunner(atomicCounter));
    }

    private static void executeCounters(String name, CounterRunner counterRunner) throws InterruptedException {
        System.out.printf("Executing the %s counter\n", name);
        long sTime = System.currentTimeMillis();
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Thread(counterRunner);
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("Total time:" + (System.currentTimeMillis() - sTime));
        System.out.println("Final Value:" + counterRunner.getCounter().value());
    }

}
