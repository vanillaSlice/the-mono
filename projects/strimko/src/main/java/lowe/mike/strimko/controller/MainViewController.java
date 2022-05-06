package lowe.mike.strimko.controller;

import javafx.fxml.FXML;
import lowe.mike.strimko.model.GameState;

/**
 * Controller class for Main View.
 *
 * @author Mike Lowe
 */
public final class MainViewController {

  @FXML
  private PlayModeViewController playModeViewController;
  @FXML
  private SolveModeViewController solveModeViewController;

  public void setGameState(GameState gameState) {
    playModeViewController.setGameState(gameState);
    solveModeViewController.setGameState(gameState);
  }

}
