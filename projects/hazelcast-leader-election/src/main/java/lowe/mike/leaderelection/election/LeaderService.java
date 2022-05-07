package lowe.mike.leaderelection.election;

/**
 * Service which can be called to find out if this instance is the leader.
 *
 * @author Mike Lowe
 */
@FunctionalInterface
public interface LeaderService {

  boolean isLeader();
}
