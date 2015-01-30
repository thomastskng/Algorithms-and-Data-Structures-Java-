/*
 *	javac MSTKruskal.java
 *	java MSTKruskalAlgo
 */

import java.io.*;
import java.util.*;
import java.math.*;

class Vertex implements Comparable<Vertex>{
    public int node;
    private int key;
    private Vertex pi;

    // additional instance variable for union find disjoint set forest data structure
    public int rank;
    public Vertex p;

    public Vertex(int node){
        this.node = node;
    }

    // return predecessor
    public Vertex get_pi(){
        return pi;
    }

    // assign predecessor
    public void set_pi(Vertex o){
        this.pi = o;
    }

    public int get_node(){
        return node;
    }

    public void set_key(int key){
        this.key = key;
    }

    public int get_key(){
        return key;
    }

    public String toString(){
        return node + "(" + key + ")";
    }

    // Need hashCode() and equals() to compare objects
    public int hashCode(){
        return (int)(node*31);
    }

    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }

        Vertex other = (Vertex) o;
        return node == other.node;
    }

    // compare Vertex objects by node;
    public int compareTo(Vertex v){
        if(key < v.get_key()){
            return -1;
        } else if(key == v.get_key()){
            return 0;
        } else{
            return 1;
        }
    }
}


/*
 *	Undirected Weighted Edges
 */
class Edge implements Comparable<Edge>{
    private Vertex u;
    private Vertex v;
    private int weight;

    // Constructor
    public Edge(Vertex u, Vertex v, int weight){
        if(u.get_node() < v.get_node()){
            this.u = u;
            this.v = v;
        } else{
            this.u = v;
            this.v = u;
        }
        this.weight = weight;
    }

    public Vertex get_u(){
        return u;
    }

    public Vertex get_v(){
        return v;
    }

    public int get_w(){
        return weight;
    }

    public String toString(){
        return "{ " + u + " - " + v + " : " + weight + " }";
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
        return ( (u.equals(e.get_u()) && v.equals(e.get_v())) || (u.equals(e.get_v())  && (v.equals(e.get_u())))  );
    }

    // compare Edge objects by weight
    public int compareTo(Edge e){
        if(weight < e.get_w() ){
            return -1;
        } else if(weight == e.get_w() ){
            return 0;
        } else{
            return 1;
        }
    }
}

/*
 *	Created acyclic graph, which is just a
 *	HashMap of Vertex (key) and ArrayList<Vertex> (value)
 */

class Graph{

    // instance variable
    HashMap<Vertex, ArrayList<Vertex>> uag;

    // Keep track of Vertex and Edge objects created
    Map<Integer, Vertex> vertices_map;
    Map<String, Edge> edges_map;

    public Graph(){
        uag = new HashMap<Vertex, ArrayList<Vertex>>();
        vertices_map = new HashMap<Integer, Vertex>();
        edges_map = new HashMap<String, Edge>();
    }

    public int numVertices(){
        return vertices_map.size();
    }

    public int numEdges(){
        return edges_map.size();
    }

    /*	Prevent creating new object when the same node has been seen once already
     *	--> memory efficient
     *
     *	Create a new Vertex object when the node occurs for the first time;
     *	And if the node occurs more than once, return a copy of the reference
     *	to the same object with the same node value.
     *
     */
    public Vertex getVertex(int node){
        if(!vertices_map.containsKey(node)){
            vertices_map.put(node, new Vertex(node));
        }
        return vertices_map.get(node);
    }

    /*
     *	Create or Return an Edge object depending whether it existed or not
     */
    public Edge getEdge(Vertex u, Vertex v, int weight){
        String key;

        if(u.get_node() < v.get_node() ){
            key = u.get_node() + " -- " + v.get_node();
        } else{
            key = v.get_node() + " -- " + u.get_node();
        }

        if(!edges_map.containsKey(key)){
            edges_map.put(key, new Edge(u,v,weight));
        }

        return edges_map.get(key);
    }

    /*
     *	Overloading : Return an Edge object
     */
    public Edge getEdge(Vertex u, Vertex v){
        String key;

        if(u.get_node() < v.get_node() ){
            key = u.get_node() + " -- " + v.get_node();
        } else{
            key = v.get_node() + " -- " + u.get_node();
        }

        return edges_map.get(key);
    }

    /*
     *
     *	Create a new vertex object when the node first occurs;
     *	And if the same node occurs more than once, return a
     *	copy of the reference to the same object with the
     *	same node value.
     *
     *	Create adjacent list
     */
    public void addEdge(int uu, int vv, int weight){
        Vertex u = getVertex(uu);
        Vertex v = getVertex(vv);
        getEdge(u,v, weight);
        if(uag.containsKey(u) == false){
            ArrayList<Vertex> adj_edges = new ArrayList<Vertex>();
            adj_edges.add(v);
            uag.put(u,adj_edges);
        } else if(uag.containsKey(u) == true){
            uag.get(u).add(v);
        }
        if(uag.containsKey(v) == false){
            ArrayList<Vertex> adj_edges_l = new ArrayList<Vertex>();
            adj_edges_l.add(u);
            uag.put(v,adj_edges_l);
        } else if(uag.containsKey(v) == true){
            uag.get(v).add(u);
        }
    }

    // adjacent edges
    public ArrayList<Vertex> adjEdges(Vertex u){
        return uag.get(u);
    }

    public Vertex[] getAllVertices(){
        Vertex[] all_vertices = new Vertex[vertices_map.size() + 1];
        int index = 1;
        for(Map.Entry<Integer, Vertex> g_map : vertices_map.entrySet()){
            Vertex u = g_map.getValue();
            all_vertices[index] = u;
            index++;
        }
        return all_vertices;
    }

