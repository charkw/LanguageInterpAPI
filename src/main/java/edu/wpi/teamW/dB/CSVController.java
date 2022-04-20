package edu.wpi.teamW.dB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CSVController {

  final String employeeFileName = "Employees.csv";
  final String languageFileName = "Languages.csv";
  final String languageInterpreterFilename = "LanguageInterpreter.csv";

  public CSVController() {}

  public void populateTables() throws Exception {
    insertIntoEmpTable(importCSV(employeeFileName));
    insertIntoLanguagesTable(importCSV(languageFileName));
    insertIntoLanguageInterpreterTable(importCSV(languageInterpreterFilename));
  }

  public ArrayList<String[]> importCSV(String fileName) throws FileNotFoundException {

    InputStream in = new FileInputStream("CSVs/" + fileName);
    if (in == null) {
      System.out.println("Failed to find file " + fileName);
      throw (new FileNotFoundException());
    }
    Scanner sc = new Scanner(in);
    System.out.println("Found File" + fileName);
    // Skip headers
    try {
      sc.next();
    } catch (NoSuchElementException e) {
      System.out.println(String.format("FILE %s IS EMPTY", fileName));
    }

    ArrayList<String[]> tokensList = new ArrayList<>();

    while (sc.hasNextLine()) {
      String line = "" + sc.nextLine();
      if (!line.isEmpty()) {
        String[] tokens = line.split(",");
        tokensList.add(tokens);
      }
    }
    sc.close(); // closes the scanner
    return tokensList;
  }

  public ArrayList<String[]> importCSVfromFile(File file) throws FileNotFoundException {

    InputStream in = new FileInputStream(file);
    Scanner sc = new Scanner(in);
    // Skip headers
    try {
      sc.next();
    } catch (NoSuchElementException e) {
      System.out.println(String.format("FILE IS EMPTY"));
    }

    ArrayList<String[]> tokensList = new ArrayList<>();

    while (sc.hasNextLine()) {
      String line = "" + sc.nextLine();
      if (!line.isEmpty()) {
        String[] tokens = line.split(",");
        tokensList.add(tokens);
      }
    }
    sc.close(); // closes the scanner
    return tokensList;
  }

  private void insertIntoLanguagesTable(ArrayList<String[]> tokens) throws SQLException {
    LanguageManager lm = LanguageManager.getLanguageManager();
    for (String[] s : tokens) {
      lm.addLanguage(s[0]);
    }
  }

  private void insertIntoLanguageInterpreterTable(ArrayList<String[]> tokens) throws SQLException {
    ArrayList<LanguageInterpreter> langInterps = new ArrayList<>();

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<String>();
      fields.addAll(Arrays.asList(s));
      langInterps.add(new LanguageInterpreter(fields));
    }

    for (LanguageInterpreter e : langInterps) {
      // add location objects to database
      try {

        LanguageInterpreterManager.getLanguageInterpreterManager().addLanguageInterpreter(e);
      } catch (SQLException s) {
        System.out.println("Connection failed. Check output console.");
        s.printStackTrace();
        throw (s);
      }
    }
  }

  private void insertIntoEmpTable(ArrayList<String[]> tokens) throws SQLException {
    ArrayList<Employee> employees = new ArrayList<>();

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<String>();
      fields.addAll(Arrays.asList(s));
      employees.add(new Employee(fields));
    }

    for (Employee e : employees) {
      // add location objects to database
      try {
        EmployeeManager.getEmployeeManager().addEmployee(e);
      } catch (SQLException s) {
        System.out.println("Connection failed. Check output console.");
        s.printStackTrace();
        throw (s);
      }
    }
  }

  public void exportAllCSVs() {
    EmployeeManager.getEmployeeManager().exportEmpCSV("CSVs/" + employeeFileName);
    LanguageManager.getLanguageManager().exportLocationsCSV("CSVs/" + languageFileName);
    LanguageInterpreterManager.getLanguageInterpreterManager()
        .exportReqCSV("CSVs/" + languageInterpreterFilename);
  }
}
