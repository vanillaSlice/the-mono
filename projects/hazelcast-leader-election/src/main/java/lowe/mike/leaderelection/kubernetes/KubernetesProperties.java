package lowe.mike.leaderelection.kubernetes;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.util.Objects;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Kubernetes properties.
 *
 * @author Mike Lowe
 */
@Configuration
@ConfigurationProperties(prefix = "leader-election.kubernetes")
public class KubernetesProperties {

  private boolean enabled = true;

  private Duration endpointsRefresh = Duration.ofSeconds(15);

  private String nodeName = "minikube";

  private String podIp = "";

  private String podName = "";

  private String podNamespace = "default";

  private String serviceName = "leader-election";

  private int servicePort = 7080;

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Duration getEndpointsRefresh() {
    return endpointsRefresh;
  }

  public void setEndpointsRefresh(Duration endpointsRefresh) {
    this.endpointsRefresh = requireNonNull(endpointsRefresh, "endpointsRefresh is null");
  }

  public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = requireNonNull(nodeName, "nodeName is null");
  }

  public String getPodIp() {
    return podIp;
  }

  public void setPodIp(String podIp) {
    this.podIp = requireNonNull(podIp, "podIp is null");
  }

  public String getPodName() {
    return podName;
  }

  public void setPodName(String podName) {
    this.podName = requireNonNull(podName, "podName is null");
  }

  public String getPodNamespace() {
    return podNamespace;
  }

  public void setPodNamespace(String podNamespace) {
    this.podNamespace = requireNonNull(podNamespace, "podNamespace is null");
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = requireNonNull(serviceName, "serviceName is null");
  }

  public int getServicePort() {
    return servicePort;
  }

  public void setServicePort(int servicePort) {
    this.servicePort = servicePort;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof KubernetesProperties)) {
      return false;
    }
    KubernetesProperties that = (KubernetesProperties) o;
    return enabled == that.enabled
        && servicePort == that.servicePort
        && Objects.equals(endpointsRefresh, that.endpointsRefresh)
        && Objects.equals(nodeName, that.nodeName)
        && Objects.equals(podIp, that.podIp)
        && Objects.equals(podName, that.podName)
        && Objects.equals(podNamespace, that.podNamespace)
        && Objects.equals(serviceName, that.serviceName);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(
        enabled,
        endpointsRefresh,
        nodeName,
        podIp,
        podName,
        podNamespace,
        serviceName,
        servicePort
    );
  }

  @Override
  public String toString() {
    return "KubernetesProperties{"
        + "enabled=" + enabled
        + ", endpointsRefresh=" + endpointsRefresh
        + ", nodeName='" + nodeName + '\''
        + ", podIp='" + podIp + '\''
        + ", podName='" + podName + '\''
        + ", podNamespace='" + podNamespace + '\''
        + ", serviceName='" + serviceName + '\''
        + ", servicePort=" + servicePort
        + '}';
  }
}
