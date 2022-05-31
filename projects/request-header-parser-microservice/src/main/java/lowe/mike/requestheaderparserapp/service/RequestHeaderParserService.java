package lowe.mike.requestheaderparserapp.service;

import javax.servlet.http.HttpServletRequest;
import lowe.mike.requestheaderparserapp.model.ClientDetails;

/**
 * Interface to extract details from a {@link HttpServletRequest}.
 *
 * @author Mike Lowe
 */
public interface RequestHeaderParserService {

  /**
   * Takes a {@link HttpServletRequest} and returns {@link ClientDetails} extracted from this
   * request.
   *
   * @param request the {@link HttpServletRequest}
   * @return the {@link ClientDetails}
   * @throws NullPointerException if {@code request} is {@code null}
   */
  ClientDetails parse(HttpServletRequest request);
}
