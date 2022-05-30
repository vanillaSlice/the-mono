package lowe.mike.timestampapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lowe.mike.timestampapp.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * JSON error controller.
 *
 * @author Mike Lowe
 */
@RestController
public class JsonErrorController implements ErrorController {

  private static final String PATH = "/error";

  @Autowired
  private ErrorAttributes errorAttributes;

  @RequestMapping(value = PATH)
  public ErrorResponse error(HttpServletRequest request, HttpServletResponse response) {
    return new ErrorResponse(response.getStatus(), getErrorMessage(request));
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }

  private String getErrorMessage(HttpServletRequest request) {
    return (String) errorAttributes.getErrorAttributes(new ServletWebRequest(request), false)
        .get("error");
  }
}
