package com.ipn.mx.reservaciones7cm3.features.reservation.controller;

import com.ipn.mx.reservaciones7cm3.core.domain.Reservacion;
import com.ipn.mx.reservaciones7cm3.core.domain.Usuario;
import com.ipn.mx.reservaciones7cm3.features.reservation.dto.CreateReservacionDTO;
import com.ipn.mx.reservaciones7cm3.features.reservation.dto.ReservacionDTO;
import com.ipn.mx.reservaciones7cm3.features.reservation.repository.ReservacionRepository;
import com.ipn.mx.reservaciones7cm3.features.reservation.service.ReservacionService;
import com.ipn.mx.reservaciones7cm3.features.room.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservaciones")
@Tag(name = "Reservaciones", description = "Operaciones para la gestión de reservaciones por parte de los clientes y administradores")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReservacionController {

    @Autowired
    private ReservacionService reservacionService;

    @Autowired
    private ReservacionRepository repo;

    @Autowired
    private UsuarioRepository userRepo;

    @Operation(summary = "Crear nueva reservación", description = "Genera una reservación bloqueando las fechas solicitadas para una habitación específica usando el usuario autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservación creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o fechas no disponibles"),
            @ApiResponse(responseCode = "404", description = "La habitación solicitada no existe")
    })
    @PostMapping
    public ResponseEntity<ReservacionDTO> createReservacion(@Valid @RequestBody CreateReservacionDTO dto, Authentication authentication) {
        // 1. Obtener el username de la persona que inició sesión
        String username = authentication.getName();

        // 2. Buscar al usuario completo en la base de datos por su username
        Usuario usuarioLogueado = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado en el sistema: " + username));

        // 3. Pasar el DTO y el Usuario al método del Service
        ReservacionDTO nuevaReservacion = reservacionService.createReservacion(dto, usuarioLogueado);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReservacion);
    }

    @Operation(summary = "Consultar todas las reservaciones", description = "Obtiene el historial completo de reservaciones del sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<ReservacionDTO>> getAllReservaciones() {
        List<ReservacionDTO> reservaciones = reservacionService.getAllReservaciones();
        return ResponseEntity.ok(reservaciones);
    }

    @Operation(summary = "Cancelar una reservación", description = "Cambia el estado de una reservación activa a cancelada, liberando la habitación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservación cancelada exitosamente"),
            @ApiResponse(responseCode = "404", description = "La reservación no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarReservacion(
            @Parameter(description = "ID de la reservación a cancelar", required = true, example = "1050")
            @PathVariable Long id) {
        reservacionService.cancelarReservacion(id);
        return ResponseEntity.ok().build();
    }
}