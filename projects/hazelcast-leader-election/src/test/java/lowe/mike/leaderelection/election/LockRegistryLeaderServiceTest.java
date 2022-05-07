package lowe.mike.leaderelection.election;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;

/**
 * {@link LockRegistryLeaderService} unit tests.
 *
 * @author Mike Lowe
 */
public class LockRegistryLeaderServiceTest {

  private final LockRegistryLeaderInitiator leaderInitiator =
      mock(LockRegistryLeaderInitiator.class, RETURNS_DEEP_STUBS);

  private final LockRegistryLeaderService lockRegistryLeaderService =
      new LockRegistryLeaderService(leaderInitiator);

  @Test
  public void constructor_nullLockRegistryLeaderInitiator_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> new LockRegistryLeaderService(null));

    assertEquals("leaderInitiator is null", exception.getMessage());
  }

  @Test
  public void isLeader_returnsIfLeader() {
    when(leaderInitiator.getContext().isLeader()).thenReturn(true, false);

    assertTrue(lockRegistryLeaderService.isLeader());
    assertFalse(lockRegistryLeaderService.isLeader());
  }
}
