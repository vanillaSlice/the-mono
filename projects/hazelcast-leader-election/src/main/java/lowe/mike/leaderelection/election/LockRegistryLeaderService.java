package lowe.mike.leaderelection.election;

import static java.util.Objects.requireNonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;
import org.springframework.stereotype.Service;

/**
 * Service which can be called to find out if this instance is the leader.
 *
 * @author Mike Lowe
 */
@Service
public class LockRegistryLeaderService implements LeaderService {

  private static final Logger logger = LoggerFactory.getLogger(LockRegistryLeaderService.class);

  private final LockRegistryLeaderInitiator leaderInitiator;

  /**
   * Creates a new {@code LockRegistryLeaderService}.
   */
  public LockRegistryLeaderService(LockRegistryLeaderInitiator leaderInitiator) {
    logger.info("Creating LockRegistryLeaderService");

    this.leaderInitiator = requireNonNull(leaderInitiator, "leaderInitiator is null");
  }

  @Override
  public boolean isLeader() {
    return leaderInitiator.getContext().isLeader();
  }
}
