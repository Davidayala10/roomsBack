package com.ipn.mx.reservaciones7cm3.features.room.service;

import com.ipn.mx.reservaciones7cm3.features.room.dto.CreateCuartoDTO;
import com.ipn.mx.reservaciones7cm3.features.room.dto.CuartoDTO;
import com.ipn.mx.reservaciones7cm3.features.room.dto.UpdateCuartoDTO;
import com.ipn.mx.reservaciones7cm3.features.room.dto.UpdateDisponibilidad;

import java.util.List;

public interface CuartoService {
    CuartoDTO createCuarto(CreateCuartoDTO dto);
    List<CuartoDTO> readAllCuartos();
    CuartoDTO readById(Long id);
    CuartoDTO updateCuarto(Long id, UpdateCuartoDTO dto);

    /**
     *
     * @param id -> Identificador del cuarto
     * @param dto -> Objeto que contiene el nuevo estatus de la disponibilidad
     * @return (@Link CuartoDTO) actualizado
     */
    CuartoDTO updateDisponibilidad(Long id, UpdateDisponibilidad dto);
    void deleteCuarto(Long id);
}
