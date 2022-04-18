package edu.wpi.teamW.dB;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDao {

  ArrayList<Employee> getAllEmployees() throws SQLException;

  Employee getEmployee(Integer empID) throws SQLException;

  void addEmployee(Employee employee) throws SQLException;

  void deleteEmployee(Integer empID) throws SQLException;

  void changeEmployee(Employee employee) throws SQLException;

  void exportEmpCSV(String fileName);
}
