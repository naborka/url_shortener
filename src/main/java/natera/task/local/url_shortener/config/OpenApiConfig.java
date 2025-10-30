package natera.task.local.url_shortener.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "URL shortener API",
        version = "0.1"
    )
)
public class OpenApiConfig {
}