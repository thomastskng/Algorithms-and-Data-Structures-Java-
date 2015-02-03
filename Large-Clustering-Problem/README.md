**Clustering Algorithm on a BIG graph**
-------------------------------------------
The problem here is again to run the clustering algorithm from lecture,
but on a MUCH bigger graph. So big, in fact, that the distances (i.e.,
edge costs) are only defined implicitly, rather than being provided as
an explicit list.
The format is:

[# of nodes] [# of bits for each node's label]

[first bit of node 1] ... [last bit of node 1]

[first bit of node 2] ... [last bit of node 2]

...

For example, the third line of the file "0 1 1 0 0 1 1 0 0 1 0 1 1 1 1 1
1 0 1 0 1 1 0 1" denotes the 24 bits associated with node #2.


The distance between two nodes u and v in this problem is defined as the
Hamming distance--- the number of differing bits --- between the two
nodes' labels. For example, the Hamming distance between the 24-bit
label of node #2 above and the label "0 1 0 0 0 1 0 0 0 1 0 1 1 1 1 1 1
0 1 0 0 1 0 1" is 3 (since they differ in the 3rd, 7th, and 21st bits).

We are interested in the largest value of k such that there is a
k-clustering with spacing at least 3 ? That is, how many clusters are
needed to ensure that no pair of nodes with all but 2 bits in common get
split into different clusters?

**NOTE**: The graph implicitly defined by the data file is so big that
  you probably can't write it out explicitly, let alone sort the edges
  by cost. 
  
I used a **binary tree** data structure to store the hamming distance
for aech node. The algorithm performs a recursive depth-first search to
walk through the tree and determine te number of differing bits between
two nodes' label. If the number of wrong turns is greater than 3, we
backtrack and look for another path to reach the root nodes. Otherwise,
we perform the *union* operation using the Union-Find Disjoint Set data
structure, which does **union by rank** and **path compression**. 
  

  
