**Dijkstra's shortest-path algorithm**
----------------------------------------------
The file contains an adjacency list representation of an undirected
weighted graph with 200 vertices labeled 1 to 200. Each row consists of
the node tuples that are adjacent to that particular vertex along with
the length of that edge. For example, the 6th row has 6 as the first
entry indicating that this row corresponds to the vertex labeled 6. The
next entry of this row "141,8200" indicates that there is an edge
between vertex 6 and vertex 141 that has length 8200. The rest of the
pairs of this row indicate the other vertices adjacent to vertex 6 and
the lengths of the corresponding edges.

When running the Dijkstra's algorithm, use the first vertex 1 as the
source vertex and compute the shortest-path distances between 1 and any
other vertex of the graph. If there is no path between a vertex v and
vertex 1, we'll define the shortest-path distance between 1 and v to be
1000000. 

The script output the shortest-path distances to the following ten
vertices, in order: 7,37,59,82,99,115,133,165,188,197.

The script contains a heap-based(efficient) implementation of Dijkstra's shortest path algorithm.

