package lowe.mike.strimko.model;

/**
 * {@code FileHandlingException} instances indicate that an error has occurred when interacting with
 * files in this application.
 *
 * @author Mike Lowe
 */
public final class FileHandlingException extends Exception {

  private static final long serialVersionUID = 4077853285459813345L;

  /**
   * Constructs a new exception with the specified detail message. The cause is not initialized, and
   * may subsequently be initialized by a call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   *     {@link #getMessage()} method.
   */
  public FileHandlingException(String message) {
    super(message);
  }

}
