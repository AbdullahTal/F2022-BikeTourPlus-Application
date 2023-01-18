package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet1Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet2Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet6Controller;
import ca.mcgill.ecse.biketourplus.controller.TOBookableItem;
import ca.mcgill.ecse.biketourplus.controller.TOCombo;
import ca.mcgill.ecse.biketourplus.controller.TOComboItem;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;

public class ComboController {
  @FXML
  private Button addComboButton;
  @FXML
  private TextField comboDiscountTextField;
  @FXML
  private TextField comboNameTextField;
  @FXML
  private TextField newComboNameTextField;
  @FXML
  private TextField newComboDiscountTextField;
  @FXML
  private Button updateComboButton;
  @FXML
  private ChoiceBox<String> existingComboChoiceBox;
  @FXML
  private Button removeGearFromComboButton;
  @FXML
  private Button addGearToComboButton;
  @FXML
  private ChoiceBox<String> gearOfComboChoiceBox;
  @FXML
  private ChoiceBox<String> updateGearComboNameChoiceBox;
  @FXML
  private ChoiceBox<String> deleteComboChoiceBox;
  @FXML
  private Button deleteComboButton;
  @FXML
  private TableView<TOCombo> comboTable;
  @FXML
  private TableColumn<TOCombo, String> comboNameColumn;
  @FXML
  private TableColumn<TOCombo, String> comboPriceColumn;
  @FXML
  private TableColumn<TOCombo, String> comboDiscountColumn;
  @FXML
  private TableView<TOComboItem> gearOfComboTable;
  @FXML
  private TableColumn<TOComboItem, String> gearOfComboNameColumn;
  @FXML
  private TableColumn<TOComboItem, String> gearOfComboQuantityColumn;

  private TOCombo selectedCombo;

