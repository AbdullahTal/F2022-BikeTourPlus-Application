package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;

import java.sql.Date;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet1Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet2Controller;
import ca.mcgill.ecse.biketourplus.controller.TOBikeTourPlus;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class SystemController {

  @FXML
  private PasswordField passwordTextField;
  @FXML
  private DatePicker startDatePicker;
  @FXML
  private Spinner<Integer> numWeeksSpinner;
  @FXML
  private Spinner<Integer> guidePriceSpinner;
  @FXML
  private Button updatePasswordButton;
  @FXML
  private Button updateBTPButton;
  @FXML
  private Button getBTPInfoButton;
  @FXML
  private Button clearBTPInfoButton;
  @FXML
  private CheckBox darkModeCheck;

  /**
   * This method runs on initialization and sets up elements
   *
   * @author Andrew Kan
   */
  @FXML
  public void initialize() {
    if (BikeTourPlusApplication.DARK_MODE) {
      darkModeCheck.setSelected(true);
    }

    numWeeksSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 53));
    guidePriceSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));

  }

  /**
   * An event listener for when the update password button is clicked
   * 
   * @param event - The action event associated with the button press
   * @author Ari Smith
   */
  public void updatePasswordClicked(ActionEvent event) {
    String password = passwordTextField.getText();
    if (ViewUtils.successful(BikeTourPlusFeatureSet1Controller.updateManager(password))) {
      passwordTextField.setText("");
      ViewUtils.showSuccess("Successfully updated password");
    }
  }

  /**
   * An event listener for when the update BikeTourPlus button is clicked
   * 
   * @param event - The action event associated with the button press
   * @author Ari Smith
   */
  public void updateBTPClicked(ActionEvent event) {
    Date startDate = null;
    if (startDatePicker.getValue() != null) {
      startDate = Date.valueOf(startDatePicker.getValue());
    }
    String error = "";
    if (startDate == null) {
      error += "Please enter a date\n";
    }
    int nrWeeks = 0;

    try {
      nrWeeks = numWeeksSpinner.getValue();
    } catch (Exception e) {
      error += "Please enter a valid number\n";
    }
    int priceOfGuide = 0;
    try {
      priceOfGuide = guidePriceSpinner.getValue();
    } catch (Exception e) {
      if (!error.contains("Please enter a valid number")) {
        error += "Please enter a valid number\n";
      }
    }
    if (!error.isEmpty()) {
      ViewUtils.showError(error);
    } else {
      if (ViewUtils.successful(
          BikeTourPlusFeatureSet2Controller.updateBikeTourPlus(startDate, nrWeeks, priceOfGuide))) {
        ViewUtils.showSuccess("Successfully updated BikeTourPlus");
        startDatePicker.setValue(null);
        numWeeksSpinner.getValueFactory().setValue(0);
        guidePriceSpinner.getValueFactory().setValue(0);
      }

    }
  }

  /**
   * An event listener for when the get info button is clicked. Loads info of btp season
   *
   * @author Andrew Kan
   * @param event The action event associated with the button press
   */
  public void getBTPClicked(ActionEvent event) {
    TOBikeTourPlus btp = BikeTourPlusFeatureSet2Controller.getBikeTourPlus();
    startDatePicker.setValue(btp.getStartDate().toLocalDate());
    numWeeksSpinner.getValueFactory().setValue(btp.getNrWeeks());
    guidePriceSpinner.getValueFactory().setValue(btp.getPriceOfGuidePerWeek());
  }

  /**
   * An event listener for when the clear info button is clicked Clears btp info fields
   *
   * @author Andrew Kan
   * @param event The action event associated with the button press
   */
  public void clearBTPInfoClicked(ActionEvent event) {
    startDatePicker.setValue(null);
    numWeeksSpinner.getValueFactory().setValue(0);
    guidePriceSpinner.getValueFactory().setValue(0);
  }

  /**
   * An event listener for when the dark mode checkbox is clicked Toggles dark mode theme of
   * application
   *
   * @author Andrew Kan
   * @param event The action event associated with the button press
   */
  public void darkModeClicked(ActionEvent event) {
    BikeTourPlusApplication.DARK_MODE = darkModeCheck.isSelected();
    BikeTourPlusFxmlView.getInstance().setDarkMode();
    BikeTourPlusFxmlView.getInstance().refresh();
  }

}
