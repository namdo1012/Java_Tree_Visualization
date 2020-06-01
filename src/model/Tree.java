package model;

public interface Tree<T extends Comparable<T>> {
  void insert(T element);
  void delete(T element);
  Node<T> search(T element);
  boolean isEmpty();
  void traverseInOrder();
}
