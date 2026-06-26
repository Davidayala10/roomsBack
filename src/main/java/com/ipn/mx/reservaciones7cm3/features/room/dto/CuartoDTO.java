package com.ipn.mx.reservaciones7cm3.features.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Objeto que representa la información que se muestra al recuperar una habitación")
public record CuartoDTO(

        @Schema(description = "Identificador único de la habitación", example = "1")
        Long id,

        @Schema(description = "Tipo de habitación (Ej. Sencilla, Doble, Suite)", example = "Suite")
        String tipo,

        @Schema(description = "Número asignado a la habitación en el hotel", example = "101")
        int numero,

        @Schema(description = "Precio por noche de la habitación", example = "1500.50")
        BigDecimal precio,

        @Schema(description = "Cantidad de camas disponibles en la habitación", example = "2")
        int numeroCamas,

        @Schema(description = "Indica si la habitación está disponible para ser reservada", example = "true")
        boolean disponible
) {
}