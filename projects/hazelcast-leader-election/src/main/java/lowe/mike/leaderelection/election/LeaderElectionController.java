package lowe.mike.leaderelection.election;

import static java.util.Objects.requireNonNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Leader election controller.
 *
 * @author Mike Lowe
 */
@RestController
public class LeaderElectionController {

  private static final Logger logger = LoggerFactory.getLogger(LeaderElectionController.class);

  private final LeaderService leaderService;

  /**
   * Creates a new {@code LeaderElectionController}.
   */
  public LeaderElectionController(LeaderService leaderService) {
    logger.info("Creating LeaderElectionController");

    this.leaderService = requireNonNull(leaderService, "leaderService is null");
  }

  /**
   * Returns if this instance is the leader and its IP address.
   */
  @GetMapping("/leader")
  public Map<String, Object> leader() throws UnknownHostException {
    logger.debug("GET /leader request");

    Map<String, Object> response = new HashMap<>();

    response.put("ip", InetAddress.getLocalHost().getHostAddress());
    response.put("leader", leaderService.isLeader());

    logger.debug("GET /leader response: {}", response);

    return response;
  }
}
