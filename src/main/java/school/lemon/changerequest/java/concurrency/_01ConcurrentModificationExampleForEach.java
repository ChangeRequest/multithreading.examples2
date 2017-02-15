package school.lemon.changerequest.java.concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

public class _01ConcurrentModificationExampleForEach {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>(Arrays.asList(1, 2, 3)));
        ListWriter listWriter = new ListWriter(list);
        listWriter.start();
        try {
            Thread.sleep(1000);
            for (Integer i : list) {
                System.out.println(i);
            }
        } catch (ConcurrentModificationException ex) {
            ex.printStackTrace();
            listWriter.interrupt();
        }
    }

    private static class ListWriter extends Thread {

        private List<Integer> list;

        private ListWriter(List<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            Random random = new Random();
            while (!isInterrupted()) {
                list.add(random.nextInt());
            }
        }
    }

}
