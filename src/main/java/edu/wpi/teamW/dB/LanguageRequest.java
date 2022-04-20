package edu.wpi.teamW.dB;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LanguageRequest {

  private String language;
  private String nodeID;
  private Employee employee;

  public LanguageRequest(String language, String nodeID, Employee employee) {
    this.language = language;
    this.nodeID = nodeID;
    this.employee = employee;
  }
}
