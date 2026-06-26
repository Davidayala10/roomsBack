package com.ipn.mx.reservaciones7cm3.features.reservation.service.impl;

import com.ipn.mx.reservaciones7cm3.core.domain.Reservacion;
import com.ipn.mx.reservaciones7cm3.core.domain.Usuario;
import com.ipn.mx.reservaciones7cm3.core.domain.Cuarto; // O la clase de tu entidad de habitaciones (p.ej. Cuarto)
import com.ipn.mx.reservaciones7cm3.features.reservation.dto.CreateReservacionDTO;
import com.ipn.mx.reservaciones7cm3.features.reservation.dto.ReservacionDTO;
import com.ipn.mx.reservaciones7cm3.features.reservation.repository.ReservacionRepository;
import com.ipn.mx.reservaciones7cm3.features.reservation.service.ReservacionService;
import com.ipn.mx.reservaciones7cm3.features.room.repository.CuartoRepository; // Revisa si se llama CuartoRepository o RoomRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservacionServiceImpl implements ReservacionService {

    @Autowired
    private ReservacionRepository reservacionRepository;

    @Autowired
    private CuartoRepository cuartoRepository; // <-- AGREGA ESTA INYECCIÓN (Ajusta el nombre si se llama RoomRepository)

    @Override
    public ReservacionDTO createReservacion(CreateReservacionDTO dto, Usuario usuario) {
        Reservacion reservacion = new Reservacion();

        // 1. Mapear fechas desde el record
        reservacion.setFechaInicio(dto.fechaInicio());
        reservacion.setFechaFin(dto.fechaFin());
        reservacion.setEstado("CONFIRMADA");

        // 2. Asignar el usuario que viene de Spring Security
        reservacion.setUsuario(usuario);

        // 3. ¡LA SOLUCIÓN! Buscar el cuarto usando el idCuarto() del DTO y asignarlo a la entidad
        // (Nota: Si tu método en Reservacion se llama setRoom, cambia setCuarto por setRoom)
        Cuarto cuarto = cuartoRepository.findById(dto.idCuarto())
                .orElseThrow(() -> new RuntimeException("La habitación no existe: " + dto.idCuarto()));
        reservacion.setCuarto(cuarto);

        // 4. Asignar el costo total fijo para satisfacer la restricción NOT NULL
        reservacion.setCostoTotal(new java.math.BigDecimal("1300.00"));

        // 5. Guardar en MySQL
        Reservacion guardada = reservacionRepository.save(reservacion);

        // 6. Retornar el DTO de respuesta
        return new ReservacionDTO(
                guardada.getId(),
                dto.idCuarto(),
                guardada.getFechaInicio(),
                guardada.getFechaFin(),
                guardada.getCostoTotal(),
                guardada.getEstado()
        );
    }

    @Override
    public List<ReservacionDTO> getAllReservaciones() {
        return reservacionRepository.findAll().stream()
                .map(r -> new ReservacionDTO(
                        r.getId(),
                        r.getCuarto() != null ? r.getCuarto().getId() : null,
                        r.getFechaInicio(),
                        r.getFechaFin(),
                        r.getCostoTotal(),
                        r.getEstado()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void cancelarReservacion(Long id) {
        Reservacion reservacion = reservacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservación no encontrada"));
        reservacion.setEstado("CANCELADA");
        reservacionRepository.save(reservacion);
    }
}