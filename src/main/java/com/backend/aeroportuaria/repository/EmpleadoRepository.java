package com.backend.aeroportuaria.repository;

import com.backend.aeroportuaria.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

    Optional<Empleado> findByNombres(String nombre);

    boolean existsByNombres(String nombre);
/*

Creado para tratar de listar todos los empleados

    @Query("SELECT idEmpleado, nombres, apellidos, direccion, telefono, edad, genero, cargo FROM Empleado") //La consulta no se hace en terminos de la base de datos sino de la entidad, por eso Aeropuerto debe iniciar en mayúscula como la clase, además el criterio a buscar debe cogincidor con un atributo de la clase.
    List<EmpleadoResponse> buscarEmpleadoAeropuerto();


    @Query(value = "SELECT id_empleado, nombres, apellidos, direccion, telefono, edad, genero, cargo FROM empleado", nativeQuery = true) //La consulta no se hace en terminos de la base de datos sino de la entidad, por eso Aeropuerto debe iniciar en mayúscula como la clase, además el criterio a buscar debe cogincidor con un atributo de la clase.
    List<EmpleadoResponse> buscarEmpleadoAeropuerto();
*/

}
