package com.ipn.mx.reservaciones7cm3.features.archivo.service;

import com.ipn.mx.reservaciones7cm3.core.domain.Archivo;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public interface ArchivoService {
    Archivo guardarArchivoEnBaseDeDatos(MultipartFile datosArchivo) throws IOException;

    Optional<Archivo> descargarArchivo(Long id) throws FileNotFoundException;
}

