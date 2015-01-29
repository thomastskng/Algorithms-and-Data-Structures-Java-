/*
 *	javac RecursiveKnapsack.java
 *	java RecursiveKnapsack
 */

import java.io.*;
import java.util.*;
import java.math.*;

class RecursiveKnapsackAlgo{

    int[] v;
    int[] w;
    int W;
    int nElems;

    // improving run time by caching results, reduce number of recursive calls
    HashMap<String,Integer> cache = new HashMap<String,Integer>();

    public RecursiveKnapsackAlgo(int W, int numOfItems){
        v = new int[numOfItems + 1];
        w = new int[numOfItems + 1];
        this.W = W;
        nElems = 1;
        // initialization
        v[0] = 0;
        w[0] = 0;
    }

    public void insert(int v_i, int w_i){
        v[nElems] = v_i;
        w[nElems] = w_i;
        nElems++;
    }

    public int recursiveKnapsack(int i,int x){
        if(i < 0){
            return 0;
        }
        Integer sol = cache.get(i+":"+x);

        if(sol == null){
            if(w[i] > x){
                sol = recursiveKnapsack(i-1, x);
            } else{
                sol = Math.max(recursiveKnapsack(i-1, x), recursiveKnapsack(i-1, x-w[i]) + v[i]);
            }
            cache.put(i+":"+x, sol);
        }
        return sol;
    }
}

class RecursiveKnapsack{
    public static void main(String[] args) throws IOException{
        RecursiveKnapsackAlgo ka = read_file_and_populate("knapsack1.txt");
        System.out.println("optimal value = " + ka.recursiveKnapsack((ka.v.length-1), ka.W));

        ka = read_file_and_populate("knapsack_big.txt");
        System.out.println("optimal value (large knapsack problem) = " + ka.recursiveKnapsack((ka.v.length-1), ka.W));
    }

    public static RecursiveKnapsackAlgo read_file_and_populate(String file_loc) throws IOException{
        FileInputStream fil = new FileInputStream(file_loc);
        BufferedReader br = new BufferedReader(new InputStreamReader(fil));
        String element = br.readLine();
        String[] line = element.split("\\s+");
        RecursiveKnapsackAlgo ka =  new RecursiveKnapsackAlgo(Integer.parseInt(line[0]), Integer.parseInt(line[1]));;
        while( (element = br.readLine()) != null ){
            line = element.split("\\s+");
            ka.insert(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
        }
        return ka;
    }
}
