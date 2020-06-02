package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Iterator;

import static model.Constants.*;

public class SearchCircleNode extends CircleNode{
  public SearchCircleNode() {
    super("", CENTER_X, CENTER_Y, H_GAP);
    Iterator rbChild = this.getChildren().iterator();

    Circle cir = (Circle) rbChild.next();
    cir.setStroke(Color.ORANGERED);
    cir.setFill(Color.TRANSPARENT);
  }

//  public void moveTo(double toX, double toY) {
//    this.createAnimationTranslateTo(toX, toY);
//    this.setLayoutX(toX);
//    this.setLayoutY(toY);
//  }
}
