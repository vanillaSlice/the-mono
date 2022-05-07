package lowe.mike.leaderelection.election;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.integration.leader.Context;

/**
 * {@link LeaderElectionCandidate} unit tests.
 *
 * @author Mike Lowe
 */
public class LeaderElectionCandidateTest {

  private final LeaderChangeReceiver receiver1 = mock(LeaderChangeReceiver.class);
  private final LeaderChangeReceiver receiver2 = mock(LeaderChangeReceiver.class);
  private final LeaderChangeReceiver receiver3 = mock(LeaderChangeReceiver.class);

  private final LeaderElectionCandidate candidate = new LeaderElectionCandidate("leader-election");

  /**
   * Test set up.
   */
  @BeforeEach
  public void setUp() {
    candidate.addReceiver(receiver1);
    candidate.addReceiver(receiver2);
    candidate.addReceiver(receiver3);
  }

  @Test
  public void addReceiver_nullReceiver_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> candidate.addReceiver(null));

    assertEquals("receiver is null", exception.getMessage());
  }

  @Test
  public void onGranted_updatesReceivers() {
    candidate.onGranted(mock(Context.class));

    verify(receiver1).update(true);
    verify(receiver2).update(true);
    verify(receiver3).update(true);
  }

  @Test
  public void onRevoked_updatesReceivers() {
    candidate.onRevoked(mock(Context.class));

    verify(receiver1).update(false);
    verify(receiver2).update(false);
    verify(receiver3).update(false);
  }
}
