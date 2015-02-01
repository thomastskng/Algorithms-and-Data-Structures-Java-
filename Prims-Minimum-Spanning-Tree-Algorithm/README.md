Prim's minimum spanning tree algorithm
----------------------------------------------
The text file describes an undirected graph with integer edge costs. It
has the format

[number_of_nodes] [number_of_edges]

[one_node_of_edge_1] [other_node_of_edge_1] [edge_1_cost]

[one_node_of_edge_2] [other_node_of_edge_2] [edge_2_cost]

...

For example, the third line of the file is "2 3 -8874", indicating that
there is an edge connecting vertex #2 and vertex #3 that has cost
-8874. We should NOT assume that edge costs are positive, nor should we
assume that they are distinct.

The code runs heaped-based Prim's minimum spanning tree algorithm on
this graph. This will give you a healthy speed-up by maintaining
relevant edges in a heap (with keys = edge costs). The unprocessed
vertices are also stored in the heaps. This requires some kind of
**bookkepping** between vertices and their positions in the heap. 
