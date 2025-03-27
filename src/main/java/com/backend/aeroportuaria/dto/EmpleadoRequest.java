package com.backend.aeroportuaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoRequest {

    private String idEmpleado;

    private String nombres;

    private String apellidos;

    private String direccion;

    private String telefono;

    private int edad;

    private String genero;

    private String cargo;

    private String idAeropuerto;

}
