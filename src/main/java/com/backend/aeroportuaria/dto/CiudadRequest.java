package com.backend.aeroportuaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CiudadRequest {

    private String id_ciudad;

    private String nombre;

    private String estado;

    private String pais;

}
