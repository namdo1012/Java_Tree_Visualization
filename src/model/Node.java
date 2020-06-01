package model;

public class Node<T extends Comparable<T>> {
  public T element;
  public Node<T> left;
  public Node<T> right;
  public int height;

  public Node(T element) {
    this.element = element;
    left = null;
    right = null;
    height = 1;
  }
}
