/*
 *	Dijkstra's Algorithm
 *
 *  javac DijkstrasAlgo.java
 *  java DijkstrasAlgo
 */


import java.util.*;
import java.io.*;
import java.math.*;

class DijkstrasAlgo{

    public static void main(String[] args) throws IOException{
        try{
            Graph dag = new Graph();
            dag.read_file_and_populate("dijkstraData.txt");
            dijkstrasAlgo(dag, dag.getExistingVertex(1));
            reportAnswer(dag);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    // initialization
    public static void initializeSingleSource(Graph graph, Vertex source){
        for(Vertex v : graph.getAdjacentList().keySet() ){
            v.set_d(1000000);
        }
        source.set_d(0);
    }

    /*
     *	Relaxation
     *
     *	relax an edge(u,v) by testing whether we can improve the shortest path to v found so far
     *	by going through u and, if so, updating v.d and v.pi.
     *
     *	A relaxation step may decrease the value of the shortest-path estimate v.d
     *	and update v's predecessor attribute v.pi .
     *
     */
    public static void relax(Vertex u, Vertex v, long weight, MinPriorityQueue<Vertex> Q){
        if(v.get_d() > u.get_d() + weight){
            v.set_d( u.get_d() + weight );
            Q.heapDecreaseKey( Q.v_pos_map_getPos(v), v);
            v.set_pi(u.get_node());
        }
    }

    public static void dijkstrasAlgo(Graph graph, Vertex source){
        initializeSingleSource(graph, source);
        Set<Vertex> S = new HashSet<Vertex>();

        Vertex[] vertices = new Vertex[graph.numVertices() + 1];
        for(int i = 1; i <= graph.numVertices(); i++){
            vertices[i] = graph.getExistingVertex(i);
        }

        MinPriorityQueue<Vertex> Q  = new Heap<Vertex>(vertices);

        Q.buildMinHeap();

        while(!Q.isEmpty()){
            Vertex u = Q.heapExtractMin();
            S.add(u);
            for(Vertex v: graph.connectedVertices(u.get_node())){
                long w =  graph.getExistingEdge(u.get_node() , v.get_node()).get_w();
                relax(u,v,w,Q);
            }
        }
    }

    public static void reportAnswer(Graph dag){
        long[] test_v = new long[]{ 7,37,59,82,99,115,133,165,188,197 };
        System.out.println("Heap-Based Implementation of Dijkstra's shortest path algorithm using source Vertex 1:");
        System.out.println("Shortest-path distance between source vertex and vertices 7,37,59,82,99,115,133,165,188,197: ");
        System.out.print("Answer = ");
        for(int i = 0; i < test_v.length; i++){
            System.out.print( dag.getExistingVertex(test_v[i]).get_d() + ",");
        }
        System.out.println("");
    }
}
