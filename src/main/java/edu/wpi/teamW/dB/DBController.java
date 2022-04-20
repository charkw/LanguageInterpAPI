package edu.wpi.teamW.dB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBController {

  private String dbName = "LanguageInterpAPIDB";
  private Statement statement;
  private Connection connection;

  private static DBController dbController = new DBController();

  public static DBController getDBController() {
    return dbController;
  }

  private DBController() {
    try {
      startConnection();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void startConnection() throws Exception {
    String connectionStringEmbedded = String.format("jdbc:derby:%s;create=true", this.dbName);

    this.connectEmbedded(connectionStringEmbedded);

    // Create Daos (tables are dropped automatically when daos are created)
    // *ORDER MATTERS BECAUSE OF FOREIGN KEYS*
    LanguageInterpreterDao languageInterpreterDao = new LanguageInterpreterDaoImpl(statement);
    EmployeeDao employeeDao = new EmployeeDaoImpl(statement);
    LanguageDao languageDao = new LanguageDaoImpl(statement);

    // Assign Daos to Managers
    EmployeeManager.getEmployeeManager().setEmployeeDao(employeeDao);
    LanguageManager.getLanguageManager().setLanguageDao(languageDao);
    LanguageInterpreterManager.getLanguageInterpreterManager()
        .setLanguageInterpreterDao(languageInterpreterDao);

    // *ORDER MATTERS BECAUSE OF FOREIGN KEYS*
    ((EmployeeDaoImpl) employeeDao).createTable();
    ((LanguageDaoImpl) languageDao).createTable();
    ((LanguageInterpreterDaoImpl) languageInterpreterDao).createTable();
    CSVController csvController = new CSVController();
    csvController.populateTables();
  }

  public Statement getStatement() {
    return this.statement;
  }

  /**
   * Establishes connection with embedded Apache Derby Database
   *
   * @return Returns a new connection to an embedded Apache Derby Database
   * @throws SQLException if unable to connect to database
   * @throws ClassNotFoundException if Apache Derby installation not found
   */
  private void connectEmbedded(String connectionString)
      throws SQLException, ClassNotFoundException {
    System.out.println("-------Embedded Apache Derby Connection Testing --------");
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found. Add the classpath to your module.");
      System.out.println("For IntelliJ do the following:");
      System.out.println("File | Project Structure, Modules, Dependency tab");
      System.out.println("Add by clicking on the green plus icon on the right of the window");
      System.out.println(
          "Select JARs or directories. Go to the folder where the database JAR is located");
      System.out.println("Click OK, now you can compile your program and run it.");
      e.printStackTrace();
      throw (e);
    }
    System.out.println("Apache Derby driver registered!");

    try {
      connection = DriverManager.getConnection(connectionString);
      statement = connection.createStatement();

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      throw (e);
    }

    System.out.println("Apache Derby connection established!");
  }
}
