package com.ipn.mx.reservaciones7cm3.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="Archivo")
public class Archivo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idArchivo;
    private String nombreArchivo;
    private String tipoArchivo;
    @Lob
    @Column(length = 1600000000)
    private byte[] datosArchivo;

    // Referencia opcional para vincular una foto/archivo a una habitación específica
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCuarto", nullable = true)
    private Cuarto cuarto;

}
