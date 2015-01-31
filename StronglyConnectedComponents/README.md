**Kosaraju's Algorithm**
-------------------------------------------
The file contains the edges of a directed graph. Vertices are labeled as
positive integers from 1 to 875714. Every row indicates an edge, the
vertex label in first column is the tail and the vertex label in second
column is the head (recall the graph is directed, and the edges are
directed from the first column vertex to the second column vertex). So
for example, the 11th row looks liks : "2 47646". This just means that
the vertex with label 2 has an outgoing edge to the vertex with label
47646.

Compute the strongly connected components (SCCs) on the given graph.

Output: the sizes of the 5 largest SCCs in the given graph, in
decreasing order of sizes. 

Note that this is a challenging problem, as it requires memory
management. 

**Note** that the Depth-first search method in Kosaraju's algorithm are
  can implemented in an **iterative** manner or **recursive**
  manner. The code here incorporates both approaches.   
