package lowe.mike.gameoflife;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.io.IOException;
import java.util.function.Predicate;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lowe.mike.gameoflife.controller.Controller;
import lowe.mike.gameoflife.model.GameOfLife;
import lowe.mike.gameoflife.model.Grid;
import lowe.mike.gameoflife.model.Speed;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * {@link GameOfLifeApplication} E2E tests.
 *
 * @author Mike Lowe
 */
public class GameOfLifeApplicationE2eTest extends ApplicationTest {

  private static final int NUMBER_OF_ROWS = 40;
  private static final int NUMBER_OF_COLUMNS = 70;

  private GameOfLife gameOfLife;

  /**
   * Headless testing set up.
   */
  @BeforeAll
  public static void headlessSetUp() {
    System.setProperty("java.awt.headless", "true");
    System.setProperty("testfx.headless", "true");
    System.setProperty("testfx.robot", "glass");
    System.setProperty("prism.order", "sw");
    System.setProperty("prism.text", "t2k");
  }

  @Override
  public void start(Stage stage) throws IOException {
    gameOfLife = spy(new GameOfLife(new Grid(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS)));
    new GameOfLifeApplication(gameOfLife).start(stage);
  }

  @Test
  public void applicationBoots() {
    new GameOfLifeApplication();
  }

  @Test
  public void playButtonClick_startsGameOfLife() {
    // initial verification
    verifyThat("#generationNumberLabel", hasText("0"));

    // execution
    clickOn("#playToggleButton");

    // verification
    sleep(2000);
    verifyThat("#generationNumberLabel", hasText(not("0")));
    verify(gameOfLife).play();
  }

  @Test
  public void pauseButtonClick_pausesGameOfLife() {
    // initial setup
    clickOn("#playToggleButton");
    sleep(2000);

    // execution
    clickOn("#pauseToggleButton");

    // verification
    String generationBefore = lookup("#generationNumberLabel").queryLabeled().getText();
    sleep(2000);
    String generationAfter = lookup("#generationNumberLabel").queryLabeled().getText();
    assertEquals(generationBefore, generationAfter);
    verify(gameOfLife).pause();
  }

  @Test
  public void resetButtonClick_resetsGameOfLife() {
    // initial setup
    clickOn("#playToggleButton");
    sleep(2000);

    // execution
    clickOn("#resetButton");

    // verification
    verifyThat("#generationNumberLabel", hasText("0"));
    verify(gameOfLife).reset();
  }

  @Test
  public void clearButtonClick_clearsGameOfLife() {
    // initial setup
    clickOn("#playToggleButton");
    sleep(2000);

    // execution
    clickOn("#clearButton");

    // verification
    verifyThat("#generationNumberLabel", hasText("0"));
    verify(gameOfLife).clear();
  }

  @Test
  public void slowButtonClick_togglesSlow() {
    // setup
    ToggleButton slowButton = lookup("#slowToggleButton").query();

    // execution
    clickOn(slowButton);

    // verification (speed should default to slow)
    assertTrue(slowButton.isSelected());
    verify(gameOfLife, never()).setSpeed(Speed.SLOW);

    // setup
    ToggleButton mediumButton = lookup("#mediumToggleButton").query();

    // execution
    clickOn(mediumButton);
    clickOn(slowButton);

    // verification
    assertTrue(slowButton.isSelected());
    verify(gameOfLife).setSpeed(Speed.SLOW);
  }

  @Test
  public void mediumButtonClick_togglesMedium() {
    // initial setup
    ToggleButton mediumButton = lookup("#mediumToggleButton").query();

    // execution
    clickOn(mediumButton);

    // verification
    assertTrue(mediumButton.isSelected());
    verify(gameOfLife).setSpeed(Speed.MEDIUM);
  }

  @Test
  public void fastButtonClick_togglesFast() {
    // initial setup
    ToggleButton fastButton = lookup("#fastToggleButton").query();

    // execution
    clickOn(fastButton);

    // verification
    assertTrue(fastButton.isSelected());
    verify(gameOfLife).setSpeed(Speed.FAST);
  }

  @Test
  public void fastestButtonClick_togglesFastest() {
    // initial setup
    ToggleButton fastestButton = lookup("#fastestToggleButton").query();

    // execution
    clickOn(fastestButton);

    // verification
    assertTrue(fastestButton.isSelected());
    verify(gameOfLife).setSpeed(Speed.FASTEST);
  }

  @Test
  public void cellClick_togglesAlive() {
    // initial setup
    Pane cellPane = lookup("." + Controller.CELL_PANE_STYLE_CLASS)
        .lookup((Predicate<Pane>) p -> !p.getStyleClass().contains(Controller.ALIVE_STYLE_CLASS))
        .query();

    // execution
    clickOn(cellPane);

    // verification
    assertTrue(cellPane.getStyleClass().contains(Controller.ALIVE_STYLE_CLASS));

    // execution
    clickOn(cellPane);

    // verification
    assertFalse(cellPane.getStyleClass().contains(Controller.ALIVE_STYLE_CLASS));
  }
}
