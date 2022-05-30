package lowe.mike.timestampapp.model;

import java.util.Objects;

/**
 * Error response details.
 *
 * @author Mike Lowe
 */
public class ErrorResponse {

  private final int status;
  private final String message;

  /**
   * Creates an {@code ErrorResponse} instance.
   *
   * @param status the status code
   * @param message the message
   */
  public ErrorResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

  /**
   * Returns the status code.
   *
   * @return the status code
   */
  public int getStatus() {
    return status;
  }

  /**
   * Returns the message.
   *
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse that = (ErrorResponse) o;
    return getStatus() == that.getStatus()
        && Objects.equals(getMessage(), that.getMessage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getStatus(), getMessage());
  }
}
