package lowe.mike.leaderelection.election;

/**
 * An object that will notify {@link LeaderChangeReceiver}s when leadership changes.
 *
 * @author Mike Lowe
 */
@FunctionalInterface
public interface LeaderChangeSender {

  void addReceiver(LeaderChangeReceiver receiver);
}
