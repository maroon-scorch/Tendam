package edu.brown.cs.student.KdTree;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.zip.DataFormatException;

import static java.lang.Math.abs;

/**
 * A class to store a KDTree.
 *
 * @param <T> - the type of the object that the KDTree is storing
 */
public class KDTree<T extends Dimensionable> {

  // fields for the KDTree
  private final int dimensions;
  private KDTreeNode<T> rootNode;

  /**
   * Constructor for the KDTree.
   *
   * @param dimensions - the number of dimensions the tree will be handling
   */
  public KDTree(int dimensions) {
    this.dimensions = dimensions;
  }

  /**
   * Sets up the tree from the list of the element.
   *
   * @param eltList - a list of T to make the tree out of.
   */
  public void setUpTree(List<T> eltList) {
    if (eltList == null) {
      return;
    }

    // defensive list copy
    List<T> eltListCopy = new ArrayList<>(eltList);

    // sorts the list by the first dimension
    eltListCopy.sort(Comparator.comparing((Dimensionable obj) -> obj.getDimension(0)));
    int midInd = ((eltListCopy.size() - 1) / 2);

    // creates the root node and its children
    this.rootNode = new KDTreeNode<>(eltListCopy.get(midInd));
    eltListCopy.remove(midInd);
    setUpChild(this.rootNode, eltListCopy, 0);
  }

  /**
   * Sets up child nodes recursively.
   *
   * @param currNode - the current Node for which children are being created
   * @param eltList  - the list of objects that are left to be put in the tree
   * @param currDimm - the current dimension that is being sorted by
   */
  private void setUpChild(KDTreeNode<T> currNode, List<T> eltList,
                          int currDimm) {

    // value for the current node
    Double currVal = currNode.getNodeObject().getDimension(currDimm);

    List<T> lList = new ArrayList<>();
    List<T> rList = new ArrayList<>();

    // sorts the remaining objects into the chilren nodes
    for (T elt : eltList) {
      if (elt.getDimension(currDimm) < currVal) {
        lList.add(elt);
      } else {
        rList.add(elt);
      }
    }

    // creates the left child
    if (!lList.isEmpty()) {
      lList.sort(Comparator.comparing((Dimensionable obj) -> obj.getDimension(currDimm)));
      int midInd = ((lList.size() - 1) / 2);

      KDTreeNode<T> lChild = new KDTreeNode<>(lList.get(midInd));
      currNode.setlChild(lChild);
      lList.remove(midInd);
      setUpChild(lChild, lList, (currDimm + 1) % this.dimensions);
    } else {
      currNode.setlChild(null);
    }

    // creates the right child
    if (!rList.isEmpty()) {
      rList.sort(Comparator.comparing((Dimensionable obj) -> obj.getDimension(currDimm)));
      int midInd = ((rList.size() - 1) / 2);

      KDTreeNode<T> rChild = new KDTreeNode<>(rList.get(midInd));
      currNode.setrChild(rChild);
      rList.remove(midInd);
      setUpChild(rChild, rList, (currDimm + 1) % this.dimensions);
    } else {
      currNode.setrChild(null);
    }
  }

  /**
   * A function to find the nearest neighbors from the given location.
   *
   * @param k        - the number of objects to find
   * @param location - the target location
   * @return a TreeMap of the objects associated with the distance
   * @throws DataFormatException if unable to parse location data
   */
  public TreeMap<Double, List<T>> nearestNeighbors(int k, List<Double> location)
      throws DataFormatException {
    IntTreePair<T> pair = nearestNeighborsRecurs(new IntTreePair<>(0, new TreeMap<>()), k,
        location, this.rootNode, 0);
    return pair.getTree();
  }

