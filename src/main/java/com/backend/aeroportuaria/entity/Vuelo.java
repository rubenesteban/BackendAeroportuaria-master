package com.backend.aeroportuaria.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VUELO")
public class Vuelo {

    @Id
    @NotNull
    private String idVuelo;

    private String fuente;

    private String destino;

    private Date llegada;

    private Date salida;

    private String estado;

    private String duracion;

    private String tipoVuelo;

    private int numeroParadas;

    private String clase;

    //private String aerolinea;

    //Crea relación con la tabla Aerolínea, muchos Vuelos pueden pertenecer a una Aerolínea
    //Referencia tomada de https://www.oscarblancarteblog.com/2018/12/20/relaciones-onetomany/
    @ManyToOne
    @JoinColumn(name = "ID_AEROLINEA", nullable = false) //Quita updatable = false para que pueda actualizar Aerolínea
    private Aerolinea aerolinea;

}
