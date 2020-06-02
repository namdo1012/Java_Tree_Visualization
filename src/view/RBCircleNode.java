package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Iterator;

import static model.Constants.BLACK;
import static model.Constants.RED;

public class RBCircleNode extends CircleNode {
  private boolean color = RED;

  public RBCircleNode(String value, double centerX, double centerY, double hGap, boolean color) {
    super(value, centerX, centerY, hGap);
    this.color = color;

    Iterator rbChild = this.getChildren().iterator();

    Circle cir = (Circle) rbChild.next();
    Text text = (Text) rbChild.next();
    text.setFill(Color.PALEGREEN);
    if (this.color == BLACK) {
      cir.setFill(Color.BLACK);
    } else {
      cir.setFill(Color.RED);
    }
  }
}
