package edu.wpi.teamW.frontEnd;

import edu.wpi.teamW.SceneManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

public class DefaultPageController implements Initializable {

  @FXML public Pane languageInterpreterServiceRequestPage;

  public void initialize(URL location, ResourceBundle rb) {

    SceneManager.getInstance().putController(SceneManager.Scenes.Default, this);

    SceneManager.getInstance()
        .putPane(SceneManager.Scenes.LanguageInterpreter, languageInterpreterServiceRequestPage);
  }

  public void switchToLanguageInterpreter() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.LanguageInterpreter);
  }

  public void exitProgram() {
    SceneManager.getInstance().exitApplication();
  }
}
