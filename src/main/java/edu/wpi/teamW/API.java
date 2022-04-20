package edu.wpi.teamW;

import edu.wpi.teamW.dB.*;
import edu.wpi.teamW.dB.enums.Languages;
import edu.wpi.teamW.frontEnd.LanguageInterpreterServiceRequestController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class API {

  private static ArrayList<LanguageRequest> reqList = new ArrayList<>();

  static {
    DBController.getDBController();
  }

  private static Stage stage;

  public static void addRequest(LanguageRequest lr) {
    reqList.add(lr);
  }

  public static ArrayList<LanguageRequest> getAllRequests() {
    return reqList;
  }

  public static ArrayList<Employee> getAllEmployees() throws SQLException {
    return EmployeeManager.getEmployeeManager().getAllEmployees();
  }

  public static void deleteAllEmployees() throws Exception {
    try {
      ArrayList<Employee> e = EmployeeManager.getEmployeeManager().getAllEmployees();

      for (Employee employee : e) {
        LanguageInterpreterManager.getLanguageInterpreterManager()
            .deleteLanguageInterpsFromEmployeeID(employee.getEmployeeID());
        EmployeeManager.getEmployeeManager().deleteEmployee(employee.getEmployeeID());
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void changeEmployeeName(Integer employeeID, String newFirstName, String newLastName)
      throws SQLException {
    Employee e = new Employee(employeeID, newFirstName, newLastName);
    EmployeeManager.getEmployeeManager().changeEmployee(e);
  }

  public static void deleteEmployee(Employee e) throws Exception {
    LanguageInterpreterManager.getLanguageInterpreterManager()
        .deleteLanguageInterpsFromEmployeeID(e.getEmployeeID());
    EmployeeManager.getEmployeeManager().deleteEmployee(e.getEmployeeID());
  }

  public static void setEmployeeLanguage(Employee e, Languages language) throws SQLException {
    LanguageInterpreter lI = new LanguageInterpreter(e.getEmployeeID(), language.getString());
    LanguageInterpreterManager.getLanguageInterpreterManager().addLanguageInterpreter(lI);
  }

  public static void addEmployeeWithoutLanguage(Employee e) throws SQLException {
    EmployeeManager.getEmployeeManager().addEmployee(e);
  }

  public static void addEmployeeWithLanguage(Employee e, Languages language) throws SQLException {
    EmployeeManager.getEmployeeManager().addEmployee(e);
    LanguageInterpreter lI = new LanguageInterpreter(e.getEmployeeID(), language.getString());
    LanguageInterpreterManager.getLanguageInterpreterManager().addLanguageInterpreter(lI);
  }

  public static void run(
      int xCoord,
      int yCoord,
      int windowWidth,
      int windowLength,
      String cssPath,
      String destLocationID,
      String originLocationID)
      throws ServiceException {

    FXMLLoader root =
        new FXMLLoader(
            Main.class.getResource(
                "/edu/wpi/teamW/views/" + "LanguageInterpreterServiceRequestPage.fxml"));

    AnchorPane mainScene = null;
    try {
      mainScene = (AnchorPane) root.load();
    } catch (IOException e) {
      e.printStackTrace();
      throw (new ServiceException());
    }

    LanguageInterpreterServiceRequestController langController =
        (LanguageInterpreterServiceRequestController) root.getController();
    langController.setLocation(destLocationID);

    Scene scene = new Scene(mainScene, (double) windowWidth, (double) windowLength);
    scene
        .getStylesheets()
        .add(((URL) Objects.requireNonNull(Main.class.getResource(cssPath))).toExternalForm());
    scene.getStylesheets().add(App.class.getResource(cssPath).toExternalForm());

    stage = new Stage();
    stage.setScene(scene);
    stage.setX((double) xCoord);
    stage.setY((double) yCoord);
    if (windowWidth == 0 && windowLength == 0) {
      stage.setFullScreen(true);
    }
    stage.setWidth((double) windowWidth);
    stage.setHeight((double) windowLength);
    stage.show();
  }

  public static void closeApp() {
    stage.close();
    CSVController csvController = new CSVController();
    csvController.exportAllCSVs();
  }
}
