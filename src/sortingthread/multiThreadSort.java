/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingthread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author rajesh
 */
public class multiThreadSort implements Runnable
{
   
    List<Integer> list = new ArrayList<Integer>();
    public static List<Integer>finalList = new ArrayList<Integer>();
    static Semaphore semaphore = new Semaphore(1);
    String threadName;
    String x = "FT" , y = "LT";

    public multiThreadSort() {
    }
    
    public multiThreadSort(List<Integer> L, String str) 
    {
        this.list = L;
        this.threadName = str;
    }
    
    public synchronized void sortWrite() {
        synchronized (this) {
            try {
                semaphore.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(multiThreadSort.class.getName()).log(Level.SEVERE, null, ex);
            }

            int len = list.size();
            int j;
            for (int i = 0; i < list.size(); i++) {

                int key = list.get(i);
                j = i - 1;

                //if(i>10000)
                if (finalList.size() != 0) {
                    finalList.add(key);
                    while (j >= 0 && finalList.get(j) > key) {
                        //System.out.println("I am here && j = "+j+" Key = "+key);
                        finalList.set(j + 1, finalList.get(j));
                        finalList.set(j, key);
                        j--;
                    }
                } else {
                    finalList.add(key);
                }
            }
        }
        semaphore.release();

    }

  

    @Override
    public void run() 
    {
       sortWrite();
        for (Integer in : list) 
        {
            System.out.println(""+threadName+"  "+ "Item in List : "+in);
        }
    }
    
    
    public static void main(String args[]) throws InterruptedException
    {
        int size,num;
        Random random = new Random();
        
        CopyOnWriteArrayList<Integer> arrList = new CopyOnWriteArrayList<>();

        System.out.println("Define your Input Size : ");
        Scanner sc = new Scanner(System.in);
        size = sc.nextInt();
        System.out.println("Input : ");
        for (int i = 0; i < size; i++)
        {
            num = Generate_Random_Int(10, 100, random);
            arrList.add(num);
        }
        int half = size/2;
        List<Integer>firstHalf = arrList.subList(0,half);
        List<Integer>LastHalf = arrList.subList(half, size);
        
        System.out.println("First Part : "+firstHalf);
        System.out.println("Last Part : "+LastHalf);

      
    Thread t1 = new Thread(new multiThreadSort(firstHalf,"FT"));
    Thread t2 = new Thread(new multiThreadSort(LastHalf, "LT"));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    
//        Thread test = new Thread(new multiThreadSort(arrList,"all"));
//        test.start();
//        test.join();
        
        System.out.println("Final List : "+finalList);
        System.out.println("List Size : "+arrList.size());
        
       
    }
   
    
    
    public static int Generate_Random_Int(int aStart, int bEnd,Random aRandom)
    {
        
        long range = (long)bEnd - (long)aStart + 1;
        long fraction = (long)(range * aRandom.nextDouble());
        int randomNumber =  (int)(fraction + aStart); 
        
    return randomNumber;
    }
}
