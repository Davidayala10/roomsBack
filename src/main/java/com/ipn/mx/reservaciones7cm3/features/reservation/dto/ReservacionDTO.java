package com.ipn.mx.reservaciones7cm3.features.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "DTO que representa los datos de una reservación confirmada")
public record ReservacionDTO(

        @Schema(description = "Identificador único de la reservación", example = "1050")
        Long id,

        @Schema(description = "ID de la habitación reservada", example = "1")
        Long idCuarto,

        @Schema(description = "Fecha de llegada", example = "2026-07-15")
        LocalDate fechaInicio,

        @Schema(description = "Fecha de salida", example = "2026-07-20")
        LocalDate fechaFin,

        @Schema(description = "Costo total calculado de la reservación", example = "7500.00")
        BigDecimal costoTotal,

        @Schema(description = "Estado actual de la reservación (Ej. Confirmada, Cancelada)", example = "Confirmada")
        String estado
) {
}