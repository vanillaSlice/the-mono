package lowe.mike.requestheaderparserapp.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger config.
 *
 * @author Mike Lowe
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Value("${info.build.version}")
  private String version;

  /**
   * Returns the {@link Docket}.
   *
   * @return the {@link Docket}
   */
  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("lowe.mike.requestheaderparserapp.controller"))
        .paths(paths())
        .build()
        .useDefaultResponseMessages(false);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .contact(new Contact("Mike Lowe", "https://www.mikelowe.xyz", "mikelowedev@gmail.com"))
        .description("This page lists all the REST APIs for the Request Header Parser App.")
        .license("MIT")
        .licenseUrl(
            "https://github.com/vanillaSlice/the-mono/blob/main/projects/request-header-parser-microservice/LICENSE")
        .title("Request Header Parser App")
        .version(version)
        .build();
  }

  private Predicate<String> paths() {
    return Predicates.and(
        PathSelectors.regex("/parse.*"),
        Predicates.not(PathSelectors.regex("/error.*")));
  }
}
