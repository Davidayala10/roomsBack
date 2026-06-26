package com.ipn.mx.reservaciones7cm3.features.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "DTO para solicitar la creación de una nueva reservación")
public record CreateReservacionDTO(

        @Schema(description = "ID de la habitación que se desea reservar", example = "1")
        @NotNull(message = "El ID de la habitación es obligatorio")
        Long idCuarto,

        @Schema(description = "Fecha de inicio de la reservación (Llegada)", example = "2026-07-15")
        @NotNull(message = "La fecha de inicio es obligatoria")
        @FutureOrPresent(message = "La fecha de inicio no puede ser en el pasado")
        LocalDate fechaInicio,

        @Schema(description = "Fecha de fin de la reservación (Salida)", example = "2026-07-20")
        @NotNull(message = "La fecha de fin es obligatoria")
        @FutureOrPresent(message = "La fecha de fin no puede ser en el pasado")
        LocalDate fechaFin
) {
}