  /**
   * The recursive function to find the nearest neighbors.
   *
   * @param currObjsPair - the intTreePair associating the number of objects in the treemap
   * @param k            - the number of objects to find
   * @param location     - the target location
   * @param currNode     - the current node that is being looked at
   * @param dim          - the current dimension being looked at
   * @return an IntTreePair that associates a map with the number of objects in the map
   * @throws DataFormatException if unable to parse location data
   */
  public IntTreePair<T> nearestNeighborsRecurs(IntTreePair<T> currObjsPair, int k,
                                               List<Double> location,
                                               KDTreeNode<T> currNode, int dim)
      throws DataFormatException {

    // gets the current tree and count of objects
    TreeMap<Double, List<T>> currObjs = currObjsPair.getTree();
    int currCount = currObjsPair.getInt();

    // the distant of the current node
    double currDist = currNode.getNodeObject().getDist(location);

    // the last entry in the map (farthest)
    Map.Entry<Double, List<T>> lastEntry = currObjs.lastEntry();

    // checks if there are k objects and if not adds the object
    if (currCount < k) {
      addNode(currNode, currObjs, currDist);
      currCount += 1;

      // if there are already k objects, checks the last entry and adds current if closer
    } else if (lastEntry != null) {
      if (lastEntry.getKey() > currDist) {
        int sizeLast = lastEntry.getValue().size();
        if (currCount - sizeLast >= k - 1) {
          currObjs.remove(lastEntry.getKey());
          currCount -= sizeLast;
        }
        addNode(currNode, currObjs, currDist);
        currCount += 1;
      } else if (lastEntry.getKey().equals(currDist)) {
        addNode(currNode, currObjs, currDist);
        currCount += 1;
      }
    }

    // gets the value for the current node at the given dimension
    double axis = currNode.getNodeObject().getDimension(dim);

    // updates the tree and count
    currObjsPair.setTree(currObjs);
    currObjsPair.setI(currCount);

    // gets the newest last entry
    lastEntry = currObjs.lastEntry();

    if (lastEntry != null) {

      // checks if the distance of the current object is farther than the last object and
      // if not iterate through both children
      if (lastEntry.getKey() >= abs(location.get(dim) - axis)) {
        if (currNode.getlChild() != null) {
          currObjsPair = nearestNeighborsRecurs(currObjsPair, k, location,
              currNode.getlChild(), (dim + 1) % this.dimensions);
        }
        if (currNode.getrChild() != null) {
          return nearestNeighborsRecurs(currObjsPair, k, location, currNode.getrChild(),
              (dim + 1) % this.dimensions);
        }
        return currObjsPair;
        // iterates just through the right child
      } else if (axis < location.get(dim)) {
        if (currNode.getrChild() != null) {
          return nearestNeighborsRecurs(currObjsPair, k, location, currNode.getrChild(),
              (dim + 1) % this.dimensions);
        } else {
          return currObjsPair;
        }
        // iterates just through the left child
      } else {
        if (currNode.getlChild() != null) {
          return nearestNeighborsRecurs(currObjsPair, k, location, currNode.getlChild(),
              (dim + 1) % this.dimensions);
        } else {
          return currObjsPair;
        }
      }
    }
    return currObjsPair;
  }

  /**
   * adds a node to the current tree.
   *
   * @param currNode - the node to add
   * @param currObjs - the tree to add the node to.
   * @param currDist - the distant of the node
   */
  private void addNode(KDTreeNode<T> currNode, TreeMap<Double, List<T>> currObjs, double currDist) {
    if (currObjs.get(currDist) != null) {
      List<T> newObjList = currObjs.get(currDist);
      newObjList.add(currNode.getNodeObject());
      currObjs.put(currDist, newObjList);
    } else {
      List<T> newObjList = new ArrayList<>();
      newObjList.add(currNode.getNodeObject());
      currObjs.put(currDist, newObjList);
    }
  }

  /**
   * A function to find all of the objects within a given radius.
   *
   * @param r        - the radius.
   * @param location -  the target location
   * @return the map of the objects and the distances
   * @throws DataFormatException if unable to parse location data
   */
  public TreeMap<Double, List<T>> radiusNeighbors(double r, List<Double> location)
      throws DataFormatException {
    return radiusNeighborsRecurs(new TreeMap<>(), r, this.rootNode, location, 0);
  }

  /**
   * The recursive function to find the neighbors within the given radius.
   *
   * @param currObjs - the current treemap of objects and distances
   * @param r        - the radius
   * @param currNode - the node that is currently being looked at
   * @param location - the target location
   * @param dim      - the current dimension
   * @return the treemap of objects and distances
   */
  private TreeMap<Double, List<T>> radiusNeighborsRecurs(TreeMap<Double, List<T>> currObjs,
                                                         double r, KDTreeNode<T> currNode,
                                                         List<Double> location, int dim)
      throws DataFormatException {

    // the current distant of the current node
    double currDist = currNode.getNodeObject().getDist(location);

    // checks if it is within the radius and adds it to the current objects
    if (currDist <= r) {
      List<T> objList = currObjs.get(currDist);
      if (objList == null) {
        objList = new ArrayList<>();
      }
      objList.add(currNode.getNodeObject());
      currObjs.put(currDist, objList);
    }

    // gets the value for the current node at the given dimension
    double axis = currNode.getNodeObject().getDimension(dim);

    // checks if the radius is greater than the distance of the node on the access
    // and if so, iterates on both children
    if (r >= abs(location.get(dim) - axis)) {
      if (currNode.getlChild() != null) {
        currObjs = radiusNeighborsRecurs(currObjs, r, currNode.getlChild(), location,
            (dim + 1) % this.dimensions);
      }
      if (currNode.getrChild() != null) {
        return radiusNeighborsRecurs(currObjs, r, currNode.getrChild(), location,
            (dim + 1) % this.dimensions);
      } else {
        return currObjs;
      }
      // iterates over the right child only
    } else if (axis < location.get(dim)) {
      if (currNode.getrChild() != null) {
        return radiusNeighborsRecurs(currObjs, r, currNode.getrChild(), location,
            (dim + 1) % this.dimensions);
      } else {
        return currObjs;
      }
      // iterates over the left child only
    } else {
      if (currNode.getlChild() != null) {
        return radiusNeighborsRecurs(currObjs, r, currNode.getlChild(), location,
            (dim + 1) % this.dimensions);
      } else {
        return currObjs;
      }
    }
  }

  /**
   * An access method for the rootNode.
   *
   * @return the rootNode
   */
  public KDTreeNode<T> getRootNode() {
    return this.rootNode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KDTree<?> kdTree = (KDTree<?>) o;
    return dimensions == kdTree.dimensions && Objects.equals(rootNode, kdTree.rootNode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dimensions, rootNode);
  }
}
