**Floyd Warshall Algorithm**
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
distance from u to v)


"g4.txt" is a much larger graph and will cause memory problem for
this algorithm. This is why we need the Johnson's Algorithm. 
