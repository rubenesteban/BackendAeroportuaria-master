package com.backend.aeroportuaria.service;

import com.backend.aeroportuaria.entity.Aeropuerto;

import java.util.List;
import java.util.Optional;

public interface AeropuertoService {

    public List<Aeropuerto> list();

    public Optional<Aeropuerto> getOne(String id);

    public Optional<Aeropuerto> getByNombre(String nombre);

    public void  save(Aeropuerto aeropuerto);

    public void delete(String id);

    public boolean existsById(String id);

    public boolean existsByNombre(String nombre);

}
