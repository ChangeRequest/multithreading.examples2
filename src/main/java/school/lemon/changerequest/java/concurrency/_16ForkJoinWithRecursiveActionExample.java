package school.lemon.changerequest.java.concurrency;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicLong;

public class _16ForkJoinWithRecursiveActionExample {

    private static final String DIR = "src";

    public static void main(final String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        AtomicLong result = new AtomicLong(0);
        long start = System.nanoTime();
        pool.invoke(new SizeOfFileAction(new File(DIR), result));
        long size = result.get();
        long taken = System.nanoTime() - start;
        System.out.println(String.format("Size of '%s': %s bytes (in %s nano)", DIR, size, taken));
        pool.shutdown();
    }

    private static class SizeOfFileAction extends RecursiveAction {

        private final File file;
        private final AtomicLong sizeAccumulator;

        private SizeOfFileAction(final File file, final AtomicLong sizeAccumulator) {
            this.file = Objects.requireNonNull(file);
            this.sizeAccumulator = Objects.requireNonNull(sizeAccumulator);
        }

        @Override
        protected void compute() {
            System.out.println("Computing size of: " + file.getPath());
            if (file.isFile()) {
                sizeAccumulator.addAndGet(file.length());
                return;
            }
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    ForkJoinTask.invokeAll(new SizeOfFileAction(child, sizeAccumulator));
                }
            }
        }
    }

}
