package fundamentals;

import java.util.EmptyStackException;

/**
 * Author: Pierre Schaus
 *
 * You have to implement the interface using
 * - a simple linkedList as internal structure
 * - a growing array as internal structure
 */
public interface Stack<E> {

    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack
     */
    public boolean empty();

    /**
     * Returns the first element of the stack, without removing it from the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E peek() throws EmptyStackException;

    /**
     * Remove the first element of the stack and returns it
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() throws EmptyStackException;

    /**
     * Adds an element to the stack
     *
     * @param item the item to add
     */
    public void push(E item);

}

/**
 * Implement the Stack interface above using a simple linked list.
 * You should have at least one constructor withtout argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
class LinkedStack<E> implements Stack<E> {

    private Node<E> top;        // the node on the top of the stack
    private int size;        // size of the stack

    // helper linked list class
    private class Node<T> {
        private T item;
        private Node<T> next;

        public Node(T element, Node<T> next) {
            this.item = element;
            this.next = next;
        }
    }

    @Override
    public boolean empty() {
        return this.size == 0;
    }

    @Override
    public E peek() throws EmptyStackException {
        if (this.empty()) throw new EmptyStackException();
        return top.item;
    }

    @Override
    public E pop() throws EmptyStackException {
        if (this.empty()) throw new EmptyStackException();
        E topItem = this.top.item;
        //Put a new top
        this.top = this.top.next;
        this.size--;
        return topItem;
    }

    @Override
    public void push(E item) {
        Node<E> newTop = new Node<>(item, this.top);
        this.top = newTop;
        this.size++;
    }
} 


/**
 * Implement the Stack interface above using an array as internal representation
 * The capacity of the array should double when the number of elements exceed its length.
 * You should have at least one constructor withtout argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
class ArrayStack<E> implements Stack<E> {

    private E[] array;        // array storing the elements on the stack
    private int size;        // size of the stack

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        array = (E[]) new Object[10];
    }

    @Override
    public boolean empty() {
         return this.size == 0;
    }

    @Override
    public E peek() throws EmptyStackException {
        if (this.empty()) throw new EmptyStackException();
        return this.array[this.size - 1];
    }

    @Override
    public E pop() throws EmptyStackException {
        if (this.empty()) throw new EmptyStackException();
        E element = this.array[0];
        for (int a = 0; a < this.size-1; a++){
            this.array[a] = this.array[a+1];
        }
        this.size--;
        return element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void push(E item) {
        // Warning for a possible overflow, need to adjust the size of the array if the one we have is too small
        if (this.size + 1 >= this.array.length){
            E[] newArray = (E[]) new Object[this.array.length*2];
            for (int a = 0; a < this.size; a++){
                newArray[a] = this.array[a];
            }
            this.array = newArray;
        }
        E prev = this.array[0];
        E curr = this.array[0];
        for (int a = 1; a <= this.size; a++){
            curr = this.array[a];
            this.array[a] = prev;
            prev = curr;
        }
        this.array[0] = item;
        this.size++;
    }
}

