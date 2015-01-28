/*
 *	Recursive + Non-recursive DFS algo
 *
 *	javac DFS.java
 *	to run:  time(java -Xmx1024m -Xms1024m -Xmn256m -Xss16m RunAlgo)
 */

import java.util.*;
import java.io.*;
import java.math.*;
import java.lang.*;

/*
 * Create Vertex object
 */

class Vertex{
    private long node;
    private Color color;
    private long d;
    private long pi;
    private long f;

    public Vertex(long node){
        this.node = node;
    }

    // return color
    public Color get_color(){
        return color;
    }

    // change color
    public void set_color(Color color){
        this.color = color;
    }

    // return discovery time
    public long get_d(){
        return d;
    }

    // change discovery time
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

    // to String (prevent print reference)
    public String toString(){
        //return node + ": {col: " + color + ", d: "+ d + ", pi = " + pi + ", f = " + f + "}";
        return node + "";
    }

    // return node
    public long get_node(){
        return node;
    }

    // return finishing time
    public long get_f(){
        return f;
    }

    // set finishing time
    public void set_f(long f){
        this.f = f;
    }

    // Need hashCode() and equals() to compare objects
     public int hashCode(){
         return (int)(node * 31);
    }

    public boolean equals(Object o) {
        if (o == this){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Vertex other = (Vertex)o;
        return node  == other.node;
    }
}


/*
 * 	Create directed acyclic graph, which is just a HashMap of Vertex
 *	and ArrayList<Vertex>
 */

class Digraph{

    // instance variable
    HashMap<Vertex, ArrayList<Vertex>> dag;
    HashMap<Long, Vertex> vertices_map;

    // constructor
    public Digraph(){
        dag = new HashMap<Vertex, ArrayList<Vertex>>();
        vertices_map = new HashMap<Long, Vertex>();
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
    public Vertex getVertex(long node){
        if( ! vertices_map.containsKey(node) ) vertices_map.put(node, new Vertex(node));
        return vertices_map.get(node);
    }

    /*
     *
     *	Create a new vertex object when the node first occurs;
     *	And if the same node occurs more than once, return a
     *	copy of the reference to the same object with the
     *	same node value.
     *
     */
    public void addEdge(long u, long v){
        Vertex v_l = getVertex(u);
        Vertex v_r = getVertex(v);

        if(dag.containsKey(v_l) == false){
            ArrayList<Vertex> adj_edges = new ArrayList<Vertex>();
            adj_edges.add(v_r);
            dag.put(v_l, adj_edges);
        } else{
            dag.get(v_l).add(v_r);
        }
    }

    public ArrayList<Vertex> getAllVertices(){
        ArrayList<Vertex> all_vertices = new ArrayList<Vertex>();
        for(Map.Entry<Long,Vertex> v_map :vertices_map.entrySet() ){
            Vertex u = v_map.getValue();
            all_vertices.add(u);
        }
        return all_vertices;
    }

    public ArrayList<Vertex> getAllVerticesWithEdges(){
        ArrayList<Vertex> vv = new ArrayList<Vertex>();
        for(Map.Entry<Vertex, ArrayList<Vertex>> g_map : dag.entrySet()){
            Vertex u = g_map.getKey();
            vv.add(u);
        }
        return vv;
    }

    // adjacent edges
    public ArrayList<Vertex> adjEdges(Vertex u){
        return dag.get(u);
    }

    public void display(){
        for(Map.Entry<Vertex, ArrayList<Vertex>> entry : dag.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue());
            for(Iterator<Vertex> iterator = dag.get(entry.getKey()).iterator(); iterator.hasNext();){
                Vertex vv = iterator.next();
                System.out.print("\t" + vv.get_color());
            }
            System.out.println("");
        }
    }

    // display particular vertex and its edges
    public void displayVertexAndEdges(int k){
        System.out.println(k  + " : " + dag.get(new Vertex(k)));
    }

    public Digraph transposed(Digraph dag){
        Digraph rev = new Digraph();
        for(Vertex u : dag.getAllVertices()){
            ArrayList<Vertex> adj_list = dag.adjEdges(u);
            if(adj_list != null){
                for(Vertex v : adj_list){
                    rev.addEdge(v.get_node(),u.get_node());
                }
            }
        }
        return rev;
    }

}

/*
 *	Iterative Depth First Search
 */
class IterativeDFS{

