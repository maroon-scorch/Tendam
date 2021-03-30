//package edu.brown.cs.student;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Objects;
//import java.util.PriorityQueue;
//
///**
// * Class that represents a KDTree.
// * @param <T> Any object that implements the HasCoordinate interface
// */
//public class KDTree<T extends HasCoordinate> {
//  private KDNode root;
//  private int totalDimension;
//  private int size;
//
//  /**
//   * CLass that represents a KDNode.
//   */
//  private class KDNode implements Comparable<KDNode> {
//    private T value;
//    private KDNode left, right;
//    private int dimension;
//    private double distance;
//
//    /**
//     * Creates a new Node object.
//     * @param dim Integer representing which dimension this node is in
//     * @param totalDim Integer representing total dimension
//     */
//    KDNode(T element, int dim, int totalDim) {
//      this.value = element;
//      this.left = null;
//      this.right = null;
//      this.dimension = dim % totalDim;
//    }
//
//    /**
//     * Constructs a KDNode.
//     */
//    KDNode(T element) {
//      this.left = null;
//      this.right = null;
//      this.value = element;
//    }
//
//    /**
//     * Gets the value of the node.
//     * @return The value of the node
//     */
//    public T getValue() {
//      return this.value;
//    }
//
//    /**
//     * Gets the left node of the node.
//     * @return Left node of the node
//     */
//    public KDNode getLeft() {
//      return this.left;
//    }
//
//    /**
//     * Gets the right node of the node.
//     * @return Right node of the node
//     */
//    public KDNode getRight() {
//      return this.right;
//    }
//
//    /**
//     * Gets the dimension of the node.
//     * @return Integer representing dimension of the node
//     */
//    public int getDimension() {
//      return this.dimension;
//    }
//
//    public void setDimension(int dimension) {
//      this.dimension = dimension;
//    }
//
//    /**
//     * Sets the left node of the node.
//     * @param leftNode Node to set
//     */
//    public void setLeft(KDNode leftNode) {
//      this.left = leftNode;
//    }
//
//    /**
//     * Sets the right node of the node.
//     * @param rightNode Node to set
//     */
//    public void setRight(KDNode rightNode) {
//      this.right = rightNode;
//    }
//
//    /**
//     * Compares the current node's distance with another node.
//     * @param node another node in the same dimension of space
//     * @return 0 if two node have the same distance;
//     *         1 if the current node's distance is larger; -1 if smaller
//     */
//    @Override
//    public int compareTo(KDNode node) {
//      return Double.compare(distance, node.distance);
//    }
//
//    /**
//     * Gets the significant fields as an array of objects.
//     * @return significant field of KDNode object
//     */
//    private Object[] getSigFields() {
//      Object[] result = {value, left, right, dimension, distance};
//      return result;
//    }
//
//    /**
//     * Overriding equality for KDNode object.
//     *
//     * @param secondKDNode KDNode to be checked if equal to this KDNode.
//     * @return True if equal, false if unequal.
//     */
//    @Override
//    public boolean equals(Object secondKDNode) {
//      if (secondKDNode == null) {
//        return false;
//      }
//      if (this == secondKDNode) {
//        return true;
//      }
//      if (secondKDNode.getClass() != this.getClass()) {
//        return false;
//      }
//      KDNode second = (KDNode) secondKDNode;
//
//      for (int i = 0; i < this.getSigFields().length; ++i) {
//        if (!Objects.equals(this.getSigFields()[i], second.getSigFields()[i])) {
//          return false;
//        }
//      }
//      return true;
//    }
//
//    /**
//     * Overrides hashCode.
//     * @return Positive number if greater, 0 if same, negative if less
//     */
//    @Override
//    public int hashCode() {
//      return Objects.hash(getSigFields());
//    }
//
//    @Override
//    public String toString() {
//      return value.toString();
//    }
//  }
//
//  /**
//   * Constructs a KDTree that contains coordinates in the specified dimension.
//   * @param totalDimension dimension of KDTree
//   */
//  public KDTree(int totalDimension) {
//    this.root = null;
//    this.totalDimension = totalDimension;
//    this.size = 0;
//  }
//
//  /**
//   * Constructs a KDTree with a list of nodes in the specified dimension.
//   * @param totalDimension dimension of KDTree
//   * @param nodes list of nodes to be constructed with
//   */
//  public KDTree(int totalDimension, List<T> nodes) {
//    this.totalDimension = totalDimension;
//    this.size = 0;
//    this.root = buildKDTreeHelper(nodes, 0);
//  }
//
//  /**
//   * Gets the root node of the KDTree.
//   * @return Root node of the KDTree
//   */
//  public KDNode getRoot() {
//    return this.root;
//  }
//
//  /**
//   * Returns the size of a tree.
//   * @return size
//   */
//  public int size() {
//    return size;
//  }
//
//  /**
//   * Sorts a given list based on the given dimension.
//   * @param l List to sort
//   * @param dim Dimension to sort list by
//   * @return Sorted list
//   */
//  public List<T> sortList(List<T> l, int dim) {
////    Collections.sort(l, new SortByDimension(
////            dim % this.totalDimension,
////            this.totalDimension));
//    l.sort(new SortByDimension(dim % this.totalDimension));
//    return l;
//  }
//
//  /**
//   * Helper for building the KDTree.
//   * @param nodes List of nodes to use to build KDTree
//   * @param dim Current dimension of node
//   * @return Node to add in KDTree
//   */
//  private KDNode buildKDTreeHelper(List<T> nodes, int dim) {
//    // If list is empty, return the node
//    if (nodes.size() != 0) {
//      // Sorts the list to get median value
//      List<T> sortedNodes = sortList(nodes, dim);
//      int medianIndex = sortedNodes.size() / 2;
//      KDNode n = new KDNode(sortedNodes.get(medianIndex), dim, this.totalDimension);
//      KDNode leftNode = buildKDTreeHelper(sortedNodes.subList(0, medianIndex), dim + 1);
//      KDNode rightNode = buildKDTreeHelper(sortedNodes.subList(medianIndex + 1,
//              sortedNodes.size()), dim + 1);
//      n.setLeft(leftNode);
//      n.setRight(rightNode);
//      this.size++;
//      return n;
//    }
//    return new KDNode(null, dim, this.totalDimension);
//  }
//
//  /**
//   * Inserts an element into a KDTree.
//   * Reports error if element is not in the same dimension as tree.
//   * @param newElement element to be inserted
//   */
//  public void insert(T newElement) {
//    if (newElement.getCoordinate().length != totalDimension) {
//      System.err.println("ERROR: New point not in the same dimension");
//    } else {
//      KDNode newNode = new KDNode(newElement);
//      root = insertRecur(newNode, root, 0);
//      size += 1;
//    }
//  }
//
//  /**
//   * Recursive helper function for inserting a node into KDTree.
//   * @param newNode      node to be inserted
//   * @param currentNode  current node being looked at during recursion
//   * @param currentDepth the level of depth that the function is at during recursion
//   * @return the new root node after insertion
//   */
//  private KDNode insertRecur(KDNode newNode, KDNode currentNode, int currentDepth) {
//    if (currentNode == null) {
//      currentNode = newNode;
//      currentNode.setDimension(currentDepth % totalDimension);
//    } else {
//      /* Recursively compares the coordinate of the newNode with the currentNode,
//       * inserts the newNode into the correct subtree */
//      int cd = currentDepth % totalDimension; //current dimension of comparison
//      if (newNode.getValue().getCoordinate()[cd] < currentNode.getValue().getCoordinate()[cd]) {
//        currentNode.left = insertRecur(newNode, currentNode.left, currentDepth + 1);
//      } else {
//        currentNode.right = insertRecur(newNode, currentNode.right, currentDepth + 1);
//      }
//    }
//    return currentNode;
//  }
//
//  /**
//   * Inserts a list of elements into a KD tree.
//   * @param elements list of elements
//   */
//  public void insertAll(List<T> elements) {
//    for (T elt : elements) {
//      insert(elt);
//    }
//  }
//
//  /* Global variable to store KNN */
//  private BoundedPriorityQueue bpq;
//
//  /**
//   * Finds k nearest neighbors in the tree from coordinates.
//   * @param k           number of neighbors to be found
//   * @param coordinates coordinate around which to look for
//   * @return a list of k nearest neighbors
//   */
//  public List<T> findKNN(int k, double[] coordinates) {
//    bpq = new BoundedPriorityQueue(k);
//    findKNNRecur(k, root, coordinates);
//    List<KDNode> foundNodes = bpq.toList();
//    List<T> output = new ArrayList<>();
//    for (KDNode node : foundNodes) {
//      output.add(node.getValue());
//    }
//    return output;
//  }
//
//  /**
//   * Recursive helper function for finding KNN.
//   * @param k           number of neighbors to be found
//   * @param currentNode current node being looked at during recursion
//   * @param coordinates coordinate around which to look for
//   */
//  private void findKNNRecur(int k, KDNode currentNode, double[] coordinates) {
//    if (currentNode == null || currentNode.getValue() == null) {
//      return;
//    }
//    double dist = Common.calcDistance(currentNode.getValue().getCoordinate(), coordinates);
//    currentNode.distance = dist;
//    bpq.add(currentNode);
//
//    /* Recursively compares the new coordinate with the currentNode,
//     * looks into the correct subtree for close neighbors
//     */
//    int cd = currentNode.getDimension();
//    boolean wentRight = false;
//    if (coordinates[cd] < currentNode.getValue().getCoordinate()[cd]) {
//      findKNNRecur(k, currentNode.left, coordinates);
//    } else {
//      wentRight = true;
//      findKNNRecur(k, currentNode.right, coordinates);
//    }
//
//    /* If the candidate hypersphere crosses this splitting plane (hence there
//     * potentially exists closer neighbors on the other side of the plane),
//     * or if the bpq is not filled yet,
//     * examines the other subtree.
//     */
//    double distanceToOtherSide =
//        Math.abs(currentNode.getValue().getCoordinate()[cd] - coordinates[cd]);
//    KDNode bpqHead = (KDNode) bpq.peekLowestPriority(); //casting?
//    if ((bpq.size() < k) || bpqHead.distance > distanceToOtherSide) {
//      if (wentRight) {
//        findKNNRecur(k, currentNode.left, coordinates);
//      } else {
//        findKNNRecur(k, currentNode.right, coordinates);
//      }
//    }
//  }
//
//  /* Global variable to store neighbors within radius */
//  private PriorityQueue<KDNode> pq;
//
//  /** Finds all elements that are within a fixed radius from a point.
//   * @param r radius
//   * @param coordinate the center coordinate to search from
//   * @return list of elements within the radius
//   */
//  public List<T> findWithinRadius(double r, double[] coordinate) {
//    pq = new PriorityQueue(Collections.reverseOrder());
//    findWithinRadiusRecur(r, root, coordinate);
//    List<KDNode> foundNodes = pqToList(pq);
//    List<T> output = new ArrayList<>();
//    for (KDNode node : foundNodes) {
//      output.add(node.getValue());
//    }
//    return output;
//  }
//
//  /**
//   * Recursive helper function for finding elements within a radius.
//   * @param r radius
//   * @param currentNode urrentNode current node being looked at during recursion
//   * @param coordinates coordinate around which to look for
//   */
//  private void findWithinRadiusRecur(double r, KDNode currentNode, double[] coordinates) {
//    if (currentNode == null) {
//      return;
//    }
//    double dist = Common.calcDistance(currentNode.getValue().getCoordinate(), coordinates);
//    currentNode.distance = dist;
//
//    if (dist <= r) {
//      pq.add(currentNode);
//    }
//
//    /* Recursively search the half of the tree that contains the test point. */
//    int cd = currentNode.getDimension();
//    boolean wentRight = false;
//    if (coordinates[cd] < currentNode.getValue().getCoordinate()[cd]) {
//      findWithinRadiusRecur(r, currentNode.left, coordinates);
//    } else {
//      wentRight = true;
//      findWithinRadiusRecur(r, currentNode.right, coordinates);
//    }
//    /* If the candidate hypersphere crosses this splitting plane, look on the
//     * other side of the plane by examining the other subtree.
//     */
//    if (Math.abs(currentNode.getValue().getCoordinate()[cd] - coordinates[cd]) <= r) {
//      if (wentRight) {
//        findWithinRadiusRecur(r, currentNode.left, coordinates);
//      } else {
//        findWithinRadiusRecur(r, currentNode.right, coordinates);
//      }
//    }
//  }
//
//  /**
//   * Returns a list containing all nodes in this queue, preserving priority order.
//   * @param q original queue
//   * @return a list of elements from the queue
//   */
//  private List<KDNode> pqToList(PriorityQueue q) {
//    PriorityQueue pqTemp = new PriorityQueue<>(q);
//    List list = new ArrayList<>();
//    for (int i = 0; i < q.size(); i++) {
//      list.add(pqTemp.poll());
//    }
//    Collections.reverse(list);
//    return list;
//  }
//
//  /**
//   * Returns the height of a tree.
//   * @return height
//   */
//  public int height() {
//    return height(root);
//  }
//
//  /**
//   * Recursive function to calculate the height of a tree.
//   * @param node current node being looked at
//   * @return height of the current node
//   */
//  private int height(KDNode node) {
//    if (node == null) {
//      return 0;
//    } else {
//      /* compute  height of each subtree */
//      int lheight = height(node.left);
//      int rheight = height(node.right);
//
//      /* use the larger one */
//      return (lheight > rheight) ? (lheight + 1) : (rheight + 1);
//    }
//  }
//
//  /**
//   * Prints a tree.
//   */
//  public void printTree() {
//    int h = height(root);
//    for (int i = 1; i <= h; i++) {
//      printGivenLevel(root, i);
//      if (i == h) {
//        return;
//      } else {
//        System.out.println();
//      }
//    }
//  }
//
//  /**
//   * Gets value of root node.
//   * @return value of root node
//   */
//  public T getNodeValue() {
//    return root.getValue();
//  }
//
//  /**
//   * Gets value of node left of root node.
//   * Used for testing.
//   *
//   * @return value of node left of root node
//   */
//  public T getLeftNodeValue() {
//    return root.getLeft().getValue();
//  }
//
//  /**
//   * Gets value of node right of root node.
//   * Used for testing.
//   *
//   * @return value of node right of root node
//   */
//  public T getRightNodeValue() {
//    return root.getRight().getValue();
//  }
//
//  /**
//   * Prints the nodes at a given level.
//   * @param node current node
//   * @param level current level
//   */
//  private void printGivenLevel(KDNode node, int level) {
//    if (node == null) {
//      System.out.print("/ ");
//    } else if (level == 1) {
//      System.out.print(node.toString() + " ");
//    } else if (level > 1) {
//      printGivenLevel(node.left, level - 1);
//      printGivenLevel(node.right, level - 1);
//    }
//  }
//}
