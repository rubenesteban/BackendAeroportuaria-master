package com.backend.aeroportuaria.service;

import com.backend.aeroportuaria.entity.Pasajero;

import java.util.List;
import java.util.Optional;

public interface PasajeroService {

    public List<Pasajero> list();

    public Optional<Pasajero> getOne(String id);

    public Optional<Pasajero> getByNombre(String nombre);

    public void  save(Pasajero pasajero);

    public void delete(String id);

    public boolean existsById(String id);

    public boolean existsByNombre(String nombre);

}
