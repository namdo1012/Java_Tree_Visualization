package model;

import static model.Constants.RED;

public class Node<T extends Comparable<T>> {
  public T element;
  public Node<T> left;
  public Node<T> right;
  public Node<T> parent;
  public int height;
  public boolean color;

  public Node(T element) {
    this.element = element;
    left = null;
    right = null;
    parent = null;
    height = 1;
    color = RED;
  }
}
