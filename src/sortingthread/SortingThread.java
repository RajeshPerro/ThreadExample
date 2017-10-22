/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingthread;

import java.util.Scanner;

/**
 *
 * @author rajesh
 */
class firstPart extends Thread
{
    private int arrayLen;
    private int [] fArray;
    
    public firstPart(int [] array, int Length) 
    {
        this.fArray = array;
        this.arrayLen = Length;
    }
    
    public  int [] bubbleSort()
    {
        int len = arrayLen;
        int temp = 0;
        for (int i = 0; i < len; i++)
        {
            for (int j = 1; j < (len-i); j++) 
            {
                if (fArray[j-1] > fArray[j]) 
                {
                    temp = fArray[j - 1];
                    fArray[j - 1] = fArray[j];
                    fArray[j] = temp;
                    
                }
            }
            
        }
//        System.out.println("Sorted Array : ");
//        for (int i = 0; i < len; i++) 
//        {
//            System.out.println(" "+numArray[i]);
//        }
     return fArray;
    }
    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
       bubbleSort();
         System.out.println("Sub Thread : I am done!");
       //try{Thread.sleep(3000);}catch(Exception e){}
    }
    
}

class secondPart extends Thread
{
     int [] lArray;
     private int arrayLen;
    public secondPart(int [] array, int Length) 
    {
        this.lArray = array;
        this.arrayLen = Length;
    }
    public  int [] bubbleSort_last()
    {
        int len = arrayLen;
        //int totalLength = lArray.length;
        //System.out.println("sLen = "+len+"Total array len = "+totalLength);
        int temp = 0;
        for (int i = 0; i < len; i++)
        {
            for (int j = 1; j < (len-i); j++) 
            {
                if (lArray[j-1] > lArray[j]) 
                {
                    temp = lArray[j - 1];
                    lArray[j - 1] = lArray[j];
                    lArray[j] = temp;
                    
                }
            }
            
        }

     return lArray;
    }
    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
         bubbleSort_last();
        System.out.println("Sub Thread : I am done!");
       //try{Thread.sleep(3000);}catch(Exception e){}
    }
    
}

public class SortingThread {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        int SIZE;
        int fLen, sLen ;
        
        
        
        int [] firstPartSorted;
        int [] lastPartSorted;
       
        Scanner sc = new Scanner(System.in);
        System.out.println("Define your Array Size: ");
        SIZE = sc.nextInt();
        int [] UnsortedArray = new int [SIZE];
        int [] sortedArray = new int [SIZE];
        int cnt=0;
        MyMergeSort ms = new MyMergeSort();
        
        //separating array
        fLen = SIZE/2;
        sLen = SIZE-fLen;
        
        int [] firstPartUnSorted = new int [fLen+1];
        int [] lastPartUnSorted = new int [sLen+1];
        
        System.out.println("Enter your array elements : ");
        for (int i = 0; i < SIZE; i++) {
            UnsortedArray[i] = sc.nextInt();
        }
        
        for (int i = 0; i < fLen; i++) {
            firstPartUnSorted[i] = UnsortedArray[i]; 
        }
        
        
        for (int j=0, i = fLen; i < SIZE; i++) 
        {
            //System.out.println("data in unSorted array : "+UnsortedArray[i] +"Position : "+i);
            lastPartUnSorted[j++] = UnsortedArray[i];
            
        }
        
       
        
        
        //first thread 
        firstPart fp = new firstPart(firstPartUnSorted,fLen);
        secondPart sp = new secondPart(lastPartUnSorted, sLen);
        fp.start();
        sp.start();
        firstPartSorted = fp.bubbleSort();
        lastPartSorted = sp.bubbleSort_last();
        
        fp.join();
        sp.join();
        System.out.println("Main Thread : Okay I am starting!");
//        System.out.println("Sorted Array : ");
//        for (int i = 0; i < fLen; i++) 
//        {
//            System.out.println(" "+firstPartSorted[i]);
//        }
        
//        System.out.println("Sorted Array Last part : ");
//        for (int i = 0; i < sLen; i++) 
//        {
//            System.out.println(" "+lastPartSorted[i]);
//        }

       for( int m=0; m < fLen; m++)
       {
           sortedArray[cnt++]=firstPartSorted[m];
       }
       for(int m1 = 0; m1<sLen ; m1++)
       {
           sortedArray[cnt++]=lastPartSorted[m1];
       }
       ms.sort(sortedArray);
       System.out.println("Final Sorted array");
       for(int ar:sortedArray)
       {
           System.out.println(ar);
           System.out.println(" ");
           Thread.sleep(1000);
       }
    }
    
}
