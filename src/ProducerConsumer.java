import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;

public class ProducerConsumer {

    private static final int BUF_SIZE = 3;
    private static int[] buffer = new int[BUF_SIZE];
    private static int add = 0;
    private static int rem = 0;
    private static int num = 0;

    private static Lock lock = new ReentrantLock();
    private static Condition cCons = lock.newCondition();
    private static Condition cProd = lock.newCondition();

    public static void main(String[] args) {
        Thread producerThread = new Thread(ProducerConsumer::producer);
        Thread consumerThread = new Thread(ProducerConsumer::consumer);

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Parent quitting");
    }

    public static void producer() {
        for (int i = 1; i <= 20; i++) {
            lock.lock();
            try {
                while (num == BUF_SIZE) {
                    cProd.await();
                }

                buffer[add] = i;
                add = (add + 1) % BUF_SIZE;
                num++;

                cCons.signal();
                System.out.println("Producer: inserted " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        System.out.println("Producer quitting");
    }

    public static void consumer() {
        while (true) {
            lock.lock();
            try {
                while (num == 0) {
                    cCons.await();
                }

                int value = buffer[rem];
                rem = (rem + 1) % BUF_SIZE;
                num--;

                cProd.signal();
                System.out.println("Consumer: consumed " + value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}

class ProducerConsumerUsingSemaphore {

    private static final int BUF_SIZE = 3;
    private static int[] buffer = new int[BUF_SIZE];
    private static int add = 0;
    private static int rem = 0;

    private static Semaphore mutex = new Semaphore(1); // Binary semaphore for mutual exclusion
    private static Semaphore empty = new Semaphore(BUF_SIZE); // Represents empty slots
    private static Semaphore full = new Semaphore(0); // Represents filled slots

    private static final int TOTAL_PRODUCE = 25;
    private static final int TOTAL_CONSUME = 25;

    public static void main(String[] args) {
        Thread producerThread = new Thread(ProducerConsumerUsingSemaphore::producer);
        Thread consumerThread = new Thread(ProducerConsumerUsingSemaphore::consumer);

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Parent quitting");
    }

    public static void producer() {
        for (int i = 0; i < TOTAL_PRODUCE; i++) {
            try {
                empty.acquire(); // Wait until there's an empty slot
                mutex.acquire(); // Acquire the lock

                /* Perform the insert operation in a slot */
                buffer[add] = (int) (Math.random() * 100); // Simulated data insertion
                System.out.println("Producer: inserted " + buffer[add]);
                add = (add + 1) % BUF_SIZE;

                mutex.release(); // Release the lock
                full.release(); // Increment 'full'
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void consumer() {
        int consumedCount = 0;
        while (consumedCount < TOTAL_CONSUME) {
            try {
                full.acquire(); // Wait until there's a full slot
                mutex.acquire(); // Acquire the lock

                /* Perform the remove operation from a slot */
                int value = buffer[rem];
                System.out.println("Consumer: consumed " + value);
                rem = (rem + 1) % BUF_SIZE;
                consumedCount++;

                mutex.release(); // Release the lock
                empty.release(); // Increment 'empty'
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