    long time;
    LinkedList<Vertex>topological_sort_list = new LinkedList<Vertex>();

    public IterativeDFS(Digraph G){
        dfs(G);
    }


    public void dfs(Digraph G){
        for(Vertex u : G.getAllVertices()){
            u.set_color(Color.WHITE);
            u.set_pi(-1);
        }
        time = 0;

        /*
         *	to be able to iterate over each adjacency list, keeping track of which
         *	vertex in each adjacency list needs to be explored next.
         */
        HashMap<Vertex, Iterator<Vertex>> adj_map = new HashMap<Vertex, Iterator<Vertex>>();

        for(Vertex u : G.getAllVerticesWithEdges()){
            if(u.get_color().equals(Color.WHITE)){
                if(!adj_map.containsKey(u)){
                    adj_map.put(u, G.adjEdges(u).iterator());
                }
                dfs_stack(G, u, adj_map);
            }
        }
    }

    public void dfs_stack(Digraph G, Vertex u,  HashMap<Vertex, Iterator<Vertex>> adj_map){

        Stack<Vertex> stack = new Stack<Vertex>();
        // time++;			// white vertex u has just been discovered
        u.set_d(time);
        u.set_color(Color.GRAY);
        stack.push(u);

        while(!stack.empty()){

            Vertex k = stack.peek();
            /*
             *	to be able to iterate over each adjacency list, keeping track of which
             *	vertex in each adjacency list needs to be explored next.
             */
            if(!adj_map.containsKey(k)){
                if(G.adjEdges(k) != null){
                    adj_map.put(k, G.adjEdges(k).iterator());
                }
            }

            if(adj_map.get(k) != null && adj_map.get(k).hasNext()){
                // explore edges (k,v)
                Vertex v = adj_map.get(k).next();
                if(v.get_color().equals(Color.WHITE)){
                    v.set_pi(k.get_node());
                    //  time++;
                    v.set_d(time);
                    v.set_color(Color.GRAY);
                    stack.push(v);
                }
            } else{
                    // v's adjacency list is exhausted
                    Vertex t = stack.pop();
                    time++;
                    t.set_f(time);
                    t.set_color(Color.BLACK);
                    /*
                     *	Topological Sort :
                     *		1. call DFS(G) to compute finishing times v.f for each vertex v
                     *		2. as each vertex is finished, insert it onto FRONT of linked list
                     *		3. return linked list of vertices
                     */
                    topological_sort_list.addFirst(t);
            }
        }
    }

    public LinkedList<Vertex> topological_sort(){
        return topological_sort_list;
    }

}
/*
 *	Using an Iterative Approach to compute the sizes of the Strongly Connected Components
 */
class Iterative_SCC_sizes{
    long time;

    // list contains leaders of all the SCC
    ArrayList<Vertex> leader_list = new ArrayList<Vertex>();
    // list contains size of each SCC s
    ArrayList<Long> size_list = new ArrayList<Long>();
    // s will be used to set the leader
    Vertex leader;

    public Iterative_SCC_sizes(Digraph G, LinkedList<Vertex> ts_list){
        dfs_2nd_pass(G, ts_list);
    }

    public void dfs_2nd_pass(Digraph G, LinkedList<Vertex> ts_list){
        for(Vertex u : G.getAllVertices()){
            u.set_color(Color.WHITE);
            u.set_pi(-1);
        }
        time = 0;
        /*
         *	to be able to iterate over each adjacency list, keeping track of which
         *	vertex in each adjacency list needs to be explored next.
         */
        HashMap<Vertex, Iterator<Vertex>> adj_map = new HashMap<Vertex, Iterator<Vertex>>();

        /*
         *	Replacement Code: decreasing order of finishing times u.f
         */
        ListIterator ls = ts_list.listIterator();
        while(ls.hasNext()){
            // System.out.println("Testing");
            Vertex test = (Vertex)ls.next();
            Vertex u = G.getVertex(test.get_node());
            // System.out.println(u);
            if(u.get_color().equals(Color.WHITE)){
                if(!adj_map.containsKey(u) && G.adjEdges(u) != null ){
                    adj_map.put(u, G.adjEdges(u).iterator());
                }
                // if u not yet explored
                leader = u;
                long x;
                x = dfs_2nd_visit(G,u, adj_map);
                size_list.add(x);
            }
        }
    }

