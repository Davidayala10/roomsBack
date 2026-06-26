package com.ipn.mx.reservaciones7cm3.features.reservation.repository;

import com.ipn.mx.reservaciones7cm3.core.domain.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {

    // Busca si existen reservaciones activas (que no estén CANCELADAS) que se traslapen con las fechas solicitadas
    @Query("SELECT COUNT(r) > 0 FROM Reservacion r " +
            "WHERE r.cuarto.id = :idCuarto " +
            "AND r.estado != 'CANCELADA' " +
            "AND (:fechaInicio < r.fechaFin AND :fechaFin > r.fechaInicio)")
    boolean existsByCuartoAndFechasOverlapping(
            @Param("idCuarto") Long idCuarto,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );
}