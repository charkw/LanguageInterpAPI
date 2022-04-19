package edu.wpi.teamW.dB;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeManager {

  private EmployeeDao ed;

  private final Integer deletedEmployee = -1;

  private static EmployeeManager employeeManager = new EmployeeManager();

  public static EmployeeManager getEmployeeManager() {
    return employeeManager;
  }

  private EmployeeManager() {}

  public Integer getDeletedEmployee() {
    return this.deletedEmployee;
  }

  public void setEmployeeDao(EmployeeDao ed) {
    this.ed = ed;
  }

  public void addEmployee(Employee employee) throws SQLException {
    ed.addEmployee(employee);
  }

  public void deleteEmployee(Integer employeeID) throws Exception {
    ed.deleteEmployee(employeeID);
  }

  public void changeEmployee(Employee employee) throws SQLException {
    ed.changeEmployee(employee);
  }

  public ArrayList<Employee> getAllEmployees() throws SQLException {
    return ed.getAllEmployees();
  }

  public Employee getEmployee(Integer empID) throws SQLException {
    return ed.getEmployee(empID);
  }

  public void exportEmpCSV(String filename) {
    ed.exportEmpCSV(filename);
  }
}
