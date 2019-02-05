/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Race_Condition;

import Race_Condition.Race_contion.First;
import Race_Condition.Race_contion.Second;

/**
 *
 * @author IICT
 */


public class Race_contion {
    
    boolean available ;
    
    int x;

    public Race_contion() {
        available = true;
        x = 5;
    }
    
    
    
    void acquire(){
        while(!available);
        available = false;
    }
    void release(){
        available = true;
    }
    
    class First implements Runnable{
        Thread t;
        public First(){
            t = new Thread();
            t.setName("First Thread");
//            t.start();
        }

        @Override
        public void run() {
            //System.out.println(t.getName()+" updated value of x: "+x);
            acquire();
            x++;
            System.out.println(t.getName()+" updated value of x: "+x);
            release();
        }
    }
    class Second implements Runnable{
        Thread t;
        public Second(){
            this.t = new Thread();
            t.setName("Second Thread");
//            t.start();
        }

        @Override
        public void run() {
             //System.out.println(t.getName()+" updated value of x: "+x);
            acquire();
            x--;
            System.out.println(t.getName()+" updated value of x: "+x);
            release();
        }
    }
    
}
class Main{
    public static void main(String[] args) {
        Race_contion rc = new Race_contion();
        
        
        First f = rc.new First();
        
        Second s = rc.new Second();
        System.out.println("Hi");
//        f.t.start();
//        s.t.start();

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
