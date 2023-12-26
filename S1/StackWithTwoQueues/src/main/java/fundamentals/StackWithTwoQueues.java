package fundamentals;

import java.util.ArrayDeque;
import java.util.EmptyStackException;
import java.util.Queue;

/**
 * Author: Pierre Schaus and Auguste Burlats
 * Implement the abstract data type stack using two queues
 * You are not allowed to modify or add the instance variables,
 * only the body of the methods
 */
public class StackWithTwoQueues<E> {

    Queue<E> queue1;
    Queue<E> queue2;

    public StackWithTwoQueues() {
        queue1 = new ArrayDeque<>();
        queue2 = new ArrayDeque<>();
    }

    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack
     */
    public boolean empty() {
        return queue1.isEmpty() && queue2.isEmpty();
    }

    /**
     * Returns the first element of the stack, without removing it from the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E peek() throws EmptyStackException {
        if (empty()) throw new EmptyStackException();

        while (queue1.size() > 1) {
            queue2.add(queue1.remove());
        }

        E res = queue1.peek();

        while (!queue2.isEmpty()) {
            queue1.add(queue2.remove());
        }

        return res;
    }

    /**
     * Remove the first element of the stack and returns it
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() throws EmptyStackException {

        if (empty()) throw new EmptyStackException();

        //Move all elements from queue1 to queue2 except the last one (use the second as a buffer)
        while (queue1.size() > 1) {
            queue2.add(queue1.remove());
        }

        //Remove the last element from queue1
        E res = queue1.remove();

        //Swap the queues again
        while (!queue2.isEmpty()) {
            queue1.add(queue2.remove());
        }

        return res;
    }

    /**
     * Adds an element to the stack
     *
     * @param item the item to add
     */
    public void push(E item) {
        queue1.add(item);
    }

}
