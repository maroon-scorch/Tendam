package edu.brown.cs.student.KdTree;


import java.util.List;
import java.util.TreeMap;

/**
 * A class to associate a TreeMap with an int (the int indicating how many objects are in the map.
 *
 * @param <T> - the type of object stored in the tree
 */
public class IntTreePair<T extends Dimensionable> {

  // fields of the IntTreePair (the int and tree)
  private int i;
  private TreeMap<Double, List<T>> tree;

  /**
   * Initializes the pair, setting the int and tree.
   *
   * @param i    - the int
   * @param tree - the TreeMap
   */
  public IntTreePair(int i, TreeMap<Double, List<T>> tree) {
    this.i = i;
    this.tree = tree;
  }

  /**
   * Returns the tree stored in the pair.
   *
   * @return the TreeMap
   */
  public TreeMap<Double, List<T>> getTree() {
    return this.tree;
  }

  /**
   * Sets the TreeMap.
   *
   * @param tree - the TreeMap to set the tree field with
   */
  public void setTree(TreeMap<Double, List<T>> tree) {
    this.tree = tree;
  }

  /**
   * Returns the int stored in the pair.
   *
   * @return the int
   */
  public int getInt() {
    return this.i;
  }

  /**
   * Sets the int.
   *
   * @param i - the int to set the int field with
   */
  public void setI(int i) {
    this.i = i;
  }
}
