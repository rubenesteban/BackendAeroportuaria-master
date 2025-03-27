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
@Table(name = "PASAJERO")
public class Pasajero {

    @Id
    @NotNull
    private String idPasajero;

    private String numeroPasaporte;

    private String nombres;

    private String apellidos;

    private String genero;

    private String direccion;

    private String telefono;

    private int edad;

    //Crea relación con la tabla Vuelo, muchos Pasajeros pueden viajar en un Vuelo
    //Referencia tomada de https://www.oscarblancarteblog.com/2018/12/20/relaciones-onetomany/
    @ManyToOne
    @JoinColumn(name = "ID_VUELO", nullable = false) //Si se pone updatable = false entonces no se podra actualizar el ID_AEROPUERTO en la entidad Empleados, la quito simulando que un Empleado puede pasar de trabajar de un Aeropuerto a otro.
    private Vuelo vuelo;

}
