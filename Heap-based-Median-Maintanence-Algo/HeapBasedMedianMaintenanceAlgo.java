/*
 *	Generic heap data structure - complete binary tree
 *
 * 	A.length  : number of elements in the array
 *	heap_size : number of elements in the heap are stored within array
 *
 *	Although heap[ 1 ... heap_length ] may contain objects,
 *	only elements in heap[1 ... heap.heap_size],
 *	where 0 <= heap.heap_size <= heap.length are valid elements of the heap.
 *
 *
 *  javac HeapBasedMedianMaintenanceAlgo.java
 *  java HeapBasedMedianMaintenanceAlgo
 */

import java.util.*;
import java.io.*;
import java.math.*;
import java.lang.*;

class Heap{
    // instance variable
    Long[] heap;
    Map<Long,Integer> v_pos_map = new HashMap<Long,Integer>();
    // two attributes
    int heap_size;
    int heap_length;

    // constructor
    public Heap(int m){
        this.heap = new Long[m];
        heap_length = heap.length - 1;
        heap_size = 0;

        // for(int i = 1; i <= heap_length; i++){
        //     v_pos_map.put(heap[i],i);
        // }
    }

    public int parent(int i){
        return  (int) Math.floor(i/2.0);
    }

    public int left(int i){
        return 2 * i;
    }

    public int right(int i){
        return 2 * i + 1;
    }

    public boolean isEmpty(){
        return heap_size == 0;
    }

    public int v_pos_map_getPos(Long key){
        return v_pos_map.get(key);
    }

    // swap
    public void swap(int i, int j){
        v_pos_map.replace(heap[i], j);
        v_pos_map.replace(heap[j], i);
        Long temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;

    }


    /*
     *	Maintain the heap property
     *
     *	this assumes binary trees rooted at left(i) and right(i) are min-heaps
     *	but that heap[i] might be larger than its children, and thus violating
     *	min-heap property.
     *
     *	Min-heapify lets the value at heap[i] "float down" in the min-heap so
     *	the subtree rooted at index i obeys the min-heap property.
     *
     */
    public void minHeapify(int i){
        int l = left(i);
        int r = right(i);
        int smallest;
        if(l <= heap_size && ( heap[l].compareTo(heap[i]) < 0 )  ){
            smallest = l;
        } else{
            smallest = i;
        }

        if(r <= heap_size && ( heap[r].compareTo(heap[smallest] ) < 0) ){
            smallest = r;
        }

        if(smallest != i){
            swap(i, smallest);
            minHeapify(smallest);
        }
    }

    public void maxHeapify(int i){
        int l = left(i);
        int r = right(i);
        int largest;
        if(l <= heap_size && (heap[l].compareTo(heap[i]) > 0 )){
            largest = l;
        } else{
            largest = i;
        }
        if(r <= heap_size && (heap[r].compareTo(heap[largest]) > 0 )){
            largest = r;
        }
        if(largest != i){
            swap(i,largest);
            maxHeapify(largest);
        }
    }

    public void buildMaxHeap(){
        heap_size = heap_length;
        int initial = (int) Math.floor(heap_length/2);
        for(int i = initial; i >= 1; i--){
            maxHeapify(i);
        }
    }

    /*
     *	Building a Min-heap in a bottom-up manner to convert an array into a min-heap
     *
     *	it goes through remaining nodes of the tree and runs MinHeapify on each one
     *
     */
    public void buildMinHeap(){
        heap_size = heap_length;
        int initial = (int) Math.floor(heap_length/2);
        for(int i = initial ; i >= 1; i --){
            minHeapify(i);
        }
    }

    /*
     * HeapSort builds a Min-heap on the E[] heap array, heap[1 , ... , n], where n = heap_length;
     */
    public void heapSort(){
        buildMinHeap();
        for(int i = heap_length; i >= 2; i--){
            swap(1,i);
            heap_size = heap_size - 1;
            minHeapify(1);
        }
    }

    // Override MinPriorityQueue
    public Long heapMinimum(){
        return heap[1];
    }

    public Long heapMaximum(){
        return heap[1];
    }

    /*
     *	The procedure Heap-Extract-Min implements the Extract-Min operation.
     *
     */
    public Long heapExtractMin(){
        if(heap_size < 1){
            System.out.println( "heap underflow");
        }
        Long min = heap[1];
        swap(1,heap_size); 	// delete min Key
        heap_size--;
        minHeapify(1);
        return min;
    }

