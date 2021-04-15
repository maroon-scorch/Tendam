import edu.brown.cs.student.algorithm.BoundedPriorityQueue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing methods in the BoundedPriorityQueue class.
 */
public class BoundedPriorityQueueTest {
  BoundedPriorityQueue<Integer> bpq1;
  BoundedPriorityQueue<Integer> bpq2;

  /**
   * Sets up the function to run prior to the tests
   */
  @Before
  public void setUp() {
    bpq1 = new BoundedPriorityQueue<>(1);
    bpq2 = new BoundedPriorityQueue<>(2);
  }

  /**
   * Resets the bpq values.
   */
  @After
  public void tearDown() {
    bpq1 = null;
    bpq2 = null;
  }

  /**
   * Tests the constructor accessor methods.
   */
  @Test
  public void testConstructor() {
    setUp();
    BoundedPriorityQueue<Integer> queue = new BoundedPriorityQueue<>(1);
    assertEquals(1, queue.getBound());
    assertTrue(queue.getPq().isEmpty());
    tearDown();
  }

  @Test
  public void testAdd() {
    setUp();
    bpq1.add(1);
    assertEquals(1, (long) bpq1.peekLowestPriority());
    bpq1.add(6);
    assertEquals(1, (long) bpq1.peekLowestPriority());
    bpq1.add(1);
    assertEquals(1, (long) bpq1.peekLowestPriority());
    bpq2.add(40);
    bpq2.add(50);
    assertEquals(50, (long) bpq2.peekLowestPriority());
    bpq2.add(1);
    assertEquals(40, (long) bpq2.peekLowestPriority());
    tearDown();
  }

  @Test
  public void testSize() {
    setUp();
    assertEquals(0, bpq1.size());
    assertEquals(0, bpq2.size());
    bpq1.add(5);
    bpq1.add(10);
    bpq1.add(50);
    bpq2.add(5);
    bpq2.add(10);
    bpq2.add(50);
    assertEquals(1, bpq1.size());
    assertEquals(2, bpq2.size());
    tearDown();
  }

  @Test
  public void testToList() {
    setUp();
    assertTrue(bpq1.toList().isEmpty());
    assertTrue(bpq2.toList().isEmpty());
    bpq1.add(5);
    bpq1.add(10);
    bpq1.add(50);
    bpq2.add(5);
    bpq2.add(10);
    bpq2.add(50);
    List<Integer> bpq1List = new ArrayList<>();
    bpq1List.add(5);
    List<Integer> bpq2List = new ArrayList<>();
    bpq2List.add(5);
    bpq2List.add(10);
    assertEquals(bpq1List, bpq1.toList());
    assertEquals(bpq2List, bpq2.toList());
  }
}
