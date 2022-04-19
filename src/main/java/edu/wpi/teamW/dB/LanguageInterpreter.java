package edu.wpi.teamW.dB;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LanguageInterpreter {

  private Integer employeeID;
  private String language;

  public LanguageInterpreter(Integer employeeID, String language) {
    this.employeeID = employeeID;
    this.language = language;
  }

  public LanguageInterpreter(ArrayList<String> fields) {
    this.employeeID = Integer.parseInt(fields.get(0));
    this.language = fields.get(1);
  }

  public String toCSVString() {
    return String.format("%d,%s", employeeID, language);
  }

  public String toValuesString() {
    return String.format("%d, '%s'", employeeID, language);
  }
}
