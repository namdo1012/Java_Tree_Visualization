package view;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;

public class CircleNode extends StackPane implements Comparable{
  public String value;
  private final double RADIUS = 20;
  private final Font FONT = Font.font("Cooper Black", FontWeight.BOLD, 16);
  private double hGap;
  private Line lineLeft, lineRight;

  public Line getLineLeft() {
    return lineLeft;
  }
  public void setLineLeft(Line lineLeft) {
    this.lineLeft = lineLeft;
  }

  public Line getLineRight() {
    return lineRight;
  }
  public void setLineRight(Line lineRight) { this.lineRight = lineRight; }

  public double gethGap() { return hGap; }

  public CircleNode(String value, double centerX, double centerY, double hGap) {
    this.value = value;
    this.hGap = hGap;

    Text text = new Text(value);
    text.setFont(FONT);
    text.setBoundsType(TextBoundsType.VISUAL);

    Circle cir = new Circle(RADIUS);
    cir.setFill(Color.GRAY);
    cir.setStroke(Color.BLUEVIOLET);
    cir.setStrokeWidth(3);
    cir.toFront();

    this.getChildren().addAll(cir, text);
    this.setLayoutX(centerX);
    this.setLayoutY(centerY);
  }

  @Override
  public int compareTo(Object o) {
    if (o == null) return -1;
    if (this.getLayoutX() == ((CircleNode) o).getLayoutX() && this.getLayoutY() == ((CircleNode) o).getLayoutY()) {
      return 0;
    }
    return 1;
  }

  // ANIMATION FOR CIRCLE NODE
  public TranslateTransition createAnimationTranslateTo(double toX, double toY){
    TranslateTransition tt = new TranslateTransition(Duration.seconds(3), this);
    tt.setCycleCount(1);
    tt.setByX(toX - this.getLayoutX());
    tt.setByY(toY - this.getLayoutY());
    return tt;
  }
}
