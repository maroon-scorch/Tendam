import edu.brown.cs.student.algorithm.BoundedPriorityQueue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoundedPriorityQueueTest {
  BoundedPriorityQueue bpq1;
  BoundedPriorityQueue bpq2;

  @Before
  public void setUp() {
    bpq1 = new BoundedPriorityQueue(1);
    bpq2 = new BoundedPriorityQueue(2);
  }

  @After
  public void tearDown() {
    bpq1 = null;
    bpq2 = null;
  }
  @Test
  public void testConstructor() {
    setUp();
    BoundedPriorityQueue queue = new BoundedPriorityQueue(1);
    assertEquals(queue.getBound(), 1);
    assertTrue(queue.getPq().isEmpty());
    tearDown();
  }

  @Test
  public void testAdd() {
    setUp();
    bpq1.add(1);
    assertEquals(bpq1.peekLowestPriority(), 1);
    bpq1.add(6);
    assertEquals(bpq1.peekLowestPriority(), 1);
    bpq1.add(1);
    assertEquals(bpq1.peekLowestPriority(), 1);
    bpq2.add(40);
    bpq2.add(50);
    assertEquals(bpq2.peekLowestPriority(), 50);
    bpq2.add(1);
    assertEquals(bpq2.peekLowestPriority(), 40);
    tearDown();
  }

  @Test
  public void testSize() {
    setUp();
    assertEquals(bpq1.size(), 0);
    assertEquals(bpq2.size(), 0);
    bpq1.add(5);
    bpq1.add(10);
    bpq1.add(50);
    bpq2.add(5);
    bpq2.add(10);
    bpq2.add(50);
    assertEquals(bpq1.size(), 1);
    assertEquals(bpq2.size(), 2);
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
    assertEquals(bpq1.toList(), bpq1List);
    assertEquals(bpq2.toList(), bpq2List);
  }
}
