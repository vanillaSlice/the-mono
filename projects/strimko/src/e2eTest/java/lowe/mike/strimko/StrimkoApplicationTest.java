package lowe.mike.strimko;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * {@link StrimkoApplication} tests.
 */
public class StrimkoApplicationTest extends ApplicationTest {

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
  public void start(Stage stage) {
    new StrimkoApplication().start(stage);
  }

  @Test
  public void applicationLoads() {
  }

}
