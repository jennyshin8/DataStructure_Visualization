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
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by jh on 11/24/17.
 */
public class QueueJShin extends Application
{
  private Queue<Integer> queue;

  /**
   * Default constructor to construct a queue.
   * Since a queue is interface, it should be initialized as a LinkedList or a PriorityQueue.
   * (https://docs.oracle.com/javase/7/docs/api/java/util/Queue.html)
   */
  public QueueJShin()
  {
    queue = new LinkedList<>();
  }

  /**
   * Puts an element(Integer) to the tail of queue
   *
   * @param i
   */
  public void enqueue(Integer i)
  {
    queue.add(i);
  }

  /**
   * Removes an element(Character) from the head of queue.
   * poll: if there is nothing to remove, returns null
   * pop: if there is nothing to remove, throws an exception
   */
  public void dequeue()
  {
    queue.poll();
  }

  private static final int WINDOW_WIDTH = 500;
  private static final int WINDOW_HEIGHT = 300;

  private BorderPane border = new BorderPane();
  private FlowPane centerView = new FlowPane();
  private FlowPane bottomView = new FlowPane();

  @Override
  public void start(Stage primaryStage)
  {
    primaryStage.setTitle("Queue_JShin");

    StackPane enqBtn = makeButton("Enqueue");
    enqBtn.setOnMouseClicked(this::handleEnqueue);

    StackPane deqBtn = makeButton("Dequeue");
    deqBtn.setOnMouseClicked(this::handleDequeue);

    setCenterView();
    setBottomView();

    bottomView.getChildren().addAll(enqBtn, deqBtn);
    primaryStage.setScene(new Scene(border, WINDOW_WIDTH, WINDOW_HEIGHT));
    primaryStage.show();
  }

  /**
   * Handles a MouseClicked event of enqueue button.
   * this.enqueue() - enqueues an element to a queue data structure
   * addNode() - adds a node to view components
   *
   * @param event
   */
  private void handleEnqueue(MouseEvent event)
  {
    int number;
    if (queue.size() == 8) return;
    while (true)
    {
      Random rnd = new Random();
      int rndNum = rnd.nextInt(10);
      if (!queue.contains(rndNum))
      {
        number = rndNum;
        this.enqueue(rndNum);
        break;
      }
    }

    if (centerView.getChildren().size() > 0)
    {
      StackPane stackLast = (StackPane) centerView.getChildren().get(centerView.getChildren().size() - 1);
      ((Rectangle) stackLast.getChildren().get(0)).setFill(Color.valueOf("#7d70f5"));
      ((Rectangle) stackLast.getChildren().get(0)).setStroke(Color.valueOf("#2F2F2F"));
    }

    addNode(number);

    if (centerView.getChildren().size() * 51 > (500 - centerView.getPadding().getLeft()) ||
            centerView.getChildren().size() == 1)
    {
      centerView.setPadding(new Insets(25, 45, 25, 45));
    }
  }

  /**
   * Handles a MouseClicked event of dequeue button.
   * this.dequeue() - dequeues an element from a queue data structure
   * removeNode() - removes a node from view components
   *
   * @param event
   */
  private void handleDequeue(MouseEvent event)
  {
    if (queue.size() == 0) return;

    this.dequeue();

    removeNode();

    if (centerView.getChildren().size() > 0)
    {
      StackPane stackLast = (StackPane) centerView.getChildren().get(centerView.getChildren().size() - 1);
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
    centerView.getChildren().addAll(element);

    FadeTransition ft = new FadeTransition(Duration.millis(200),
            centerView.getChildren().get(centerView.getChildren().size()-1));
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
    centerView.setAlignment(Pos.CENTER_LEFT);
    centerView.setPadding(new Insets(25, 45, 25, 45));
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
    bottomView.setPadding(new Insets(25, 25, 25, 25));
    bottomView.setHgap(100);
    bottomView.setVgap(0);
    bottomView.setStyle("-fx-background-color: #2F2F2F;");
    border.setBottom(bottomView);
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}
