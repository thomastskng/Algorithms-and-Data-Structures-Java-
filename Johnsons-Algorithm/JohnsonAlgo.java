/*
 *	javac JohnsonAlgo.java
 *	java RunAlgo
 */

import java.util.*;
import java.io.*;
import java.math.*;
import java.lang.*;

class Vertex implements Comparable<Vertex>{

    private int node;
    /*
     * d : shortest path distance
     * h : function mapping vertices to real numbers
     * h(v) = delta(s,v)
     * for reweighting purpose
     */
    private Long d,d_hat , h;

    private Vertex pi;

    public Vertex(int node){
        this.node = node;
    }

    public int get_node(){
        return node;
    }

    public Long get_d_hat(){
        return d_hat;
    }

    public void set_d_hat(long d_hat){
        this.d_hat = d_hat;
    }

    public Long get_d(){
        return d;
    }

    public void set_d(long d){
        this.d = d;
    }

    public long get_h(){
        return h;
    }

    public void set_h(long h){
        this.h = h;
    }

    public Vertex get_pi(){
        return pi;
    }

    public void set_pi(Vertex pi){
        this.pi = pi;
    }

    public String toString(){
        return node + ":{d_hat:"+ d_hat + ", d:" + d + ", h: " + h + "}";
    }

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
        return node == other.get_node();
    }

    public int compareTo(Vertex v){
        if(d_hat < v.get_d_hat()){
            return -1;
        } else if(d_hat == v.get_d_hat()){
            return 0;
        } else{
            return 1;
        }
    }

}

class Edge implements Comparable<Edge>{

    private Vertex u,v;
    private long w, w_hat;		// 2 weight functions for reweighting purposes

    public Edge(Vertex u, Vertex v, long w){
        this.u = u;
        this.v = v;
        this.w = w;
    }

    public Vertex get_u(){
        return u;
    }

    public Vertex get_v(){
        return v;
    }

    public long get_w(){
        return w;
    }

    public long get_w_hat(){
        return w_hat;
    }
    public void set_w_hat(long w_hat){
        this.w_hat = w_hat;
    }


    public String toString(){
        return "{"+u.get_node() + "-" + v.get_node() + ": w: " + w + ", w_hat:" + w_hat + "}";
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
        return ( u.equals(e.get_u()) &&  v.equals(e.get_v()) );
    }

    public int compareTo(Edge e){
        if( w < e.get_w() ){
            return -1;
        } else if(w == e.get_w()){
            return 0;
        } else{
            return 1;
        }
    }
}

/*
 * 	Create directed acyclic graph, which is just a HashMap of Vertex
 *	and ArrayList<Vertex>
 */

class Digraph{
    // adjacency list
    HashMap<Vertex, ArrayList<Vertex>> dag;
    HashMap<Integer, Vertex> vertices_map;
    HashMap<String, Edge> edges_map;

    public Digraph(){
        dag = new HashMap<Vertex, ArrayList<Vertex>>();
        vertices_map = new HashMap<Integer, Vertex>();
        edges_map = new HashMap<String, Edge>();
    }

    public int numVertices(){
        return vertices_map.size();
    }

    public int numEdges(){
        return edges_map.size();
    }

    /*
     *	Prevent creating new object when the same node has been seen once
     *	already --> Memory-efficient
     *
     *	Create a new vertex object when the node first occurs;
     *	And if the node occurs more than once, return a copy of the
     *	reference to the same object with the same node value.
     *
     */
    public Vertex getVertex(int node){
        if( !vertices_map.containsKey(node)){
            vertices_map.put(node, new Vertex(node));
        }
        return vertices_map.get(node);
    }

    public Edge getEdge(Vertex u, Vertex v, long w){
        String key = u.get_node() + "-" + v.get_node();
        if(!edges_map.containsKey(key)){
            edges_map.put(key, new Edge(u,v,w));
        }
        return edges_map.get(key);
    }

