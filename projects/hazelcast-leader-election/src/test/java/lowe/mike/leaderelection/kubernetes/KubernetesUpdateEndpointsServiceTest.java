package lowe.mike.leaderelection.kubernetes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.CoreV1Api;
import org.junit.jupiter.api.Test;

/**
 * {@link KubernetesUpdateEndpointsService} unit tests.
 *
 * @author Mike Lowe
 */
public class KubernetesUpdateEndpointsServiceTest {

  private final CoreV1Api coreV1Api = mock(CoreV1Api.class);

  private final KubernetesProperties properties = new KubernetesProperties();

  private final KubernetesUpdateEndpointsService updateEndpointsService =
      new KubernetesUpdateEndpointsService(coreV1Api, properties);

  @Test
  public void constructor_nullCoreV1Api_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> new KubernetesUpdateEndpointsService(null, properties));

    assertEquals("coreV1Api is null", exception.getMessage());
  }

  @Test
  public void constructor_nullProperties_throwsNullPointerException() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> new KubernetesUpdateEndpointsService(coreV1Api, null));

    assertEquals("properties is null", exception.getMessage());
  }

  @Test
  public void update_notLeader_doesNotUpdateEndpoints() throws ApiException {
    updateEndpointsService.update(false);

    verify(coreV1Api, never()).replaceNamespacedEndpoints(any(), any(), any(), any(), any());
  }

  @Test
  public void update_leader_updatesEndpoints() throws ApiException {
    updateEndpointsService.update(true);

    verify(coreV1Api).replaceNamespacedEndpoints(any(), any(), any(), any(), any());
  }
}
