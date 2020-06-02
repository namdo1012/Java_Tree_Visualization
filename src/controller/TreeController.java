package controller;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import model.BST;
import model.Node;
import model.RBTree;
import view.CircleNode;
import view.RBCircleNode;
import view.SearchCircleNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static model.Constants.*;

public class TreeController<T extends Comparable<T>> {
  public HashMap<Node, CircleNode> treeView;
  public BST tree;

  // For test


  public TreeController(BST tree) {
    this.treeView = createTreeView(tree);
    this.tree = tree;
  }

  private HashMap<Node, CircleNode> createTreeView(BST tree) {
    HashMap<Node, CircleNode> treeView = new HashMap<>();
    if (tree.root != null) {
      if (tree instanceof RBTree) {
        createRBTreeView(treeView, tree.root, CENTER_X, CENTER_Y, H_GAP);
      } else {
        createTreeView(treeView, tree.root, CENTER_X, CENTER_Y, H_GAP);
      }
    }
    return treeView;
  }

  private void createTreeView(HashMap treeView, Node current, double centerX, double centerY, double hGap) {
    CircleNode cir = new CircleNode(current.element.toString(), centerX, centerY, hGap);

    if (current.left != null) {
      cir.setLineLeft(new Line(cir.getLayoutX() + 20, cir.getLayoutY() + 20, cir.getLayoutX() - cir.gethGap() + 20, cir.getLayoutY() + 50 + 20));
      createTreeView(treeView, current.left, centerX - hGap, centerY + V_GAP, hGap / 2);
    }

    if (current.right != null) {
      cir.setLineRight(new Line(cir.getLayoutX() + 20, cir.getLayoutY() + 20, cir.getLayoutX() + cir.gethGap() + 20, cir.getLayoutY() + 50 + 20));
      createTreeView(treeView, current.right, centerX + hGap, centerY + V_GAP, hGap / 2);
    }

    treeView.put(current, cir);
  }

  private void createRBTreeView(HashMap treeView, Node current, double centerX, double centerY, double hGap) {
    RBCircleNode cir = new RBCircleNode(current.element.toString(), centerX, centerY, hGap, current.color);

    if (current.left != null) {
      cir.setLineLeft(new Line(cir.getLayoutX() + 20, cir.getLayoutY() + 20, cir.getLayoutX() - cir.gethGap() + 20, cir.getLayoutY() + 50 + 20));
      createRBTreeView(treeView, current.left, centerX - hGap, centerY + V_GAP, hGap / 2);
    }

    if (current.right != null) {
      cir.setLineRight(new Line(cir.getLayoutX() + 20, cir.getLayoutY() + 20, cir.getLayoutX() + cir.gethGap() + 20, cir.getLayoutY() + 50 + 20));
      createRBTreeView(treeView, current.right, centerX + hGap, centerY + V_GAP, hGap / 2);
    }

    treeView.put(current, cir);
  }

  public void updateTreeView() {
    this.treeView = createTreeView(this.tree);
  }

  public void displayTree(Group root) {
    this.treeView.forEach((node, cir) -> {
      if (cir.getLineLeft() != null) {
        root.getChildren().add(cir.getLineLeft());
      }
      if (cir.getLineRight() != null) {
        root.getChildren().add(cir.getLineRight());
      }
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


  public SequentialTransition createAnimationOnSearchTree(Group root, T element) {
    SequentialTransition sq = new SequentialTransition();
    if (this.tree.search(element) == null) {
      System.out.println(element.toString() + " not exists!");
      return null;
    } else {
      double lastLayoutX = 500;
      double lastLayoutY = 50;
      for (CircleNode o : getSearchPath(element)) {
        SearchCircleNode searchCir = new SearchCircleNode();
        searchCir.setLayoutX(lastLayoutX);
        searchCir.setLayoutY(lastLayoutY);

        TranslateTransition tr = searchCir.createAnimationTranslateTo(o.getLayoutX(), o.getLayoutY());
        sq.getChildren().add(tr);
        root.getChildren().add(searchCir);
        lastLayoutX = o.getLayoutX();
        lastLayoutY = o.getLayoutY();
      }
      return sq;
    }
  }

  private ArrayList<CircleNode> getSearchPath(T element) {
    ArrayList<CircleNode> searchPath = new ArrayList<>();

    for (Object key : tree.path(element)) {
      searchPath.add(this.treeView.get(key));
    }
    return searchPath;
  }

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

  // Function return HashMap of changed Node and CircleNode
  public HashMap<Node, CircleNode> getListNodeChanged() {
    HashMap<Node, CircleNode> list = new HashMap<>();
    HashMap<Node, CircleNode> newTreeView = createTreeView(this.tree);

    for (Node key : newTreeView.keySet()) {
      if (newTreeView.get(key).compareTo(this.treeView.get(key)) != 0) {
        list.put(key, newTreeView.get(key));
      }
    }
    return list;
  }
}
