package com.ipn.mx.reservaciones7cm3.features.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "DTO utilizado para la creación de una nueva habitación. Incluye validaciones para asegurar la integridad de los datos.")
public record CreateCuartoDTO(

        @Schema(description = "Tipo de habitación (Ej. Sencilla, Doble, Suite)", example = "Suite Presidencial")
        @NotBlank(message = "El tipo de cuarto es obligatorio")
        @Size(min = 4, max = 50, message = "El tipo debe estar entre 4 y 50 caracteres")
        String tipo,

        @Schema(description = "Número único asignado a la habitación en el hotel", example = "105")
        @NotNull(message = "El numero es obligatorio")
        @Positive(message = "El numero asignado al cuarto debe ser un valor positivo")
        Integer numero,

        @Schema(description = "Precio por noche de la habitación", example = "2500.00")
        @NotNull(message = "El precio es obligatorio")
        @Digits(integer = 8, fraction = 2, message = "El precio tiene un formato invalido (maximo 8 enteros y 2 decimales)")
        BigDecimal precio,

        @Schema(description = "Cantidad de camas que tiene la habitación", example = "2")
        @Min(value = 1, message = "El cuarto debe tener al menos una cama")
        int numeroCamas
) {
}