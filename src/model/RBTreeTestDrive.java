package model;

public class RBTreeTestDrive {
  public static void main(String[] args) {
    RBTree<Integer> rbTree = new RBTree<>();
    rbTree.insert(10);
    rbTree.insert(16);
    rbTree.insert(7);
    rbTree.insert(12);
    rbTree.insert(19);
    rbTree.insert(2);
    rbTree.insert(4);

    rbTree.traverseInOrder();
    System.out.println(rbTree.root.right.left.color);
  }
}
