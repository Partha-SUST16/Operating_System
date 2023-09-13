import java.util.concurrent.Semaphore;

public class ReaderWriterProblems {
    private static Semaphore mutex = new Semaphore(1); // Binary semaphore for mutual exclusion
    private static Semaphore w = new Semaphore(1); // Controls access for writers
    private static int readCount = 0; // Number of current readers
    private static boolean terminate = false; // Termination signal

    private static int sharedData = 0;

    public static void main(String[] args) {
        // Create reader and writer threads
        Thread[] readerThreads = new Thread[5]; // 5 reader threads
        Thread[] writerThreads = new Thread[2]; // 2 writer threads

        for (int i = 0; i < readerThreads.length; i++) {
            readerThreads[i] = new Thread(ReaderWriterProblems::reader);
            readerThreads[i].start();
        }

        for (int i = 0; i < writerThreads.length; i++) {
            writerThreads[i] = new Thread(ReaderWriterProblems::writer);
            writerThreads[i].start();
        }

        // Simulate program running for some time
        try {
            Thread.sleep(5000); // Run for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Set the termination signal
        terminate = true;

        // Wait for all threads to finish
        for (Thread thread : readerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Thread thread : writerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Parent quitting");
    }

    public static void reader() {
        while (!terminate) {
            try {
                mutex.acquire(); // Acquire the mutex to update readCount

                readCount++;

                if (readCount == 1) {
                    w.acquire(); // Acquire the w semaphore to block writers
                }

                mutex.release(); // Release the mutex

                // Read from the shared resource
                System.out.println("Reader is reading: " + sharedData);

                mutex.acquire(); // Acquire the mutex to update readCount

                readCount--;

                if (readCount == 0) {
                    w.release(); // Release the w semaphore to allow writers
                }

                mutex.release(); // Release the mutex

                // Continue reading or allow other readers to read
                Thread.sleep((int) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writer() {
        while (!terminate) {
            try {
                w.acquire(); // Acquire the w semaphore to write

                sharedData++;
                System.out.println("Writer is writing: " + sharedData);

                w.release(); // Release the w semaphore

                // Continue writing or allow other writers to write
                Thread.sleep((int) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
