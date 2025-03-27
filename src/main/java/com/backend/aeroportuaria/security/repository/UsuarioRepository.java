package com.backend.aeroportuaria.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.aeroportuaria.security.entity.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario); //Busca usuario por el nombre
    boolean existsByNombreUsuario(String nombreUsuario); //Verifica si el usuario existe por nombre
    boolean existsByEmail(String email); //Verifica si el usuario existe por email

}
