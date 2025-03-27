package com.backend.aeroportuaria.security.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.backend.aeroportuaria.security.enums.RolNombre;

@Entity
@Table(name = "ROLES") //No estaba
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotNull
    @Enumerated(EnumType.STRING) //Especifica que es un tipo cadena porque sino la base de datos por defecto crearía un ordinal, un número
    private RolNombre rolNombre;

    public Rol() {
    }

    public Rol(@NotNull RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RolNombre getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }
}