    /*
     *  Depth First Search 2nd Visit ( G , u )
     *	- assign same leader to nodes within Strongly Connected Components
     *	- compute size of each Strongly Connected Componenets
     */

    public long dfs_2nd_visit(Digraph G, Vertex u, HashMap<Vertex, Iterator<Vertex>> adj_map){
        long component_size = 1;

        Stack<Vertex> stack = new Stack<Vertex>();
        // time++;			// white vertex u has just been discovered
        u.set_d(time);
        u.set_color(Color.GRAY);
        stack.push(u);

        Vertex lead = leader;

        while(!stack.empty()){
            Vertex k = stack.peek();

            if(!adj_map.containsKey(k) && G.adjEdges(k) != null){
                adj_map.put(k, G.adjEdges(k).iterator());
            }

            if(adj_map.get(k) != null && adj_map.get(k).hasNext()){
                // explore edges (k,v)
                Vertex v = adj_map.get(k).next();
                if(v.get_color().equals(Color.WHITE)){
                    v.set_pi(k.get_node());
                    //  time++;
                    v.set_d(time);
                    v.set_color(Color.GRAY);
                    stack.push(v);
                    component_size++;
                }
            } else{
                    // v's adjacency list is exhausted
                    Vertex t = stack.pop();
                    time++;
                    t.set_f(time);
                    t.set_color(Color.BLACK);

                    leader_list.add(lead);
            }
        }
        return component_size;
    }

    public ArrayList<Long> results(){
        return size_list;
    }
}

/*
 *	Recursive Depth First Search
 */
class RecursiveDFS{

    long time;
    LinkedList<Vertex>topological_sort_list = new LinkedList<Vertex>();

    public RecursiveDFS(Digraph G){
        dfs(G);
    }

    public void dfs(Digraph G){
        // mark all nodes unexplored
        for(Vertex u : G.getAllVertices()){
            u.set_color(Color.WHITE);
            u.set_pi(-1);
        }

        time = 0;
        for(Vertex u : G.getAllVertices()){
            if(u.get_color().equals(Color.WHITE)){
                dfs_visit(G, u);
            }
        }
    }

    public void dfs_visit(Digraph G, Vertex u){
        //time++;				// white vertex u has just been discovered
        u.set_d(time);
        u.set_color(Color.GRAY);

        // explore edge(u, v)
        if(G.adjEdges(u) != null){
            for(Vertex v : G.adjEdges(u)){
                if(v.get_color().equals(Color.WHITE)){
                    v.set_pi(u.get_node());
                    dfs_visit(G,v);
                }
            }
        }
        u.set_color(Color.BLACK);			// blacken u; it is finished
        time++;
        u.set_f(time);
        /*
         *	Topological Sort :
         *		1. call DFS(G) to compute finishing times v.f for each vertex v
         *		2. as each vertex is finished, insert it onto FRONT of linked list
         *		3. return linked list of vertices
         */
        topological_sort_list.addFirst(u);
    }

    public LinkedList<Vertex> topological_sort(){
        return topological_sort_list;
    }
}
/*
 *	Recursive Approach to compute the sizes of Strongly Connected Components
 */
class SCC_sizes{
    long time;

    // list contains leaders of all the SCC
    ArrayList<Vertex> leader_list = new ArrayList<Vertex>();
    // list contains size of each SCC s
    ArrayList<Long> size_list = new ArrayList<Long>();
    // s will be used to set the leader
    Vertex leader;

    public SCC_sizes(Digraph G, LinkedList<Vertex> ts_list){
        dfs_2nd_pass(G, ts_list);
    }

    public void dfs_2nd_pass(Digraph G, LinkedList<Vertex> ts_list){
        for(Vertex u : G.getAllVertices() ){
            u.set_color(Color.WHITE);
            u.set_pi(-1);
        }
        time = 0;
        /*
         *	Replacement Code: decreasing order of finishing times u.f
         */
        ListIterator ls = ts_list.listIterator();
        while(ls.hasNext()){
            // System.out.println("Testing");
            Vertex test = (Vertex)ls.next();
            Vertex u = G.getVertex(test.get_node());
            if(u.get_color().equals(Color.WHITE)){
                // if u not yet explored
                leader = u;
                long x;
                x = dfs_2nd_visit(G,u);
                size_list.add(x);
            }
        }
    }

