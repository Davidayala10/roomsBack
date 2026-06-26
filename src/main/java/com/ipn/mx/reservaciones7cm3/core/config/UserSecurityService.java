package com.ipn.mx.reservaciones7cm3.core.config;

import com.ipn.mx.reservaciones7cm3.core.domain.Usuario;
import com.ipn.mx.reservaciones7cm3.features.room.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary // <--- Mantenemos la etiqueta para que Spring use este servicio sobre cualquier otro
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true) // Agregamos Transaccional por si la carga de roles es Lazy (perezosa)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Mapeamos dinámicamente los roles guardados en la BD a las autoridades de Spring Security
        // Asegúrate de que en tu BD los roles se guarden como 'ROLE_ADMIN' o 'ROLE_USER'
        List<SimpleGrantedAuthority> authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombreRol())) // Ajusta '.getNombre()' al método de tu entidad Rol
                .collect(Collectors.toList());

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword()) // Lee el hash bcrypt desde la BD
                .authorities(authorities)        // Cargamos los roles reales de la BD
                .accountLocked(false)
                .disabled(false)
                .build();
    }
}