    public Edge findEdge(Vertex u, Vertex v){
        String key = u.get_node() + "-" + v.get_node();
        return edges_map.get(key);
    }
    /*
     *
     *	Create a new vertex object when the node first occurs;
     *	And if the same node occurs more than once, return a
     *	copy of the reference to the same object with the
     *	same node value.
     *
     */
    public void addEdge(int u, int v, long w){
        // create new Vertex objects if they do not exist already
        Vertex v_l = getVertex(u);
        Vertex v_r = getVertex(v);

        // add v to adjacency list of u
        if(dag.containsKey(v_l) == false){
            ArrayList<Vertex> adj_edges = new ArrayList<Vertex>();
            adj_edges.add(v_r);
            dag.put(v_l, adj_edges);
        } else{
            dag.get(v_l).add(v_r);
        }

        // create a new edge object if it does not exist previously
        getEdge(v_l, v_r, w);
    }

    public void removeVertex(int i){
        vertices_map.remove(i);
    }

    public Vertex[]  getAllVertices(){
        Vertex[] all_vertices = new Vertex[vertices_map.size()+1];
        int ind = 1;
        for(Map.Entry<Integer, Vertex> v_map : vertices_map.entrySet()){
            all_vertices[ind] = v_map.getValue();
            ind++;
        }
        return all_vertices;
    }

    public Edge[] getAllEdges(){
        Edge[] all_edges = new Edge[edges_map.size()];
        int ind = 0;
        for(Map.Entry<String, Edge> e_map : edges_map.entrySet()){
            all_edges[ind] = e_map.getValue();
            ind++;
        }
        return all_edges;
    }

    public ArrayList<Vertex> adjEdges(Vertex u){
        return dag.get(u);
    }

}

class MinHeap{
    Vertex[] heap;
    Map<Vertex, Integer> v_pos_map = new HashMap<Vertex, Integer>();
    // two attributes
    int heap_size;
    int heap_length;

    // constructor
    public MinHeap(Vertex[] heap){
        this.heap = heap;
        heap_length = heap.length - 1;
        heap_size = heap_length;

        for(int i = 1; i <= heap_length ; i++){
            v_pos_map.put(heap[i], i);
        }
    }

    public int parent(int i){
        return (int) Math.floor(i/2.0);
    }

    public int left(int i){
        return 2*i;
    }

    public int right(int i){
        return 2 * i + 1;
    }

    public boolean isEmpty(){
        return heap_size == 0;
    }

