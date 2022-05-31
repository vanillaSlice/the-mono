package lowe.mike.requestheaderparserapp.controller;

import static java.util.Objects.requireNonNull;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import lowe.mike.requestheaderparserapp.model.ClientDetails;
import lowe.mike.requestheaderparserapp.service.RequestHeaderParserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Application controller.
 *
 * @author Mike Lowe
 */
@RestController
@Api(tags = "Request Header Parser", description = "Request Header Parser Endpoints")
public class RequestHeaderParserController {

  private final RequestHeaderParserService requestHeaderParserService;

  /**
   * Creates a new {@code RequestHeaderParserController}.
   *
   * @param requestHeaderParserService the {@link RequestHeaderParserService}
   * @throws NullPointerException if {@code requestHeaderParserService} is {@code null}
   */
  public RequestHeaderParserController(RequestHeaderParserService requestHeaderParserService) {
    this.requestHeaderParserService =
        requireNonNull(requestHeaderParserService, "request header parser service is null");
  }

  @ApiOperation(
      value = "Parses request headers",
      response = ClientDetails.class,
      notes = "Parses request headers and returns client details.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Returns client details", response = ClientDetails.class)
  })
  @GetMapping(value = "/parse", produces = "application/json")
  public ClientDetails parse(HttpServletRequest request) {
    return requestHeaderParserService.parse(request);
  }
}
