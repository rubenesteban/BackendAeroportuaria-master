package com.backend.aeroportuaria.repository;

import com.backend.aeroportuaria.entity.Empleado;
import com.backend.aeroportuaria.entity.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasajeroRepository extends JpaRepository<Pasajero, String> {

    Optional<Pasajero> findByNombres(String nombre);

    boolean existsByNombres(String nombre);

}
