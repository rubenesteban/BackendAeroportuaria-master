package com.backend.aeroportuaria.security.serviceimpl;

import com.backend.aeroportuaria.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.aeroportuaria.security.entity.Usuario;

//Esta clase lo que hace es implementar el método loadUserByUsername de la clase UserDetailsService

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException { //Si no lo encuentra lanza una excepción
        Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario).get(); //Se debe poner .get porque usuario es de la clase Usuario y getByNombreUsuario responde un optional 
        return UserJWTServiceImpl.build(usuario); //build construye el usuario
    }
}
