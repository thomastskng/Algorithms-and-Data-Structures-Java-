**Kruskal's Algorithm**
---------------------------------
I coded up a clustering algorithm for computing a max-spacing
k-clustering. The text file describes a  distance function
(equivalently, a complete graph with edge costs). It has the following
format:

[number_of_nodes]

[edge 1 node 1] [edge 1 node 2] [edge 1 cost]

[edge 2 node 1] [edge 2 node 2] [edge 2 cost]

...

There is one edge (i,j) for each choice of 1≤i<j≤n, where n is the
number of nodes. For example, the third line of the file is "1 3 5250",
indicating that the distance between nodes 1 and 3 (equivalently, the
cost of the edge (1,3)) is 5250. Assume that the distances are positive,
but they may not be distinct.

The program makes use of Kruskal's algorithm and runs the clustering
algorithm where the target number k of clusters is set to 4. It outputs
the maximum spacing of a 4-clustering.
