package com.juliandonati.backendPortafolio.security.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    private static final String SCHEME_NAME = "bearerAuth";
    private static final String BEARER_FORMAT = "JWT";
    private static final String DESCRIPTION = "Autenticación JWT para la API del BackEnd Portafolio";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SCHEME_NAME)
                                        .description(DESCRIPTION)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat(BEARER_FORMAT)
                                        .in(SecurityScheme.In.HEADER)
                                ))
                .info(new Info()
                        .title("Portafolio API")
                        .version("1.0")
                        .description("API RESTful para la gestión de usuarios, portafolios, y sus componentes")
                        .contact(new Contact()
                                .name("Julián Donati")
                                .email("juliandonati5@gmail.com")
                                .url("https://github.com/juliandonati")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }
}
