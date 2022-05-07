package lowe.mike.leaderelection.kubernetes;

import static java.util.Objects.requireNonNull;

import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1EndpointAddress;
import io.kubernetes.client.models.V1EndpointPort;
import io.kubernetes.client.models.V1EndpointSubset;
import io.kubernetes.client.models.V1Endpoints;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1ObjectReference;
import lowe.mike.leaderelection.election.LeaderChangeReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service that updates the Kubernetes service endpoints to route traffic to only this instance of
 * the application.
 *
 * @author Mike Lowe
 */
public class KubernetesUpdateEndpointsService implements LeaderChangeReceiver {

  private static final Logger logger =
      LoggerFactory.getLogger(KubernetesUpdateEndpointsService.class);

  private final CoreV1Api coreV1Api;
  private final String name;
  private final String namespace;
  private final V1Endpoints body;
  private final String pretty = null;
  private final String dryRun = null;

  /**
   * Creates a new {@code KubernetesUpdateEndpointsService}.
   */
  public KubernetesUpdateEndpointsService(CoreV1Api coreV1Api, KubernetesProperties properties) {
    this.coreV1Api = requireNonNull(coreV1Api, "coreV1Api is null");
    requireNonNull(properties, "properties is null");
    this.name = properties.getServiceName();
    this.namespace = properties.getPodNamespace();
    this.body = new V1Endpoints()
        .metadata(new V1ObjectMeta()
            .name(properties.getServiceName())
            .namespace(properties.getPodNamespace())
        )
        .addSubsetsItem(new V1EndpointSubset()
            .addAddressesItem(new V1EndpointAddress()
                .ip(properties.getPodIp())
                .nodeName(properties.getNodeName())
                .targetRef(new V1ObjectReference()
                    .name(properties.getPodName())
                    .namespace(properties.getPodNamespace())
                )
            )
            .addPortsItem(new V1EndpointPort()
                .port(properties.getServicePort())
            )
        );
  }

  @Override
  public void update(boolean isLeader) {
    if (!isLeader) {
      logger.debug("Not the leader, so not updating Kubernetes endpoints");
      return;
    }

    logger.debug("Leader, so updating Kubernetes endpoints");

    try {
      coreV1Api.replaceNamespacedEndpoints(name, namespace, body, pretty, dryRun);
    } catch (ApiException e) {
      logger.error(e.getResponseBody());
    }
  }
}
