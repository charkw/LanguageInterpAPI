package edu.wpi.teamW.frontEnd;

import edu.wpi.teamW.API;
import edu.wpi.teamW.dB.*;
import edu.wpi.teamW.dB.LanguageInterpreterManager;
import edu.wpi.teamW.dB.enums.Languages;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class LanguageInterpreterServiceRequestController implements Initializable {

  @FXML ComboBox employeeSelection;
  @FXML ComboBox languageSelection;
  @FXML Label locationSelection;
  @FXML Button exitButton;
  @FXML Label successLabel;
  Alert confirm = new ConfirmAlert();
  Alert emptyFields = new EmptyAlert();

  private LanguageInterpreterManager languageInterpreterManager =
      LanguageInterpreterManager.getLanguageInterpreterManager();

  private String destinationLocation = "Default Location";

  public void setLocation(String location) {
    this.destinationLocation = location;
    locationSelection.setText(this.destinationLocation);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    fadeOut.setNode(successLabel);
    fadeOut.setFromValue(1.0);
    fadeOut.setToValue(0.0);
    fadeOut.setCycleCount(1);
    fadeOut.setAutoReverse(false);
    employeeSelection.setDisable(true);
    // TODO UPDATE LOCATION
    languageSelection.setItems(FXCollections.observableArrayList(getLanguageTypeList()));
    languageSelection.setOnAction(
        (event) -> {
          if (!languageSelection.getSelectionModel().isEmpty()) {
            String language = languageSelection.getSelectionModel().getSelectedItem().toString();
            employeeSelection.setDisable(false);
            try {
              employeeSelection.setItems(
                  FXCollections.observableArrayList(getEmployeeNames(language)));
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
        });
  }

  private FadeTransition fadeOut = new FadeTransition(Duration.millis(5000));

  public void submitButton(ActionEvent actionEvent) throws SQLException {
    if (!emptyFields()) {
      pushLanguageRequestToDB();
      clearFields();
      successLabel.setVisible(true);
      fadeOut.playFromStart();
    } else {
      emptyFields.show();
    }
  }

  public void exitButton(ActionEvent actionEvent) {
    API.closeApp();
  }

  public void onUnload() {
    clearFields();
  }

  private boolean emptyFields() {
    return employeeSelection.getSelectionModel().isEmpty()
        || languageSelection.getSelectionModel().isEmpty();
  }

  private void pushLanguageRequestToDB() throws SQLException {
    ArrayList<String> srFields = new ArrayList<String>();
    String language = languageSelection.getSelectionModel().getSelectedItem().toString();
    Integer empID =
        getEmployeeID(employeeSelection.getSelectionModel().getSelectedItem().toString());
    Employee employee = EmployeeManager.getEmployeeManager().getEmployee(empID);
    LanguageRequest languageRequest = new LanguageRequest(language, destinationLocation, employee);
    API.reqList.add(languageRequest);
  }

  private void clearFields() {
    if (!employeeSelection.getSelectionModel().isEmpty()) {
      employeeSelection.getSelectionModel().clearSelection();
    }
    if (!languageSelection.getSelectionModel().isEmpty()) {
      languageSelection.getSelectionModel().clearSelection();
    }

    employeeSelection.setDisable(true);
  }

  private Integer getEmployeeID(String name) throws SQLException {
    name = name.trim();
    Integer employeeID = null;
    String employeeLastName;
    String employeeFirstName;
    Integer commaIndex = name.indexOf(',');
    employeeLastName = name.substring(0, commaIndex);
    employeeFirstName = name.substring(commaIndex + 2);

    for (Employee e : EmployeeManager.getEmployeeManager().getAllEmployees()) {
      if (e.getLastName().equals(employeeLastName) && e.getFirstName().equals(employeeFirstName)) {
        employeeID = e.getEmployeeID();
      }
    }

    return employeeID;
  }

  private ArrayList<String> getEmployeeNames(String language) throws SQLException {
    ArrayList<Integer> ids = languageInterpreterManager.getEligibleEmployees(language);
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();

    for (Integer id : ids) {
      try {
        employees.add(EmployeeManager.getEmployeeManager().getEmployee(id));
      } catch (SQLException e) {
        System.out.println("Failed to unearth employees from database");
        e.printStackTrace();
      }
    }

    for (Employee e : employees) {
      if (e.getEmployeeID() != -1) {
        String empName = String.format("%s, %s", e.getLastName(), e.getFirstName());
        names.add(empName);
      }
    }
    return names;
  }

  private ArrayList<String> getLanguageTypeList() {
    ArrayList<String> languages = new ArrayList<>();
    for (Languages l : Languages.values()) {
      languages.add(l.getString());
    }
    return languages;
  }
}
