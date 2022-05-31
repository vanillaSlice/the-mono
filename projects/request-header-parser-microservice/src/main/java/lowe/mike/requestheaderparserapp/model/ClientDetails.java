package lowe.mike.requestheaderparserapp.model;

import static java.util.Objects.requireNonNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

/**
 * Stores client details.
 *
 * @author Mike Lowe
 */
@ApiModel(value = "Client Details")
public class ClientDetails {

  @ApiModelProperty(notes = "IP Address", example = "251.1.41.24")
  private final String ipAddress;

  @ApiModelProperty(notes = "Language", example = "en-GB")
  private final String language;

  @ApiModelProperty(notes = "Software", example = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) "
      + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")
  private final String software;

  /**
   * Creates a new {@code ClientDetails} instance.
   *
   * @param ipAddress the IP address
   * @param language the language
   * @param software the software
   * @throws NullPointerException if {@code ipAddress}, {@code language} or {@code software} are
   *     {@code null}
   */
  public ClientDetails(String ipAddress, String language, String software) {
    this.ipAddress = requireNonNull(ipAddress, "ip address is null");
    this.language = requireNonNull(language, "language is null");
    this.software = requireNonNull(software, "software is null");
  }

  /**
   * Returns the IP address.
   *
   * @return the IP address
   */
  public String getIpAddress() {
    return ipAddress;
  }

  /**
   * Returns the language.
   *
   * @return the language
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Returns the software.
   *
   * @return the software
   */
  public String getSoftware() {
    return software;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientDetails that = (ClientDetails) o;
    return Objects.equals(ipAddress, that.ipAddress)
        && Objects.equals(language, that.language)
        && Objects.equals(software, that.software);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ipAddress, language, software);
  }
}
