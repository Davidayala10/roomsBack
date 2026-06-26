package com.ipn.mx.reservaciones7cm3.features.reservation.service;

import com.ipn.mx.reservaciones7cm3.core.domain.Usuario;
import com.ipn.mx.reservaciones7cm3.features.reservation.dto.CreateReservacionDTO;
import com.ipn.mx.reservaciones7cm3.features.reservation.dto.ReservacionDTO;
import java.util.List;

public interface ReservacionService {
    // CORREGIDO: Añadimos 'Usuario usuario' como segundo parámetro
    ReservacionDTO createReservacion(CreateReservacionDTO dto, Usuario usuario);
    List<ReservacionDTO> getAllReservaciones();
    void cancelarReservacion(Long id);
}