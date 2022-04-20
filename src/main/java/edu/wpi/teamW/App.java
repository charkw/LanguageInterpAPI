package edu.wpi.teamW;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
  @Override
  public void init() {
    // log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    try {
      API.run(-10, -10, 0, 0, "", "NodeID", "NodeID");
    } catch (ServiceException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void stop() {
    // log.info("Shutting Down");
  }
}
