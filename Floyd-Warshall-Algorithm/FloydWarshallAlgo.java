/*
 *	javac FloydWarshallAlgo.java
 *	java FloydWarshallAlgo
 */

import java.util.*;
import java.io.*;
import java.math.*;

class FloydWarshallAlgo{
    public static void main(String[] args){
        try{
            int counter = 1;
            while(counter < 4){
                String file = "g" + counter + ".txt";
                System.out.print("Graph " + counter + "\t");
                long[][] W = read_file_and_populate(file);
                FWAlgo_efficient(W);
                //FWAlgo(W);                // uncomment this line to get inefficient computation
                counter++;
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static long[][] read_file_and_populate(String file_loc) throws IOException{
        FileInputStream fil = new FileInputStream(file_loc);
        BufferedReader br = new BufferedReader(new InputStreamReader(fil));

        String element = br.readLine();
        String[] lines = element.split("\\s+");
        int n = Integer.parseInt(lines[0]) + 1;
        long[][] W = new long[n][n];

        // initialization for cases i = j and i != j
        // index starts at 1 instead of 0
        for(int i = 1; i < n; i++){
            for(int j = 1; j < n; j++){
                if(i == j){
                    W[i][j] = 0;
                } else{
                    W[i][j] = (long) Integer.MAX_VALUE;
                }
            }
        }

        while( (element = br.readLine()) != null){
            String[] line = element.split("\\s+");
            int i = Integer.parseInt(line[0]);
            int j = Integer.parseInt(line[1]);
            long w = Long.parseLong(line[2]);
            W[i][j] = w;
        }

        return W;
    }

    // Floyd-Warshall Algorithm (bottom-up approach)
    // efficient approach: only use 2 columns to deal with k-1 and k
    public static void FWAlgo_efficient(long[][] W){
        int n = W.length;
        long[][][] D = new long[n][n][2];

        // initialization of 3d array D
        for(int i = 1; i < n; i++){
            for(int j = 1; j < n; j++){
                if(i == j) D[i][j][0] = 0;
                D[i][j][0] = W[i][j];
            }
        }

        for(int k = 1; k < n; k++){
            for(int i = 1; i < n; i++){
                for(int j = 1; j < n; j++){
                    D[i][j][1] = Math.min(D[i][j][0], D[i][k][0] + D[k][j][0]);
                }
            }
            // copy items in A[][1] to A[][][0]
            for(int i = 1; i < n; i++){
                for(int j = 1; j < n; j++){
                    D[i][j][0] = D[i][j][1];
                }
            }
        }

        boolean neg =  detectNegativeCycle(D,false);
        System.out.print("Negative Cycle: " + neg + "\t");
        if(!neg ){
            findShortestShortestPath(D, false);
        }
        System.out.println("");
    }


    public static boolean detectNegativeCycle(long[][][] D, boolean big){
        // detect negative cycle
        int n = D.length;
        int k;
        if(big){
            k = n - 1;
        } else{
            k = 0;
        }
        for(int i = 1; i < n; i++){
            if(D[i][i][k] < 0){
                return true;
            }
        }
        return false;
    }

    public static void findShortestShortestPath(long[][][] D, boolean big){
        int n = D.length;
        long min = Long.MAX_VALUE;
        int k;
        if(big){
            k = n - 1;
        } else{
            k = 0;
        }
        for(int i = 1; i < n; i++){
            for(int j = 1; j < n; j++){
                min = Math.min(min, D[i][j][k]);
            }
        }
        System.out.println("shortest shortest path = " + min);
    }

    public static void displayD(long[][][] D, int k){
        int n = D.length;
        System.out.println("D matrix: ");
        for(int i = 1; i < n; i++ ){
            for(int j = 1; j < n; j++){
                System.out.print(D[i][j][k] + "\t\t");
            }
            System.out.println("");
        }
        System.out.println("--------------------------");
    }

    public static void displayPi(int[][][] Pi, int k){
        int n = Pi.length;
        System.out.println("Pi matrix: ");
        for(int i = 1; i < n; i++ ){
            for(int j = 1; j < n; j++){
                System.out.print(Pi[i][j][k] + "\t\t");
            }
            System.out.println("");
        }
        System.out.println("--------------------------");
    }

    // Floyd-Warshall Algorithm (bottom-up approach)
    // memory-inefficient approach below
    public static void FWAlgo(long[][] W){
        int n = W.length;
        long[][][] D = new long[n][n][n];
        /*
         * predecessor matrix: pi[i][j][k] :
         * 	predecessor of vertex j on a shortest path
         *	from vertex i with all intermediate vertices
         * 	in the set {1,2...,k}
         */
        int[][][] Pi = new int[n][n][n];

        // initialization of 3d array D
        for(int i = 1; i < n; i++){
            for(int j = 1; j < n; j++){
                if(i == j) D[i][j][0] = 0;
                D[i][j][0] = W[i][j];
            }
        }

        // initialization of Pi matrix
        for(int i = 1; i < n; i++){
            for(int j = 1; j < n; j++){
                if(i == j) Pi[i][j][0] = -1;
                else{
                    Pi[i][j][0] = i;
                }
            }
        }

        // compute D and Pi
        for(int k = 1; k < n; k++){
            for(int i = 1; i < n; i++){
                for(int j = 1; j < n; j++){
                    D[i][j][k] = Math.min(D[i][j][k-1], D[i][k][k-1] + D[k][j][k-1]);
                    if(D[i][j][k-1] <= D[i][k][k-1] + D[k][j][k-1]){
                        Pi[i][j][k] = Pi[i][j][k-1];
                    } else{
                        Pi[i][j][k] = Pi[k][j][k-1];
                    }
                }
            }
        }

        boolean neg =  detectNegativeCycle(D, true);
        System.out.print("Negative Cycle: " + neg + "\t");
        if(!neg ){
            findShortestShortestPath(D,true);
        }
        System.out.println("");
    }

}
