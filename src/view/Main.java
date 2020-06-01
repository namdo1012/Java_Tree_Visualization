package view;

import controller.TreeController;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.AVLTree;
import model.BST;
import model.Node;

import java.util.HashMap;
import java.util.Scanner;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception{

        BST<Integer> bstTree = new BST<>();
        AVLTree<Integer> avlTree = new AVLTree<>();

        TreeController treeControllerForBST = new TreeController(bstTree);
        TreeController treeControllerForAVL = new TreeController(avlTree);

    //    Intitialize parallel transition
        ParallelTransition pl = new ParallelTransition();
        Group root = new Group();

        primaryStage.setTitle("Visualize Tree Algorithms");
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);

        TextField userText = new TextField();
        root.getChildren().add(userText); //attach the text field to the scene.

        Button b = new Button();
        b.setLayoutX(500);
        b.setLayoutY(0);
        b.setMinWidth(50);
        root.getChildren().add(b); // attach the button to desired node in the scene

        // Program run when user input new value
        b.setOnAction(event -> {

            String text = userText.getText();
            int userInput = Integer.parseInt(text);
            System.out.println("Before run: " + bstTree);
            // Add value to tree
            treeControllerForAVL.tree.insert(userInput);

            // I fixed there and it's worked, but i don't know why :\
            treeControllerForBST.tree.insert(userInput);


            // Remove the last tree to paint again
            root.getChildren().removeAll(root.getChildren().filtered(el -> el instanceof Line));
            root.getChildren().removeAll(root.getChildren().filtered(el -> el instanceof CircleNode));

            // Create treeView from treeNode
            treeControllerForAVL.updateTreeView();
            treeControllerForBST.updateTreeView();
            System.out.println(treeControllerForBST.treeView.toString());

            // Display BST Tree to screen
            treeControllerForBST.displayTree(root);

            // Run rotate animation on BST Tree
            for (Node bstKey : treeControllerForBST.treeView.keySet()) {
                for (Node avlKey : treeControllerForAVL.treeView.keySet()) {
                    if (bstKey.element.compareTo(avlKey.element) == 0) {
                          CircleNode bstNode = treeControllerForBST.treeView.get(bstKey);
                          CircleNode avlNode = treeControllerForAVL.treeView.get(avlKey);

                          // Find position to moving to in avlTree
                          double moveToX = avlNode.getLayoutX();
                          double moveToY = avlNode.getLayoutY();

                          TranslateTransition tt = new TranslateTransition(Duration.seconds(3), bstNode);
                          tt.setCycleCount(1);
                          tt.setByX(moveToX - bstNode.getLayoutX());
                          tt.setByY(moveToY - bstNode.getLayoutY());
                          pl.getChildren().add(tt);
                    }
                }
            }

//            pl.setOnFinished(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent actionEvent) {
//                    treeControllerForBST.setTreeNode(treeControllerForAVL.getTreeNode());
//                }
//            });

            // Play rotate animation
            pl.play();

            // Clear all lines on the screen
            root.getChildren().removeAll(root.getChildren().filtered(el -> el instanceof Line));

            //  After animation, change BST Tree to AVL Tree to continue

//              treeControllerForBST.updateTreeView();
            //  treeControllerForAVL.updateTreeView();

            //  Display lines again
            treeControllerForAVL.treeView.forEach((node, cir) -> {
                  System.out.println("Value: " + node.element + ": " + cir.getLayoutX());
                  if (cir.getLineLeft() != null) root.getChildren().add(cir.getLineLeft());
                  if (cir.getLineRight() != null) root.getChildren().add(cir.getLineRight());
            });

            treeControllerForBST.tree = treeControllerForAVL.tree.cloneTree();
            System.out.println("After assign: " + treeControllerForBST.tree);
        });

        primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
