package com.backend.aeroportuaria.service;

import com.backend.aeroportuaria.entity.Boleto;

import java.util.List;
import java.util.Optional;

public interface BoletoService {

    public List<Boleto> list();

    public Optional<Boleto> getOne(String id);

    public void  save(Boleto boleto);

    public void delete(String id);

    public boolean existsById(String id);

}
