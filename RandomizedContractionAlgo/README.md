**Randomized Contraction Algorithm**
-------------------------------------------
The file contains the adjacency list representation of a simple
undirected graph. There are 200 vertices labeled 1 to 200. The first
column in the file represents the vertex label, and the particular row
(other entries except the first column) tells all the vertices that the
vertex is adjacent to. So for example, the 6th row looks like : "6
155	56	52	120	......". This just means that the vertex
with label 6 is adjacent to (i.e., shares an edge with) the vertices
with labels 155,56,52,120,......,etc.

The randomized contraction algorithm is used for the min cut problem and
applied to the text file. 

**Subtle Point**: This requires cloning the graph. 
