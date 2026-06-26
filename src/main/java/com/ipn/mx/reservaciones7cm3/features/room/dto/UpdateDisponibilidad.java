package com.ipn.mx.reservaciones7cm3.features.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO utilizado para actualizar únicamente el estado de disponibilidad de una habitación.")
public record UpdateDisponibilidad(

        @Schema(description = "Nuevo estado de disponibilidad (true = disponible, false = ocupada/mantenimiento)", example = "false")
        @NotNull(message = "El status para la disponibilidad es obligatorio (Esta o no disponible (falso o verdadero))")
        Boolean disponible
) {
}