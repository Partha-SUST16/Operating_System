/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class Main {
    static void findWaitingTime(int processes[], int n, int bt[], int wt[], int quantum,Thread[] arr){
        int rem_bt[] = new int[n];
        for(int i=0;i<n;i++)
            rem_bt[i] = bt[i];
        int t = 0;
        while(true){
            boolean done = true;
            for(int i=0;i<n;i++)
            {
                if(rem_bt[i]>0)
                {
                    done = false;
                    System.out.println(arr[i].getName()+" exicuting ");
                    if(rem_bt[i]>quantum)
                    {
                      t+=quantum;
                      rem_bt[i]-=quantum;
                    }
                    else {
                        t+=rem_bt[i];
                        wt[i] = t-bt[i];
                        rem_bt[i] = 0;
                        System.out.println(arr[i].getName()+" completed ");
                    }
                }
            }
            if(done)
                break;
        }
    }
    
    static void findTurnAroundTime(int process[],int n,int bt[],int wt[],int tat[]){
        for(int i=0;i<n;i++)
        {
            tat[i] = bt[i]+wt[i];
        }
    }
   static void findAvgTime(int process[],int n,int brust[],int quantam,Thread[] arr)
    {
        int wt[] = new int[n],tat[]=new int[n];
        findWaitingTime(process, n, brust, wt, quantam,arr);
        findTurnAroundTime(process, n, brust, wt, tat);
        
        int tot = 0;
        int tau = 0;
         System.out.println("Name      " + " Burst time " + 
                      " Waiting time " + " Turn around time"); 
        for(int i=0;i<n;i++){
            tot+=brust[i];
            tau+=tat[i];
            System.out.println(" " + arr[i].getName() + "\t" + brust[i] +"\t " + 
                              wt[i] +"\t\t " + tat[i]);
        }
        System.out.println("Average waiting time = " + 
                          (float)tot / (float)n); 
        System.out.println("Average turn around time = " + 
                           (float)tau / (float)n); 
        
    }
   
    public static void main(String[] args) {
        ThreadEx te = new ThreadEx();
        te.run();
        findAvgTime(te.process, 5, te.time, 50,te.arr);
    }
   
       
   }

