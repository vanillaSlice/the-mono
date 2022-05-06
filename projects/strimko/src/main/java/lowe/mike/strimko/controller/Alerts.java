package lowe.mike.strimko.controller;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.NONE;
import static javafx.scene.control.Alert.AlertType.WARNING;
import static javafx.scene.control.ButtonType.OK;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * {@code Alerts} provides methods for showing different types of alerts.
 *
 * <p>Instances of {@code Alerts} cannot be created.
 *
 * @author Mike Lowe
 */
public final class Alerts {

  // don't want instances
  private Alerts() {
  }

  /**
   * Show a message alert box with the given message and wait.
   *
   * @param message the message
   */
  public static void showMessageAndWait(String message) {
    showAlertAndWait(NONE, message, OK);
  }

  /**
   * Show an error alert box with the given message and wait.
   *
   * @param message the message
   */
  public static void showErrorAndWait(String message) {
    showAlertAndWait(ERROR, message);
  }

  /**
   * Show an error alert box, which gets the message from {@code throwable}, and wait.
   *
   * @param throwable the {@link Throwable} to get the message from
   */
  public static void showErrorAndWait(Throwable throwable) {
    showErrorAndWait(throwable.getMessage());
  }

  /**
   * Show a warning alert box and wait.
   *
   * @param message the message
   */
  public static void showWarningAndWait(String message) {
    showAlertAndWait(WARNING, message);
  }

  /**
   * Show a warning alert box, which gets the message from {@code throwable}, and wait.
   *
   * @param throwable the {@link Throwable} to get the message from
   */
  public static void showWarningAndWait(Throwable throwable) {
    showWarningAndWait(throwable.getMessage());
  }

  private static void showAlertAndWait(AlertType alertType, String message,
      ButtonType... buttonTypes) {
    Alert alert = new Alert(alertType, message, buttonTypes);
    alert.showAndWait();
  }

}
