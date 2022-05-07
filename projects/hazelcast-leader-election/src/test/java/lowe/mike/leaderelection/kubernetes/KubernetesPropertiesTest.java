package lowe.mike.leaderelection.kubernetes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

/**
 * {@link KubernetesProperties} unit tests.
 *
 * @author Mike Lowe
 */
public class KubernetesPropertiesTest {

  private final KubernetesProperties properties = new KubernetesProperties();

  @Test
  public void setEndpointsRefresh_null_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> properties.setEndpointsRefresh(null));

    assertEquals("endpointsRefresh is null", exception.getMessage());
  }

  @Test
  public void setNodeName_null_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> properties.setNodeName(null));

    assertEquals("nodeName is null", exception.getMessage());
  }

  @Test
  public void setPodIp_null_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> properties.setPodIp(null));

    assertEquals("podIp is null", exception.getMessage());
  }

  @Test
  public void setPodName_null_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> properties.setPodName(null));

    assertEquals("podName is null", exception.getMessage());
  }

  @Test
  public void setPodNamespace_null_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> properties.setPodNamespace(null));

    assertEquals("podNamespace is null", exception.getMessage());
  }

  @Test
  public void setServiceName_null_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> properties.setServiceName(null));

    assertEquals("serviceName is null", exception.getMessage());
  }

  @Test
  public void equalsAndHashCode() {
    EqualsVerifier.forClass(KubernetesProperties.class)
        .suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }
}
