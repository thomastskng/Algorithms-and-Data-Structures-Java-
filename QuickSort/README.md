**QuickSort**
-------------------
Perform quicksort on the text file and compute the **total number of
comparisons** used to sort the given input file by QuickSort. 

Note that we are not counting the comparisons one-by-one. Rather, when
there is a recursive call on a subarray of length m, we add m-1 to the
running total of comparisons. (This is because the pivot element is
compared to each of the other m-1 elements in the subarray in this
recursive call.)

There are three choices of pivot element: 
1. use first element of array as pivot element.
2. use last element of array as pivot element.
3. use the "median-of-three" pivot rule
  + make use of the **randomized selection algorithm**: 
    + consider the first, middle, and final elements of the given array,
    identify which element is the median and use this as your pivot.  
