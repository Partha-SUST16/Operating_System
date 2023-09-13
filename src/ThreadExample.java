public class ThreadExample {
    public static void main(String[] args) {
        final int NUM_THREADS = 4;

        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                System.out.println("Hello Thread: "+ finalI);
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
