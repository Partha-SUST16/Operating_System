import java.util.concurrent.ThreadLocalRandom;

class ReaderWriterProblemBuggy {
    private static int sharedResource = 0;
    private static int readerCount = 0;

    private static final Object lock = new Object();

    private static void startReading() {
        synchronized (lock) {
            readerCount++;
            if (readerCount == 1) {
                // First reader, block writers
                System.out.println("First reader arrives, blocking writers");
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000)); // Simulate slow readers
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void endReading() {
        synchronized (lock) {
            readerCount--;
            if (readerCount == 0) {
                // Last reader leaves, unblocking writers
                System.out.println("Last reader leaves, unblocking writers");
            }
        }
    }

    private static void startWriting() {
        System.out.println("Writer wants to write");
        synchronized (lock) {
            // Writers are not blocked by readers
        }
    }

    private static void endWriting() {
        System.out.println("Writer finished writing");
    }

    static class Reader extends Thread {
        @Override
        public void run() {
            while (true) {
                startReading();
                System.out.println("Reader reads: " + sharedResource);
                endReading();
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000)); // Sleep for random time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Writer extends Thread {
        @Override
        public void run() {
            while (true) {
                startWriting();
                sharedResource++;
                System.out.println("Writer writes: " + sharedResource);
                endWriting();
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000)); // Sleep for random time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        int numReaders = 5;
        int numWriters = 2;

        for (int i = 0; i < numReaders; i++) {
            new Reader().start();
        }

        for (int i = 0; i < numWriters; i++) {
            new Writer().start();
        }
    }
}


