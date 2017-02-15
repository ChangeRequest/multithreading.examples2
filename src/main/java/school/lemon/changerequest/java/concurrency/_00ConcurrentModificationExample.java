package school.lemon.changerequest.java.concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class _00ConcurrentModificationExample {

    public static void main(String[] args) {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>(Arrays.asList(1, 2, 3)));
        Iterator<Integer> iterator = list.iterator();
        list.add(4);
        System.out.println(iterator.next());
    }


}
