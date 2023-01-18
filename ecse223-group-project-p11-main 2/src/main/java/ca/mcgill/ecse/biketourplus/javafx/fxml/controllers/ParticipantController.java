package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusCommonAPI;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet1Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet2Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet3Controller;
import ca.mcgill.ecse.biketourplus.controller.TOBookableItem;
import ca.mcgill.ecse.biketourplus.controller.TOBookedItem;
import ca.mcgill.ecse.biketourplus.controller.TOGuide;
import ca.mcgill.ecse.biketourplus.controller.TOParticipant;
import ca.mcgill.ecse.biketourplus.controller.TOParticipantCost;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ParticipantController {

  @FXML
  private TextField nameTextField;
  @FXML
  private TextField emailTextField;
  @FXML
  private TextField emergencyContactTextField;
  @FXML
  private Spinner<Integer> numberOfWeeksWantedTextField;
  @FXML
  private Spinner<Integer> weekAvailableFromTextField;
  @FXML
  private Spinner<Integer> weekAvailableToTextField;
  @FXML
  private TextField displayedCost;
  @FXML
  private TextField codeTextField;
  @FXML
  private PasswordField passwordTextField;
  @FXML
  private Button registerButton;
  @FXML
  private Button updateInformationButton;
  @FXML
  private Button addButton;
  @FXML
  private Button removeButton;
  @FXML
  private Button deleteButton;
  @FXML
  private Button viewCostButton;
  @FXML
  private Button payButton;
  @FXML
  private Button cancelButton;
  @FXML
  private Button clearParticipantInfoButton;
  @FXML
  private Button emailGuideButton;
  @FXML
  private ComboBox<String> participantComboBox;
  @FXML
  private ComboBox<String> bookableItemBox;
  @FXML
  private TableView<TOBookedItem> gearTable;
  @FXML
  private CheckBox lodgeCheckBox;

  /**
   * This method will be called when the application is initialized. Sets up fields and refreshable
   * elements
   * 
   * @author Ari Smith and Andrew Kan
   */
  @FXML
  public void initialize() {

    numberOfWeeksWantedTextField
        .setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 53));
    weekAvailableFromTextField
        .setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 53));
    weekAvailableToTextField
        .setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 53));
    emailGuideButton.setDisable(true);

    bookableItemBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      List<String> bookableStringList = new ArrayList<String>();
      for (TOBookableItem b : BikeTourPlusFeatureSet1Controller.getListBookableItems()) {
        bookableStringList.add(b.getName() + " - $" + b.getPrice() + "/week");
      }
      bookableItemBox.getItems().clear();
      bookableItemBox.getItems().addAll(bookableStringList);
    });

    gearTable.setPlaceholder(new Label("None found"));

    TableColumn<TOBookedItem, String> nameColumn = new TableColumn<>("Name");
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    gearTable.getColumns().add(nameColumn);

    TableColumn<TOBookedItem, Double> priceColumn = new TableColumn<>("Quantity");
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    gearTable.getColumns().add(priceColumn);

    gearTable.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT,
        e -> gearTable.setItems(getBookedItems()));
    participantComboBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      participantComboBox.setItems(BikeTourPlusFeatureSet1Controller.getParticipantEmails());
    });


    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(gearTable);
    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(participantComboBox);
    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(bookableItemBox);
  }


  /**
   * An event listener for when the register button is pressed
   * 
   * @param event - The action event associated with the button press
   * @author Ari Smith
   */
  @FXML
  public void registerParticipantClicked(ActionEvent event) {
    String error = "";
    String name = nameTextField.getText();
    String email = emailTextField.getText();
    String emergencyContact = emergencyContactTextField.getText();
    String password = passwordTextField.getText();
    int nrWeeks = 0;
    int weekAvailableFrom = 0;
    int weekAvailableTo = 0;
    boolean lodgeRequired = false;
    try {
      nrWeeks = numberOfWeeksWantedTextField.getValueFactory().getValue();
    } catch (Exception e) {
      error += "Please enter a valid number\n";
    }
    try {
      weekAvailableFrom = weekAvailableFromTextField.getValueFactory().getValue();
    } catch (Exception e) {
      if (!error.contains("Please enter a valid number")) {
        error += "Please enter a valid number\n";
      }
    }
    try {
      weekAvailableTo = weekAvailableToTextField.getValueFactory().getValue();
    } catch (Exception e) {
      if (!error.contains("Please enter a valid number")) {
        error += "Please enter a valid number\n";
      }
    }
    lodgeRequired = lodgeCheckBox.isSelected();
    if (!error.isEmpty()) {
      ViewUtils.showError(error);
    } else {
      if (ViewUtils
          .successful(BikeTourPlusFeatureSet3Controller.registerParticipant(email, password, name,
              emergencyContact, nrWeeks, weekAvailableFrom, weekAvailableTo, lodgeRequired))) {
        ViewUtils.showSuccess("Successfully registered participant");
        clearFields();
      }
    }

  }

  /**
   * An event listener for when the update button is pressed
   * 
   * @param event - The action event associated with the button press
   * @author Ari Smith
   */
  @FXML
  public void updateInformationClicked(ActionEvent event) {
    String error = "";
    String name = nameTextField.getText();
    String email = emailTextField.getText();
    String emergencyContact = emergencyContactTextField.getText();
    String password = passwordTextField.getText();
    int nrWeeks = 0;
    int weekAvailableFrom = 0;
    int weekAvailableTo = 0;
    try {
      nrWeeks = numberOfWeeksWantedTextField.getValue();
    } catch (Exception e) {
      error += "Please enter a valid number\n";
    }
    try {
      weekAvailableFrom = weekAvailableFromTextField.getValue();
    } catch (Exception e) {
      if (!error.contains("Please enter a valid number\n")) {
        error += "Please enter a valid number\n";
      }
    }
    try {
      weekAvailableTo = weekAvailableToTextField.getValue();
    } catch (Exception e) {
      if (!error.contains("Please enter a valid number\n")) {
        error += "Please enter a valid number\n";
      }
    }
    if (!error.isEmpty()) {
      ViewUtils.showError(error);
    } else {
      if (ViewUtils.successful(BikeTourPlusFeatureSet3Controller.updateParticipant(email, password,
          name, emergencyContact, nrWeeks, weekAvailableFrom, weekAvailableTo,
          lodgeCheckBox.isSelected()))) {
        ViewUtils.showSuccess("Successfully updated information");
        clearFields();
      }
    }

  }

  /**
   * An event listener for when the participant adds gear
   * 
   * @param event - The action event associated with the button press
   * @author Ari Smith
   */
  public void addButtonClicked(ActionEvent event) {
    String email = participantComboBox.getValue();
    if (email == null) {
      ViewUtils.showError("Select a participant");
    } else {
      String bookableItemString = bookableItemBox.getValue();
      if (bookableItemString == null) {
        ViewUtils.showError("Select a bookable item");
      } else {
        String[] stringList = bookableItemString.split(" - ");
        String bookableItem = stringList[0].trim();

        ViewUtils.callController(
            BikeTourPlusFeatureSet3Controller.addBookableItemToParticipant(email, bookableItem));

        bookableItemBox.getSelectionModel().select(bookableItemString); // re-select gear
      }
    }
  }

  /**
   * An event listener for when the participant removes gear
   * 
   * @param event - The action event associated with the button press
   * @author Ari Smith
   */
  public void removeButtonClicked(ActionEvent event) {
    String email = participantComboBox.getValue();
    if (email == null) {
      ViewUtils.showError("Select a participant");
    } else {
      String bookableItemString = bookableItemBox.getValue();
      if (bookableItemString == null) {
        ViewUtils.showError("Select a bookable item");
      } else {
        String[] stringList = bookableItemString.split(" - ");
        String bookableItem = stringList[0].trim();

        ViewUtils.callController(BikeTourPlusFeatureSet3Controller
            .removeBookableItemFromParticipant(email, bookableItem));

        bookableItemBox.getSelectionModel().select(bookableItemString); // re-select gear
      }
    }
  }

  /**
   * An event listener for when the delete participant button is pressed
   * 
   * @param event - The action event associated with the button press
   * @author Ari Smith
   */
  public void deleteButtonClicked(ActionEvent event) {

    String error = "";
    String email = participantComboBox.getValue();
    try {
      error += BikeTourPlusFeatureSet2Controller.deleteParticipant(email);
    } catch (NullPointerException e) {
      return; // useless
    }

    if (!error.isEmpty()) {
      ViewUtils.showError("Cannot delete participant");
    } else {
      ViewUtils.showSuccess("Successfully deleted participant");
      BikeTourPlusFxmlView.getInstance().refresh();
    }

    clearFields(); // reset GUI

  }

  /**
   * An event listener for when the clear button is pressed
   * 
   * @param event - The action event associated with the button press
   * @author Ari Smith
   */
  public void clearParticipantButtonClicked(ActionEvent event) {
    clearFields();
  }

  /**
   * An event listener for when the view cost button is pressed
   * 
   * @param event - The action event associated with the button press
   * @author Ari Smith
   */
  public void viewCostButtonClicked(ActionEvent event) {
    String participant = participantComboBox.getValue();
    if (participant != null) {
      TOParticipantCost participantCost =
          BikeTourPlusFeatureSet1Controller.getParticipantCostByEmail(participant);
      if (participantCost != null) {
        displayedCost.setText("Cost: $" + participantCost.getTotalCostForBikingTour() + " ($"
            + participantCost.getTotalCostForBookableItems() + " for items)");
      } else {
        ViewUtils.showError("Participant cost not found");
      }
    } else {
      ViewUtils.showError("Select a participant");
    }
  }

  /**
   * An event listener for when the pay button is pressed
   * 
   * @param event - The action event associated with the button press
   * @author Ari Smith
   */
  public void payButtonClicked(ActionEvent event) {

    if (ViewUtils.successful(BikeTourPlusCommonAPI
        .payForParticipantTrip(participantComboBox.getValue(), codeTextField.getText()))) {
      ViewUtils.showSuccess("Payment processed successfully");
      codeTextField.setText("");
      displayedCost.setText("");
    }
  }

  /**
   * An event listener for when the participant presses the cancel button
   * 
   * @param event - The action event associated with the button press
   * @author Ari Smith
   */
  public void cancelButtonClicked(ActionEvent event) {
    if (ViewUtils
        .successful(BikeTourPlusCommonAPI.cancelParticipantTrip(participantComboBox.getValue()))) {
      ViewUtils.showSuccess("Trip successfully canceled");
    }
  }

  /**
   * An event listener for when the email guide button is pressed
   * 
   * @param event - The action event associated with the button press
   * @author Ari Smith and Andrew Kan
   */
  public void emailGuideClicked(ActionEvent event) {
    String selectedParticipantEmail = participantComboBox.getValue();
    if (selectedParticipantEmail != null) {
      TOGuide g = BikeTourPlusFeatureSet1Controller.getGuideByParticipant(selectedParticipantEmail);
      if (g != null) {
        try {
          String recipient = g.getGuideEmail();
          String uriStr = String.format("mailto:%s?subject=%s", recipient, "BikeTourPlus");
          Desktop.getDesktop().browse(new URI(uriStr));
        } catch (Exception e) {
          ViewUtils.showError(e.getMessage());
        }
      } else {
        ViewUtils.showError("Participant's guide not found");
      }
    } else {
      ViewUtils.showError("Select a participant");
    }
  }

  /**
   * A method that greys out the email guide button if a participant has not yet been assigned
   * 
   * @param participantEmail - the email of the participant in order to check if they have been
   *        assigned
   * @author Ari Smith and Andrew Kan
   */
  public void setEmailGuideVisibility(String participantEmail) {
    if (participantEmail != null) {
      TOGuide g = BikeTourPlusFeatureSet1Controller.getGuideByParticipant(participantEmail);
      emailGuideButton.setDisable(g == null);
    } else {
      emailGuideButton.setDisable(true);
    }
  }

  /**
   * An event listener for when a new participant is chosen from the combo box
   * 
   * @param event - The action event associated with the selection from the combo box
   * @author Ari Smith
   */
  @FXML
  public void participantComboBoxAction(ActionEvent event) {
    TOParticipant p =
        BikeTourPlusFeatureSet1Controller.getParticipantFromEmail(participantComboBox.getValue());
    if (p != null) {
      nameTextField.setText(p.getParticipantName());
      emailTextField.setText(p.getParticipantEmail());
      passwordTextField.setText(p.getParticipantEmergencyContact());
      emergencyContactTextField.setText(p.getParticipantEmergencyContact());
      numberOfWeeksWantedTextField.getValueFactory().setValue(p.getNrWeeks());
      weekAvailableFromTextField.getValueFactory().setValue(p.getWeekAvailableFrom());
      weekAvailableToTextField.getValueFactory().setValue(p.getWeekAvailableUntil());
      passwordTextField.setText("" + p.getParticipantPassword());
      lodgeCheckBox.setSelected(p.getLodgeRequired());
      gearTable.setItems(getBookedItems());
      setEmailGuideVisibility(participantComboBox.getValue());
      displayedCost.setText("");
    }
  }

  /**
   * This method gets an ObservableList of booked items that have been booked by the currently
   * selected participant in combo box
   * 
   * @return An ObservableList containing the booked items of a participant
   * @author Ari Smith
   */
  public ObservableList<TOBookedItem> getBookedItems() {
    String selectedParticipantEmail = participantComboBox.getValue();
    if (selectedParticipantEmail == null) {
      return FXCollections.emptyObservableList();
    } else {
      ObservableList<TOBookedItem> itemList =
          FXCollections.observableArrayList(BikeTourPlusFeatureSet1Controller
              .getBookedItemsByParticipantEmail(selectedParticipantEmail));
      return itemList;
    }


  }

  /**
   * This method clears all the fields on the participant page
   *
   * @author Andrew Kan
   */
  public void clearFields() {
    nameTextField.setText("");
    emailTextField.setText("");
    passwordTextField.setText("");
    emergencyContactTextField.setText("");
    numberOfWeeksWantedTextField.getValueFactory().setValue(0);
    weekAvailableFromTextField.getValueFactory().setValue(0);
    weekAvailableToTextField.getValueFactory().setValue(0);
    lodgeCheckBox.setSelected(false);
    participantComboBox.setValue(null);
    displayedCost.setText("");
    codeTextField.setText("");
    bookableItemBox.setValue(null);
    gearTable.setItems(getBookedItems());
  }
}
