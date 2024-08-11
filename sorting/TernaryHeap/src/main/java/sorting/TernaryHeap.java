package sorting;

/**
 * A max-heap is a hierarchical tree structure with the following invariants:
 *  - The tree is essentially complete, i.e., all levels of the tree are filled except possibly the right most child part of the last one,
 *  - For any node in the tree, the value associated with the node is greater or equal than the values of its children.
 *
 * Consequently, the maximum value is situated at the root and can be accessed in constant time.
 * Notably, this invariant must be maintained after insertions or removals.
 *
 * In this assignment, your task is to implement the insert, size, getMax, and delMax operations for a ternary max heap data structure
 * implemented with an array.
 * In a ternary max heap, each node can have at most three children, and all children have values lower than the parent node.
 * The tree is represented by the array `content`, where the parent-child relationship is implicitly defined by indices.
 * Specifically, a node at index i has three children at indices 3i+1, 3i+2, and 3i+3.
 * It is assumed that the root is at index 0 in the array.
 *
 * For instance, consider a heap with a capacity of 6. After inserting numbers <8,2,3,8,9> in this order, the array content should be as follows:
 *  content: [9, 8, 3, 8, 2, 0], the size = 5 and the heap looks like this :
 *
 *                                                  9
 *                                                  |
 *                                       ----------------------
 *                                       |          |         |
 *                                       8          3         8
 *                                       |
 *                                       2
 *
 * Now after deleting the max, the array content should be content : [8, 2, 3, 8, (9), 0] and the size = 4 and the heap :
 *                                                  8
 *                                                  |
 *                                        ----------------------
 *                                        |          |         |
 *                                        2          3         8
 *
 *  Notice that we left the 9 in the array, but it is not part of the heap anymore since the size is 4.
 *
 * To remove the maximum element from the heap while maintaining its structure,
 * the approach involved swapping the root with last element of the last layer (at position size-1) in the content array.
 * Subsequently, re-heapify the structure by allowing the new root to sink using swap with the largest of its children
 * until it is greater than all its children or reaches a leaf.
 *
 * Complete the implementation of the TernaryHeap class.
 *
 * The insert operation should run in O(log_3(n)) time, where n is the number of elements in the heap.
 * The delMax operation should run in O(log_3(n)) time, where n is the number of elements in the heap.
 * The getMax operation should run in O(1) time.
 *
 * Debug your code on the small examples in the test suite.
 */
public class TernaryHeap {

    // Array representing the heap. This is where all the values must be added
    // let this variable protected so that it can be accessed from the test suite
    protected int[] content;
    int size;


    /**
     * Initializes an empty max-heap with the given initial capacity.
     * @param capacity : the initial capacity of the heap
     */
    public TernaryHeap(int capacity) {
        this.content = new int[capacity];
        this.size = 0;
    }

    /**
     * @return the number of keys currently in the heap.
     */
    public int size() {
        return this.size;
    }

    public void swap(int ind1, int ind2){
        int temp = content[ind2];
        content[ind2] = content[ind1];
        content[ind1] = temp;
    }



    /**
     * Inserts a new key into the heap. After this method is finished, the heap-invariant must be respected.
     * @param x The key to be inserted
     */
    public void insert(int x) {
        /* Idea : put new item at the end, and swim it back up */
        this.size++;
        if (this.size >= this.content.length) doubleArray(this.content);
        this.content[this.size - 1] = x;
        /* Swim back up to restore the heap proprety */
        this.swim(this.size - 1);
    }

    private void swim(int x){
        while (x >= 1 && this.content[(x-1)/3] < this.content[x]){
            this.swap(x, (x-1)/3);
            x = (x-1) / 3;
        }
    }

    private void doubleArray(int[] array){
        int[] newArray = new int[array.length * 2];
        for (int a = 0; a < array.length; a++) newArray[a] = array[a];
        array = newArray;
    }

    /**
     * Removes and returns the largest key on the heap. After this method is finished, the heap-invariant must be respected.
     * @return The largest key on the heap
     */
    public int delMax() {
        /* Idea : yoink a value from the bottom all the way to the root, then sink it */
        int max = this.content[0];
        this.swap(0, this.size - 1);
        this.size--;
        /* now sink the new root back to a correct position */
        this.sink(0);
        return max;
    }

    private void sink(int x){
        while (3*x < this.size - 1){
            int j = 3*x;
            int swapOff = findSwapOffset(x,j);
            if (swapOff == 0) break;
            j += swapOff;
            this.swap(x,j);
            x = j;
        }
    }
    private int findSwapOffset(int currPos, int childPos){
        int checkVal = this.content[currPos];
        int offset = 0;
        for (int a = 1; a <= 3; a++){
            if (childPos+a >= this.size) break;
            if (checkVal < this.content[childPos+a]) {checkVal = this.content[childPos+a]; offset = a;}
        }
        return offset;
    }
    

    /**
     * @return The largest key on the heap without removing it.
     */
    public int getMax() {
        return this.content[0];
    }


}
