package com.backend.aeroportuaria.security.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

//Esta clase se utiliza en el momento que se haga un login, va a devolver esto.

public class LoginResponse {
    private String token;
    private String nombreUsuario;
    private Collection<? extends GrantedAuthority> authorities;

    public LoginResponse(String token, String nombreUsuario, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
        this.authorities = authorities;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
