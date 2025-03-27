package com.backend.aeroportuaria.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

//Se utiliza para mandar un nuevo usuario

public class UsuarioRequest {
	
    @NotBlank //No puede ser nulo, no puede ser una cadena de longitud 0 ni puede tener uno o más espacios en blanco
    private String nombre;
    
    @NotBlank
    private String nombreUsuario;
    
    @Email
    private String email;
    
    @NotBlank
    private String password;
    
    private Set<String> roles = new HashSet<>(); //Se utiliza por que la API RES utiliza objetos tipo json y es mejor que sea en cadenas para que el tráfico sea más ágil. El usuario tiene varios atributos pero por cada petición sólo se necesita el nombre de usuario y la contraseña, se se manda todos los datos (atributos del  usuario) el tráfico de datos entre el cliente y el servidor van a ser mucho mayores, con esto se agiliza. Es como crear el uaurio sólo con el nombre y la contraseña y cuando ingrese actualiza los demás datos   

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