    public int v_pos_map_getPos(Vertex v){
        return v_pos_map.get(v);
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
        if(l <= heap_size && (heap[l].compareTo(heap[i]) < 0) ){
            smallest = l;
        } else{
            smallest = i;
        }

        if(r <= heap_size && (heap[r].compareTo(heap[smallest]) < 0) ){
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
        for(int i = initial ; i >= 1; i --){
            minHeapify(i);
        }
    }

    // Override MinPriorityQueue
    public Vertex heapMinimum(){
        return heap[1];
    }

    public Vertex heapExtractMin(){
        if(heap_size < 1){
            System.out.println("heap underflow");
        }
        Vertex min = heap[1];
        swap(1,heap_size);		// delete min key
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
    public void heapDecreaseKey(int i, Vertex key){
        if(key.compareTo(heap[i]) > 0){
            System.out.println("New key is larger than current key");
        }
        heap[i] = key;
        while(i > 1 && heap[parent(i)].compareTo(heap[i]) > 0){
            swap(i, parent(i));
            i = parent(i);
        }
    }
}

class DijkstrasAlgo{
    Digraph dag;
    public DijkstrasAlgo(Digraph dag){
        this.dag = dag;
        // remove dummy vertex
        dag.removeVertex(0);
    }

    // initialization
    public void initializeSingleSource(Vertex source){
        for(Vertex v : dag.getAllVertices()){
            if(v != null){
                v.set_d_hat(1000000);
            }
        }
        source.set_d_hat(0);
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
     *  work with d_hat instead of d  <<<<<<**********************
     *  NOTE: work with w_hat instead of w here
     */
    public void relax(Vertex u, Vertex v, long w_hat, long w, MinHeap Q){
        if(v.get_d_hat() > u.get_d_hat() + w_hat){
            v.set_d_hat(  u.get_d_hat() + w_hat );
            //System.out.println(Q.v_pos_map.size());
            Q.heapDecreaseKey( Q.v_pos_map_getPos(v), v);
        }
    }

    public void runDijkstrasAlgo(Vertex source){
        initializeSingleSource(source);

        Set<Vertex> S = new HashSet<Vertex>();
        // although operating on new graph, use all_vertices_old to build minHeap
        MinHeap Q = new MinHeap(dag.getAllVertices());
        Q.buildMinHeap();

        while(!Q.isEmpty()){
            Vertex u = Q.heapExtractMin();
            S.add(u);
            for(Vertex v : dag.adjEdges(u)){
                if(v.get_node() == -1){
                    System.out.println("damn !!!!!!!");
                }
                Edge e = dag.findEdge(u,v);
                relax(u, v, e.get_w_hat(), e.get_w(), Q);
            }
        }
    }
}

class BellmanFordAlgo{

    Digraph dag;

    public BellmanFordAlgo(Digraph dag){
        this.dag = dag;
    }

    public void initializeSingleSource(Vertex source){
        for(Vertex v : dag.getAllVertices()){
            if(v != null){
                v.set_d(10000000);
                v.set_pi(null);
            }
        }
        source.set_d(0);
    }

    public void relax(Vertex u, Vertex v, long w){
        if(v.get_d() > u.get_d() + w){
            v.set_d( u.get_d() + w );
            v.set_pi(u);
        }
    }

    public boolean runBellmanFordAlgo(Vertex source){
        initializeSingleSource(source);
        //testing
        Vertex[] all_vertices = dag.getAllVertices();
        long[] previous_d = new long[all_vertices.length];
        for(int i = 1; i < all_vertices.length; i++){
            Vertex u = all_vertices[i];
            //System.out.println(u.get_node());
            previous_d[u.get_node()] = u.get_d();
        }
        for(int i = 1; i <= dag.numVertices() - 1; i++){
            for(Edge e : dag.getAllEdges()){
                relax(e.get_u(), e.get_v(), e.get_w());
            }
            if(stoppingEarly(previous_d)){
                //System.out.println("break");
                break;
            }
        }
        // Detect negative-weight cycles
        for(Edge e : dag.getAllEdges()){
            Vertex u = e.get_u();
            Vertex v = e.get_v();
            if(v.get_d() > u.get_d() + e.get_w()){
                return false;		// there is negative-weight cycle
            }
        }
        return true;			// no neative-weight cycle
    }

    public boolean stoppingEarly(long[] previous_d){
        Vertex[] all_vertices = dag.getAllVertices();
        int updates = 0;
        for(int i = 1; i < all_vertices.length; i++){
            Vertex u = all_vertices[i];
            if(previous_d[u.get_node()] > u.get_d()){
                //System.out.println("working ? ");
                updates++;
                previous_d[u.get_node()] = u.get_d();
            }
        }
        if(updates == 0){
            return true;
        }
        //System.out.println("updates = " + updates);
        return false;
    }
}


class JohnsonAlgo{
    Digraph dag;

    public JohnsonAlgo(Digraph dag){
        this.dag = dag;
    }

    public long[][] runJohnsonAlgo(){
        /*
         * Step 1:
         *	create a new dummy Vertex(0) and this vertex has an edge
         *	with every other vertices in the graph G with weight = 0
         */
        Vertex[] all_vertices = dag.getAllVertices();

        int n = all_vertices.length;
        Vertex dummy_source = dag.getVertex(0);
        for(int i = 1; i < all_vertices.length; i++){
            dag.addEdge(dummy_source.get_node() ,all_vertices[i].get_node(), 0);
        }
        //System.out.println(dag.numVertices() + " " + dag.numEdges() + " " + all_vertices.length + " " +dag.getAllVertices().length);

        /*
         * Note that Step 1 alters(extend) the structure of the graph G',
         * hence need to call getAllvertices() and getAllEdges() again.
         *
         * Step 2: detect negative cycles using Bellman Ford
         */
        BellmanFordAlgo bfa = new BellmanFordAlgo(dag);
        if(bfa.runBellmanFordAlgo(dummy_source) == false){
            System.out.println("The input graph contains a negative-weight cycle");
        }else{

            /*
             *	Step 3:
             *		Reweighting
             */
            // G'.V
            all_vertices = dag.getAllVertices();
            for(int i = 1; i < all_vertices.length; i++){
                Vertex v = all_vertices[i];
                // set h(v) to the value of delta(s,v) computed by BellmanFord Algo
                v.set_h( v.get_d() );
            }

            // G'.E
            Edge[] all_edges = dag.getAllEdges();
            for(int i = 0; i < all_edges.length; i++){
                Edge e = all_edges[i];
                Vertex u = e.get_u();
                Vertex v = e.get_v();
                e.set_w_hat( e.get_w() + u.get_h() - v.get_h() );
            }
            //System.out.println("**********");
            //print(dag.getAllVertices(), dag.getAllEdges());

            /*
             *	Step 5:
             *		Run Dijkstra's Algorithm to compute all-pairs shortest paths
             */

            // D matrix: D[i][j] = delta(i,j)
            long[][] D = new long[n][n];
            DijkstrasAlgo da = new DijkstrasAlgo(dag);
            // note that we are using old all_vertices array here, i.e. without dummy vertex here
            all_vertices = dag.getAllVertices();
            long min = 1000000;
            for(int i = 1; i < all_vertices.length; i++){
                Vertex u = all_vertices[i];
                da.runDijkstrasAlgo(u);
                for(int j = 1; j < all_vertices.length; j++){
                    Vertex v = all_vertices[j];
                    D[u.get_node()][v.get_node()] = v.get_d_hat() + v.get_h() - u.get_h();
                    min = Math.min(min, D[u.get_node()][v.get_node()] );
                }
                //print(dag.getAllVertices(), dag.getAllEdges());
            }
            reportAnswer(D);
            return D;
        }
        return null;
    }

    public  void reportAnswer(long[][] D){
        long min = 10000000;
        String locate = "";
        for(int i = 0; i < D.length; i++){
            for(int j = 0; j< D.length; j++){
                if( min > D[i][j]){
                    min = Math.min(min, D[i][j]);
                    locate = i +"-" + j;
                }
                //System.out.print(D[i][j] + " ");
            }
            //System.out.println("");
        }
        System.out.println("shortest shortest path = " + min + " from edge " + locate + " ");
    }

    // debugging
    public void print(Vertex[] all_vertices, Edge[] all_edges){
        System.out.println("Vertices : ");
        for(int i = 1; i < all_vertices.length ; i++){
            System.out.println(all_vertices[i]);
        }

        System.out.println("Edges : ");
        for(int i = 1; i < all_edges.length; i++){
            System.out.println(all_edges[i]);
        }
    }
}

class RunAlgo{
    public static void main(String[] args){
        try{
            int i = 1;
            while( i < 4){
                String file = "g" + i + ".txt";
                Digraph dag = new Digraph();
                System.out.println("Graph " + i + "\t");
                //read_file_and_populate(dag, "testingJohnson.txt");
                read_file_and_populate(dag, file);
                JohnsonAlgo ja = new JohnsonAlgo(dag);
                ja.runJohnsonAlgo();
                i++;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void read_file_and_populate(Digraph dag, String file_loc) throws IOException{
        FileInputStream fil = new FileInputStream(file_loc);
        BufferedReader br = new BufferedReader(new InputStreamReader(fil));
        String element = br.readLine();		//ignore first line of input
        while((element = br.readLine()) != null){
            String[] line = element.split("\\s+");
            dag.addEdge(Integer.parseInt(line[0]),
                        Integer.parseInt(line[1]),
                        Long.parseLong(line[2]));
        }
    }
}
