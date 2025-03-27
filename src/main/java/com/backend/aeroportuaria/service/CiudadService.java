package com.backend.aeroportuaria.service;

import com.backend.aeroportuaria.dto.CiudadRequest;
import com.backend.aeroportuaria.entity.Ciudad;

import java.util.List;
import java.util.Optional;

public interface CiudadService {

    public List<Ciudad> list();

    public Optional<Ciudad> getOne(String id);

    public Optional<Ciudad> getByNombre(String nombre);

    public void  save(Ciudad ciudad);

    public void delete(String id);

    public boolean existsById(String id);

    public boolean existsByNombre(String nombre);

}
