package school.lemon.changerequest.java.concurrency;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class _15ForkJoinWithRecursiveTaskExample {

    private static final String DIR = "src";

    public static void main(final String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        long start = System.nanoTime();
        long size = pool.invoke(new SizeOfFileTask(new File(DIR)));
        long taken = System.nanoTime() - start;
        System.out.println(String.format("Size of '%s': %s bytes (in %s nano)", DIR, size, taken));
        pool.shutdown();
    }

    private static class SizeOfFileTask extends RecursiveTask<Long> {

        private final File file;

        private SizeOfFileTask(final File file) {
            this.file = Objects.requireNonNull(file);
        }

        @Override
        protected Long compute() {
            System.out.println("Computing size of: " + file.getPath());
            if (file.isFile()) {
                return file.length();
            }
            long size = 0;
            File[] children = file.listFiles();
            if (children == null) {
                return size;
            }
            List<SizeOfFileTask> tasks = new ArrayList<>();
            for (File child : children) {
                SizeOfFileTask task = new SizeOfFileTask(child);
                task.fork();
                tasks.add(task);
            }
            for (SizeOfFileTask task : tasks) {
                size += task.join();
            }
            return size;
        }
    }

}
