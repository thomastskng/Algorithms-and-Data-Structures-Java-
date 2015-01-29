/*
 *	javac LargeClustering.java
 *	java ClusteringAlgo
 */

import java.io.*;
import java.util.*;
import java.math.*;


class Node{

    Integer node;
    Node left, right, p;
    int N;
    int prefix_code;
    int rank;
    boolean end;

    public Node(){
        this.node = null;
        this.prefix_code = -1;
        N = 0;
        end = false;
    }

    public Node(int node, int prefix_code, boolean end){
        this.node = node;
        this.prefix_code = prefix_code;
        N = 1;
        this.end = end;
    }

    public Node(int node){
        this.node = node;
    }

    public String toString(){
        if(node != null){
            return node + "(" + prefix_code +", p = " + p.prefix_code +", N = " + N + ")";
        } else{
            return "nil";
        }
    }

    public int hashCode(){
        return (int)(node * 31);
    }

    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(o == null || getClass() != o.getClass() ){
            return false;
        }
        Node other = (Node) o;
        return node == other.node && prefix_code == other.prefix_code && end == other.end;
    }

    public Node[] adj(){
        Node[] adjacent_v = new Node[]{left, right};
        return adjacent_v;
    }

    public boolean isNull(){
        return node == null;
    }
}

class BinaryTree{
    Node root;
    ArrayList<Node> all_nodes = new ArrayList<Node>();
    UnionFindDisjointSetForest ufdsf;

    public BinaryTree(){
        root = new Node(-1,-1, false);		// make sure Tree is not empty
    }

    public void initializeUnionFind(int numOfNodes){
        ufdsf = new UnionFindDisjointSetForest(numOfNodes);
    }

    public int size(){
        return size(root);
    }

    public int size(Node x){
        if(x == null){
            return 0;
        } else{
            return x.N;
        }
    }

    /*
     *	Insertion begins at the root of the tree
     *  and the pointer x traces a simple path downward
     *  looking for a null to replace with the input item.
     *
     *	The method also maintains a trailing pointer y as
     * 	the parent of x.
     *
     *	Within the while loop,these two pointers will
     *	move down the tree, going left or right depending on
     *	input until x becomes null.
     *
     *	This null occupies the position where we wish to place the
     *	input item.
     *
     * 	The trailing pointer is needed because by the time we find
     *	the null where z belongs, the search has proceeded one step
     * 	beyond the node that needs to be changed.
     *
     */
    public void treeInsert(Integer[] bits, int node){
        Node x = root;		// parent
        for(int i = 0; i < bits.length; i++){
            Node y = x;		// track parent and its child
            if(bits[i] == 0){
                y = x.left;	// child
                if(y == null){
                    if(i == bits.length - 1){
                        x.left = new Node(node, bits[i], true);
                    } else{
                        x.left = new Node(node, bits[i], false);
                    }
                    x.left.p = x;	// set new child's parent to x

                    // update size
                    Node z = x.left;
                    z.N = size(z.left) + size(z.right) + 1;
                    //UpdateSize(root);
                }
                x = x.left;		// new child becomes new parent for next node

            } else{
                y = x.right;
                if(y == null){
                    if(i == bits.length - 1){
                        x.right = new Node(node, bits[i], true);
                    } else{
                        x.right = new Node(node, bits[i], false);
                    }
                    x.right.p = x;

                    // update size
                    Node z = x.right;
                    z.N = size(z.left) + size(z.right) + 1;
                    //UpdateSize(root);
                }
                x = x.right;
            }

        }
    }

    public void treeSearch(Integer[] bits){
        Node x = root;
        for(int i = 0; i < bits.length; i++){
            if(bits[i] == 0){
                x = x.left;
            } else{
                x = x.right;
            }
            System.out.print(x.prefix_code + " ");
        }
        System.out.println(" = " + x.node);
    }

    public void recursiveTreeSearch(Integer[] bits, int bitVal, int k){
        for(int i = 1; i < k; i++){
            recursiveDFS(root,bits, bitVal ,0, 0, i);
        }
    }

    public void recursiveDFS(Node x, Integer[] bits, int bitVal, int index, int wrongTurn, int k){

        if(!x.equals(root)){				// if x is not root
            if(bits[index] != x.prefix_code){		// then start counting wrongTurn
                wrongTurn++;
            }
            // System.out.println("root ?" + x.equals(root));
            // System.out.println("bits = (" + bits[index]+  " == "  + x.prefix_code + ") index =  " + index + ", wrongTurn " + wrongTurn + " x = (" + bitVal + " == " + x.node + ")" + " end = " + x.end);
        }

        if(x.end == true && wrongTurn <= k && wrongTurn >= 1){
            //union find  here
            if( !ufdsf.findSet(bitVal).equals(ufdsf.findSet(x.node))){
                ufdsf.union(bitVal, x.node);
            }
        }

        if(x.left != null && wrongTurn <= k){
            if(x.equals(root)){				// if x is root, go left without incrementing index
                recursiveDFS(x.left, bits, bitVal, index, wrongTurn, k);
            } else{
                recursiveDFS(x.left, bits, bitVal, index + 1, wrongTurn, k);
            }
        }
        if(x.right != null && wrongTurn <= k){
            if(x.equals(root)){
                recursiveDFS(x.right, bits, bitVal, index, wrongTurn, k);
            } else{
                recursiveDFS(x.right, bits, bitVal, index + 1, wrongTurn, k);
            }
        }
    }

