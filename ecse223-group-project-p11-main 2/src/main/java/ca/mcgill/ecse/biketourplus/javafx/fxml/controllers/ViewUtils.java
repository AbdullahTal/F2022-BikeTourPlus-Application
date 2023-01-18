package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;


import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet1Controller;
import ca.mcgill.ecse.biketourplus.controller.TOBikeTour;

import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Adapted from BTMS example
 */
public class ViewUtils {

  /** Calls the controller and shows an error, if applicable. */
  public static boolean callController(String result) {
    BikeTourPlusFxmlView.getInstance().refresh();
    if (result.isEmpty()) {
      return true;
    }
    showError(result);
    return false;
  }

  /** Calls the controller and returns true on success. This method is included for readability. */
  public static boolean successful(String controllerResult) {
    return callController(controllerResult);
  }

  /**
   * Creates a popup window.
   *
   * @param title: title of the popup window
   * @param message: message to display
   */
  public static void makePopupWindow(String title, String message) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    Text text = new Text(message);
    Button okButton = new Button("OK");
    okButton.setOnAction(a -> dialog.close());

    // display the popup window
    int innerPadding = 10; // inner padding/spacing
    int outerPadding = 100; // outer padding
    dialogPane.setSpacing(innerPadding);
    dialogPane.setAlignment(Pos.CENTER);
    dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
    dialogPane.getChildren().addAll(text, okButton);
    Scene dialogScene = new Scene(dialogPane, outerPadding + 5 * message.length(), outerPadding);
    dialog.setScene(dialogScene);
    dialog.setTitle(title);
    dialog.show();
  }

  /**
   * Creates an error popup window
   * 
   * @param message : error message to display
   */
  public static void showError(String message) {
    makePopupWindow("Error", message);
  }

  /**
   * Creates a success popup window
   * 
   * @param message : success message to display
   */
  public static void showSuccess(String message) {
    makePopupWindow("Success", message);
  }

  /**
   * Returns a list of the starting weeks of the bike tours in the system
   *
   * @author Simon Younaki
   * @return observable list of starting weeks
   */
  public static ObservableList<Integer> getAllStartingWeeks() {
    // We need to know what are the offered/available BikeTours, and then we get their starting week
    ArrayList<TOBikeTour> allBikeTours =
        (ArrayList<TOBikeTour>) BikeTourPlusFeatureSet1Controller.getAllBikeTours();
    // A hashSet that contains all possible starting weeks
    HashSet<Integer> allStartingWeeks = new HashSet<Integer>();
    // Adding the possible starting weeks to the hashSet
    for (TOBikeTour aBikeT : allBikeTours) {
      allStartingWeeks.add(aBikeT.getStartWeek());
    } // Done, now the hashSet has all the possible starting weeks
    // now transform the hashSet into an ArrayList
    ArrayList<Integer> allStarting_Weeks = new ArrayList<Integer>(allStartingWeeks);
    // as javafx works with observable list, we need to convert the java.util.List to
    // javafx.collections.observableList
    return FXCollections.observableList(allStarting_Weeks);
  }

}


