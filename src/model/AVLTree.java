package model;

public class AVLTree<T extends Comparable<T>> extends BST<T>{

  private int height(Node<T> current) {
    return current == null ? -1 : current.height;
  }

  private void updateHeight(Node<T> current) {
    current.height = 1 + Math.max(height(current.left), height(current.right));
  }

  private int getBalance(Node<T> current) {
    return current == null ? 0 : height(current.right) - height(current.left);
  }

  private Node<T> rotateRight(Node<T> current) {
    Node<T> x = current.left;
    Node<T> z = x.right;
    x.right = current;
    current.left = z;
    updateHeight(current);
    updateHeight(x);
    return x;
  }

  private Node<T> rotateLeft(Node<T> current) {
    Node<T> x = current.right;
    Node<T> z = x.left;
    x.left = current;
    current.right = z;
    updateHeight(current);
    updateHeight(x);
    return x;
  }

  private Node<T> rebalance(Node<T> current) {
    updateHeight(current);
    int balance = getBalance(current);
    if (balance > 1) {
      if (height(current.right.right) > height(current.right.left)) {
        current = rotateLeft(current);
      } else {
        current.right = rotateRight(current.right);
        current = rotateLeft(current);
      }
    } else if (balance < -1) {
      if (height(current.left.left) > height(current.left.right)) {
        current = rotateRight(current);
      } else {
        current.left = rotateLeft(current.left);
        current = rotateRight(current);
      }
    }
    return current;
  }

  @Override
  public Node<T> insert(Node<T> current, T element) {
    return rebalance(super.insert(current, element));
  }

  @Override
  public Node<T> delete(Node<T> current, T element) {
    current = super.delete(current, element);
    return current != null ? rebalance(current) : null;
  }
}
