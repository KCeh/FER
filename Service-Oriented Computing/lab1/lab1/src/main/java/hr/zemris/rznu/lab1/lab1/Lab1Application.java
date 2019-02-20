package hr.zemris.rznu.lab1.lab1;

import hr.zemris.rznu.lab1.lab1.Util.Constants;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
@SwaggerDefinition(info = @Info(description = "REST API for lab1", title = "App API", version = "V2.0.0"), consumes = {
        "application/json" }, produces = { "application/json" })
public class Lab1Application implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(Lab1Application.class, args);
	}

    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage(Constants.Swagger.controllerReferences)).paths(PathSelectors.ant(Constants.Api.apiAll)).build();
    }

    @Bean
    public MessageSource messageSource()
    {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
