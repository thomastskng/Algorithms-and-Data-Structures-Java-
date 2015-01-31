**Heap-based-Median-Maintanence-Algorithm**
----------------------------------------------
The goal of this problem is to implement the "Median Maintenance"
algorithm. The text
file contains a list of the integers from 1 to 10000 in unsorted order;
we should treat this as a stream of numbers, arriving one by
one. Letting xi denote the ith number of the file, the kth median mk is
defined as the median of the numbers x1,…,xk. (So, if k is odd, then mk
is ((k+1)/2)th smallest number among x1,…,xk; if k is even, then mk is
the (k/2)th smallest number among x1,…,xk.)

**Output**: the sum of these 10000 medians, modulo 10000 (i.e., only the last 4
 digits). That is, (m1+m2+m3+⋯+m10000) mod 10000.
 
The code here uses a **RED-BLACK TREE**-based approach.
