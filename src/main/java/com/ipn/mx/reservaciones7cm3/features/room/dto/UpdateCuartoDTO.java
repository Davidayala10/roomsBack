package com.ipn.mx.reservaciones7cm3.features.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "DTO utilizado para actualizar la información completa de una habitación existente.")
public record UpdateCuartoDTO(

        @Schema(description = "Nuevo tipo de habitación (Ej. Sencilla, Doble, Suite)", example = "Doble Superior")
        @NotBlank(message = "El tipo de cuarto es obligatorio")
        @Size(min = 4, max = 50, message = "El tipo debe estar entre 4 y 50 caracteres")
        String tipo,

        @Schema(description = "Nuevo número asignado a la habitación en el hotel", example = "205")
        @NotNull(message = "El numero es obligatorio")
        @Positive(message = "El numero asignado al cuarto debe ser un valor positivo")
        Integer numero,

        @Schema(description = "Nuevo precio por noche de la habitación", example = "1850.00")
        @NotNull(message = "El precio es obligatorio")
        @Digits(integer = 8, fraction = 2, message = "El precio tiene un formato invalido (maximo 8 enteros y 2 decimales)")
        BigDecimal precio,

        @Schema(description = "Nueva cantidad de camas que tiene la habitación", example = "3")
        @Min(value = 1, message = "El cuarto debe tener al menos una cama")
        int numeroCamas
) {
}