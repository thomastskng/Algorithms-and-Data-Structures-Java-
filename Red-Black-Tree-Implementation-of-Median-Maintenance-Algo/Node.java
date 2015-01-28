/*
 *	Node.java
 */

import java.util.*;
import java.io.*;
import java.math.*;

class Node implements Comparable<Node>{
    Long key;
    Node left, right, p; 			// left child, right child, parent
    int N;					// number of nodes in this subtree
    Color color;

    // Sentinel to represent NIL
    public Node(){
        key = null;
        N = 0;
        color = Color.BLACK;
    }

    public Node(long key,Color color){
        this.key = key;
        N = 1;
        this.color = color;
    }

    public String toString(){
        if( key != null){
            return key + "";
        } else{
            return "nil";
        }
        // return key + ": { N:" + N + ", Color: " + color + ", left: " + left + ", right: "+ right + ", p: " + p +  "}";
    }

    public int hashCode(){
        return (int) (key * 31);
    }

    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Node other = (Node) o;
        return key == other.key;
    }

    public int compareTo(Node x){
        if(key < x.key){
            return -1;
        } else if(key == x.key){
            return 0;
        } else{
            return 1;
        }
    }

    public boolean isNull(){
        return key == null;
    }
}
