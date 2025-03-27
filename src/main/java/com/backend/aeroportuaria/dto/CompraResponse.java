package com.backend.aeroportuaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraResponse {

    String idAerolinea;
    String fuente;
    String destino;
    Date fechaViaje;
    String clase;
    float tarifa;

}
