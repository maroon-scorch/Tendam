package edu.brown.cs.student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Class for a bounded priority queue.
 * @param <T> Any type that implements the comparable interface
 */
public class BoundedPriorityQueue<T extends Comparable<T>> {
  private int bound;
  private PriorityQueue<T> pq;

  /**
   * Constructs a bounded priority queue,
   * which stores items in reversed order of their priority.
   * @param bound max number of items in the queue
   */
  public BoundedPriorityQueue(int bound) {
    this.bound = bound;
    this.pq = new PriorityQueue<>(bound, Collections.reverseOrder());
  }

  /**
   * Inserts the item into the bounded priority queue.
   * @param item item to be inserted
   */
  public void add(T item) {
    if (pq.size() < bound) {
      pq.add(item);
      // if bound is reached, compare new item with the first item in queue
    } else {
      int comp = pq.peek().compareTo(item);
      // if new item has higher priority, take out the old item before insertion
      if (comp > 0) {
        pq.poll();
        pq.add(item);
        // if new item has equal priority, randomly decided if we insert it
      } else if (comp == 0) {
        Random random = new Random();
        if (random.nextBoolean()) {  /// need to test for randomness
          pq.poll();
          pq.add(item);
        }
      }
    }
  }

  /**
   * Retrieves, but does not remove, the head of this queue,
   * which is the element with the lowest priority,
   * or returns null if this queue is empty.
   * @return the head of the queue
   */
  public T peekLowestPriority() {
    return pq.peek();
  }

  /**
   * Returns the number of elements in the queue.
   * @return number of elements
   */
  public int size() {
    return pq.size();
  }

  /**
   * Returns a list containing all of the elements in this queue, preserving priority order.
   * @return a list of all elements
   */
  public List<?> toList() {
    PriorityQueue<T> pqTemp = new PriorityQueue<>(pq);
    List<T> list = new ArrayList<>();
    for (int i = 0; i < size(); i++) {
      list.add(pqTemp.poll());
    }
    Collections.reverse(list);
    return list;
  }
}
