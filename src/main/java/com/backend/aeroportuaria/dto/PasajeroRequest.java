package com.backend.aeroportuaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasajeroRequest {

    private String idPasajero;

    private String numeroPasaporte;

    private String nombres;

    private String apellidos;

    private String genero;

    private String direccion;

    private String telefono;

    private int edad;

    private String idVuelo;

}
