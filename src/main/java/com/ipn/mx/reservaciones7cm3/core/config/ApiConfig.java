package com.ipn.mx.reservaciones7cm3.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Bean
    public OpenAPI myOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Sistema de Reservaciones")
                        .version("1.0")
                        .description("Documentación automática de los servicios REST para el sistema de gestión de habitaciones y reservaciones.")
                        .contact(new Contact()
                                .name("David")
                                .email("dayalac1800@alumno.ipn.mx")))
                // Estas dos líneas agregan el candado de seguridad a Swagger
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    // Método para configurar la seguridad por Usuario y Contraseña (Basic Auth)
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("basic"); // Cambiamos "bearer" por "basic"
    }
}