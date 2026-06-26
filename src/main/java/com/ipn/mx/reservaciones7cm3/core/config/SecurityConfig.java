package com.ipn.mx.reservaciones7cm3.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.util.List;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Activar la configuración de CORS definida abajo en el Bean
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // 2. Permitir peticiones OPTIONS (Preflight) de forma libre para evitar bloqueos del navegador
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 3. Permitir acceso libre a Swagger para documentación y pruebas iniciales
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // 4. Restricciones del Módulo de Cuartos
                        .requestMatchers(HttpMethod.GET, "/api/v1/cuartos/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/cuartos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/cuartos/**").hasRole("ADMIN")

                        // 5. Restricciones del Módulo de Reservaciones (Tu práctica individual)
                        .requestMatchers(HttpMethod.GET, "/api/v1/reservaciones/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/reservaciones/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/reservaciones/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/reservaciones/**").hasRole("ADMIN")

                        // Cualquier otra ruta requiere autenticación previa
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Autenticación Básica (ideal para conectar fácil con JS por ahora)
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Cambiamos el patrón a tu origen exacto de Live Server para asegurar consistencia con credenciales activas
        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:5500"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}