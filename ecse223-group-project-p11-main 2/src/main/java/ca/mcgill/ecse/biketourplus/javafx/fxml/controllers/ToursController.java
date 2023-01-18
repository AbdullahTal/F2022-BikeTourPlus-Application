package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;

import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet1Controller;
import ca.mcgill.ecse.biketourplus.controller.TOBikeTour;
import ca.mcgill.ecse.biketourplus.controller.TOParticipantCost;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * View controller for Tours UI Page
 * 
 * @author Andrew Kan
 *
 */
public class ToursController {

  @FXML
  private TableView<TOParticipantCost> tourTable;
  @FXML
  private ComboBox<Integer> tourComboBox;
  @FXML
  private Label guideNameLabel;
  @FXML
  private Label guideEmailLabel;
  @FXML
  private Label guideCostLabel;
  @FXML
  private Label startWeekLabel;
  @FXML
  private Label endWeekLabel;

  private Integer selectedTourId;

  /**
   * Initializes the refreshable table and dropdown when the application is initialized
   *
   * @author Andrew Kan
   */
  @FXML
  public void initialize() {

    // the tour combo box ID list is refreshable
    tourComboBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      tourComboBox.setItems(getTourIds());
    });

    // Refreshes tab info when selected tour changes
    tourComboBox.setOnAction((event) -> {
      selectedTourId = tourComboBox.getValue();
      TOBikeTour tour = getSpecificTour();
      guideNameLabel.setText(tour.getGuideName());
      guideEmailLabel.setText(tour.getGuideEmail());
      guideCostLabel.setText("$" + tour.getTotalCostForGuide());
      startWeekLabel.setText(String.valueOf(tour.getStartWeek()));
      endWeekLabel.setText(String.valueOf(tour.getEndWeek()));
      tourTable.setItems(getParticipantCosts());
    });

    // initialize the columns of tour participant table
    tourTable.getColumns().add(createTableColumn("Name", "participantName"));
    tourTable.getColumns().add(createTableColumn("Email", "participantEmail"));
    var statusColumn = createTableColumn("Status", "status");
    tourTable.getColumns().add(statusColumn);
    tourTable.getColumns().add(createTableColumn("Gear Cost", "totalCostForBookableItems"));
    tourTable.getColumns().add(createTableColumn("Total Cost", "totalCostForBikingTour"));
    tourTable.getColumns().add(createTableColumn("AuthCode", "authorizationCode"));
    tourTable.getColumns().add(createTableColumn("Refund %", "refundedPercentageAmount"));

    tourTable.setPlaceholder(new Label("No tour info found"));

    // set status column text color to depend on participant status
    statusColumn.setCellFactory(col -> new TableCell<>() {
      @Override
      public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        var row = getTableRow();
        setText(item);
        setTextFill(Color.BLACK);
        if (row.getItem() != null && !row.getItem().getStatus().isEmpty()) {
          String status = row.getItem().getStatus();
          if (status.equals("Started")) {
            setTextFill(Color.GREEN);
          } else if (status.equals("Banned")) {
            setTextFill(Color.DARKRED);
          } else if (status.equals("Cancelled")) {
            setTextFill(Color.RED);
          } else if (status.equals("Paid")) {
            setTextFill(Color.BLUE);
          } else if (status.equals("Finished")) {
            setTextFill(Color.PURPLE);
          }
        }
      }
    });

    // overview table is a refreshable element
    tourTable.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT,
        e -> tourTable.setItems(getParticipantCosts()));

    // register refreshable nodes
    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(tourTable);
    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(tourComboBox);

  }

  /**
   * Returns observable list of tour ids in system
   *
   * @author Andrew Kan
   * @return ObservableList of Integers representing tour ids
   */
  public ObservableList<Integer> getTourIds() {
    List<Integer> idList = new ArrayList<>();
    int numTours = BikeTourPlusFeatureSet1Controller.getNumTours();
    for (int i = 0; i < numTours; i++) {
      idList.add(i + 1);
    }

    if (numTours == 0) {
      return FXCollections.emptyObservableList();
    }
    return FXCollections.observableArrayList(idList);
  }

  /**
   * Gets TOBikeTour of the selected tour in dropdown
   *
   * @author Andrew Kan
   * @return TOBikeTour of selected tour
   */
  public TOBikeTour getSpecificTour() {
    if (selectedTourId == null) {
      return new TOBikeTour(0, 0, 0, null, null, 0, (TOParticipantCost) null);
    }
    return BikeTourPlusFeatureSet1Controller.getBikeTour(selectedTourId);
  }

  /**
   * Gets participant cost info in the dropdown-selected tour
   *
   * @author Andrew Kan
   * @return ObservableList of TOParticipantCost objects of selected tour
   */
  public ObservableList<TOParticipantCost> getParticipantCosts() {
    if (selectedTourId == null) {
      return FXCollections.emptyObservableList();
    }
    return FXCollections
        .observableArrayList(BikeTourPlusFeatureSet1Controller.getParticipantCosts(selectedTourId));
  }


  /**
   * The table column will automatically display the string value of the property for each instance
   * in the table. Adapted from ECSE223 BTMS tutorial example.
   *
   * @author Andrew Kan
   * @param header Column header name
   * @param propertyName Name of property in TOBikeTour
   * @return TableColumn with bike tour info
   */
  public static TableColumn<TOParticipantCost, String> createTableColumn(String header,
      String propertyName) {
    TableColumn<TOParticipantCost, String> column = new TableColumn<>(header);
    column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
    return column;
  }

}
