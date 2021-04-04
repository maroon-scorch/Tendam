package edu.brown.cs.student.tree;

import edu.brown.cs.student.miscenllaneous.CustomException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Class for implementing and running through KD-Trees.
 *
 * @param <T> the type of tree
 */
public class Tree<T extends ValuePoint> {

  private final T root;
  private final Tree<T> leaf1;
  private final Tree<T> leaf2;
  private Tree<T> storedTree = null;

  private double worstDist = -1;
  private static final NeighborCompare NEIGHCOMPARATOR = new NeighborCompare();
  private static final PriorityQueue<Neighbor> VISITED
          = new PriorityQueue<>(NEIGHCOMPARATOR);

  /**
   * Tree constructor.
   *
   * @param root  the current root of the node
   * @param leaf1 the left leaf
   * @param leaf2 the right leaf
   */
  public Tree(T root, Tree<T> leaf1, Tree<T> leaf2) {
    this.root = root;
    this.leaf1 = leaf1;
    this.leaf2 = leaf2;
  }

  /**
   * Tree constructor but empty.
   */
  public Tree() {
    this.root = null;
    this.leaf1 = null;
    this.leaf2 = null;
  }

  /**
   * Method for creating the range needed in createTree.
   *
   * @param num the dimensional size
   * @return a list of integers from 1 to num.
   */
  public static List<Integer> createRange(int num) {
    List<Integer> results = new ArrayList<>();
    for (int i = 1; i < num; i++) {
      results.add(i);
    }
    return results;
  }

  /**
   * Shuffles the first item in this list of integers to end of the list.
   *
   * @param given the given list
   * @return an List of integers
   */
  public static List<Integer> rotateList(List<Integer> given) {
    if (!given.isEmpty()) {
      int first = given.get(0);
      given.remove(0);
      given.add(first);
    }
    return given;
  }

  /**
   * A wrapper method for buildTree which stored the built tree in the appropriate place.
   *
   * @param inputData the given input list
   * @param range     the input range
   * @return a Tree of type T
   * @throws CustomException.NotWithinRangeException if the range is not within scope
   * @throws CustomException.InvalidTypeException    if the type is not accepted
   */
  public Tree<T> buildTreeWrapper(List<T> inputData, List<Integer> range)
          throws CustomException.NotWithinRangeException, CustomException.InvalidTypeException {
//    if (type == Stars.class) {
//      storedStarsTree = buildTree(inputData, range);
//      return storedStarsTree;
//    } else {
//      storedNodesTree = buildTree(inputData, range);
//      return storedNodesTree;
//    }

    storedTree = buildTree(inputData, range);
    return storedTree;

  }

  /**
   * Builds a valuePoint tree through recursion.
   *
   * @param inputData the given list of data of type T
   * @param range     the dimensional range representation
   * @return a tree of type T
   * @throws CustomException.NotWithinRangeException if the range is not within scope
   */
  public Tree<T> buildTree(List<T> inputData, List<Integer> range)
          throws CustomException.NotWithinRangeException {
    if (inputData.size() == 1) {
      return new Tree<>(inputData.get(0), null, null);
    } else if (inputData.isEmpty()) {
      return null;
    }
    List<T> sortedOnDim = sortDim(inputData, range);
    int medianIndex = median(sortedOnDim);
    T chosenRoot = inputData.get(medianIndex);
    List<T> lower = inputData.subList(0, medianIndex);
    List<T> higher = inputData.subList(medianIndex + 1, inputData.size());

    return new Tree<>(chosenRoot, buildTree(lower,
            rotateList(range)), buildTree(higher,
            rotateList(range)));
  }

  /**
   * finds the k nearest neighbors.
   *
   * @param chosenTree the input tree to search
   * @param inputData  the given valuePoint data
   * @param k          the number of nearest neighbors to return
   * @param endCoords  an array of floats representing the valuePoints of the central point
   * @param type       the class type of the data
   * @return the k nearest neighbors
   * @throws CustomException.NotWithinRangeException if a x, y, or z has not been inputted
   * @throws CustomException.InvalidTypeException    if the type is not accepted
   */
  public List<Neighbor> nearestNeighbors(Tree<T> chosenTree, List<T> inputData,
                                         int k, float[] endCoords, Class<?> type)
          throws CustomException.NotWithinRangeException, CustomException.InvalidTypeException {

    clearWorstDist();
    List<Neighbor> finalList = new ArrayList<>();
    List<Integer> range;

    range = getRangeByType(type);

    if (chosenTree == null) {
      chosenTree = buildTreeWrapper(inputData, range, type);
    }

    if (type == Stars.class) {
      storedStarsTree = chosenTree;
    } else {
      storedNodesTree = chosenTree;
    }

    recurNeighbors(chosenTree, range, k, endCoords, type);

    for (int i = 0; i < Math.min(k, inputData.size() + 1); i++) {
      if (type == Stars.class) {
        finalList.add(STARSVISITED.poll());
      } else {
        finalList.add(NODESVISITED.poll());
      }
    }

    finalList.removeAll(Collections.singleton(null));
    return finalList;
  }

