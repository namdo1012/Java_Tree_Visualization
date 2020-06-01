package model;

public class BST<T extends Comparable<T>> implements Tree<T> {
  public Node<T> root = null;

  public BST() {
  }

  public BST(T[] objects) {
    for (T obj : objects) {
      insert(obj);
    }
  }

  @Override
  public void insert(T element) {
    root = insert(root, element);
  }

  public Node<T> insert(Node<T> current, T element) {
    if (current == null) {
      return new Node<T>(element);
    }

    if (element.compareTo(current.element) > 0) {
      current.right = insert(current.right, element);
    } else if (element.compareTo(current.element) < 0) {
      current.left = insert(current.left, element);
    }
    return current;
  }

  @Override
  public void delete(T element) {
    root = delete(root, element);
  }

  public Node<T> delete(Node<T> current, T element) {
    if (current == null) {
      return null;
    }

    if (element.compareTo(current.element) == 0) {
      if (current.left == null && current.right == null) {
        return null;
      } else if (current.left == null) {
        return current.right;
      } else if (current.right == null) {
        return current.left;
      } else {
        T smallestElement = findSmallestElement(current.right);
        current.element = smallestElement;
        current.right = delete(current.right, smallestElement);
        return current;
      }
    }
    if (element.compareTo(current.element) > 0) {
      current.right = delete(current.right, element);
      return current;
    }
    current.left = delete(current.left, element);
    return current;
  }

  public T findSmallestElement(Node<T> root) {
    return root.left == null ? root.element : findSmallestElement(root.left);
  }

  @Override
  public Node<T> search(T element) {
    return search(root, element);
  }

  public Node<T> search(Node<T> current, T element) {
    if (current == null) {
      return null;
    }

    if (element.compareTo(current.element) == 0) {
      return current;
    } else if (element.compareTo(current.element) > 0) {
      return search(current.right, element);
    } else if (element.compareTo(current.element) < 0) {
      return search(current.left, element);
    }
    return null;
  }

  @Override
  public boolean isEmpty() {
    return root == null;
  }

  @Override
  public void traverseInOrder() {
    traverseInOrder(root);
  }

  public void traverseInOrder(Node<T> current) {
    if (current != null) {
      traverseInOrder(current.left);
      System.out.print(current.element.toString() + " -> ");
      traverseInOrder(current.right);
    }
  }
}
