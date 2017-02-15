package school.lemon.changerequest.java.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Sample program illustrating that trying to lock a Lock object leaves thread in WAITING state,
 * rather than BLOCKED state.  The latter is reserved for monitor locks only.
 */
public class _04LockExample {

    private static Lock l = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            public void run() {
                l.lock();
            }
        };

        l.lock();
        t.start();
        Thread.sleep(50);
        System.out.println(t.getState());
    }

}
