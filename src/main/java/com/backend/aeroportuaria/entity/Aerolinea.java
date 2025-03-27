package com.backend.aeroportuaria.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "AEROLINEA")
public class Aerolinea {

    @Id
    @NotNull
    private String idAerolinea;

    private String nombre;

    private String codigoTresDigitos;
/*
    //Crea relación con la tabla Vuelos, una Aerolinea puede tener muchos Vuelos
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aerolinea")
    private List<Vuelo> vuelos;
*/
    public Aerolinea(@NotNull String idAerolinea, String nombre, String codigoTresDigitos) {
        this.idAerolinea = idAerolinea;
        this.nombre = nombre;
        this.codigoTresDigitos = codigoTresDigitos;
    }

}
