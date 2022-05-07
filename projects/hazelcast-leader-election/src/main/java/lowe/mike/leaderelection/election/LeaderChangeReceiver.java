package lowe.mike.leaderelection.election;

/**
 * An object that will be notified when leadership changes.
 *
 * @author Mike Lowe
 */
@FunctionalInterface
public interface LeaderChangeReceiver {

  void update(boolean isLeader);
}
