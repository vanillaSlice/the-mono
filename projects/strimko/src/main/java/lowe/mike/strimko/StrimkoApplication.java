package lowe.mike.strimko;

import static javafx.application.Platform.exit;
import static lowe.mike.strimko.controller.Alerts.showErrorAndWait;
import static lowe.mike.strimko.model.FileHandler.copyPuzzlesToUserDirectory;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lowe.mike.strimko.controller.MainViewController;
import lowe.mike.strimko.model.FileHandlingException;
import lowe.mike.strimko.model.GameState;

/**
 * Entry point for Strimko application.
 *
 * @author Mike Lowe
 */
public final class StrimkoApplication extends Application {

  private static final String APP_NAME = "Strimko";
  private static final String MAIN_VIEW_RESOURCE_PATH = "/view/main-view.fxml";
  private static final Image ICON = new Image("/view/icon.png");

  private GameState gameState;
  private Stage primaryStage;
  private Parent mainView;

  @Override
  public void start(Stage primaryStage) {
    if (successfullyCopiedPuzzlesToUserDirectory()) {
      initializeGameState();
      initializePrimaryStage(primaryStage);
      initializeMainView();
      addIcon();
      showScene();
    }
  }

  private static boolean successfullyCopiedPuzzlesToUserDirectory() {
    try {
      copyPuzzlesToUserDirectory();
      return true;
    } catch (FileHandlingException e) {
      showErrorAndWait(e);
      return false;
    }
  }

  private void initializeGameState() {
    gameState = new GameState();
  }

  private void initializePrimaryStage(Stage primaryStage) {
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle(APP_NAME);
    this.primaryStage.setOnCloseRequest(event -> exit());
    this.primaryStage.setResizable(false);
    this.primaryStage.sizeToScene();
  }

  private void initializeMainView() {
    FXMLLoader loader = new FXMLLoader();
    URL location = MainViewController.class.getResource(MAIN_VIEW_RESOURCE_PATH);
    loader.setLocation(location);
    mainView = loadFxml(loader);
    MainViewController controller = loader.getController();
    controller.setGameState(gameState);
  }

  private static <T> T loadFxml(FXMLLoader loader) {
    try {
      return loader.load();
    } catch (IOException e) {
      throw new AssertionError("Must always be able to load FXML resource");
    }
  }

  private void addIcon() {
    primaryStage.getIcons().add(ICON);
  }

  private void showScene() {
    Scene scene = new Scene(mainView);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

}
