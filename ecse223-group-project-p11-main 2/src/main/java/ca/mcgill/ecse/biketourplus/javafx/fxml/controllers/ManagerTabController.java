package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;

import static ca.mcgill.ecse.biketourplus.javafx.fxml.controllers.ViewUtils.callController;
import static ca.mcgill.ecse.biketourplus.javafx.fxml.controllers.ViewUtils.successful;

import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusCommonAPI;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet1Controller;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class ManagerTabController {
  @FXML
  private Button finishParticipantTripButton;
  @FXML
  private Button startTripClicked;
  @FXML
  private ChoiceBox<Integer> specificWeekChoiceBox;
  @FXML
  private Button initializationBTButton;
  @FXML
  private ChoiceBox<String> participantEmailChoiceBox;

  @FXML
  /**
   * Initializes the drop boxes and register refreshable nodes of the application
   *
   * @author Simon Younaki
   */
  public void initialize() {
    // the choice boxes listen to the refresh event
    initializeTheDropBox();
    // let the application be aware of the refreshable node
    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(specificWeekChoiceBox,
        participantEmailChoiceBox);
  }

  @FXML
  /**
   * Initializes the bike tour creation. it shows a success message in case of a success. else, it
   * shows an error
   *
   * @author Simon Younaki
   * @param event ActionEvent. it is a Listener on Button[#initilizationBTbutton].onAction
   */
  public void initializationBTCClicked(ActionEvent event) {
    try {
      callController(BikeTourPlusCommonAPI.initiateTourCreation());
      if (BikeTourPlusCommonAPI.initiateTourCreation().equals("Bike tours already created"))
        initializationBTButton.setDisable(true);
      ViewUtils.showSuccess("Initiated tour creation successfully");
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());

    }

  }

  // Preparing the choiceBox to includes all possible starting weeks
  @FXML
  /**
   * A void method that sets the items inside the choiceBoxes in the ManagerTab Sets the items in
   * the specificWeekChoiceBox to all starting weeks of the bikeTours Sets the items in the
   * participantEmailChoiceBox to all registered participant's emails
   *
   * @author Simon Younaki
   */
  public void initializeTheDropBox() {
    // the choice boxes listen to the refresh event
    specificWeekChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      specificWeekChoiceBox.setItems(ViewUtils.getAllStartingWeeks());
      // reset the choice
      specificWeekChoiceBox.setValue(null);
    });
    participantEmailChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      participantEmailChoiceBox.setItems(BikeTourPlusFeatureSet1Controller.getParticipantEmails());
      // reset the choice
      participantEmailChoiceBox.setValue(null);
    });

    // let the application be aware of the refreshable node
    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(specificWeekChoiceBox,
        participantEmailChoiceBox);
  }


  @FXML
  /**
   * The action event on that button starts the trip number indicated in the specificWeekChoiceBox
   *
   * @author Simon Younaki
   * @param event ActionEvent; A listener to on Button[#startTripClicked].onAction
   *
   */
  public void startTripClicked(ActionEvent event) {
    Integer specificWeek = specificWeekChoiceBox.getValue();
    if (specificWeek == null) {
      ViewUtils.showError("Please select a valid week number");
    } else {
      callController(BikeTourPlusCommonAPI.startTripsForWeek(specificWeek));
      ViewUtils.showSuccess("Week " + specificWeek + " trips started successfully");
    }
  }


  @FXML
  /**
   * The action event on that button finish the participant's trip according to the email string
   * indicated in participantEmailChoiceBox.
   *
   * @author Simon Younaki
   * @param event ActionEvent; a listener on Button[#finishParticipantTripButton].onAction
   */
  public void finishParticipantTripClicked(ActionEvent event) {
    String participantEmail = participantEmailChoiceBox.getValue();
    if (participantEmail == null || participantEmail.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid participant email address");
    } else {
      // reset the driver text field if success
      if (successful(BikeTourPlusCommonAPI.finishParticipantTrip(participantEmail))) {
        ViewUtils.showSuccess("Successfully finished trip for " + participantEmail);
      }
    }
  }



}
