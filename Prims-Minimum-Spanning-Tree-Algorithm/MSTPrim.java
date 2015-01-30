/*
 *	javac MSTPrim.java
 *	java MSTPrimAlgo
 *
 */
import java.io.*;
import java.util.*;
import java.math.*;

class Vertex implements Comparable<Vertex>{
    private int node;
    private int key;
    private Vertex pi;

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

    public Vertex getRoot(){
        int i = (int)(Math.random() * uag.size());
        System.out.print("root = " + getVertex(i));
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
        System.out.println("measured vertices = " + v + " \tmeasured edges = " + e);
    }
}

class Heap{
    /*
     *	Heap : array index starts at 1
     */

    // instance variable
    Vertex[] heap;
    Map<Vertex, Integer> v_pos_map = new HashMap<Vertex, Integer>();
    // two attributes
    int heap_size;
    int heap_length;

    // constructor
    public Heap(Vertex[] heap){
        this.heap = heap;
        heap_length = heap.length - 1;
        heap_size = heap_length;

        for(int i = 1; i <= heap_length; i++){
            v_pos_map.put(heap[i], i);
        }
    }

    public int parent(int i){
        return (int) Math.floor(i/2.0);
    }

    public int left(int i){
        return 2 * i;
    }

    public int right(int i){
        return 2 * i + 1;
    }

    public boolean isEmpty(){
        return heap_size == 0;
    }

    public int v_pos_map_getPos(Vertex key){
        return v_pos_map.get(key);
    }

    public boolean in_v_pos_map(Vertex key){
        return v_pos_map.containsKey(key);
    }

    // swap
    public void swap(int i, int j){
        v_pos_map.replace(heap[i], j);
        v_pos_map.replace(heap[j], i);
        Vertex temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /*
     *	Maintain the heap property
     *
     *	this assumes binary trees rooted at left(i) and right(i) are min-heaps
     *	but that heap[i] might be larger than its children, and thus violating
     *	min-heap property.
     *
     *	Min-heapify lets the value at heap[i] "float down" in the min-heap so
     *	the subtree rooted at index i obeys the min-heap property.
     *
     */
    public void minHeapify(int i){
        int l = left(i);
        int r = right(i);
        int smallest;
        if(l <= heap_size && (heap[l].compareTo(heap[i]) < 0  )){
            smallest = l;
        } else{
            smallest = i;
        }

        if(r <= heap_size && (heap[r].compareTo(heap[smallest]) < 0)){
            smallest = r;
        }

        if(smallest != i){
            swap(i, smallest);
            minHeapify(smallest);
        }
    }

    /*
     *	Building a Min-heap in a bottom-up manner to convert an array into a min-heap
     *
     *	it goes through remaining nodes of the tree and runs MinHeapify on each one
     *
     */
    public void buildMinHeap(){
        heap_size = heap_length;
        int initial = (int) Math.floor(heap_length/2);
        for(int i = initial; i >= 1; i--){
            minHeapify(i);
        }
    }

    /*
     * HeapSort builds a Min-heap on the E[] heap array, heap[1 , ... , n], where n = heap_length;
     */
    public void heapSort(){
        buildMinHeap();
        for(int i = heap_length; i >= 2; i--){
            swap(1,i);
            heap_size = heap_size - 1;
            minHeapify(1);
        }
    }

    // Override MinPriorityQueue
    public Vertex heapMinimum(){
        return heap[1];
    }

    public Vertex heapExtractMin(){
        if(heap_size < 1){
            System.out.println( "heap underflow");
        }
        Vertex min = heap[1];
        swap(1,heap_size); 		// delete min Key
        v_pos_map.remove(min);		// remove the min from the hashmap, i.e. it does not exist in the minheap anymore (only used in this case)
        heap_size--;
        minHeapify(1);
        return min;
    }

    /*
     *	This procedure implements the Decrease-Key operation.
     *	An index i into the array identifies the priority queue element whose key we wish to decrease.
     *
     *	The procedure first updates the key of element heap[i] to its new value. Because decreasing the
     *	key of heap[i] might violate the min-heap property, this in turns warrant the method below.
     *
     *
     *	heapDecreaseKey traverses this path, it repeatedly compares to an element to its parent,
     *	exchanging their keys and continuing if the element's key is smaller and terminating if
     *	the element's key is bigger, since the min-heap property now holds.
     */
    public void heapDecreaseKey(int i, Integer key){
        if( key > heap[i].get_key() ){
            System.out.println("New key is larger than current key");
        }
        heap[i].set_key(key);
        while(i > 1 && heap[parent(i)].compareTo(heap[i]) > 0 ){
            swap(i,parent(i));
            i = parent(i);
        }
    }

    /*
     *	MinHeapInsert implements the Insert operation.
     *
     *	The procedure expands the min-heap by adding to the tree a new leaf whose key is + infinity.
     *	This calls HeapInsertKey to set the key of ths new node to its correct value and maintain the
     *	min-heap property.
     *
     */
    public void minHeapInsert(Integer key){
         heap_size = heap_size + 1;
         heap[heap_size].set_key(Integer.MAX_VALUE); 			//<------- CHANGE HERE
         heapDecreaseKey(heap_size, key);
    }

}


class MSTPrimAlgo{
    public static void main(String[] args) throws IOException{
        try{
            Graph uag = new Graph();
            read_file_and_populate(uag, "edges.txt");
            //uag.display();
            MSTPrim(uag, uag.getRoot());
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

    public static void MSTPrim(Graph uag, Vertex r){
        Vertex[] V = uag.getAllVertices();
        for(int i = 1; i < V.length; i++){		// at index 0, null value in heap
            Vertex u = V[i];
            u.set_key(Integer.MAX_VALUE);
            u.set_pi(null);
        }
        r.set_key(0);

        //  compute overall cost
        int overall_cost = 0;

        // Create a min priority queue based on a key attribute.
        Heap Q = new Heap(V);
        Q.buildMinHeap();
        while(!Q.isEmpty()){
            Vertex u = Q.heapExtractMin();
            overall_cost += u.get_key();
            ArrayList<Vertex> adjEdges = uag.adjEdges(u);
            for(int i = 0; i < adjEdges.size(); i++){
                Vertex v = adjEdges.get(i);
                Edge e = uag.getEdge(u,v);
                int w_uv = e.get_w();
                if(Q.in_v_pos_map(v) == true && w_uv < v.get_key()){
                    v.set_pi(u);
                    Q.heapDecreaseKey(Q.v_pos_map_getPos(v), w_uv);
                }
            }
        }
        System.out.println("\tfinal overall cost = " + overall_cost);
    }
}
