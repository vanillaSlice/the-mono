package lowe.mike.requestheaderparserapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home controller.
 *
 * @author Mike Lowe
 */
@Controller
public class HomeController {

  @GetMapping("/")
  public String redirectToSwaggerDocs() {
    return "redirect:/swagger-ui.html";
  }
}
