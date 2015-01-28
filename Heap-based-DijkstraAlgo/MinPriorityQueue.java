import java.lang.*;

public interface MinPriorityQueue<E>{
    public int v_pos_map_getPos(E key);
    public boolean isEmpty();
    public void minHeapify(int i);
    public void buildMinHeap();
    public void heapSort();
    public E heapMinimum();
    public E heapExtractMin();
    public void heapDecreaseKey(int i, E key);

    // public void minHeapInsert(E key);

}
