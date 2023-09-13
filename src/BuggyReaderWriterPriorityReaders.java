//class SharedResource {
//    private int data = 0;
//    private int readerCount = 0;
//
//    public int readData() {
//        readerCount++;
//        return data;
//    }
//
//    public void writeData(int newValue) {
//        data = newValue;
//    }
//
//    public void doneReading() {
//        readerCount--;
//    }
//}
//
//class Reader extends Thread {
//    private SharedResourceFixed resource;
//
//    public Reader(SharedResourceFixed resource) {
//        this.resource = resource;
//    }
//
//    public void run() {
//        int value = 0;
//        try {
//            value = resource.readData();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("Reader read: " + value);
//        resource.doneReading();
//    }
//}
//
//class Writer extends Thread {
//    private SharedResourceFixed resource;
//    private int newValue;
//
//    public Writer(SharedResourceFixed resource, int newValue) {
//        this.resource = resource;
//        this.newValue = newValue;
//    }
//
//    public void run() {
//        try {
//            resource.writeData(newValue);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("Writer wrote: " + newValue);
//    }
//}
//
//public class BuggyReaderWriterPriorityReaders {
//    public static void main(String[] args) {
//        SharedResourceFixed resource = new SharedResourceFixed();
//
//        // Create reader threads
//        for (int i = 0; i < 3; i++) {
//            Thread readerThread = new Reader(resource);
//            readerThread.start();
//        }
//
//        // Create writer threads
//        for (int i = 0; i < 2; i++) {
//            Thread writerThread = new Writer(resource, i + 1);
//            writerThread.start();
//        }
//    }
//}
//
////
//class SharedResourceFixed {
//    private int data = 0;
//    private int readerCount = 0;
//    private boolean writerWriting = false;
//
//    public synchronized int readData() throws InterruptedException {
//        while (writerWriting) {
//            wait();
//        }
//        readerCount++;
//        return data;
//    }
//
//    public synchronized void doneReading() {
//        readerCount--;
//        if (readerCount == 0) {
//            notifyAll(); // Notify waiting writers if no readers are active
//        }
//    }
//
//    public synchronized void writeData(int newValue) throws InterruptedException {
//        while (readerCount > 0 || writerWriting) {
//            wait();
//        }
//        writerWriting = true;
//        data = newValue;
//        writerWriting = false;
//        notifyAll(); // Notify waiting threads
//    }
//}
