package com.backend.aeroportuaria.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMPLEADO")
public class Empleado {

    @Id
    @NotNull
    private String idEmpleado;

    private String nombres;

    private String apellidos;

    private String direccion;

    private String telefono;

    private int edad;

    private String genero;

    private String cargo;

    //Crea relación con la tabla Aeropuerto, muchos Empleados pueden pertenecer a un Aeropuerto
    //Referencia tomada de https://www.oscarblancarteblog.com/2018/12/20/relaciones-onetomany/
    @ManyToOne
    @JoinColumn(name = "ID_AEROPUERTO", nullable = false) //Si se pone updatable = false entonces no se podra actualizar el ID_AEROPUERTO en la entidad Empleados, la quito simulando que un Empleado puede pasar de trabajar de un Aeropuerto a otro.
    private Aeropuerto aeropuerto;
}
