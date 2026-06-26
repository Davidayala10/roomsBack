package com.ipn.mx.reservaciones7cm3.features.room.repository;

import com.ipn.mx.reservaciones7cm3.core.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
    Optional <Usuario> findByUsername(String username);
}