  /**
   * Initializes the tables and choice boxes when the application is initialized
   * 
   * @author Annie Gouchee
   */
  @FXML
  public void initialize() {

    gearOfComboChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      gearOfComboChoiceBox.setItems(getGearNames());
      gearOfComboChoiceBox.setValue(null);
    });

    // initialize combo choice box
    existingComboChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      existingComboChoiceBox.setItems(getComboNames());
      existingComboChoiceBox.setValue(null);
    });

    updateGearComboNameChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      updateGearComboNameChoiceBox.setItems(getComboNames());
      updateGearComboNameChoiceBox.setValue(null);
    });

    deleteComboChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      deleteComboChoiceBox.setItems(getComboNames());
      deleteComboChoiceBox.setValue(null);
    });

    // combo table
    comboNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    comboPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    comboDiscountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));

    comboTable.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      comboTable.setItems(getComboWithDiscount());
    });

    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(comboTable);

    comboTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    comboTable.setPlaceholder(new Label("No gear found"));

    // gearOfCombo table

    gearOfComboNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    gearOfComboQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    gearOfComboTable.setPlaceholder(new Label("No gear found"));

    gearOfComboTable.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      gearOfComboTable.setItems(FXCollections.emptyObservableList());
    });
    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(gearOfComboTable);

    // let the application know about refreshable nodes
    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(existingComboChoiceBox,
        updateGearComboNameChoiceBox);
    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(gearOfComboChoiceBox,
        deleteComboChoiceBox);

  }

  /**
   * Helper method that gets all the gears of a selected combo.
   * 
   * @return An observable list of the gears in a combo
   * @author Annie Gouchee
   */
  public ObservableList<TOComboItem> getGearOfCombo() {
    if (selectedCombo == null) {
      return FXCollections.emptyObservableList();
    }
    String comboName = selectedCombo.getName();
    return FXCollections
        .observableArrayList(BikeTourPlusFeatureSet1Controller.getGearOfCombo(comboName));
  }

  /**
   * Helper method that gets all combos in the bike tour plus system.
   * 
   * @return An observable list of combos
   * @author Annie Gouchee
   */
  public ObservableList<TOCombo> getComboWithDiscount() {
    return FXCollections
        .observableArrayList(BikeTourPlusFeatureSet1Controller.getComboWithDiscount());
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
   * Helper method that gets all combo names in the bike tour plus system.
   * 
   * @return An observable list of combo names
   * @author Annie Gouchee
   */
  public ObservableList<String> getComboNames() {
    List<String> comboNames = new ArrayList<>();
    for (TOBookableItem combo : BikeTourPlusFeatureSet1Controller.getCombos()) {
      comboNames.add(combo.getName());
    }
    if (comboNames.size() == 0) {
      return FXCollections.emptyObservableList();
    }
    return FXCollections.observableArrayList(comboNames);
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
   * An event listener for when the addCombo button has been pressed that adds the combo to the bike
   * tour plus system if possible
   * 
   * @param event Action corresponding to the user clicking the button
   * @author Annie Gouchee
   */
  // Event Listener on Button[#addComboButton].onAction
  @FXML
  public void addComboClicked(ActionEvent event) {
    String error = "";

    String comboName = comboNameTextField.getText();
    String comboDiscount = comboDiscountTextField.getText();
    int discount = 0;

    if (comboName == null || comboName.equals("")) {
      error += "Please enter a name \n";
    }
    if (comboDiscount == null || comboDiscount.equals("")) {
      error += "Please enter a discount \n";
    } else {
      if (!isNumber(comboDiscount)) {
        error += "Please enter a valid number \n";
      } else {
        discount = Integer.parseInt(comboDiscount);
      }
    }

    if (!error.isEmpty()) {
      ViewUtils.showError(error);
    } else {
      if (ViewUtils.successful(BikeTourPlusFeatureSet6Controller.addCombo(comboName, discount))) {
        comboNameTextField.setText("");
        comboDiscountTextField.setText("");
        ViewUtils.showSuccess("Combo Created Successfully");
      }
    }
  }

  /**
   * An event listener for when the updateCombo button has been pressed that updates the combo with
   * the appropriate parameters if possible
   * 
   * @param event Action corresponding to the user clicking the button
   * @author Annie Gouchee
   */
  // Event Listener on Button[#updateComboButton].onAction
  @FXML
  public void updateComboClicked(ActionEvent event) {
    String error = "";

    String oldComboName = existingComboChoiceBox.getValue();
    String newComboName = newComboNameTextField.getText();
    String newDiscount = newComboDiscountTextField.getText();
    int discount = 0;

    if (oldComboName == null || oldComboName.equals("")) {
      error += "Please choose a combo to modify \n";
    }
    if (newComboName == null || newComboName.equals("")) {
      error += "Please enter a new name \n";
    }

    if (newDiscount == null || newDiscount.equals("")) {
      error += "Please enter a discount \n";
    } else {
      if (!isNumber(newDiscount)) {
        error += "Please enter a valid number \n";
      } else {
        discount = Integer.parseInt(newDiscount);
      }
    }

    if (!error.isEmpty()) {
      ViewUtils.showError(error);
    } else if (ViewUtils.successful(
        BikeTourPlusFeatureSet6Controller.updateCombo(oldComboName, newComboName, discount))) {
      existingComboChoiceBox.setValue(null);
      newComboNameTextField.setText("");
      newComboDiscountTextField.setText("");
      ViewUtils.showSuccess("Combo Updated Successfully");
    }
  }

  /**
   * An event listener for when the removeGearFromCombo button has been clicked that removes an
   * instance of a given gear from the combo if possible
   * 
   * @param event Action corresponding to the user clicking the button
   * @author Annie Gouchee
   */
  // Event Listener on Button[#removeGearFromComboButton].onAction
  @FXML
  public void removeGearFromComboClicked(ActionEvent event) {
    String error = "";

    String comboName = updateGearComboNameChoiceBox.getValue();
    String gearName = gearOfComboChoiceBox.getValue();

    if (comboName == null || comboName.equals("")) {
      error += "Please choose a combo \n";
    }

    if (gearName == null || gearName.equals("")) {
      error += "Please choose a gear \n";
    }

    int selectedComboIndex = comboTable.getSelectionModel().getSelectedIndex();
    if (!error.isEmpty()) {
      ViewUtils.showError(error);
    } else {
      error = BikeTourPlusFeatureSet6Controller.removeGearFromCombo(gearName, comboName);
      if (!error.isEmpty()) {
        ViewUtils.showError(error);
      } else {
        updateGearOfComboTable();
        comboTable.setItems(getComboWithDiscount());
        comboTable.getSelectionModel().select(selectedComboIndex);
      }
    }
  }

  /**
   * An event listener for when the addGearToCombo button has been clicked that adds an instance of
   * a given gear to a combo if possible
   * 
   * @param event Action corresponding to the user clicking the button
   * @author Annie Gouchee
   */
  // Event Listener on Button[#addGearToComboButton].onAction
  @FXML
  public void addGearToComboClicked(ActionEvent event) {
    String error = "";

    String comboName = updateGearComboNameChoiceBox.getValue();
    String gearName = gearOfComboChoiceBox.getValue();

    if (comboName == null || comboName.equals("")) {
      error += "Please choose a combo \n";
    }

    if (gearName == null || gearName.equals("")) {
      error += "Please choose a gear \n";
    }

    int selectedComboIndex = comboTable.getSelectionModel().getSelectedIndex();
    if (!error.isEmpty()) {
      ViewUtils.showError(error);
    } else {
      error = BikeTourPlusFeatureSet6Controller.addGearToCombo(gearName, comboName);
      if (!error.isEmpty()) {
        ViewUtils.showError(error);
      } else {
        updateGearOfComboTable();
        comboTable.setItems(getComboWithDiscount());
        comboTable.getSelectionModel().select(selectedComboIndex);
      }
    }
  }

  /**
   * An event listener for when the deleteCombo button has been clicked that deletes the given combo
   * if possible
   * 
   * @param event Action corresponding to the user clicking the button
   * @author Annie Gouchee
   */
  // Event Listener on Button[#deleteComboButton].onAction
  @FXML
  public void deleteComboClicked(ActionEvent event) {
    String error = "";

    String comboName = deleteComboChoiceBox.getValue();

    if (comboName == null || comboName.equals("")) {
      error += "Please choose a combo to delete \n";
    }

    if (!error.isEmpty()) {
      ViewUtils.showError(error);
    } else if (ViewUtils.successful(BikeTourPlusFeatureSet2Controller.deleteCombo(comboName))) {
      deleteComboChoiceBox.setValue(null);
      ViewUtils.showSuccess("Combo Deleted Successfully");
    }
  }

  /**
   * An event listener that for when an item from the combo table has been selected by the user that
   * lists the gears in the given table
   * 
   * @param event Action corresponding to if the user has clicked an item of the combo table
   * @author Annie Gouchee, Ari Smith and Andrew Kan
   */
  @FXML
  public void comboTableClicked(MouseEvent event) {
    comboTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    selectedCombo = comboTable.getSelectionModel().getSelectedItem();
    updateGearOfComboTable();
  }

  /**
   * Helper method that lists the gears of a selected combo in the table
   * 
   * @author Annie Gouchee, Ari Smith and Andrew Kan
   */
  public void updateGearOfComboTable() {
    if (selectedCombo != null) {
      gearOfComboTable.setItems(getGearOfCombo());

      ObservableList<TOCombo> selectedItems = comboTable.getSelectionModel().getSelectedItems();

      selectedItems.addListener(new ListChangeListener<TOCombo>() {
        @Override
        public void onChanged(Change<? extends TOCombo> change) {
          if (!selectedItems.isEmpty()) {
            gearOfComboTable.setItems(getGearOfCombo());
          }
        }
      });
    }
  }
}
