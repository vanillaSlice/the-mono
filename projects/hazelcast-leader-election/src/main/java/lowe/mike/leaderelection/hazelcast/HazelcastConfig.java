package lowe.mike.leaderelection.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.LockConfig;
import com.hazelcast.config.QuorumConfig;
import com.hazelcast.config.QuorumListenerConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.quorum.QuorumType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.hazelcast.lock.HazelcastLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;

/**
 * Hazelcast config.
 *
 * @author Mike Lowe
 */
@Configuration
public class HazelcastConfig {

  private static final Logger logger = LoggerFactory.getLogger(HazelcastConfig.class);

  /**
   * Hazelcast config.
   */
  @Bean
  public Config config(HazelcastProperties properties,
      JoinConfig joinConfig,
      QuorumConfig quorumConfig,
      LockConfig lockConfig) {
    logger.info("Creating Config");

    Config config = new Config();

    config.getProperties().putAll(properties.getSystemProperties());
    config.getNetworkConfig().setJoin(joinConfig);

    logger.info("Quorum enabled: {}", properties.isQuorumEnabled());

    if (properties.isQuorumEnabled()) {
      config.addQuorumConfig(quorumConfig);
    }

    config.addLockConfig(lockConfig);

    return config;
  }

  /**
   * Join config.
   */
  @Bean
  public JoinConfig joinConfig(HazelcastProperties properties) {
    logger.info("Creating JoinConfig");

    JoinConfig joinConfig = new JoinConfig();

    logger.info("Kubernetes enabled: {}", properties.getKubernetes().isEnabled());

    if (properties.getKubernetes().isEnabled()) {
      joinConfig.getMulticastConfig().setEnabled(false);
      joinConfig.getKubernetesConfig().setEnabled(true)
          .setProperty("namespace", properties.getKubernetes().getNamespace())
          .setProperty("service-name", properties.getKubernetes().getServiceName())
          .setProperty("resolve-not-ready-addresses", "true");
    }

    return joinConfig;
  }

  /**
   * Quorum config.
   */
  @Bean
  public QuorumConfig quorumConfig(@Value("${spring.application.name}") String applicationName,
      HazelcastProperties properties,
      QuorumListenerConfig quorumListenerConfig) {
    logger.info("Creating QuorumConfig");

    return new QuorumConfig()
        .setEnabled(true)
        .setName(applicationName)
        .setSize(properties.getMinQuorumSize())
        .setType(QuorumType.READ_WRITE)
        .addListenerConfig(quorumListenerConfig);
  }

  /**
   * Quorum listener config.
   */
  @Bean
  public QuorumListenerConfig quorumListenerConfig() {
    logger.info("Creating QuorumListenerConfig");

    return (QuorumListenerConfig) new QuorumListenerConfig()
        .setImplementation(e -> logger.info("Quorum present: {}", e.isPresent()));
  }

  /**
   * Lock config.
   */
  @Bean
  public LockConfig lockConfig(@Value("${spring.application.name}") String applicationName) {
    logger.info("Creating LockConfig");

    return new LockConfig()
        .setName(applicationName)
        .setQuorumName(applicationName);
  }

  /**
   * Lock registry.
   */
  @Bean
  public LockRegistry lockRegistry(HazelcastInstance hazelcastInstance) {
    logger.info("Creating LockRegistry");

    return new HazelcastLockRegistry(hazelcastInstance);
  }
}