  /**
   * finds the k nearest neighbors based on a given node name.
   *
   * @param chosenTree the tree to recurse over
   * @param inputData  the loaded input data
   * @param k          the number of valuePoints types to return
   * @param inputName  the name of the input node
   * @param type       the class type of the data
   * @return the k nearest neighbors
   * @throws CustomException.NotWithinRangeException     if a x, y, or z is not provided
   * @throws CustomException.ValuePointNotFoundException if the valuePoint could not be found
   * @throws CustomException.InvalidTypeException        if the type is not accepted
   */
  public List<Neighbor> nearestNeighbors(Tree<T> chosenTree,
                                         List<T> inputData, int k, String inputName, Class<?> type)
          throws CustomException.NotWithinRangeException,
          CustomException.InvalidTypeException,
          CustomException.ValuePointNotFoundException {

    List<Neighbor> finalList = new ArrayList<>();
    List<Neighbor> cutFinalList = new ArrayList<>();
    List<Integer> range;
    int foundIndex;

    range = getRangeByType(type);

    if (chosenTree == null) {
      chosenTree = buildTree(inputData, range);
    }

    List<ValuePoint> castInput = new ArrayList<>();
    for (T datum : inputData) {
      try {
        castInput.add(datum);
      } catch (ClassCastException e) {
        throw new CustomException.InvalidTypeException();
      }
    }

    ValuePoint foundCor = chosenTree.getRoot().findWithNodeName(
            castInput, inputName);

    recurNeighbors(chosenTree, range, k,
            foundCor.rootToArray(), type);


    for (int i = 0; i < inputData.size(); i++) {
      if (type == Stars.class) {
        finalList.add(STARSVISITED.poll());
      } else {
        finalList.add(NODESVISITED.poll());
      }
    }

    finalList.removeAll(Collections.singleton(null));
    foundIndex = foundCor.whereIndex(finalList);

    if (foundIndex != -1) {
      finalList.remove(foundIndex);
    }

    for (int i = 0; i < Math.min(k, finalList.size()); i++) {
      cutFinalList.add(finalList.get(i));
    }

    return cutFinalList;
  }

  /**
   * Produces a range of numbers from 1 to the nth dimension of the data type.
   *
   * @param type the given data type
   * @return a range of numbers
   * @throws CustomException.InvalidTypeException if the type is not accepted
   */
  private List<Integer> getRangeByType(Class<?> type)
          throws CustomException.InvalidTypeException {
    int dim;
    List<Integer> range;
    if (Stars.class.equals(type)) {
      STARSVISITED.clear();
      dim = 3;
    } else if (Node.class.equals(type)) {
      NODESVISITED.clear();
      dim = 2;
    } else {
      throw new CustomException.InvalidTypeException();
    }

    range = createRange(dim);
    return range;
  }

  /**
   * Produces a range of numbers from 1 to the nth dimension of the data type.
   *
   * @param type the given data type
   * @return a range of numbers
   * @throws CustomException.InvalidTypeException if the type is not accepted
   */
  private List<Integer> getRangeFromMap(Class<?> type)
          throws CustomException.InvalidTypeException {

    range = createRange(dim);
    return range;
  }

  /**
   * finds the neighbors within the given radius of the target point.
   *
   * @param chosenTree the tree to recurse over
   * @param inputData  the given stars data
   * @param r          the radius
   * @param endCoords  the central valuePoints
   * @return all the neighbors within the given radius of the target point
   * @throws CustomException.NotWithinRangeException if no x, y, or z has been inputted
   * @throws CustomException.InvalidTypeException    if the type is not accepted
   */
  public List<Neighbor> radiusNeighbors(Tree<T> chosenTree,
                                        List<T> inputData, float r, float[] endCoords)
          throws CustomException.NotWithinRangeException,
          CustomException.InvalidTypeException {

    List<Neighbor> clone = new ArrayList<>();
    List<Neighbor> neighborData =
            nearestNeighbors(chosenTree, inputData,
                    inputData.size(), endCoords, Stars.class);

    for (Neighbor datum : neighborData) {
      if (datum != null && datum.getDist() <= (r * r)) {
        clone.add(datum);
      }
    }
    return clone;
  }