    public void reportAnswer(){
        System.out.print("FINAL Answer: ");
        ufdsf.numOfClusters();
    }

    public void UpdateSize(Node x){
        if(x.left != null){
            UpdateSize(x.left);
        }
        if( x.right != null){
            UpdateSize(x.right);
        }
        x.N = size(x.left) + size(x.right) + 1;
    }
}

class UnionFindDisjointSetForest{

    Node[] nodes;
    int count;			// number of clusters

    public UnionFindDisjointSetForest(int numOfNodes){
        count = numOfNodes;
        nodes = new Node[numOfNodes + 1];
        for(int i = 1; i <= numOfNodes; i++){
            nodes[i] = new Node(i);
            makeSet(i);
        }
    }

    public void makeSet(int i){
        Node x = nodes[i];
        x.p = x;
        x.rank = 0;
    }

    public void union(int i, int j){
        link(findSet(i), findSet(j));
    }

    public void link(Node x, Node y){
        if(x.rank > y.rank){
            y.p = x;
        } else{
            x.p = y;
            if(x.rank == y.rank){
                y.rank++;
            }
        }
        count--;
    }

    public Node findSet(int i){
        Node x = nodes[i];
        if(!x.equals(x.p)){
            // find parent
            int xp = x.p.node;
            x.p = findSet(xp);
        }
        return x.p;
    }

    public void numOfClusters(){
        System.out.println(" number of clusters = " + count);
    }
}



class ClusteringAlgo{
    public static void main(String[] args) throws IOException{
        HashMap<Integer[], Integer> bitToNode = new HashMap<Integer[], Integer>();
        BinaryTree bt = new BinaryTree();
        long startTime = System.currentTimeMillis();
        int size = read_file_and_populate(bitToNode, bt,"clustering_big.txt");
        long endTime = System.currentTimeMillis();
        System.out.println("Load file: " + (endTime - startTime) + " milliseconds");
        //display(bitToNode);
        startTime = System.currentTimeMillis();
        kClusteringAlgo(bitToNode, bt, size);
        endTime = System.currentTimeMillis();
        System.out.println("Clustering: " + (endTime - startTime) + " milliseconds");

    }

    public static int read_file_and_populate(HashMap<Integer[], Integer> bitToNode, BinaryTree binarytree ,String file_loc) throws IOException{
        FileInputStream fil = new FileInputStream(file_loc);
        BufferedReader br = new BufferedReader( new InputStreamReader(fil));

        String element = br.readLine();
        String[] line = element.split("\\s+");
        int numOfNodes = Integer.parseInt(line[0]);
        int numOfBits = Integer.parseInt(line[1]);

        Integer node = 1;

        HashSet<String> duplicates = new HashSet<String>();
        while( (element = br.readLine()) != null){
            line = element.split("\\s+");
            Integer[] bits = new Integer[numOfBits];
            String str = "";
            for(int i = 0; i < line.length; i++){
                int input = Integer.parseInt(line[i]);
                bits[i] = input;
                str += line[i];
            }

            if(!duplicates.contains(str)){
                duplicates.add(str);
                binarytree.treeInsert(bits, node);
                bitToNode.put(bits,node);
                node++;
            }
        }
        //System.out.println("size = " + bitToNode.size() + ", tree size = " + binarytree.size());
        return bitToNode.size();
    }

    public static void display(HashMap<Integer[], Integer> bitToNode){
        for(Map.Entry<Integer[], Integer> entry: bitToNode.entrySet()){
            Integer[] ii = entry.getKey();
            for(int i = 0; i < ii.length; i++){
                System.out.print(ii[i] + " ");
            }
            System.out.println(": " + entry.getValue());
        }
    }

    public static void kClusteringAlgo(HashMap<Integer[], Integer> bitToNode, BinaryTree bt, int size){
        bt.initializeUnionFind(size);
        for(Map.Entry<Integer[], Integer> entry : bitToNode.entrySet()){
            Integer[] bits = entry.getKey();
            Integer bitVal = entry.getValue();
            //bt.treeSearch(bits);
            bt.recursiveTreeSearch(bits, bitVal, 3);
        }
        bt.reportAnswer();
    }
}
