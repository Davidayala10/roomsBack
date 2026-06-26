package com.ipn.mx.reservaciones7cm3.features.room.controller; // Ajusta el paquete según tu estructura

import com.ipn.mx.reservaciones7cm3.core.domain.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Autenticación y Usuarios", description = "Operaciones de seguridad, manejo de usuarios y asignación de roles")
public class UsuarioController {

    @Operation(summary = "Autenticar usuario", description = "Recibe las credenciales del usuario y devuelve un token de acceso (JWT).")
    @ApiResponse(responseCode = "200", description = "Autenticación exitosa")
    @PostMapping("/login")
    public ResponseEntity<String> login() {
        // Tu lógica de autenticación aquí
        return ResponseEntity.ok("Token simulado");
    }

    @Operation(summary = "Consultar usuarios del sistema", description = "Devuelve la lista de usuarios registrados con sus respectivos roles (Ej. ROLE_ADMIN, ROLE_USER).")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios y roles obtenida")
    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuariosYRoles() {
        // Tu lógica para consultar usuarios aquí
        return ResponseEntity.ok(null);
    }
}