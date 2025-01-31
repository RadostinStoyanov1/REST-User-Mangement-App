package users.rest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI();

        openAPI.components(
                new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ));

        openAPI.setInfo(
                new Info()
                        .description("This is a micro service of User Management REST application.")
                        .title("CRUD User Management Rest Application")
                        .version("0.0.1")
                        .contact(
                                new Contact()
                                        .name("Radostin Stoyanov")
                                        .email("radostoyanov2003@gmail.com")
                        )
        );

        return openAPI;
    }


}
