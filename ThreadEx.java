/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class ThreadEx implements Runnable{
    
    Thread t;
    Thread arr[];
    int time[],process[];
    public ThreadEx() {
        t = new Thread(this);
        t.setName("Root");
        arr = new Thread[5];
        time = new int[5];
        process = new int[5];
        t.start();
    }

    @Override
    public void run() {
        
        for(int i=0;i<5;i++){
            arr[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    
                }
            });
            arr[i].setName("Child "+Integer.toString(i));
            System.out.println("Process "+arr[i].getName()+"  Created");
            if(i<3)
                time[i] = 180;
            else 
                time[i] = 200;
            process[i]  =i;
           
            
        }
        
    }
    
    
}
