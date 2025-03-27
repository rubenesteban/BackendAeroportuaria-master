package com.backend.aeroportuaria.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AerolineaRequest {

    @NotBlank
    private String idAerolinea;

    @NotBlank
    private String nombre;

    @NotBlank
    private String codigoTresDigitos;

}
