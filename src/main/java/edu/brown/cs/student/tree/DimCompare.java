package edu.brown.cs.student.tree;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Class for comparing stars along the X axis.
 */
public class DimCompare implements Comparator<ValuePoint>, Serializable {

  private static final long serialVersionUID = 1L;
  private final int dim;

  /**
   * Constructor with a dimension passed to it.
   *
   * @param dim the dimension, with index starting at 1
   */
  public DimCompare(int dim) {
    this.dim = dim;
  }

  /**
   * Compares two coordinates along the x dimension.
   *
   * @param t1 The first coordinate object
   * @param t2 The second coordinate object
   * @return -1, 0, or 1 if t1 is less than, equal to, or greater than t2
   */
  @Override
  public int compare(ValuePoint t1, ValuePoint t2) {
    float[] arr1 = t1.rootToArray();
    float[] arr2 = t2.rootToArray();
    int trueDim = dim - 1;
    return Float.compare(arr1[trueDim], arr2[trueDim]);
  }
}
