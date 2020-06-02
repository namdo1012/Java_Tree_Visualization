package model;

import java.util.ArrayList;

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
  public boolean insert(T element) {
    this.root = insert(this.root, element);
    if (this.root == null) return false;
    return true;
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
  public boolean delete(T element) {
    root = delete(root, element);
    if (root == null) return false;
    return true;
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

  private Node<T> search(Node<T> current, T element) {
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

  public ArrayList<Node<T>> path(T element){
    ArrayList<Node<T>> list = new ArrayList<>();
    Node<T> current = root;
    while(current != null){
      list.add(current);
      if(element.compareTo(current.element) < 0) {
        current = current.left;
      } else if(element.compareTo(current.element) > 0) {
        current = current.right;
      } else {
        break;
      }
    }
    return list;
  }

  @Override
  public boolean isEmpty() {
    return root == null;
  }

  @Override
  public void traverseInOrder() {
    traverseInOrder(root);
  }

  private void traverseInOrder(Node<T> current) {
    if (current != null) {
      System.out.print(current.element.toString() + " -> ");
      traverseInOrder(current.left);
      traverseInOrder(current.right);
    }
  }

  public BST<T> cloneTree() {
    BST<T> newTree = new BST<>();
    newTree.insertInOrder(newTree, this.root);
    return newTree;
  }

  private void insertInOrder(BST<T> tree, Node<T> current) {
    if (current != null) {
      tree.insert(current.element);
      insertInOrder(tree, current.left);
      insertInOrder(tree, current.right);
    }
  }
}
