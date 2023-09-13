public class Race_condition {
    boolean available;

    int x;

    public Race_condition() {
        available = true;
        x = 5;
    }


    void acquire() {
        while (!available) ;
        available = false;
    }

    void release() {
        available = true;
    }

    class First implements Runnable {
        Thread t;

        public First() {
            t = new Thread();
            t.setName("First Thread");
            t.start();
        }

        @Override
        public void run() {
            //System.out.println(t.getName()+" updated value of x: "+x);
            acquire();
            x++;
            System.out.println(t.getName() + " updated value of x: " + x);
            release();
        }
    }

    class Second implements Runnable {
        Thread t;

        public Second() {
            this.t = new Thread();
            t.setName("Second Thread");
            t.start();
        }

        @Override
        public void run() {
            //System.out.println(t.getName()+" updated value of x: "+x);
            acquire();
//            x--;
            System.out.println(t.getName() + " existing value of x: " + x);
            release();
        }
    }

}

class Main {
    public static void main(String[] args) {
        Race_condition rc = new Race_condition();


        Race_condition.First f = rc.new First();

        Race_condition.Second s = rc.new Second();
        System.out.println("Hi");


        for(int i = 0;i<10;i++){
            s.run();
            f.run();
        }

        try {
            f.t.join();
            s.t.join();
        } catch (Exception e) {
        }
    }
}
