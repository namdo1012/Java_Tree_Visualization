package model;

public interface Tree<T extends Comparable<T>> {
  boolean insert(T element);
  boolean delete(T element);
  Node<T> search(T element);
  boolean isEmpty();
  void traverseInOrder();
}
