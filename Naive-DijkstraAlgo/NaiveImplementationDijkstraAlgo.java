/*
 *	Naive implementation of Dijkstra's Algo
 *
 *  javac NaiveImplementationDijkstraAlgo.java
 *  java NaiveImplementationDijkstraAlgo
 */


import java.util.*;
import java.io.*;
import java.math.*;
import java.lang.*;

class NaiveImplementationDijkstraAlgo{
    public static void main(String[] args) throws IOException{
        Graph dag = new Graph();
        dag.read_file_and_populate("dijkstraData.txt");
        dijkstrasAlgo(dag, dag.getExistingVertex(1));
        reportAnswer(dag);
    }

        // initialization
    public static void initializeSingleSource(Graph graph, Vertex source){
        for(Vertex v : graph.getAdjacentList().keySet() ){
            v.set_d(1000000);
        }
        source.set_d(0);
    }

    public static void dijkstrasAlgo(Graph graph, Vertex source){
        initializeSingleSource(graph, source);
        Set<Vertex> X = new HashSet<Vertex>();
        X.add(source);

        while(X.size() <= graph.numVertices()){
            for(Vertex v:X){
                ArrayList<Vertex> adj_v = graph.connectedVertices(v.get_node());
                for(Vertex w:adj_v){
                    if(!X.contains(w)){
                        long weight = graph.getExistingEdge(v.get_node(), w.get_node()).get_w();
                        if(w.get_d() > v.get_d() + weight){
                            w.set_d(  v.get_d() + weight );
                        }
                    }
                }
            }

            long minDist = 1000000;
            Vertex minV = null;
            // find minimum
            for(Vertex v:X){
                ArrayList<Vertex> adj_v = graph.connectedVertices(v.get_node());
                for(Vertex w:adj_v){
                    if(!X.contains(w)){
                        if(w.get_d() < minDist){
                            minDist = w.get_d();
                            minV = w;
                        }
                    }
                }
            }

            X.add(minV);

        }
    }

    public static void reportAnswer(Graph dag){
        long[] test_v = new long[]{ 7,37,59,82,99,115,133,165,188,197 };
        System.out.println("Naive Implementation of Dijkstra's shortest path algorithm using source Vertex 1:");
        System.out.println("Shortest-path distance between source vertex and vertices 7,37,59,82,99,115,133,165,188,197: ");
        System.out.print("Answer = ");
        for(int i = 0; i < test_v.length; i++){
            System.out.print( dag.getExistingVertex(test_v[i]).get_d() + ",");
        }
        System.out.println("");
    }
}
