package com.ipn.mx.reservaciones7cm3.core.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Reservacion")
public class Reservacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Llave foránea que lo conecta con tu tabla de Cuartos/rooms
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCuarto", nullable = false)
    private Cuarto cuarto;

    // Llave foránea que conecta la reservación con el cliente que la hizo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private BigDecimal costoTotal;

    @Column(nullable = false, length = 50)
    private String estado;


}