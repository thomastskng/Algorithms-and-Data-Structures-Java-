**Johnson's Algorithm**
---------------------------------------------
The algorithm solves the all-pairs shortest-path problem. "g1.txt",
"g2.txt" and "g3.txt" are data files. The first line indicates the
number of vertices and edges, respectively. Each subsequent line
describes an edge (the first two numbers are its tail and head,
respectively) and its length (the third number). NOTE: some of the edge
lengths are negative. NOTE: These graphs may or may not have
negative-cost cycles.

The goal here is to compute the  "shortest shortest path". Precisely,
we first identify which, if any, of the three graphs have no
negative cycles. For each such graph, we compute all-pairs
shortest paths and remember the smallest one (i.e., compute
min_u,d(u,v) such that v∈V , where d(u,v) denotes the shortest-path
distance from u to v) **as well as the corresponding edge**.

"g4.txt" is a much larger graph and causes memory problem when
running Floyd–Warshall algorithm. Nonetheless, the Johnson's Algorithm
can solve this much bigger graph but it takes a quite long time to get
the answer. 

**Note:** Johnson's algorithm makes use of the **Bellman-Ford
  algorithm** and *heap-based Dijkstra's algorithm*. I also adopted a
  method within the Bellman-Ford algorithm to **stop early** in order to
  speed up the algorithm. 
