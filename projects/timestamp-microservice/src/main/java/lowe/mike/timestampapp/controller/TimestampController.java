package lowe.mike.timestampapp.controller;

import static java.util.Objects.requireNonNull;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lowe.mike.timestampapp.model.TimestampResponse;
import lowe.mike.timestampapp.service.TimestampService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Timestamp controller.
 *
 * @author Mike Lowe
 */
@RestController
@Api(tags = "Timestamp", description = "Timestamp Endpoints")
public class TimestampController {

  private final TimestampService timestampService;

  /**
   * Creates a new {@code TimestampController}.
   *
   * @param timestampService the {@link TimestampService}
   * @throws NullPointerException if {@code timestampService} is {@code null}
   */
  public TimestampController(TimestampService timestampService) {
    this.timestampService = requireNonNull(timestampService, "timestamp service is null");
  }

  @ApiOperation(
      value = "Converts timestamp",
      response = TimestampResponse.class,
      notes = "Takes a Unix timestamp or natural date string and returns both forms of that date.")
  @ApiResponses(value = {
      @ApiResponse(
          code = 200,
          message = "Returns a timestamp response",
          response = TimestampResponse.class),
  })
  @GetMapping(value = "/convert/{date}", produces = "application/json")
  public TimestampResponse convert(
      @ApiParam(required = true, name = "date", value = "Unix timestamp or natural date string")
      @PathVariable("date") String date) {
    return timestampService.convert(date);
  }
}
