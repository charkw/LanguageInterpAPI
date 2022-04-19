package edu.wpi.teamW;

import edu.wpi.teamW.dB.CSVController;
import edu.wpi.teamW.dB.DBController;

public class Main {

  public static void main(String[] args) {

    DBController.getDBController();

    CSVController csvController = new CSVController();

    try {
      csvController.populateTables();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    App.launch(App.class, args);
  }
}
