package com.backend.aeroportuaria.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CIUDAD")
public class Ciudad {

    @Id
    @NotNull
    private String id_ciudad;

    private String nombre;

    private String estado;

    private String pais;
/*
PENDIENTE CORREGIR
Si dejo lo siguiente habilitado entonces en la petición de listar ciudades se crea un bucle
Como se comentó esto entonces ahora se puede crear dos o más Aeropuertos en la misma ciudad

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "ciudad")
    private Aeropuerto aeropuerto;
*/
    public Ciudad(String id_ciudad, String nombre, String estado, String pais) {
        this.id_ciudad = id_ciudad;
        this.nombre = nombre;
        this.estado = estado;
        this.pais = pais;
    }
}
