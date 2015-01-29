/*
 *	javac Knapsack.java
 *	java Knapsack
 */

import java.io.*;
import java.util.*;
import java.math.*;

class KnapsackAlgo{

    int[] v;
    int[] w;
    int[][] A;
    int W;
    int nElems;

    public KnapsackAlgo(int W, int numOfItems, boolean big){
        v = new int[numOfItems + 1];
        w = new int[numOfItems + 1];
        if(big == true){
            A = new int[2][W+1];
        } else{
            A = new int[v.length][W + 1];
        }
        this.W = W;
        nElems = 1;
    }


    public void insert(int v_i, int w_i){
        v[nElems] = v_i;
        w[nElems] = w_i;
        nElems++;
    }

    public void display_v(){
        for(int i = 1; i < v.length; i++){
            System.out.print(v[i] + " ");
        }
        System.out.println("");
    }

    public void display_w(){
        for(int x = 1; x < w.length; x++){
            System.out.print(w[x] + " ");
        }
        System.out.println("");
    }

    public int[][] solve(){
        for(int x = 0; x < A[0].length; x++){
            A[0][x] = 0;
        }
        int max = 0;
        for(int i = 1; i < A.length; i++){
            for(int x = 0; x < A[0].length; x++){
                if(w[i] > x){
                    A[i][x] = A[i-1][x];
                } else{
                    //System.out.println("v[i] = " + v[i]);
                    A[i][x] = Math.max( A[i-1][x] , A[i-1][x - w[i]] + v[i]);
                }
                if(A[i][x] > max){
                    max = A[i][x];
                }
            }
        }
        System.out.println("Max = " + max);
        return A;
    }

    // for knapsack_big problem, only have A[0] and A[1], since only these 2 columns are relevant
    public int[][] solveBig(){
        for(int x = 0; x < A[0].length; x++){
            A[0][x] = 0;
        }
        int max = 0;
        for(int i = 1; i < nElems; i++){
            for(int x = 0; x < A[0].length; x++){
                int j = 0;
                if(w[i] > x){
                    A[1][x] = A[j][x];
                } else{
                    A[1][x] = Math.max(A[j][x], A[j][x-w[i]] + v[i]);
                }
                if(A[1][x] > max){
                    max = A[1][x];
                }
            }
            // copy A[1] to A[0]
            for(int k = 0; k < A[0].length; k++){
                A[0][k] = A[1][k];
            }
        }
        System.out.println("Max (large knapack problem) = " + max);
        return A;
    }

    public void display(){
        for(int x = A[0].length- 1; x >= 0; x--){
            for(int i = 0; i < A.length; i++){
            //System.out.print(A[i][x] + " ");
                System.out.print(A[i][x] + " ");
            }
            System.out.println("");
        }
    }

}

class Knapsack{
    public static void main(String[] args){
        try{
            KnapsackAlgo ka = read_file_and_populate("knapsack1.txt", false);
            ka.solve();
            //ka.solve();
            //ka.display();
            //ka.display_v();
            //ka.display_w();
            KnapsackAlgo ka_big = read_file_and_populate("knapsack_big.txt", true);
            ka_big.solveBig();
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    public static KnapsackAlgo read_file_and_populate(String file_loc, boolean big) throws IOException{
        FileInputStream fil = new FileInputStream(file_loc);
        BufferedReader br = new BufferedReader(new InputStreamReader(fil));
        String element = br.readLine();
        String[] line = element.split("\\s+");
        KnapsackAlgo ka =  new KnapsackAlgo(Integer.parseInt(line[0]), Integer.parseInt(line[1]),big);
        while( (element = br.readLine()) != null ){
            line = element.split("\\s+");
            ka.insert(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
        }
        return ka;
    }
}
