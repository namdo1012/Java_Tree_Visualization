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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.AVLTree;
import model.BST;
import model.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception{

        BST<Integer> bstTree = new BST<>();
        AVLTree<Integer> avlTree = new AVLTree<>();

        TreeController treeControllerForBST = new TreeController(bstTree);
        TreeController treeControllerForAVL = new TreeController(avlTree);

        Group root = new Group();
        primaryStage.setTitle("Visualize Tree Algorithms");
        Scene scene = new Scene(root, 1000, 600);
        scene.setFill(Color.rgb(219, 237, 187));
        primaryStage.setScene(scene);

        TextField insertText = new TextField();
        insertText.setPromptText("Insert a value...");
        root.getChildren().add(insertText); //attach the text field to the scene.

        TextField deleteText = new TextField();
        deleteText.setLayoutX(250);
        deleteText.setPromptText("Delete a value...");
        root.getChildren().add(deleteText); //attach the text field to the scene.

        Button insertButton = new Button("Insert");
        insertButton.setLayoutX(500);
        insertButton.setLayoutY(0);
        insertButton.setMinWidth(50);
        root.getChildren().add(insertButton);

        Button deleteButton = new Button("Delete");
        deleteButton.setLayoutX(600);
        deleteButton.setLayoutY(0);
        deleteButton.setMinWidth(50);
        root.getChildren().add(deleteButton); // attach the button to desired node in the scene

      /***
       *  DELETE: Program run when user input value need to be deleted and click this button
       */
      deleteButton.setOnAction(event -> {
          // Get user input value
          String text = deleteText.getText();
          int userInput = Integer.parseInt(text);

          // Add value to tree
          treeControllerForAVL.getTreeNode().delete(userInput);
//          treeControllerForBST.getTreeNode().delete(userInput);

          // Remove the last tree to paint again
          root.getChildren().removeAll(root.getChildren().filtered(el -> el instanceof Line));
          root.getChildren().removeAll(root.getChildren().filtered(el -> el instanceof CircleNode));

          // Create treeView from treeNode
          treeControllerForAVL.updateTreeView();
          treeControllerForBST.updateTreeView();

          // Display BST Tree to screen
          treeControllerForBST.displayTree(root);

          // Create animation when delete on BST Tree
          ParallelTransition deleteAnima = treeControllerForBST.createAnimationHandleDelete(root, treeControllerForAVL.treeView);

          // Play rotate animation
          deleteAnima.play();

          // Clear all lines on the screen
          root.getChildren().removeAll(root.getChildren().filtered(el -> el instanceof Line));

          //  Display lines again
          treeControllerForAVL.displayLines(root);

          // Reassign the last AVLTree to BSTTree to continue
          treeControllerForBST.setTreeNode(treeControllerForAVL.getTreeNode().cloneTree());
      });

      /***
       *  INSERT: Program run when user input new value and click this button
       */

        insertButton.setOnAction(event -> {

            // Get user input value
            String text = insertText.getText();
            int userInput = Integer.parseInt(text);

            // Add value to tree
            treeControllerForAVL.getTreeNode().insert(userInput);
            treeControllerForBST.getTreeNode().insert(userInput);

            // Remove the last tree to paint again
            root.getChildren().removeAll(root.getChildren().filtered(el -> el instanceof Line));
            root.getChildren().removeAll(root.getChildren().filtered(el -> el instanceof CircleNode));

            // Create treeView from treeNode
            treeControllerForAVL.updateTreeView();
            treeControllerForBST.updateTreeView();

            // Display BST Tree to screen
            treeControllerForBST.displayTree(root);

            // Run rotate animation on BST Tree
            ParallelTransition insertAnima = treeControllerForBST.createAnimationHandleInsert(root, treeControllerForAVL.treeView);

            // Play rotate animation
            insertAnima.play();

            // Clear all lines on the screen
            root.getChildren().removeAll(root.getChildren().filtered(el -> el instanceof Line));

            //  Display lines again
            treeControllerForAVL.displayLines(root);

            // Reassign the last AVLTree to BSTTree to continue
            treeControllerForBST.setTreeNode(treeControllerForAVL.getTreeNode().cloneTree());
        });

        primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
