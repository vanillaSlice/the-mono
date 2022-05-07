package lowe.mike.leaderelection.election;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * {@link LeaderElectionController} unit tests.
 *
 * @author Mike Lowe
 */
public class LeaderElectionControllerTest {

  private final LeaderService leaderService = mock(LeaderService.class);

  private final LeaderElectionController controller = new LeaderElectionController(leaderService);

  @Test
  public void constructor_nullLeaderService_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> new LeaderElectionController(null));

    assertEquals("leaderService is null", exception.getMessage());
  }

  @Test
  public void leader_returnsExpectedResponse() throws UnknownHostException {
    when(leaderService.isLeader()).thenReturn(true, false);

    Map<String, Object> response1 = controller.leader();
    assertTrue((boolean) response1.get("leader"));
    assertEquals(InetAddress.getLocalHost().getHostAddress(), response1.get("ip"));

    Map<String, Object> response2 = controller.leader();
    assertFalse((boolean) response2.get("leader"));
    assertEquals(InetAddress.getLocalHost().getHostAddress(), response2.get("ip"));
  }
}
