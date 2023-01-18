package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;

import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet4Controller;
import ca.mcgill.ecse.biketourplus.controller.TOGuide;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class GuideController {
  @FXML
  private TextField guideEmailTextField;
  @FXML
  private TextField guideNameTextField;
  @FXML
  private TextField guideEmergencyContactTextField;
  @FXML
  private PasswordField guidePasswordField;
  @FXML
  private ComboBox<String> guideComboBox;
  @FXML
  private Button guideClearButton;

    /**
   * Initialize the controller and refreshable combo box
   * 
   * @author Martin Nguyen
   */
  @FXML
  public void initialize() {
    // the choice boxes listen to the refresh event
    guideComboBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, (e) -> {
      guideComboBox.setItems(BikeTourPlusFeatureSet4Controller.getAllGuideEmail());
      // reset the choice
      guideComboBox.setValue(null);
    });
    // let the application be aware of the refreshable node
    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(guideComboBox);
  }

  /**
   * Register button handler, register a guide with the information provided
   * 
   * @author Martin Nguyen
   * @param event ActionEvent for button clicked
   */
  @FXML
  public void guideRegisterButtonClicked(ActionEvent event) {
    String error = "";

    String email = guideEmailTextField.getText();
    String password = guidePasswordField.getText();
    String name = guideNameTextField.getText();
    String emergencyContact = guideEmergencyContactTextField.getText();


    // ERROR HANDLING
    if (!error.isEmpty()) {
      ViewUtils.showError(error);
    } else {
      boolean result = ViewUtils.successful(
          BikeTourPlusFeatureSet4Controller.registerGuide(email, password, name, emergencyContact));
      if (result)
      {
        clearFields();
        ViewUtils.showError("Guide registered successfully. Email: " + email);
        BikeTourPlusFxmlView.getInstance().refresh();
      }
    }
  }

  /**
   * Update button handler, updates the guide with the information provided in the fields
   * 
   * @author Martin Nguyen
   * @param event ActionEvent for button clicked
   */
  @FXML
  public void guideUpdateButtonClicked(ActionEvent event) {
    String error = "";
    String email = guideEmailTextField.getText();
    String password, name, emergencyContact;

    try {
      password = guidePasswordField.getText();

      name = guideNameTextField.getText();

      emergencyContact = guideEmergencyContactTextField.getText();
      error += BikeTourPlusFeatureSet4Controller.updateGuide(email, password, name, emergencyContact);

    } catch (NullPointerException e) {
      error += "Guide with email " + email + " does not exist";
    }

    // ERROR HANDLING
    if (!error.isEmpty()) {
      ViewUtils.showError(error);
    } else {  //SUCCESS
      // reset GUI
      ViewUtils.showSuccess("Successfully updated guide with email " + email);
      clearFields();
      BikeTourPlusFxmlView.getInstance().refresh();
    }
  }

  /**
   * Delete button handler, delete the guide with the specified email
   * 
   * @author Martin Nguyen
   * @param event ActionEvent for button clicked
   */
  @FXML
  public void guideDeleteButtonClicked(ActionEvent event) {
    String error = "";
    String email = guideEmailTextField.getText();
    try {
      error += BikeTourPlusFeatureSet4Controller.deleteGuide(email);
    } catch (NullPointerException e) {
      return;
    }

    // ERROR HANDLING
    if (!error.isEmpty()) {
      ViewUtils.showError(error);
    } else {  // SUCCESS
      ViewUtils.showError("Successfully deleted Guide with email " + email);
      // reset GUI
      clearFields();
      BikeTourPlusFxmlView.getInstance().refresh();
    }
  }

  /**
   * Combo box action handler, populate the GUI fields with the information of the selected guide
   * from combo box
   * 
   * @author Martin Nguyen
   * @param event ActionEvent for button clicked
   */
  @FXML
  public void guideComboBoxSelected(ActionEvent event) { 
    guideEmailTextField.setText("");

    String selectedGuideEmail;
    TOGuide selectedGuide;

    selectedGuideEmail = guideComboBox.getValue();
    selectedGuide = BikeTourPlusFeatureSet4Controller.getGuideFromEmail(selectedGuideEmail);
    if(selectedGuide == null) {
      return; //sentinel for null pointer exception
    }


    String email = selectedGuide.getGuideEmail();
    String password = selectedGuide.getGuidePassword();
    String name = selectedGuide.getGuideName();
    String emergencyContact = selectedGuide.getGuideEmergencyContact();

    guideEmailTextField.setText(email);
    guidePasswordField.setText(password);
    guideNameTextField.setText(name);
    guideEmergencyContactTextField.setText(emergencyContact);

  }

  /**
   * Clear button handler, clear the fields
   * 
   * @author Martin Nguyen
   * @param event ActionEvent for button clicked
   */
  @FXML
  public void clearGuideButtonClicked(ActionEvent event) {
    clearFields();
  }

  /**
   * Set all the GUI fields to blank
   * 
   * @author Martin Nguyen
   */
  public void clearFields() {
    guideComboBox.setValue("");
    guideEmailTextField.setText("");
    guidePasswordField.setText("");
    guideNameTextField.setText("");
    guideEmergencyContactTextField.setText("");
  }

}
