package com.backend.aeroportuaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
Clase crada para tratar de retornar la lista de Empleados, no funciona PENDIENTE CORREGIR
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoResponse {

    private String idEmpleado;

    private String nombres;

    private String apellidos;

    private String direccion;

    private String telefono;

    private int edad;

    private String genero;

    private String cargo;

}