    public Edge[] getAllEdges(){
        Edge[] all_edges = new Edge[edges_map.size()];
        int index = 0;
        for(Map.Entry<String, Edge> m : edges_map.entrySet()){
            all_edges[index] = m.getValue();
            index++;
        }
        return all_edges;
    }

    public Vertex getRoot(){
        int i = (int)(Math.random() * uag.size());
        System.out.println("root = " + getVertex(i));
        return getVertex(i);
    }

    // display
    public void display(){
        int v = 0;
        int e = 0;
        for(Map.Entry<Vertex, ArrayList<Vertex>> entry: uag.entrySet()){
            System.out.print(entry.getKey() + " : " );
            v++;
            for(int i = 0; i < entry.getValue().size(); i++){
                System.out.print(entry.getValue().get(i));
                e++;
            }
            System.out.println("");
        }
        System.out.println("measured vertices = " + v + " \tmeasured edges = " + e + " edge_map size = " + edges_map.size());
    }
}


class UnionFindDisjointSetForest{

    Vertex[] vertices;
    int count;			// number of clusters

    public UnionFindDisjointSetForest(int numOfNodes){
        count = numOfNodes;
        vertices = new Vertex[numOfNodes + 1];
        for(int i = 1; i <= numOfNodes; i++){
            vertices[i] = new Vertex(i);
            makeSet(i);
        }
    }

    public void makeSet(int i){
        Vertex x = vertices[i];
        x.p = x;
        x.rank = 0;
    }

    public void union(int i, int j){
        link(findSet(i), findSet(j));
    }

    public void link(Vertex x, Vertex y){
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

    public Vertex findSet(int i){
        Vertex x = vertices[i];
        if(!x.equals(x.p)){
            // find parent
            int xp = x.p.node;
            x.p = findSet(xp);
        }
        return x.p;
    }

    public int numOfClusters(){
        return count;
    }
}


class MSTKruskalAlgo{

    public static void main(String[] args) throws IOException{
        try{
            Graph uag = new Graph();
            read_file_and_populate(uag, "clustering1.txt");
            // uag.display();
            HashSet<Edge> ee = MSTKruskalAlgo(uag);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

     public static void read_file_and_populate(Graph uag, String file_loc) throws IOException{
        FileInputStream fil = new FileInputStream(file_loc);
        BufferedReader br = new BufferedReader( new InputStreamReader(fil));
        String element = br.readLine();

        while( (element = br.readLine()) != null ){
            String[] line = element.split("\\s+");
            uag.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]));
        }
    }

    /*
     *	QuickSort Algo to sort the schedule[] in DECREASING order
     */
    public static void quickSortAlgo(Edge[] input_arr){
        quickSort(input_arr, 0, input_arr.length-1);
    }

    public static void quickSort(Edge[] input_arr, int p, int r){
        if(p < r){
            int q = randomized_partition(input_arr, p, r);
            quickSort(input_arr, p, q - 1);
            quickSort(input_arr, q + 1, r);
        } else{
            return;
        }
    }

    public static int partition(Edge[] input_arr, int p, int r){
        Edge x = input_arr[r];
        int i = p - 1;
        for(int j = p; j <= r - 1; j++){
            //if(input_arr[j] <= x){		// <= leads to Increasing order
            if(input_arr[j].compareTo(x) <= 0){
                i = i + 1;
                swap(input_arr, i, j);
            }
        }
        swap(input_arr, i+1, r);
        return i+1;
    }

    public static void swap(Edge[] input_arr, int i, int j){
        Edge temp = input_arr[i];
        input_arr[i] = input_arr[j];
        input_arr[j] = temp;
    }

    /*
     *  Randomized Partition: generate a random number , then swap it with originally fixed pivot input_arr[r]
     *  before actually implementing the partition
     */

    public static int randomized_partition(Edge[] input_arr, int p, int r){
        int i = p + (int)(Math.random() * ((r-p) + 1));
        swap(input_arr, r, i);
        return partition(input_arr, p, r);
    }

    // End of QuickSort Algo

    public static HashSet<Edge> MSTKruskalAlgo(Graph uag){
        HashSet<Edge> A = new HashSet<Edge>();
        HashSet<Vertex> discovered_vertices = new HashSet<Vertex>();
        HashSet<Vertex> nc = new HashSet<Vertex>();

        // V only has non-null values starting at index 1
        Vertex[] V = uag.getAllVertices();
        // hence need V.length - 1 = 500 nodes
        UnionFindDisjointSetForest ufdsf = new UnionFindDisjointSetForest(V.length-1);
        Edge[] E = uag.getAllEdges();
        quickSortAlgo(E);
        for(int i = 0; i < E.length; i++){
            Vertex u = E[i].get_u();
            Vertex v = E[i].get_v();
            if(ufdsf.numOfClusters() > 4){

                if(!ufdsf.findSet(u.node).equals(ufdsf.findSet(v.node))){
                    A.add(E[i]);

                    // maintain representative member in cluster hashset
                    discovered_vertices.add(u);
                    discovered_vertices.add(v);
                    ufdsf.union(u.node, v.node);
                    //System.out.println("discovered vertex =  " + discovered_vertices.size() + ", weight = " + E[i].get_w() + ", numOfClusters = " + ufdsf.numOfClusters()  );
                }
            }
        }
        int max = Integer.MAX_VALUE;
        for(int j = 0; j < E.length; j++){
            Vertex u = E[j].get_u();
            Vertex v = E[j].get_v();
            if(!ufdsf.findSet(u.node).equals( ufdsf.findSet(v.node)) ){
                max = Math.min(max,E[j].get_w());
            }
        }

        System.out.println("max spacing = " + max);

        return A;
    }

}