    /*
     *  Depth First Search 2nd Visit ( G , u )
     *	- assign same leader to nodes within Strongly Connected Components
     *	- compute size of each Strongly Connected Componenets
     */
    public long dfs_2nd_visit(Digraph G, Vertex u){
        long component_size = 1;
        // time++			// white vertex u has just been discovered
        u.set_d(time);
        u.set_color(Color.GRAY);

        Vertex lead = leader;

        // explore edge (u,v)
        if(G.adjEdges(u) != null){
            for(Vertex v : G.adjEdges(u)){
                if(v.get_color().equals(Color.WHITE)){
                    v.set_pi(u.get_node());
                    component_size += dfs_2nd_visit(G,v);
                }
            }
        }
        // blacken u; its is finished
        u.set_color(Color.BLACK);
        time++;
        u.set_f(time);

        leader_list.add(lead);
        return component_size;
    }

    public ArrayList<Long> results(){
        return size_list;
    }
}

class RunAlgo{
    public static void main(String[] args){
        try{
            // load file
            long startTime = System.currentTimeMillis();
            Digraph dag = new Digraph();
            read_file_and_populate(dag ,"SCC.txt");
            long endTime = System.currentTimeMillis();
            System.out.println("Load file: " + (endTime - startTime) + " milliseconds");

            // Transposing graph
            startTime = System.currentTimeMillis();
            Digraph dag_rev = new Digraph();
            Digraph dag_transposed = dag_rev.transposed(dag);
            endTime = System.currentTimeMillis();
            System.out.println("Transposing graph: " + (endTime - startTime) + " milliseconds");

            boolean iterative = true;		      // <----set to true for Iterative DFS and false for Recursive DFS

            ArrayList<Long> answer;
            int[] input_arr;

            if(iterative == true){
                System.out.println("ITERATIVE Approach ");
                // Iterative DFS + SCC ALGO
                startTime = System.currentTimeMillis();
                IterativeDFS iterativeDFS = new IterativeDFS(dag);
                IterativeDFS iterativeDFS_rev = new IterativeDFS(dag_transposed);
                Iterative_SCC_sizes scc = new Iterative_SCC_sizes(dag_transposed, iterativeDFS.topological_sort());
                endTime = System.currentTimeMillis();
                System.out.println("compute SCCs: " + (endTime - startTime) + " milliseconds");

                // Convert Arraylist to array + QUICKSORT
                startTime = System.currentTimeMillis();
                answer = scc.results();

            } else{
                System.out.print("RECURSIVE Approach ");
                // Recursive DFS + SCC ALGO
                startTime = System.currentTimeMillis();
                RecursiveDFS recursiveDFS = new RecursiveDFS(dag);
                RecursiveDFS recursiveDFS_rev = new RecursiveDFS(dag_transposed);
                SCC_sizes scc = new SCC_sizes(dag_transposed, recursiveDFS.topological_sort());
                endTime = System.currentTimeMillis();
                System.out.println("compute SCCs: " + (endTime - startTime) + " milliseconds");

                // Convert Arraylist to array + QUICKSORT
                startTime = System.currentTimeMillis();
                answer = scc.results();
            }

            input_arr = new int[answer.size()];
            for(int i = 0; i < answer.size(); i++){
                input_arr[i] = (int)(long) answer.get(i);
            }

            // merge sort
            MergeSortArray testing_arr = new MergeSortArray(input_arr);
            reportAnswer2(testing_arr.input_arr);
            endTime = System.currentTimeMillis();
            System.out.println("MergeSort: " + (endTime - startTime) + " milliseconds");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    // read file and return graphs
    public static void read_file_and_populate(Digraph dag, String file_loc) throws IOException{

        FileInputStream fil = new FileInputStream(file_loc);
        BufferedReader br = new BufferedReader( new InputStreamReader(fil));
        String element = null;

        while( (element = br.readLine()) != null){
            String[] line = element.split("\\s+");
            dag.addEdge(Long.parseLong(line[0]), Long.parseLong(line[1]));
        }
    }

    public static void reportAnswer2(int[] testing_arr ){
        for(int i = testing_arr.length - 1; i > testing_arr.length - 6; i--){
            System.out.print(testing_arr[i] + ",");
        }
        System.out.println("");
    }

    // Print results of topological sorted list
    public static void printTopologicalSortList(LinkedList<Vertex> ts_list){
        ListIterator ls = ts_list.listIterator();
        while(ls.hasNext()){
            System.out.print( ls.next() + " ");
        }
    }

}
