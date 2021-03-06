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
public class threadTest {

    static Semaphore semaphore = new Semaphore(1);
    public static List<Integer> finalList = new ArrayList<Integer>();
    //public static int trun=0;

    static class myThread extends Thread {

        List<Integer> list = new ArrayList<Integer>();

        String threadName;

        public myThread(List<Integer> L, String str) {
            this.list = L;
            this.threadName = str;

        }

        public void run() {
            try {
                semaphore.acquire();
                System.out.println(threadName + " : got the permit!");
                int len = list.size();

                for (int i = 0; i < list.size(); i++) {

                    //finalList.add(list.get(i));
                    //System.out.println("Data now : " + finalList);

                    int key = list.get(i);

                    //if(i>10000)
                    if (finalList.size() != 0) {

                        //finalList.add(key);
                        int flag = 0;
                        for (int j = finalList.size(); j > 0; j--) {
                            if (finalList.get(j - 1) > key) {
                                //System.out.println("I am here && j = " + j + " Key = " + key);
                                if (j == finalList.size()) 
                                {
                                    finalList.add(j, finalList.get(j - 1));
                                } 
                                else 
                                {
                                    finalList.set(j, finalList.get(j - 1));
                                }
                                finalList.set(j - 1, key);

                                flag = 1;
                            }

                        }
                        //System.out.println("array line : " + finalList);
                        if (flag == 0) {
                            finalList.add(key);
                        }
                    } else {
                        finalList.add(key);
                    }
                }

                Thread.sleep(1000);
                System.out.println(threadName + " : releasing lock...");
                semaphore.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(threadTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void main(String args[]) throws InterruptedException {
        int size, num;

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
//        arrList.add(6);
//        arrList.add(7);
//        arrList.add(8);
//        arrList.add(9);
//        arrList.add(9);

        int half = size / 2;
        List<Integer> firstHalf = arrList.subList(0, half);
        List<Integer> LastHalf = arrList.subList(half, size);
        System.out.println("First Part : " + firstHalf);
        System.out.println("Last Part : " + LastHalf);

        myThread t1 = new myThread(firstHalf, "FirstThread");
        t1.start();
        myThread t2 = new myThread(LastHalf, "SecondThread");
        t2.start();
        t1.join();
        t2.join();

//        myThread test = new myThread(arrList, "singleThread");
//        test.start();
//        test.join();
        System.out.println("Final List : " + finalList);
    }

    public static int Generate_Random_Int(int aStart, int bEnd, Random aRandom) {

        long range = (long) bEnd - (long) aStart + 1;
        long fraction = (long) (range * aRandom.nextDouble());
        int randomNumber = (int) (fraction + aStart);

        return randomNumber;
    }
}
