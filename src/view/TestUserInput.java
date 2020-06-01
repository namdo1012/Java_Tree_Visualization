//package view;
//
//import controller.TreeController;
//import javafx.animation.ParallelTransition;
//import javafx.animation.TranslateTransition;
//import javafx.application.Application;
//import javafx.event.EventHandler;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.shape.Line;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//import model.AVLTree;
//import model.BST;
//import model.Node;
//
//public class Main extends Application {
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//
//        BST<Integer> bstTree = new BST<>();
//        AVLTree<Integer> avlTree = new AVLTree<>();
//
//        TreeController treeControllerForBST = new TreeController(bstTree);
//        TreeController treeControllerForAVL = new TreeController(avlTree);
//
//        //    Intitialize parallel transition
//        ParallelTransition pl = new ParallelTransition();
//        Group root = new Group();
//
////    TEST WITH USER INPUT
//        int i = 0;
//        int[] array = {10, 16, 7, 19, 25, 2, 1, 0, -2};
//
//        for (int i = 0 ; i < array.length; i++){
//
//
//
//            avlTree.insert(10);
//            bstTree.insert(10);
//            avlTree.insert(16);
//            bstTree.insert(16);
//            avlTree.insert(7);
//            bstTree.insert(7);
//            avlTree.insert(19);
//            bstTree.insert(19);
//            avlTree.insert(25);
//            bstTree.insert(25);
//            avlTree.insert(2);
//            bstTree.insert(2);
//            avlTree.insert(1);
//            bstTree.insert(1);
//            avlTree.insert(0);
//            bstTree.insert(0);
//            avlTree.insert(-2);
//            bstTree.insert(-2);
//            avlTree.insert(3);
//            bstTree.insert(3);
//
//            // Create treeView from treeNode
//            treeControllerForAVL.updateTreeView();
//            treeControllerForBST.updateTreeView();
//
////    Display BST Tree to screen
//            treeControllerForBST.displayTree(root);
//
//            for (Node bstKey : treeControllerForBST.treeView.keySet()) {
//                for (Node avlKey : treeControllerForAVL.treeView.keySet()){
//                    if (bstKey.element.compareTo(avlKey.element) == 0){
//                        CircleNode bstNode = treeControllerForBST.treeView.get(bstKey);
//                        CircleNode avlNode = treeControllerForAVL.treeView.get(avlKey);
//
//                        // Find position to moving to in avlTree
//                        double moveToX = avlNode.getLayoutX();
//                        double moveToY = avlNode.getLayoutY();
//
//                        TranslateTransition tt = new TranslateTransition(Duration.seconds(3), bstNode);
//                        tt.setCycleCount(1);
//                        tt.setByX(moveToX - bstNode.getLayoutX());
//                        tt.setByY(moveToY - bstNode.getLayoutY());
//                        pl.getChildren().add(tt);
//                    }
//                }
//            }
//
//            primaryStage.setTitle("Visualize Tree Algorithms");
//            Scene scene = new Scene(root, 1000, 600);
//            primaryStage.setScene(scene);
//
//            //    Fire the rotating animation when hit ENTER!
//            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//                public void handle(KeyEvent ke) {
//                    System.out.println("Key Pressed: " + ke.getCode());
////              Play rotate animation for AVL Tree
//                    pl.play();
////              Clear all lines on the screen
//                    root.getChildren().removeAll(root.getChildren().filtered(el -> el instanceof Line));
//
////              After animation, change BST Tree to AVL Tree to continue
//                    treeControllerForBST.treeView = treeControllerForAVL.treeView;
//
////              Display tree again
//                    treeControllerForBST.treeView.forEach((node, cir) -> {
//                        System.out.println("Value: " + node.element + ": " + cir.getLayoutX());
//                        if (cir.getLineLeft() != null) root.getChildren().add(cir.getLineLeft());
//                        if (cir.getLineRight() != null) root.getChildren().add(cir.getLineRight());
//                    });
//                }
//            });
//
//            primaryStage.show();
//        }
//    }
