package model;

public class BSTTestDrive {
  public static void main(String[] args) {
    BST<Integer> bst = new BST<>();
    bst.insert(10);
    bst.insert(16);
    bst.insert(7);
    bst.insert(12);
    bst.insert(19);
    bst.insert(2);
    bst.insert(4);
    bst.traverseInOrder();
  }
}
