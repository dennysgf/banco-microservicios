package com.banco.cuentamovimiento.infrastructure.config;

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
                        .title("API Cuenta-Movimiento")
                        .description("Documentación de la API para gestión de Cuentas")
                        .version("v1.0"));
    }
}
