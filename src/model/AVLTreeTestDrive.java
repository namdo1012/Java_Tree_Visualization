package model;

public class AVLTreeTestDrive {
  public static void main(String[] args) {
//    AVLTree<Integer> avl = new AVLTree<>();
//    avl.insert(10);
//    avl.insert(16);
//    avl.insert(7);
//    avl.insert(12);
//    avl.insert(19);
//    avl.insert(2);
//    avl.insert(4);
//    System.out.println(avl.root.left.element.toString());

    AVLTree<String> avl = new AVLTree<>();
    avl.insert("dm");
    avl.insert("nam");
    avl.insert("Tsuki");
    avl.traverseInOrder();
  }
}
