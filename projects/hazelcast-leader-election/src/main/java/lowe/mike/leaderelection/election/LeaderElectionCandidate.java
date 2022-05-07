package lowe.mike.leaderelection.election;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.leader.Context;
import org.springframework.integration.leader.DefaultCandidate;
import org.springframework.stereotype.Component;

/**
 * Leader election candidate that notifies {@link LeaderChangeReceiver}s when leadership changes.
 *
 * @author Mike Lowe
 */
@Component
public class LeaderElectionCandidate extends DefaultCandidate implements LeaderChangeSender {

  private static final Logger logger = LoggerFactory.getLogger(LeaderElectionCandidate.class);

  private final List<LeaderChangeReceiver> receivers = new ArrayList<>();

  /**
   * Creates a new {@code LeaderElectionCandidate}.
   */
  public LeaderElectionCandidate(@Value("${spring.application.name}") String role) {
    super(null, role);

    logger.info("Creating LeaderElectionCandidate");
  }

  @Override
  public void addReceiver(LeaderChangeReceiver receiver) {
    requireNonNull(receiver, "receiver is null");
    synchronized (receivers) {
      logger.info("Adding LeaderChangeReceiver");
      receivers.add(receiver);
    }
  }

  @Override
  public void onGranted(Context ctx) {
    super.onGranted(ctx);
    synchronized (receivers) {
      logger.info("Notifying LeaderChangeReceivers");
      receivers.forEach(receiver -> receiver.update(true));
    }
  }

  @Override
  public void onRevoked(Context ctx) {
    super.onRevoked(ctx);
    synchronized (receivers) {
      logger.info("Notifying LeaderChangeReceivers");
      receivers.forEach(receiver -> receiver.update(false));
    }
  }
}
