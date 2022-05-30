package lowe.mike.timestampapp.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

/**
 * Stores timestamp details.
 *
 * @author Mike Lowe
 */
@ApiModel(value = "Timestamp Response")
public class TimestampResponse {

  public static final TimestampResponse EMPTY = new TimestampResponse(null, null);

  @ApiModelProperty(notes = "Unix timestamp", example = "1450137600")
  private final Long unix;

  @ApiModelProperty(notes = "Natural date", example = "December 15, 2015")
  private final String natural;

  /**
   * Creates a new {@code TimestampResponse}.
   *
   * @param unix the unix value
   * @param natural the natural value
   */
  public TimestampResponse(Long unix, String natural) {
    this.unix = unix;
    this.natural = natural;
  }

  /**
   * Returns the unix value.
   *
   * @return the unix value
   */
  public Long getUnix() {
    return unix;
  }

  /**
   * Returns the natural value.
   *
   * @return the natural value
   */
  public String getNatural() {
    return natural;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimestampResponse that = (TimestampResponse) o;
    return Objects.equals(unix, that.unix)
        && Objects.equals(natural, that.natural);
  }

  @Override
  public int hashCode() {
    return Objects.hash(unix, natural);
  }
}
