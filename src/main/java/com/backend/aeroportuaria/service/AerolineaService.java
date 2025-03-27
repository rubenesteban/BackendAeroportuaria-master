package com.backend.aeroportuaria.service;

import com.backend.aeroportuaria.entity.Aerolinea;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AerolineaService {

    public List<Aerolinea> list();

    public Optional<Aerolinea> getOne(String id);

    public Optional<Aerolinea> getByNombre(String nombre);

    public void  save(Aerolinea aerolinea);

    public void delete(String id);

    public boolean existsById(String id);

    public boolean existsByNombre(String nombre);

}
