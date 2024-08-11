package fundamentals;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        queue1 = new ArrayDeque();
        queue2 = new ArrayDeque();
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
           /*
          * Idea here is to make a main queue and a secondary one (since we have to use 2 of them)
          * Basically the main will be used as storage and the other will be used for push and/or delete operations
          * to maintain the order
          * */
          if (empty()) throw new EmptyStackException();

          while (queue1.size() > 1 ){
               queue2.add(queue1.poll());
          }

          E element = queue1.peek();

          while (!queue2.isEmpty()){
               queue1.add(queue2.poll());
          }

          return element;
    }

    /**
     * Remove the first element of the stack and returns it
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() throws EmptyStackException {
          if (empty()) throw new EmptyStackException();

          // get to the first element 
          while (queue1.size() > 1){
               queue2.add(queue1.poll());
          }

          E element = queue1.poll();

          // Put them back 
          while (!queue2.isEmpty()){
               queue1.add(queue2.poll());
          }

          return element;
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
