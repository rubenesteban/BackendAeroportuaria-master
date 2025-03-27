package com.backend.aeroportuaria.repository;

import com.backend.aeroportuaria.entity.Aeropuerto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AeropuertoRepository extends JpaRepository<Aeropuerto, String> {

    Optional<Aeropuerto> findByNombre(String nombre);

    boolean existsByNombre(String nombre);


    /*
    Creado para tratar de encontrar el id de ciudad en la BD

    @Query("SELECT o FROM Aeropuerto o  WHERE o.ciudad = :estado") //La consulta no se hace en terminos de la base de datos sino de la entidad, por eso Aeropuerto debe iniciar en mayúscula como la clase, además el criterio a buscar debe cogincidor con un atributo de la clase.
    Optional<Aeropuerto> buscarCiudadesAeropuerto(String estado);
*/
}
