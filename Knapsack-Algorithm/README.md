**Knapsak Algorithm**
-------------------------------------------
There are two text files here. "knapsack1.txt" describes  a knapsack
instance, and it has the following format:

[knapsack_size][number_of_items]

[value_1] [weight_1]

[value_2] [weight_2]

...

For example, the third line of the file is "50074 659", indicating that
the second item has value 50074 and size 659, respectively.

Assume that all numbers are positive and that item weights and the
knapsack capacity are integers.

The "knapsack_big.txt" also describes a knapsack instance, but a much
*BIGGER* one. It follows the same assumptions and format above. This
instance is so big that the straightforward iterative implemetation uses
an infeasible amount of time and space. 

The program "Knapsack.java" uses dynamic programming to solve
**overlapping** problem (i.e. when subproblems share subsubproblems) and hence the run time is better than the other program that I
mention below. This is because dynamic programming algorithm solves each
subsubproblem just once and then saves its answer in a table, thereby
avoiding the work of recomputing the answer everytime it solves each subsubproblem.


The program "RecursiveKnapsack.java" runs a recursive algorithm to
**repeatedly** solve common subproblems and cache results to avoid redundant work (only on "as
needed" basis) to improve run time. The recursive approach does more
work than necessary.

Both programs output the values of the optimal solution of both Knapsack Problems. 


