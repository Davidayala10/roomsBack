package com.ipn.mx.reservaciones7cm3.features.room.controller;

import com.ipn.mx.reservaciones7cm3.features.room.dto.CreateCuartoDTO;
import com.ipn.mx.reservaciones7cm3.features.room.dto.CuartoDTO;
import com.ipn.mx.reservaciones7cm3.features.room.dto.UpdateCuartoDTO;
import com.ipn.mx.reservaciones7cm3.features.room.dto.UpdateDisponibilidad;
import com.ipn.mx.reservaciones7cm3.features.room.service.CuartoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cuartos")
@RequiredArgsConstructor
@Tag(name = "Cuarto", description = "Operaciones para la gestión de las habitaciones del hotel")
public class CuartoController {
    private final CuartoService cuartoService;

    @Operation(summary = "Registrar un nuevo cuarto", description = "Crea una nueva habitación en el sistema utilizando los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Habitación creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o incompletos")
    })
    @PostMapping
    public ResponseEntity<CuartoDTO> createCuarto(@Valid @RequestBody CreateCuartoDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cuartoService.createCuarto(dto));
    }

    @Operation(summary = "Obtener todas las habitaciones", description = "Retorna una lista completa de todas las habitaciones registradas en la base de datos.")
    @ApiResponse(responseCode = "200", description = "Lista de habitaciones obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<CuartoDTO>> findAllCuartos() {
        return ResponseEntity.ok(cuartoService.readAllCuartos());
    }

    @Operation(summary = "Buscar habitación por ID", description = "Devuelve los detalles de una habitación específica basada en su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habitación encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "La habitación con el ID proporcionado no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CuartoDTO> findCuarto(
            @Parameter(description = "ID de la habitación a buscar", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(cuartoService.readById(id));
    }

    @Operation(summary = "Actualizar datos de la habitación", description = "Actualiza la información general de una habitación existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habitación actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "La habitación a actualizar no existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CuartoDTO> updateCuarto(
            @Parameter(description = "ID de la habitación a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateCuartoDTO dto) {
        return ResponseEntity.ok(cuartoService.updateCuarto(id, dto));
    }

    @Operation(summary = "Actualizar disponibilidad", description = "Modifica únicamente el estado de disponibilidad (libre/ocupada) de una habitación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidad actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Estado de disponibilidad inválido"),
            @ApiResponse(responseCode = "404", description = "La habitación no existe")
    })
    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<CuartoDTO> updateCuartoDisponibilidad(
            @Parameter(description = "ID de la habitación", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateDisponibilidad dto) {
        return ResponseEntity.ok(cuartoService.updateDisponibilidad(id, dto));
    }

    @Operation(summary = "Eliminar habitación", description = "Elimina una habitación del sistema de forma permanente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Habitación eliminada exitosamente (Sin contenido)"),
            @ApiResponse(responseCode = "404", description = "La habitación a eliminar no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuarto(
            @Parameter(description = "ID de la habitación a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        cuartoService.deleteCuarto(id);
        return ResponseEntity.noContent().build();
    }
}