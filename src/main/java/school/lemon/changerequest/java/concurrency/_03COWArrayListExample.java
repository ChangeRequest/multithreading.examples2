package school.lemon.changerequest.java.concurrency;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class _03COWArrayListExample {

    public static void main(String[] args) {
        List<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> iterator = list.iterator();
        list.add(4);
        System.out.println(iterator.next());
    }

}
