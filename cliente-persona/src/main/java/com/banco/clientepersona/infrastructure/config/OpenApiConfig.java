package com.banco.clientepersona.infrastructure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI clientePersonaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Cliente-Persona")
                        .description("Documentación de la API para gestión de Clientes")
                        .version("v1.0"));
    }
}
