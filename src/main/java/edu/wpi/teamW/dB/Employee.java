package edu.wpi.teamW.dB;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {

  private Integer employeeID;
  private String firstName;
  private String lastName;

  public Employee(Integer employeeID, String fName, String lName) {
    this.employeeID = employeeID;
    this.firstName = fName;
    this.lastName = lName;
  }

  public Employee(ArrayList<String> employeeData) {
    this.employeeID = Integer.parseInt(employeeData.get(0));
    this.firstName = employeeData.get(1);
    this.lastName = employeeData.get(2);
  }

  public String toCSVString() {
    return String.format("%d,%s,%s", employeeID, firstName, lastName);
  }

  public String toValuesString() {
    return String.format("%d, '%s', '%s'", employeeID, firstName, lastName);
  }
}
