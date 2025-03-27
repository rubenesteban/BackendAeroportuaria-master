package com.backend.aeroportuaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VueloRequest {

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

    private String idAerolinea;

}
