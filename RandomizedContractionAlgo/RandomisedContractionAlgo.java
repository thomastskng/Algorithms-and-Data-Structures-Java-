// could TRY HashMap of Linked list Next TIME
import java.util.*;
import java.io.*;
import java.math.*;

class UndirectedGraph{

    // instance variable - adjacency list representation
    HashMap<Integer, ArrayList<Integer>> graph;

    // constructor
    public UndirectedGraph(){
        graph = new HashMap<Integer, ArrayList<Integer>>();
    }

    // clone all integers from undirected graph to create a clone
    public UndirectedGraph(UndirectedGraph source){
        // cloned graph
        HashMap<Integer, ArrayList<Integer>> cloned_graph = source.getGraph();

        // define our graph
        graph = new HashMap<Integer, ArrayList<Integer>>();
        for(Map.Entry<Integer, ArrayList<Integer>> entry : cloned_graph.entrySet()){
            // iterate through the graph
            //System.out.println(entry.getValue());
            //ArrayList<Integer> source_list = entry.getValue();
            //ArrayList<Integer> cloned_list = new ArrayList<Integer>(source_list);
            ArrayList<Integer> cloned_list = new ArrayList<Integer>(entry.getValue());

            Integer source_key = entry.getKey();
            Integer cloned_key = new Integer(source_key);

            graph.put(cloned_key, cloned_list);
            //graph.put(entry.getKey(), cloned_list);
            //graph.put(entry.getKey(), entry.getValue());
        }
    }

    public void addEdge(int vertex, int edge){
        if(graph.containsKey(vertex)){
            graph.get(vertex).add(edge);
        } else{
            ArrayList<Integer> edges_of_this_vertex = new ArrayList<Integer>();
            edges_of_this_vertex.add(edge);
            graph.put(vertex, edges_of_this_vertex);
        }
    }

    // return HashMap of ArrayList
    public HashMap<Integer, ArrayList<Integer>> getGraph(){
        return graph;
    }
    // display size
    public int size(){
        return graph.size();
    }

    // display HashMap
    public void display(){
        for(Map.Entry<Integer, ArrayList<Integer>> entry : graph.entrySet() ){
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("number of vertices = " + graph.size());
        System.out.println("key Set = " + graph.keySet());
    }

    // display particular vertex and its edges
    public void displayVertexAndEdges(int k){
        System.out.println(k  + " : " + graph.get(k));
    }


    // show number of vertices left
    public void num_Vertices_left(){
        System.out.println("Number of vertices left = " + graph.size());
    }

    /*	While there are > 2 vertices in graph:
     * 		- pick a remaining EDGE (u,v) uniformly at random
     *		- merge (or "contract") u and v into a single vertex
     *		- remove self-loops
     *		return Cut represented by final 2 vertices
     */
    public int random_contraction_algo(){
        // select first vertex from discrete unif dist.

        while(graph.size() > 2){
            int u = select_remaining_edges_at_random(new ArrayList<Integer> (graph.keySet()));
            int v = select_remaining_edges_at_random(graph.get(u));
            merge(u,v);

        }

        int one = (int) graph.keySet().toArray()[0];
        int two = (int) graph.keySet().toArray()[1];

        return graph.get(one).size();
    }

    // select a pair of vertices from an edge
    public int select_remaining_edges_at_random(ArrayList<Integer> vertices){
        int index = (int)(Math.random() * (vertices.size()));
        return vertices.get(index);
    }

    // merge v into u ; remove self loop (i.e. remove u in v); remove the key v;
    public void merge(int u, int v){

        // MERGE
        graph.get(u).addAll(graph.get(v));

        // remove edges
        graph.get(u).removeAll(Collections.singleton(v));
        graph.get(v).removeAll(Collections.singleton(u));
        // remove ALL self-loops
        graph.get(u).removeAll(Collections.singleton(u));
        graph.get(v).removeAll(Collections.singleton(v));

        // make sure all the edges of v are connected to u instead v
        for(Iterator<Integer> iterator = graph.get(v).iterator(); iterator.hasNext();){
            Integer i = iterator.next();
            graph.get(i).remove((Integer) v);
            graph.get(i).add(u);
        }
        // remove key
         graph.remove(v);
    }
}

class RunAlgo{
    public static void main(String[] args) throws IOException{
        try{
            UndirectedGraph graph = new UndirectedGraph();
            read_file_and_populate("kargerMinCut.txt", graph);
            // graph.display();
            int min_cut = graph.size();
            //System.out.println("begin min cut=  " + min_cut);
            for(int i = 0; i < 200; i++){
                UndirectedGraph cloned_graph = new UndirectedGraph(graph);
                int this_cut = cloned_graph.random_contraction_algo();
                if( this_cut < min_cut){
                    min_cut = this_cut;
                }
                //System.out.println("this cut = " + this_cut);
            }
            System.out.println("Minimum-possible number of crossing edges (min cut) = " + min_cut);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    // read file and populate the HashMap
    public static void read_file_and_populate(String file_loc, UndirectedGraph graph) throws IOException{
        FileInputStream fil = new FileInputStream(file_loc);
        BufferedReader br = new BufferedReader( new InputStreamReader(fil));
        String element = null;

        while( (element = br.readLine()) != null){
            String[] line = element.split("\\s");
            for(int i = 1; i < line.length; i++){
                graph.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[i]));
            }
        }
    }
}
