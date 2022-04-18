package edu.wpi.teamW.dB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmployeeDaoImpl implements EmployeeDao {

  Statement statement;

  EmployeeDaoImpl(Statement statement) throws SQLException {
    this.statement = statement;
    dropTable();
  }

  void dropTable() {
    try {
      statement.execute("DROP TABLE EMPLOYEES");
      System.out.println("Dropped Employee Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Employee Table");
    }
  }

  String CSVHeaderString = "employeeID,firstName,lastName";

  void createTable() throws SQLException {
    try {
      statement.execute(
          "CREATE TABLE EMPLOYEES(\n"
              + "employeeID INT, \n "
              + "firstName varchar(25), \n "
              + "lastName varchar(25), \n "
              + "constraint Employees_PK primary key (employeeID))");
    } catch (SQLException e) {
      System.out.println("Employee Table failed to be created!");
      throw (e);
    }
    System.out.println("Employee Table created");
  }

  @Override
  public ArrayList<Employee> getAllEmployees() throws SQLException {
    ArrayList<Employee> employeeList = new ArrayList<Employee>();

    try {
      ResultSet employees = statement.executeQuery("SELECT * FROM EMPLOYEES");

      while (employees.next()) {
        ArrayList<String> employeeData = new ArrayList<String>();

        for (int i = 0; i < employees.getMetaData().getColumnCount(); i++) {
          employeeData.add(employees.getString(i + 1));
        }

        employeeList.add(new Employee(employeeData));
      }

    } catch (SQLException e) {
      System.out.println("Query from locations table failed");
      throw (e);
    }
    return employeeList;
  }

  @Override
  public void addEmployee(Employee emp) throws SQLException {

    statement.executeUpdate(
        String.format("INSERT INTO EMPLOYEES VALUES (%s)", emp.toValuesString()));
  }

  @Override
  public void deleteEmployee(Integer empID) throws SQLException {
    statement.executeUpdate(String.format("DELETE FROM EMPLOYEES WHERE EMPLOYEEID=%d", empID));
  }

  public void changeEmployee(Employee emp) throws SQLException {

    statement.executeUpdate(
        String.format(
            "UPDATE EMPLOYEES SET FIRSTNAME = '%s', LASTNAME = '%s', EMPLOYEETYPE = '%s', EMAIL = '%s', PHONENUMBER = '%s', ADDRESS = '%s', USERNAME = '%s', PASSWORD = '%s' WHERE EMPLOYEEID = %d",
            emp.getFirstName(), emp.getLastName(), emp.getEmployeeID()));
  }

  @Override
  public void exportEmpCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

      // print all locations
      for (Employee e : getAllEmployees()) {
        pw.println();
        pw.print(e.toCSVString());
      }

    } catch (FileNotFoundException | SQLException s) {
      System.out.println(String.format("Error Exporting to File %s", fileName));
      s.printStackTrace();
    }
  }

  @Override
  public Employee getEmployee(Integer empID) throws SQLException {
    ResultSet rs =
        statement.executeQuery(
            String.format("SELECT * FROM EMPLOYEES WHERE EMPLOYEEID = %d", empID));
    rs.next();
    ArrayList<String> employeeFields = new ArrayList<String>();
    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
      employeeFields.add(rs.getString(i + 1));
    }
    return new Employee(employeeFields);
  }
}
