/*
 *	Created directed acyclic graph, which is just a
 *	HashMap of Vertex (key) and ArrayList<Vertex> (value)
 */


import java.util.*;
import java.io.*;
import java.math.*;

class Graph{
    // instance variable
    HashMap<Vertex, ArrayList<Vertex>> adj_v;
    HashMap<Vertex, ArrayList<Edge>> adj_edges;

    // Keep track of Vertex and Edge objects created
    Map<Long, Vertex> vertices_map;
    Map<String, Edge> edges_map;

    public Graph(){
        adj_v = new HashMap<Vertex, ArrayList<Vertex>>();
        adj_edges = new HashMap<Vertex, ArrayList<Edge>>();
        vertices_map = new HashMap<Long, Vertex>();
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
    public Vertex getVertex(long node){
        if(!vertices_map.containsKey(node) ){
            vertices_map.put(node, new Vertex(node));
        }
        return vertices_map.get(node);
    }

    /*
     * Create or Return an Edge object depending whether it existed or not
     */
    public Edge getEdge(Vertex u, Vertex v, long weight){
        String key;
        if(u.get_node() < v.get_node()){
            key = u.get_node() + " -- " + v.get_node();
        } else{
            key = v.get_node() + " -- " + u.get_node();
        }
        if( !edges_map.containsKey(key)){
            edges_map.put(key, new Edge(u,v,weight));
        }
        return edges_map.get(key);
    }

    // read file and return graph
    public void read_file_and_populate(String file_loc) throws IOException{
        FileInputStream fil = new FileInputStream(file_loc);
        BufferedReader br = new BufferedReader(new InputStreamReader(fil));
        String element = null;

        while( (element = br.readLine()) != null  ){
            String [] line = element.split(",|\\s+");
            /*
             *
             *	Create a new vertex object when the node first occurs;
             *	And if the same node occurs more than once, return a
             *	copy of the reference to the same object with the
             *	same node value.
             *
             */

            // System.out.println("length = " + line.length);
            long node  = Long.parseLong(line[0]);
            Vertex u = getVertex(node);
            if( adj_v.containsKey(u) == false ){
                ArrayList<Vertex> adj_vertices = new ArrayList<Vertex>();
                ArrayList<Edge> edges_of_this_vertex = new ArrayList<Edge>();

                for(int i = 1; i < line.length; i += 2){
                    Vertex v = getVertex(Long.parseLong(line[i]));
                    long weight = Long.parseLong(line[i+1]);

                    Edge e = getEdge(u,v,weight);
                    adj_vertices.add(v);
                    edges_of_this_vertex.add(e);
                }
                adj_v.put(u, adj_vertices);
                adj_edges.put(u, edges_of_this_vertex);
            }
        }
    }

    // display vertices and edges of the vertices
    public void display(){
        for(Map.Entry<Vertex, ArrayList<Vertex>> entry : adj_v.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue());
            System.out.println("number of edges of Vertex " + entry.getKey() + ": " + entry.getValue().size());
            System.out.println("");
        }
    }

    // display edges of vertex
    public void displayEdge(){
        for(Map.Entry<Vertex, ArrayList<Edge>> entry : adj_edges.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue() );
            System.out.println("number of edges of vertex " + entry.getKey() + ": " + entry.getValue().size());
            System.out.println("");
        }
    }

    // Input node value and return EXISTING Vertex object
    public Vertex getExistingVertex(long node){
        if( vertices_map.containsKey(node)){
            return vertices_map.get(node);
        } else{
            return null;
        }
    }

    // input node and return its ADJACENCY list vertices
    public ArrayList<Vertex> connectedVertices(long node){
        return adj_v.get(getExistingVertex(node));
    }

    // Input node values and return EXISTING Edge object
    public Edge getExistingEdge(long node1, long node2){
        String key;
        if(node1 < node2){
            key = node1 + " -- " + node2;
        } else{
            key = node2 + " -- " + node1;
        }
        if( edges_map.containsKey(key)){
            return edges_map.get(key);
        } else{
            return null;

        }
    }

    //  input node and return its ADJACENCY list edges
    public ArrayList<Edge> edgesOfVertex(long node){
        Vertex u = getExistingVertex(node);
        return adj_edges.get(u);
    }

    // return hashmap
    public HashMap<Vertex, ArrayList<Vertex>> getAdjacentList(){
        return adj_v;
    }

}
