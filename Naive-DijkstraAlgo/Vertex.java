/*
 *	Vertex.java
 */

import java.util.*;
import java.io.*;
import java.math.*;

class Vertex implements Comparable<Vertex>{
    private long node;
    private long pi;
    private long d;

    public Vertex(long node){
        this.node = node;
    }

    // return distance
    public long get_d(){
        return d;
    }

    // change distance from vertex
    public void set_d(long d){
        this.d = d;
    }

    // return predecessor
    public long get_pi(){
        return pi;
    }

    // assign predecessor
    public void set_pi(long pi){
        this.pi = pi;
    }

    // to String (prevent print memory address)
    public String toString(){
        // return node + ": { d : " + edge_dist_map + " , pi = " + pi + " }";
        return node + "(" + d  + ")";
    }

    public long get_node(){
        return node;
    }

    // Need hashCode() and equals() to compare objects
    public int hashCode(){
        return (int)(node * 31);
    }

    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Vertex other = (Vertex) o;
        return node == other.get_node();
    }

    // compare Vertex objects by node;
    public int compareTo(Vertex v){
        if(d < v.get_d()){
            return -1;
        }
        else if(d == v.get_d()){
            return 0;
        }
        else{
            return 1;
        }
    }
}
