package com.backend.aeroportuaria.security.serviceimpl;

import com.backend.aeroportuaria.security.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//Esta clase va a ser la encargada de crear la seguridad. Implementa los privilegios de cada usuario.
//Obtiene la autorización del usuario para hacer o no cosas en la aplicación, el usuario puede ver los productos pero
//el administrador puede hacer todo el CRUD

public class UserJWTServiceImpl implements UserDetails {
	
    private String nombre;
    private String nombreUsuario;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities; 

    public UserJWTServiceImpl(String nombre, String nombreUsuario, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    
    //Método que asigna los privilegios a cada usuario, si es administrador o usuario
    
    public static UserJWTServiceImpl build(Usuario usuario){
        List<GrantedAuthority> authorities = usuario.getRoles() //Lo primero que se hace es obtener los roles
        		.stream().map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name()))
        		.collect(Collectors.toList()); //Al final obtine una lista de GrantedAuthority a partir de los roles, se convierte la clase Rol en GrantedAuthority que es una clase del núcleo de seguridad de spring boot
        return new UserJWTServiceImpl(usuario.getNombre(), usuario.getNombreUsuario(), usuario.getEmail(), usuario.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password; //Devuelve el pasword
    }

    @Override
    public String getUsername() {
        return nombreUsuario; //Devuelve el nombre del usuario
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}
