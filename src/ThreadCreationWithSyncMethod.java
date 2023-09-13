public class ThreadCreationWithSyncMethod {
    private static class PrintToTenTask implements Runnable {
        private int threadNumber;

        public PrintToTenTask(int threadNumber) {
            this.threadNumber = threadNumber;
        }

        @Override
        public void run() {
            printToTen(threadNumber);
        }
    }

    // Use synchronized later
    private static void printToTen(int threadNumber) {
        for (int i = 0; i < 10; i++) {
            System.out.println("ThreadNo: " + threadNumber + " i: " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Runnable runnable = new PrintToTenTask(i);
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }
}
