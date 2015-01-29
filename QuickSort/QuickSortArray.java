/*
 *  javac QuickSortArray.java
 *  java QuickSortArrayApp
 */

import java.util.*;
import java.io.*;
import java.math.*;


class QuickSortArray extends ArrayClass{

    int numOfComparisons = 0;
    // multiple constructor(){
    public QuickSortArray(){
        super();
    }

    public QuickSortArray(int max){
        super(max);
    }

    // initial call
    public void quickSortAlgo(Choice choice){
        quickSort(input_array, 0, nElems-1, choice);
        String str = "(First element as pivot)";
        if(choice == Choice.LAST){
            str = "(Last element as pivot)";
        } else if(choice == Choice.MEDIAN){
            str = "(Median of the first, middle, last elements of the given array)";
        }
        System.out.println("Number of Comparisons " + str +  " = " + numOfComparisons);
    }

    public void quickSort(int[] input_array, int p, int r, Choice choice){
        if(p < r){
            int q = partition(input_array, p, r, choice);
            quickSort(input_array, p, q - 1, choice);
            quickSort(input_array, q + 1, r, choice);
        } else{
            return;
        }
    }

    public int partition(int[] input_array, int l, int r, Choice choice){
        // choose pivot here depending on choice
        if(choice == Choice.LAST){
            // use last element as pivot
            swap(r,l);
        }

        // use Randomized selection method to find median of three pivot rule;
        int mid = l + (r - l) /2;

        if(choice == Choice.MEDIAN){
            int index_of_median = findMedian(l, mid, r);
            swap(index_of_median,l);
        }

        // use first element as pivot
        int x = input_array[l];

        int i = l + 1;
        for(int j = l + 1; j <= r ; j++){
            if(input_array[j] <= x){
                swap(i, j);
                i++;
            }
        }
        numOfComparisons += r-l;
        swap(l,i-1);
        return i-1;
    }


    /*
     * This method calls Randomization Selection method to find the
     * the second smallest number in an array of length 3, i.e. MEDIAN
     *
     * Return index of median
     */
    public int findMedian(int l, int m, int u){
        int[] A = new int[] {input_array[l] ,input_array[m], input_array[u]};
        int ans = randomized_select(A,0,2,2);

        // return index of the median value
        if(input_array[l] == ans){
            return l;
        } else if(input_array[m] == ans){
            return m;
        }else if(input_array[u] == ans){
            return u;
        }else{
            return -1;
        }
    }

    // Randomized - Selection method
    public int randomized_select(int[] A, int p, int r, int i){
        if(p == r){
            return A[p];
        }
        // Generate a random pivot between range(p,r) inclusive.
        int q = randomized_partition(A, p, r);
        int k = q - p + 1;
        if(i == k){		// the pivot value is the answer
            return A[q];
        } else if(i < k){
            return randomized_select(A, p, q-1,i);
        } else{
            return randomized_select(A, q+1, r, i-k);
        }
    }

    // randomized partition method - generate random pivot and partition
    public int randomized_partition(int[] A, int p, int r){
        int i = p + (int)(Math.random() * ((r-p) + 1));
        rswap(A,r,i);
        return rPartition(A,p,r);
    }

    // Swap method only for randomized-selection method
    public void rswap(int[] A, int i, int j){
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    // partition method for randomized-selection method
    public int rPartition(int[] A, int l, int r){
        int x = A[r];
        int i = l-1;
        for(int j = l; j <= r-1 ; j++){
            if(A[j] <= x){
                i++;
                rswap(A,i, j);
            }
        }
        rswap(A,i+1,r);
        return i+1;
    }

}

class QuickSortArrayApp{
    public static void main(String[] args) throws IOException{
        try{
            Choice [] choices = new Choice[]{Choice.FIRST, Choice.LAST, Choice.MEDIAN};
            String filepath = "QuickSort.txt";

            int len = new ArrayClass().file_line_count(filepath);
            for(int i = 0; i < choices.length; i++){
                QuickSortArray input_array = new QuickSortArray(len);
                input_array.read_file_and_populate(filepath);
                input_array.quickSortAlgo(choices[i]);

            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
