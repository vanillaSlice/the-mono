package lowe.mike.strimko;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * {@link StrimkoApplication} tests.
 */
public class StrimkoApplicationTest extends ApplicationTest {

  @Override
  public void start(Stage stage) {
    new StrimkoApplication().start(stage);
  }

  @Test
  public void applicationLoads() {
  }

}
