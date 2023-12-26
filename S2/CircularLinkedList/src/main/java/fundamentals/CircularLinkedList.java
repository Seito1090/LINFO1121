package fundamentals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author Pierre Schaus
 *
 * We are interested in the implementation of a circular simply linked list,
 * i.e. a list for which the last position of the list refers, as the next position,
 * to the first position of the list.
 *
 * The addition of a new element (enqueue method) is done at the end of the list and
 * the removal (remove method) is done at a particular index of the list.
 *
 * A (single) reference to the end of the list (last) is necessary to perform all operations on this queue.
 *
 * You are therefore asked to implement this circular simply linked list by completing the class see (TODO's)
 * Most important methods are:
 *
 * - the enqueue to add an element;
 * - the remove method [The exception IndexOutOfBoundsException is thrown when the index value is not between 0 and size()-1];
 * - the iterator (ListIterator) used to browse the list in FIFO.
 *
 * @param <Item>
 */
public class CircularLinkedList<Item> implements Iterable<Item> {

    private long nOp = 0; // count the number of operations
    private int n;          // size of the stack
    private Node  last;   // trailer of the list

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
    }

    public CircularLinkedList() {
        this.last = null;
        this.n = 0;
    }

    public boolean isEmpty() {return n == 0;}

    public int size() {return n;}

    private long nOp() {
        return nOp;
    }


    /**
     * Append an item at the end of the list
     * @param item the item to append
     */
    public void enqueue(Item item) {
        //Case where the list is initially empty
        Node newLast = new Node();
        newLast.item = item;
        if (isEmpty()){
            newLast.next = newLast;
        } else {
            newLast.next =  this.last.next;
            this.last.next = newLast;
        }
        this.last = newLast;
        this.n++;
        this.nOp++;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     */
    public Item remove(int index) {
        Item item;
        //First check if the index is completely valid / preliminary checks
        if (this.isEmpty()){
            throw new NoSuchElementException("The list is empty, make sure you used what you wanted !");
        }
        if (index >= this.n){
            throw new IndexOutOfBoundsException("Dumb kid, you're out of range :)");
        }

        //If it is alright, check if the list is only one in size
        if (this.n <= 1){
            item = this.last.item;
            this.last = null;
        } else {
            //Get to the intended node
            Node previousNode = this.last;
            Node currentNode = this.last;

            for (int a = 0; a < index+1; a ++){
                previousNode = currentNode;
                currentNode = currentNode.next;
            }

            item = currentNode.item;
            previousNode.next = currentNode.next;
        }
        this.n--;
        this.nOp++;
        return item;
    }


    /**
     * Returns an iterator that iterates through the items in FIFO order.
     * @return an iterator that iterates through the items in FIFO order.
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /**
     * Implementation of an iterator that iterates through the items in FIFO order.
     * The iterator should implement a fail-fast strategy, that is ConcurrentModificationException
     * is thrown whenever the list is modified while iterating on it.
     * This can be achieved by counting the number of operations (nOp) in the list and
     * updating it everytime a method modifying the list is called.
     * Whenever it gets the next value (i.e. using next() method), and if it finds that the
     * nOp has been modified after this iterator has been created, it throws ConcurrentModificationException.
     */
    private class ListIterator implements Iterator<Item> {

        // TODO You probably need a constructor here and some instance variables
        Node current;
        int index = 0;
        long nOpS;

        public ListIterator() {
            this.current = last;
            this.nOpS = nOp();
        }


        @Override
        public boolean hasNext() {
            return this.index < size();
        }

        @Override
        public Item next() {
            if (this.nOpS != nOp()) {
                throw new ConcurrentModificationException();
            }

            if (!hasNext()) {
                return null;
            }
            this.current = this.current.next;
            this.index++;
            return this.current.item;
        }

    }

}