import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;
import java.util.Stack;

/**
 * Created by jh on 11/24/17.
 */
public class StackJShin extends Application
{
  private Stack<Integer> stack;

  /**
   * Default constructor to construct a stack.
   */
  public StackJShin()
  {
    stack = new Stack<>();
  }

  /**
   * Pushes an element(Integer) to the top of stack
   *
   * @param i
   */
  public void push(Integer i)
  {
    stack.push(i);
  }

  /**
   * Removes an element(Character) from the top of stack.
   * peek: Looks at the object at the top of this stack without removing it from the stack
   * pop: Removes the object at the top of this stack and returns that object as the value of this function.
   */
  public void pop()
  {
    stack.pop();
  }

  private static final int WINDOW_WIDTH = 300;
  private static final int WINDOW_HEIGHT = 550;

  private BorderPane border = new BorderPane();
  private FlowPane centerView = new FlowPane();
  private FlowPane bottomView = new FlowPane();

  @Override
  public void start(Stage primaryStage)
  {
    primaryStage.setTitle("Stack_JShin");

    StackPane pushBtn = makeButton("Push");
    pushBtn.setOnMouseClicked(this::handlePush);

    StackPane popBtn = makeButton("Pop");
    popBtn.setOnMouseClicked(this::handlePop);

    setCenterView();
    setBottomView();

    bottomView.getChildren().addAll(pushBtn, popBtn);
    primaryStage.setScene(new Scene(border, WINDOW_WIDTH, WINDOW_HEIGHT));
    primaryStage.show();
  }

  /**
   * Handles a MouseClicked event of push button.
   * this.push() - pushes an element to a stack data structure
   * addNode() - adds a node to view components
   *
   * @param event
   */
  private void handlePush(MouseEvent event)
  {
    int number;
    if (stack.size() == 8) return;
    while (true)
    {
      Random rnd = new Random();
      int rndNum = rnd.nextInt(10);
      if (!stack.contains(rndNum))
      {
        number = rndNum;
        this.push(rndNum);
        break;
      }
    }

    if (centerView.getChildren().size() > 0)
    {
      StackPane stackLast = (StackPane) centerView.getChildren().get(0);
      ((Rectangle) stackLast.getChildren().get(0)).setFill(Color.valueOf("#7d70f5"));
      ((Rectangle) stackLast.getChildren().get(0)).setStroke(Color.valueOf("#2F2F2F"));
    }

    addNode(number);
  }

  /**
   * Handles a MouseClicked event of pop button.
   * this.pop() - pops an element from a stack data structure
   * removeNode() - removes a node from view components
   *
   * @param event
   */
  private void handlePop(MouseEvent event)
  {
    if (stack.size() == 0) return;

    this.pop();

    removeNode();

    if (centerView.getChildren().size() > 0)
    {
      StackPane stackLast = (StackPane) centerView.getChildren().get(0);
      ((Rectangle) stackLast.getChildren().get(0)).setFill(Color.valueOf("#7d70f5"));
      ((Rectangle) stackLast.getChildren().get(0)).setStroke(Color.valueOf("#2F2F2F"));
    }
  }

  /**
   * Adds a node to view components with inputted random number from 0 to 9
   *
   * @param number
   * @return a StackPane which stacks rectangle and text to create a node
   */
  private void addNode(int number)
  {
    Rectangle rect = new Rectangle();
    rect.setFill(Color.valueOf("#F92F2F"));
    rect.setStroke(Color.valueOf("#2F2F2F"));
    rect.setWidth(50);
    rect.setHeight(50);

    Text text = new Text();
    text.setText(Integer.toString(number));
    text.setFont(Font.font("Verdana", FontWeight.NORMAL, 16));

    StackPane element = new StackPane();
    element.getChildren().addAll(rect, text);
    centerView.getChildren().add(0,element);

    FadeTransition ft = new FadeTransition(Duration.millis(200),
            centerView.getChildren().get(0));
    ft.setFromValue(0.0);
    ft.setToValue(1.0);
    ft.play();

  }

  /**
   * Removes a node from view components
   */
  private void removeNode()
  {
    FadeTransition ft = new FadeTransition(Duration.millis(500), centerView.getChildren().get(0));
    ft.setFromValue(1.0);
    ft.setToValue(0.0);
    ft.setOnFinished(e -> centerView.getChildren().remove(0));
    ft.play();
  }

  /**
   * Makes a button with inputted text
   *
   * @param buttonText
   * @return a StackPane which stacks rectangle and text to create a button
   */
  private StackPane makeButton(String buttonText)
  {
    Rectangle rect = new Rectangle(100, 30);
    rect.setFill(Color.valueOf("#ffd15c"));
    rect.setStroke(Color.valueOf("#ffd15c"));
    rect.setArcWidth(10);
    rect.setArcHeight(10);

    Text text = new Text();
    text.setText(buttonText);
    text.setFont(Font.font("Verdana", FontWeight.NORMAL, 16));

    StackPane btn = new StackPane();
    btn.getChildren().addAll(rect, text);

    return btn;
  }

  /**
   * Sets a center view of GridPane
   */
  private void setCenterView()
  {
    centerView.setPrefWrapLength(50);
    centerView.setAlignment(Pos.BOTTOM_CENTER);
    centerView.setPadding(new Insets(25, 120, 25, 120));
    centerView.setHgap(0);
    centerView.setVgap(0);
    centerView.setStyle("-fx-background-color: #2F2F2F;");
    border.setCenter(centerView);
  }


  /**
   * Sets a bottom view of GridPane
   */
  private void setBottomView()
  {
    bottomView.setAlignment(Pos.CENTER);
    bottomView.setPadding(new Insets(25, 5, 25, 5));
    bottomView.setHgap(50);
    bottomView.setVgap(0);
    bottomView.setStyle("-fx-background-color: #2F2F2F;");
    border.setBottom(bottomView);
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}
