package edu.wpi.teamW.dB;

import java.sql.SQLException;
import java.util.ArrayList;

public class LanguageInterpreterManager {

  private static LanguageInterpreterManager lim = new LanguageInterpreterManager();
  private LanguageInterpreterDao lid;

  private LanguageInterpreterManager() {}

  public static LanguageInterpreterManager getLanguageInterpreterManager() {
    return lim;
  }

  public void setLanguageInterpreterDao(LanguageInterpreterDao lid) {
    this.lid = lid;
  }

  public LanguageInterpreter addLanguageInterpreter(ArrayList<String> fields) throws SQLException {
    LanguageInterpreter li = new LanguageInterpreter(fields);
    lid.addLanguageInterpreter(li);
    return li;
  }

  public void addLanguageInterpreter(LanguageInterpreter li) throws SQLException {
    lid.addLanguageInterpreter(li);
  }

  public void deleteLanguageInterpreter(LanguageInterpreter li) throws SQLException {
    lid.deleteLanguageInterpreter(li);
  }

  public void changeLanguageInterpreter(LanguageInterpreter li, String lang) throws SQLException {
    lid.changeLanguageInterpreter(li, lang);
  }

  public ArrayList<LanguageInterpreter> getAllRequests() throws SQLException {
    return lid.getAllLanguageInterpreters();
  }

  public void exportReqCSV(String filename) {
    lid.exportLangInterpCSV(filename);
  }

  public ArrayList<Integer> getEligibleEmployees(String lanuage) throws SQLException {
    return lid.getEligibleEmployees(lanuage);
  }
}
