package com.ipn.mx.reservaciones7cm3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Reservaciones7Cm3Application {

    public static void main(String[] args) {
        SpringApplication.run(Reservaciones7Cm3Application.class, args);
    }

    @Bean
    CommandLineRunner init(PasswordEncoder encoder, JdbcTemplate jdbcTemplate) {
        return args -> {
            System.out.println("Clave = admin -> " + encoder.encode("admin"));
            System.out.println("Clave = limitado -> " + encoder.encode("limitado"));

            try {
                // 1. Insertar los Roles oficiales si no existen
                jdbcTemplate.update("INSERT INTO Rol (idRol, nombreRol, descripcionRol, fecha) VALUES (1, 'ROLE_ADMIN', 'Administrador', CURDATE()) ON DUPLICATE KEY UPDATE nombreRol=nombreRol");
                jdbcTemplate.update("INSERT INTO Rol (idRol, nombreRol, descripcionRol, fecha) VALUES (2, 'ROLE_USER', 'Usuario Regular', CURDATE()) ON DUPLICATE KEY UPDATE nombreRol=nombreRol");

                // 2. Crear el usuario 'admin' (ADMIN)
                String passAdmin = encoder.encode("admin");
                jdbcTemplate.update("INSERT INTO Usuario (idUsuario, nombreDeUsuario, email, claveDeUsuario, role, habilitado, bloqueado) " +
                        "VALUES (1, 'admin', 'admin@escom.mx', ?, 'ROLE_ADMIN', 1, 0) " +
                        "ON DUPLICATE KEY UPDATE claveDeUsuario=claveDeUsuario", passAdmin);

                // 3. Crear el usuario 'limitado' (USER)
                String passLimitado = encoder.encode("limitado");
                jdbcTemplate.update("INSERT INTO Usuario (idUsuario, nombreDeUsuario, email, claveDeUsuario, role, habilitado, bloqueado) " +
                        "VALUES (2, 'limitado', 'limitado@escom.mx', ?, 'ROLE_USER', 1, 0) " +
                        "ON DUPLICATE KEY UPDATE claveDeUsuario=claveDeUsuario", passLimitado);


                // =========================================================================
                // 4. NUEVO USUARIO PERSONALIZADO
                // =========================================================================
                String miNuevaClave = encoder.encode("ipn2026"); // <-- Aquí va tu contraseña
                jdbcTemplate.update("INSERT INTO Usuario (idUsuario, nombreDeUsuario, email, claveDeUsuario, role, habilitado, bloqueado) " +
                        "VALUES (3, 'David', 'david@escom.mx', ?, 'ROLE_USER', 1, 0) " +
                        "ON DUPLICATE KEY UPDATE claveDeUsuario=claveDeUsuario", miNuevaClave);
                // =========================================================================


                // 5. Vincular los usuarios con sus roles correspondientes en la tabla intermedia
                jdbcTemplate.update("INSERT INTO UsuarioRol (idUsuario, idRol) VALUES (1, 1) ON DUPLICATE KEY UPDATE idUsuario=idUsuario"); // admin -> ROLE_ADMIN
                jdbcTemplate.update("INSERT INTO UsuarioRol (idUsuario, idRol) VALUES (2, 2) ON DUPLICATE KEY UPDATE idUsuario=idUsuario"); // limitado -> ROLE_USER
                jdbcTemplate.update("INSERT INTO UsuarioRol (idUsuario, idRol) VALUES (3, 2) ON DUPLICATE KEY UPDATE idUsuario=idUsuario"); // helen -> ROLE_USER

                // 6. Habitaciones de prueba para el Front End
                jdbcTemplate.update("INSERT INTO rooms (id, numero, tipo, numeroCamas, precio, disponible) VALUES (1, 101, 'Individual', 1, 650.00, 1) ON DUPLICATE KEY UPDATE numero=numero");
                jdbcTemplate.update("INSERT INTO rooms (id, numero, tipo, numeroCamas, precio, disponible) VALUES (2, 202, 'Doble', 2, 950.00, 1) ON DUPLICATE KEY UPDATE numero=numero");

                System.out.println("¡ÉXITO: Usuarios oficiales, nuevo usuario personalizado y habitaciones inyectadas!");
            } catch (Exception e) {
                System.out.println("Nota de inicialización: " + e.getMessage());
            }
        };
    }
}