    public Long heapExtractMax(){
        if(heap_size < 1){
            System.out.println("heap underflow");
        }
        Long max = heap[1];
        swap(1,heap_size);
        heap_size--;
        maxHeapify(1);
        return max;
    }

    /*
     *	This procedure implements the Decrease-Key operation.
     *	An index i into the array identifies the priority queue element whose key we wish to decrease.
     *
     *	The procedure first updates the key of element heap[i] to its new value. Because decreasing the
     *	key of heap[i] might violate the min-heap property, this in turns warrant the method below.
     *
     *
     *	heapDecreaseKey traverses this path, it repeatedly compares to an element to its parent,
     *	exchanging their keys and continuing if the element's key is smaller and terminating if
     *	the element's key is bigger
     */
    public void heapDecreaseKey(int i, Long key){
        if( (key.compareTo(heap[i])) > 0 ){
            System.out.println("New key is larger than current key");
        }
        heap[i] = key;
        while(i > 1 && heap[parent(i)].compareTo(heap[i]) > 0 ){
            swap(i,parent(i));
            i = parent(i);
        }
    }

    /*
     *	MinHeapInsert implements the Insert operation.
     *
     *	The procedure expands the min-heap by adding to the tree a new leaf whose key is + infinity.
     *	This calls HeapInsertKey to set the key of ths new node to its correct value and maintain the
     *	min-heap property.
     *
     */
    public void minHeapInsert(Long key){
         heap_size = heap_size + 1;
         heap[heap_size] = Long.MAX_VALUE; 			//<------- CHANGE HERE
         heapDecreaseKey(heap_size, key);
    }

    public void heapIncreaseKey(int i, Long key){
        if( (key.compareTo(heap[i])) < 0 ){
            System.out.println(" New key is smaller than current key");
        }
        heap[i] = key;
        while(i > 1 && heap[parent(i)].compareTo(heap[i]) < 0){
            swap(i, parent(i));
            i = parent(i);
        }
    }

    public void maxHeapInsert(Long key){
        heap_size = heap_size + 1;
        heap[heap_size] = Long.MIN_VALUE;
        heapIncreaseKey(heap_size,key);
    }

    public void displayHeap(){
        for(int i = 1; i <= heap_size; i++){
            System.out.print( heap[i] + " ");
        }
        System.out.println("");
    }
}

class HeapBasedMedianMaintenanceAlgo{
    public static void main(String[] args){
        try{
            Long [] medians = new Long[10000];
            // h_low supports extract max
            Heap h_low = new Heap(5001);
            // h_high supports extract min
            Heap h_high = new Heap(5001);

            FileInputStream fil = new FileInputStream("Median.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fil));
            String element = null;
            int j = 0;
            while( (element = br.readLine()) != null){
                String[] line = element.split("//s+");
                medians[j] = medianMaintanenceAlgo(h_low,h_high, Long.parseLong(line[0]));
                j++;
            }

            long sum = 0;
            for(int i = 0; i < medians.length; i++){
                if(medians[i] != null){
                    // System.out.println("medians = " + medians[i]);
                    sum += medians[i];
                }
            }
            System.out.println("The answer = " + (sum % 10000));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Long medianMaintanenceAlgo(Heap h_low, Heap h_high, Long key){
        if(h_low.heap_size <= 0 || h_high.heap_size <= 0){
            h_low.maxHeapInsert(key);
        }else if(h_low.heapMaximum() <= key){
            h_high.minHeapInsert(key);
        } else if(h_high.heapMinimum() >= key){
            h_low.maxHeapInsert(key);
        }

        if((h_low.heap_size - h_high.heap_size) > 1){
            Long i = h_low.heapExtractMax();
            h_high.minHeapInsert(i);

        } else if((h_high.heap_size - h_low.heap_size) > 1){
            Long i = h_high.heapExtractMin();
            h_low.maxHeapInsert(i);
        }


        int index = h_low.heap_size + h_high.heap_size;
        int m = 0;
        if( (index % 2) == 0){
            m = index / 2;
        } else{
            m = (index + 1)/2;
        }

        if(m <= h_low.heap_size){
            return h_low.heapMaximum();
        } else{
            return h_high.heapMinimum();
        }
    }
}
