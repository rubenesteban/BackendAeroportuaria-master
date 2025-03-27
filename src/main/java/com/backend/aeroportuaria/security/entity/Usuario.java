package com.backend.aeroportuaria.security.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

//Esta clase es para acceder a la base de datos.

@Entity
@Table(name = "USUARIOS") //No estaba
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotNull
    private String nombre;
    
    @NotNull
    @Column(unique = true) //No se puede repetir
    private String nombreUsuario;
    
    @NotNull
    private String email;
    
    @NotNull
    private String password;


    @NotNull
    @ManyToMany(fetch = FetchType.EAGER) //Si no se pone esto el sistema muestra error (fail en el método do filter failed to lazily initialize a collection of role). Un usuario puede tener varios roles y un rol puede pertenecer a varios usuarios, para implementar lo anterior se crea una tabla intermedia que va a contener dos campos, uno va a ser el id del usuario y el otro el  id del rol para relacionar cada rol con cada usuario.
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"), //usuario_rol es el nombre de la tabla, usuario_id va a ser la columna a que hace referencia la otra tabla hacia esta
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>(); //Conjunto de roles

    public Usuario() {
    }

    public Usuario(@NotNull String nombre, @NotNull String nombreUsuario, @NotNull String email, @NotNull String password) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
}
