package com.backend.aeroportuaria.service;

import com.backend.aeroportuaria.entity.Empleado;

import java.util.List;
import java.util.Optional;

public interface EmpleadoService {

    public List<Empleado> list();

    public Optional<Empleado> getOne(String id);

    public Optional<Empleado> getByNombre(String nombre);

    public void  save(Empleado empleado);

    public void delete(String id);

    public boolean existsById(String id);

    public boolean existsByNombre(String nombre);
/*
Creado para tratar de listar todos los empleados

    public List<EmpleadoResponse> buscarEmpleadoAeropuerto();
 */

}
