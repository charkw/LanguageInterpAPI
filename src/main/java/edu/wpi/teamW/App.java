package edu.wpi.teamW;

import edu.wpi.teamW.dB.EmployeeManager;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {
  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    primaryStage.setFullScreen(false);

    primaryStage.setMaximized(true);
    primaryStage.setResizable(true);
    primaryStage.toFront();
    primaryStage.setTitle("Mass General Brigham Service Requests");
    primaryStage
        .getIcons()
        .add(new Image(getClass().getResourceAsStream("/edu/wpi/teamW/icons/mgb_logo.png")));
    primaryStage.setOnCloseRequest(
        e -> {
          EmployeeManager.getEmployeeManager()
              .exportEmpCSV("edu/wpi/cs3733/d22/teamW/wDB/CSVs/Employees.csv");
        });
    //    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamW/views/" + fileName));
    //    SceneManager.getInstance()
    //        .setScene("LanguageInterpreterServiceRequestPage.fxml");
    Parent root =
        FXMLLoader.load(
            getClass()
                .getResource(
                    "/edu/wpi/teamW/views/" + "LanguageInterpreterServiceRequestPage.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
    // SceneManager.getInstance().setScene("LanguageInterpreterServiceRequestPage.fxml");
    // SceneManager.getInstance().transitionTo(SceneManager.Scenes.LanguageInterpreter);
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
