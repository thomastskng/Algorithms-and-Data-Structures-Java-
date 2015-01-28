/*
 *	Undirected Weighted Edges.java
 */

import java.util.*;
import java.io.*;
import java.math.*;

class Edge implements Comparable<Edge>{
    private Vertex u;
    private Vertex v;
    private long weight;

    // Constructor
    public Edge(Vertex u, Vertex v, long weight){
        if(u.get_node() < v.get_node()){
            this.u = u;
            this.v = v;
        } else{
            this.u = v;
            this.v = u;
        }
            this.weight = weight;

    }

    //return vertex
    public Vertex get_u(){
        return u;
    }

    public Vertex get_v(){
        return v;
    }

    public long get_w(){
        return weight;
    }

    public String toString(){
        return "{ "+ u + " - " + v + " : " + weight + " }";
    }

    public int hashCode(){
        return u.hashCode() + v.hashCode();
    }

    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }

        Edge e = (Edge) o;
        return ( (u.equals(e.get_u())) && (v.equals(e.get_v())) ) || ((u.equals(e.get_v())) && (v.equals(e.get_u())));
    }

    // compare Edge objects by weight
    public int compareTo(Edge e){
        if(weight <  e.get_w() ){
            return -1;
        } else if(weight == e.get_w() ){
            return 0;
        } else{
            return 1;
        }
    }
}
