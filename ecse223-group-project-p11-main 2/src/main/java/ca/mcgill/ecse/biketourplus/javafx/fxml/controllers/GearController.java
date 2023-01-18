package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet1Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet5Controller;
import ca.mcgill.ecse.biketourplus.controller.TOBookableItem;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;

public class GearController {
  @FXML
  private TableView<TOBookableItem> gearTable;
  @FXML
  private TableColumn<TOBookableItem, String> gearNameColumn;
  @FXML
  private TableColumn<TOBookableItem, String> gearPriceColumn;
  @FXML
  private TextField gearNameTextField;
  @FXML
  private TextField gearPriceTextField;
  @FXML
  private Button createGearButton;
  @FXML
  private TextField newGearNameTextField;
  @FXML
  private TextField newGearPriceTextField;
  @FXML
  private Button updateGearButton;
  @FXML
  private ChoiceBox<String> updateGearChoiceBox;
  @FXML
  private Button deleteGearButton;
  @FXML
  private ChoiceBox<String> deleteGearChoiceBox;

  /**
   * Initializes the tables and choice boxes when the application is initialized
   * 
   * @author Annie Gouchee
   */
  @FXML
  public void initialize() {

    // initialize the gear choice box
    updateGearChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      updateGearChoiceBox.setItems(getGearNames());
      updateGearChoiceBox.setValue(null);
    });

    deleteGearChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      deleteGearChoiceBox.setItems(getGearNames());
      deleteGearChoiceBox.setValue(null);
    });

    // gear table
    gearNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    gearPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    gearTable.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      gearTable.setItems(getGears());
    });

    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(gearTable);


    // let the application know about refreshable nodes
    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(updateGearChoiceBox,
        deleteGearChoiceBox);

  }


  /**
   * Helper method that gets all gear names in the bike tour plus system.
   * 
   * @return An observable list of gear names
   * @author Annie Gouchee
   */
  public ObservableList<String> getGearNames() {
    List<String> gearNames = new ArrayList<>();
    for (TOBookableItem gear : BikeTourPlusFeatureSet1Controller.getGears()) {
      gearNames.add(gear.getName());
    }
    if (gearNames.size() == 0) {
      return FXCollections.emptyObservableList();
    }
    return FXCollections.observableArrayList(gearNames);
  }

  /**
   * Helper method that gets all gears in the bike tour plus system.
   * 
   * @return An observable list of gears
   * @author Annie Gouchee
   */
  public ObservableList<TOBookableItem> getGears() {
    return FXCollections.observableArrayList(BikeTourPlusFeatureSet1Controller.getGears());
  }

  /**
   * Helper method that verifies if a given string is an integer
   * 
   * @param number string to check if integer
   * @return boolean being true if it is a number else false if not
   * @author Annie Gouchee
   */
  public boolean isNumber(String number) {
    boolean isNumber = true;
    try {
      Integer.parseInt(number);
    } catch (Exception e) {
      isNumber = false;
    }
    return isNumber;
  }


  /**
   * An event listener for when the createGear button has been pressed that adds a gear to the 
   * bike tour plus system if possible
   * 
   * @param event Action corresponding to the user clicking the button
   * @author Annie Gouchee
   */
  // Event Listener on Button[#createGearButton].onAction
  @FXML
  public void createGearClicked(ActionEvent event) {
    String error = "";

    String gearName = gearNameTextField.getText();
    String gearPrice = gearPriceTextField.getText();
    int price = 0;

    if (gearName == null || gearName.equals("")) {
      error += "Please enter a name \n";
    }
    if (gearPrice == null || gearPrice.equals("")) {
      error += "Please enter a price \n";
    } else {
      if (!isNumber(gearPrice)) {
        error += "Please enter a valid number \n";
      } else {
        price = Integer.parseInt(gearPrice);
      }
    }

    if (!error.isEmpty()) {
      ViewUtils.showError(error);
    } else {
      if (ViewUtils.successful(BikeTourPlusFeatureSet5Controller.addGear(gearName, price))) {
        gearNameTextField.setText("");
        gearPriceTextField.setText("");
        ViewUtils.showSuccess("Gear Created Successfully");
      }
    }
  }

  /**
   * An event listener for when the updateGear button has been pressed that updates the gear 
   * with the appropriate parameters if possible
   * 
   * @param event Action corresponding to the user clicking the button
   * @author Annie Gouchee
   */
  // Event Listener on Button[#updateGearButton].onAction
  @FXML
  public void updateGearClicked(ActionEvent event) {
    String error = "";

    String oldGearName = updateGearChoiceBox.getValue();
    String newGearName = newGearNameTextField.getText();
    String gearPrice = newGearPriceTextField.getText();
    int price = 0;

    if (oldGearName == null || oldGearName.equals("")) {
      error += "Please select a gear \n";
    }

    if (newGearName == null || newGearName.equals("")) {
      error += "Please enter a gear name \n";
    }

    if (gearPrice == null || gearPrice.equals("")) {
      error += "Please enter a price \n";
    } else {
      if (!isNumber(gearPrice)) {
        error += "Please enter a valid number \n";
      } else {
        price = Integer.parseInt(gearPrice);
      }
    }

    if (!error.isEmpty()) {
      ViewUtils.showError(error);
    } else if (ViewUtils.successful(
        BikeTourPlusFeatureSet5Controller.updateGear(oldGearName, newGearName, price))) {
      newGearNameTextField.setText("");
      newGearPriceTextField.setText("");
      updateGearChoiceBox.setValue(null);
      ViewUtils.showSuccess("Gear Updated Successfully");
    }

  }

  /**
   * An event listener for when the deleteGear button has been pressed that deletes a gear from the 
   * bike tour plus system if possible
   * 
   * @param event Action corresponding to the user clicking the button
   * @author Annie Gouchee
   */
  // Event Listener on Button[#deleteGearButton].onAction
  @FXML
  public void deleteGearClicked(ActionEvent event) {
    String error = "";
    String gearName = deleteGearChoiceBox.getValue();

    if (gearName == null) {
      error += "Please select a gear \n";
    }

    if (!error.isEmpty()) {
      ViewUtils.showError(error);
    } else if (ViewUtils.successful(BikeTourPlusFeatureSet5Controller.deleteGear(gearName))) {
      deleteGearChoiceBox.setValue(null);
      ViewUtils.showSuccess("Gear Deleted Successfully");
    }
  }
}
