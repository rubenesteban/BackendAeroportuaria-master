package com.backend.aeroportuaria.security.service;

import com.backend.aeroportuaria.security.entity.Usuario;

import java.util.Optional;

public interface UsuarioService {

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario);

    public boolean existsByNombreUsuario(String nombreUsuario);

    public boolean existsByEmail(String email);

    public void save(Usuario usuario);
}
