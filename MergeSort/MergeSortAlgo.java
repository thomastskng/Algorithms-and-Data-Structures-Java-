/*
 *  javac MergeSortAlgo.java
 *  java MergeSortApp
 */

import java.util.*;
import java.io.*;
import java.math.*;

class MergeSortArray{
    protected int[] input_arr;
    protected int nElems;

    public MergeSortArray(int max){
        input_arr = new int[max];
        nElems = 0;
    }

    public void insert(int value){
        input_arr[nElems] = value;
        nElems++;
    }

    public void display(int j){
        for(int i = 0; i < nElems; i++){
            System.out.print(input_arr[i] + " ");
            if(j >= 0 && i == j){
                break;
            }
        }
        System.out.println("");
    }

    public void run(){
        long ans = mergeSort(0,nElems-1);
        System.out.println("Number of inversions = " + ans);
    }

    public long mergeSort(int p, int r){
        if(p < r){
            double dd = (double) (p + r)/2;
            int q = (int) Math.floor(dd);
            long x = mergeSort(p, q);
            long y = mergeSort(q+1, r);
            long z = merge(p,q,r);
            return x + y + z;
        }
        return 0;
    }

    public long merge(int p, int q, int r){

        int n1 = q - p + 1;
        int n2 = r - q;
        //create new arrays
        int[] L = new int[n1+2];
        int[] R = new int[n2+2];
        for(int i = 1; i <= n1; i++){
            L[i] = input_arr[p + i -1];
        }
        for(int j = 1; j <= n2; j++){
            R[j] = input_arr[q + j];
        }
        // sentinel at the end of sub-arrays
        //(therefore need to have "minus 1" in the numOfInversions below)
        L[n1+1] = Integer.MAX_VALUE;
        R[n2+1] = Integer.MAX_VALUE;
        int i = 1;
        int j = 1;

        long numOfInversions = 0;
        for(int k = p; k <= r; k++){
            if(L[i] <= R[j]){
                input_arr[k] = L[i];
                i++;
            } else{
                input_arr[k] = R[j];
                j++;
                // Key step below to count number of inversions
                numOfInversions+= (L.length-i-1);
            }
        }
        return numOfInversions;
    }
}

class MergeSortApp{
    public static void main(String[] args){
        try{
            MergeSortArray msa = read_file_and_populate("IntegerArray.txt");
            msa.run();
            //msa.display(msa.nElems);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static MergeSortArray read_file_and_populate(String file_loc) throws IOException{
        // Read input file
        FileInputStream fil = new FileInputStream(file_loc);
        BufferedReader br = new BufferedReader(new InputStreamReader(fil));

        MergeSortArray msa = new MergeSortArray(file_line_count(file_loc));
        String element = null;
        while( (element = br.readLine()) != null ){
            msa.insert(Integer.parseInt(element));
        }
        return msa;
    }

    public static int file_line_count(String file_loc) throws IOException{
        // Execute terminal command to count lines of file
        String[] command = new String[]{"/usr/bin/wc", "-l", file_loc};
        ProcessBuilder builder = new ProcessBuilder(command);
        // Redirect errorstream
        builder.redirectErrorStream(true);
        Process process = builder.start();
        // Read output on Terminal as our input stream
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        // Get line count for file
        String line = reader.readLine();
        String[] line_parts = line.split("\\s+");
        return Integer.parseInt(line_parts[line_parts.length - 2]);
    }
}
