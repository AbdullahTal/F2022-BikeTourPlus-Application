package ca.mcgill.ecse.biketourplus.javafx.fxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;


public class BikeTourPlusFxmlView extends Application {

  public static final EventType<Event> REFRESH_EVENT = new EventType<>("REFRESH");
  private static BikeTourPlusFxmlView instance;
  private List<Node> refreshableNodes = new ArrayList<>();
  private Scene scene;
  private JMetro jMetro;

  @Override
  /**
   * Starts the BikeTourPlus application view
   *
   * @param primaryStage : Stage object for the application window
   */
  public void start(Stage primaryStage) {
    instance = this;
    try {
      var root = (Pane) FXMLLoader.load(getClass().getResource("MainPage.fxml"));
      scene = new Scene(root);
      jMetro = new JMetro(Style.LIGHT);
      jMetro.setScene(scene);

      setDarkMode(); // Initializes to light mode
      primaryStage.setScene(scene);
      primaryStage.setMinWidth(800);
      primaryStage.setMinHeight(600);
      primaryStage.setTitle("BikeTourPlus");
      primaryStage.show();
      refresh();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Changes application theme to match dark_mode variable
   *
   * @author Ari Smith and Andrew Kan
   */
  public void setDarkMode() {
    try {
      if (BikeTourPlusApplication.DARK_MODE) {
        scene.getRoot().setStyle("-fx-base: rgba(60, 60, 60, 255)");
        jMetro.setStyle(Style.DARK);
        scene.getStylesheets().remove(getClass().getResource("light.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
      }
      else {
        scene.getRoot().setStyle("-fx-base: rgb(138, 254, 177)");
        jMetro.setStyle(Style.LIGHT);
        scene.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  // Register the node for receiving refresh events
  public void registerRefreshEvent(Node node) {
    refreshableNodes.add(node);
  }

  // Register multiple nodes for receiving refresh events
  public void registerRefreshEvent(Node... nodes) {
    for (var node : nodes) {
      refreshableNodes.add(node);
    }
  }

  // remove the node from receiving refresh events
  public void removeRefreshableNode(Node node) {
    refreshableNodes.remove(node);
  }

  // fire the refresh event to all registered nodes
  public void refresh() {
    for (Node node : refreshableNodes) {
      node.fireEvent(new Event(REFRESH_EVENT));
    }
  }

  public static BikeTourPlusFxmlView getInstance() {
    return instance;
  }

}
