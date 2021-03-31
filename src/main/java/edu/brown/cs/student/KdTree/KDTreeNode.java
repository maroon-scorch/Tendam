package edu.brown.cs.student.KdTree;


import java.util.Objects;

/**
 * A class for a KDTreeNode.
 *
 * @param <T> - indicates the type of object the node is holding.
 */
public class KDTreeNode<T extends Dimensionable> {

  // fields for the KDTreeNode
  private final T nodeObject;
  private KDTreeNode<T> lChild;
  private KDTreeNode<T> rChild;

  /**
   * Constructor for the KDTreeNode.
   *
   * @param obj - the object the node holds
   */
  public KDTreeNode(T obj) {
    this.nodeObject = obj;
  }

  /**
   * Gets the left child.
   *
   * @return the left child
   */
  public KDTreeNode<T> getlChild() {
    return this.lChild;
  }

  /**
   * Sets the left child of the node.
   *
   * @param node - the node being set
   */
  public void setlChild(KDTreeNode<T> node) {
    this.lChild = node;
  }

  /**
   * Gets the right child.
   *
   * @return the right child
   */
  public KDTreeNode<T> getrChild() {
    return this.rChild;
  }

  /**
   * Sets the right child of the node.
   *
   * @param node - the node being set
   */
  public void setrChild(KDTreeNode<T> node) {
    this.rChild = node;
  }

  /**
   * Gets the object being stored by the node.
   *
   * @return the object
   */
  T getNodeObject() {
    return this.nodeObject;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KDTreeNode<?> that = (KDTreeNode<?>) o;
    return Objects.equals(nodeObject, that.nodeObject) && Objects.equals(lChild, that.lChild)
        && Objects.equals(rChild, that.rChild);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nodeObject, lChild, rChild);
  }
}
