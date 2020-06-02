package controller;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.control.TreeView;
import javafx.scene.shape.Line;
import model.BST;
import model.Node;
import view.CircleNode;
import java.util.HashMap;
import java.util.Map;

public class TreeController {
  public HashMap<Node, CircleNode> treeView;
  private BST tree;

  private final double CENTER_X = 500;
  private final double CENTER_Y = 50;
  private final double V_GAP = 50;
  private final double H_GAP = 240;

  public TreeController(BST tree) {
    this.treeView = createTreeView(tree); //treeView
    this.tree = tree; //treeNode
  }

  public BST getTreeNode(){
    return this.tree;
  }
  public void setTreeNode(BST tree){
    this.tree = tree;
  }

  private HashMap<Node, CircleNode> createTreeView(BST tree) {
    HashMap<Node, CircleNode> treeView = new HashMap<>();
    if (tree.root != null) {
      createTreeView(treeView, tree.root, this.CENTER_X, this.CENTER_Y, this.H_GAP);
    }
    return treeView;
  }

  private void createTreeView(HashMap treeView, Node current, double centerX, double centerY, double hGap) {
    CircleNode cir = new CircleNode(current.element.toString(), centerX, centerY, hGap);

    if (current.left != null) {
      Line lineLeft = new Line(cir.getLayoutX() + 20, cir.getLayoutY() + 20, cir.getLayoutX() - cir.gethGap() + 20, cir.getLayoutY() + 50 + 20);
      lineLeft.toBack();
      cir.setLineLeft(lineLeft);
      createTreeView(treeView, current.left, centerX - hGap, centerY + V_GAP, hGap / 2);
    } else {cir.setLineLeft(null);}

    if (current.right != null) {
      cir.setLineRight(new Line(cir.getLayoutX() + 20, cir.getLayoutY() + 20, cir.getLayoutX() + cir.gethGap() + 20, cir.getLayoutY() + 50 + 20));
      createTreeView(treeView, current.right, centerX + hGap, centerY + V_GAP, hGap / 2);
    } else {cir.setLineRight(null);}

    treeView.put(current, cir);
  }

  public void updateTreeView() {
    this.treeView = createTreeView(this.tree);
  }

  public void displayTree(Group root ){
    this.treeView.forEach((node, cir) -> {
      // Display two lines
      if (cir.getLineLeft() != null) root.getChildren().add(cir.getLineLeft());
      if (cir.getLineRight() != null) root.getChildren().add(cir.getLineRight());

      // Display circleNode
      root.getChildren().add(cir);
    });
  }

  public void displayLines(Group root ){
    this.treeView.forEach((node, cir) -> {
      // Display two lines
      if (cir.getLineLeft() != null) root.getChildren().add(cir.getLineLeft());
      if (cir.getLineRight() != null) root.getChildren().add(cir.getLineRight());
    });
  }

  // Function return HashMap of changed Node and CircleNode
//  public HashMap<Node, CircleNode> getListNodeChanged() {
//    HashMap<Node, CircleNode> list = new HashMap<>();
//    HashMap<Node, CircleNode> newTreeView = createTreeView(this.tree);
//
//    for (Node key : newTreeView.keySet()) {
//      if (newTreeView.get(key).compareTo(this.treeView.get(key)) != 0) {
//        list.put(key, newTreeView.get(key));
//      }
//    }
//    return list;
//  }

  // ANIMATION FOR TREECONTROLLER
  public ParallelTransition createAnimationHandleDelete(Group root, HashMap<Node, CircleNode> afterDeleteTreeView){

      ParallelTransition pl = new ParallelTransition();

      this.treeView.forEach((bstKey, bstNode) -> {
          int check = 0; // if this node have in both tree -> 1; else -> 0 -> delete
          for (Map.Entry<Node, CircleNode> entry : afterDeleteTreeView.entrySet()) {
              Node avlKey = entry.getKey();
              CircleNode avlNode = entry.getValue();
              if (bstKey.element.compareTo(avlKey.element) == 0) {
                  // this node represent in both tree
                  check = 1;
                  // Find position to moving to in avlTree
                  double moveToX = avlNode.getLayoutX();
                  double moveToY = avlNode.getLayoutY();
                  // Add animation to root
                  pl.getChildren().add(bstNode.createAnimationTranslateTo(moveToX, moveToY));
              }
        }

        // If not fine this node in AVL Tree -> remove it from screen
        if (check == 0) root.getChildren().remove(bstNode);
      });

      return pl;
  }

  public ParallelTransition createAnimationHandleInsert(Group root, HashMap<Node, CircleNode> afterInsertTreeView){

      ParallelTransition pl = new ParallelTransition();

      this.treeView.forEach((bstKey, bstNode) -> {
          afterInsertTreeView.forEach((avlKey, avlNode) -> {
              if (bstKey.element.compareTo(avlKey.element) == 0) {
                // Find position to moving to in avlTree
                double moveToX = avlNode.getLayoutX();
                double moveToY = avlNode.getLayoutY();
                // Add animation to root
                pl.getChildren().add(bstNode.createAnimationTranslateTo(moveToX, moveToY));
              }
          });
      });

      return pl;
  }
}
