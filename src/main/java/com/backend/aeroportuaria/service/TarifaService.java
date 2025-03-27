package com.backend.aeroportuaria.service;

import com.backend.aeroportuaria.entity.Tarifa;

import java.util.List;
import java.util.Optional;

public interface TarifaService {

    public List<Tarifa> list();

    public Optional<Tarifa> getOne(int id);

    public void  save(Tarifa tarifa);

    public void delete(int id);

    public boolean existsById(int id);

    public float buscarTarifa(String fuente, String destino);

    public List<Float> listaTarifas(String fuente, String destino);

}
