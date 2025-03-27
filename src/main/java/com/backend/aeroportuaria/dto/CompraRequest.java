package com.backend.aeroportuaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraRequest {

    String idAerolinea;
    String fuente;
    String destino;
    Date fechaViaje;
    String clase;

}
