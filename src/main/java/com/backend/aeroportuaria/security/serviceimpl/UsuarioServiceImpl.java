package com.backend.aeroportuaria.security.serviceimpl;

import com.backend.aeroportuaria.security.entity.Usuario;
import com.backend.aeroportuaria.security.repository.UsuarioRepository;
import com.backend.aeroportuaria.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional //Es para mantener la coherencia en la base de datos, si falla una operación se hace un roll back y se vuelve al estado anterior
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){ //Método para obtener los datos de usuario por nombre
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){ //Método para saber si existe usuario por su nombre
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public boolean existsByEmail(String email){ //Método para saber si existe usuario por su email
        return usuarioRepository.existsByEmail(email);
    }

    public void save(Usuario usuario){ //Guarda usuario
        usuarioRepository.save(usuario);
    }
}