  /**
   * finds the neighbors within the given radius of the given star.
   *
   * @param chosenTree the tree to recurse over
   * @param inputData  the given stars data
   * @param r          the radius
   * @param starName   the name of the star
   * @return the neighbors within the given radius
   * @throws CustomException.NotWithinRangeException     if an x, y, or z has not been inputted
   * @throws CustomException.valuePointNotFoundException if the valuePoint
   * @throws CustomException.InvalidTypeException        if the type is not accepted
   *                                                     of given name cannot be found
   */
  public List<Neighbor> radiusNeighbors(Tree<T> chosenTree,
                                        List<T> inputData, float r, String starName)
          throws CustomException.NotWithinRangeException,
          CustomException.ValuePointNotFoundException,
          CustomException.InvalidTypeException {

    List<Neighbor> clone = new ArrayList<>();
    List<Neighbor> neighborData =
            nearestNeighbors(chosenTree, inputData, inputData.size(),
                    starName, Stars.class);

    for (Neighbor datum : neighborData) {
      if (datum != null && datum.getDist() <= (r * r)) {
        clone.add(datum);
      }
    }
    return clone;
  }

  /**
   * Runs the iterative aspect of searching for the nearest neighbors.
   *
   * @param inputTree the current kd-tree
   * @param range     the list representing the current dimension
   * @param k         the number of total neighbors to find
   * @param endCoords the central valuePoints
   * @param type      the given input type
   */
  public void recurNeighbors(
          Tree<T> inputTree, List<Integer> range, int k, float[] endCoords, Class<?> type) {

    double distanceToThresh;
    Tree<T> newNode;
    Tree<T> otherNode;
    T currentRoot;
    double currentDistance;
    Neighbor foundNeighbor;

    if (inputTree != null) {
      currentRoot = inputTree.getRoot();
      currentDistance = currentRoot.calcDist(endCoords);
      float[] arrayOfCurrentCoords = currentRoot.rootToArray();

      if (worstDist == -1) {
        worstDist = currentDistance;
      }
      int dim = range.get(0) - 1;

      if (endCoords[dim] <= arrayOfCurrentCoords[dim]) {
        newNode = inputTree.getLeaf1();
        otherNode = inputTree.getLeaf2();
      } else {
        newNode = inputTree.getLeaf2();
        otherNode = inputTree.getLeaf1();
      }
      distanceToThresh = Math.pow(arrayOfCurrentCoords[dim] - endCoords[dim], 2);

      foundNeighbor = currentRoot.createNeighbor(currentDistance);

      if (type == Stars.class) {
        if (currentDistance > worstDist && STARSVISITED.size() < k) {
          worstDist = currentDistance;
          STARSVISITED.add(foundNeighbor);
        } else if ((currentDistance <= worstDist)
                || STARSVISITED.size() < k) {
          STARSVISITED.add(foundNeighbor);
        }
      } else {
        if (currentDistance > worstDist && NODESVISITED.size() < k) {
          worstDist = currentDistance;
          NODESVISITED.add(foundNeighbor);
        } else if ((currentDistance <= worstDist)
                || NODESVISITED.size() < k) {
          NODESVISITED.add(foundNeighbor);
        }
      }

      if (worstDist >= distanceToThresh) {
        recurNeighbors(otherNode, rotateList(range),
                k, endCoords, type);
      }
      recurNeighbors(newNode, rotateList(range),
              k, endCoords, type);
    }
  }


  /**
   * Finds the median of a sorted list of valuePoints.
   *
   * @param sorted the sorted list of valuePoints
   * @return the index of the valuePoint
   */
  public int median(List<T> sorted) {
    int index;
    if (sorted.size() % 2 == 1) {
      index = (int) Math.floor(sorted.size() / 2.0);
    } else {
      index = (sorted.size() / 2);
    }
    return (index);
  }

  /**
   * Sorts a list of stars along a given dimension.
   *
   * @param inputData the given stars data
   * @param range     the list of dimensions as a range
   * @return a list of sorted stars
   * @throws CustomException.NotWithinRangeException if numerical scope is outside range
   */
  public List<T> sortDim(List<T> inputData, List<Integer> range)
          throws CustomException.NotWithinRangeException {
    switch (range.get(0)) {
      case (1):
        inputData.sort(new DimCompare(1));
        break;
      case (2):
        inputData.sort(new DimCompare(2));
        break;
      case (3):
        inputData.sort(new DimCompare(3));
        break;
      default:
        throw new CustomException.NotWithinRangeException();
    }
    return inputData;
  }

  /**
   * Accesses the root of the current tree node.
   *
   * @return the root.
   */
  public T getRoot() {
    return root;
  }

  /**
   * Accesses leaf1 of the current tree node.
   *
   * @return leaf1
   */
  public Tree<T> getLeaf1() {
    return (leaf1);
  }

  /**
   * Accesses leaf2 of the current tree node.
   *
   * @return leaf2
   */
  public Tree<T> getLeaf2() {
    return (leaf2);
  }

  /**
   * clears the storedNodesTree and sets it to null.
   */
  public void clearStoredNodesTree() {
    storedNodesTree = null;
  }

  /**
   * clears the worstDist variable and sets it to -1.
   */
  public void clearWorstDist() {
    worstDist = -1;
  }

  /**
   * Accesses the storedTree.
   *
   * @return the storedTree
   */
  public Tree<T> getStoredTree() {
    return storedTree;
  }

}

