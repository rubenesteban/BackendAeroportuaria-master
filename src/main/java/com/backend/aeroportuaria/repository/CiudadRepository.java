package com.backend.aeroportuaria.repository;

import com.backend.aeroportuaria.entity.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CiudadRepository extends JpaRepository<Ciudad, String> {

    Optional<Ciudad> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}
