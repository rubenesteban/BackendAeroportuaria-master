package com.backend.aeroportuaria.serviceimpl;

import com.backend.aeroportuaria.entity.Tarifa;
import com.backend.aeroportuaria.repository.TarifaRepository;
import com.backend.aeroportuaria.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TarifaServiceImpl implements TarifaService {

    @Autowired
    TarifaRepository tarifaRepository;

    public List<Tarifa> list(){
        return tarifaRepository.findAll();
    }

    public Optional<Tarifa> getOne(int id){
        return tarifaRepository.findById(id);
    }

    public void  save(Tarifa tarifa){
        tarifaRepository.save(tarifa);
    }

    public void delete(int id){
        tarifaRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return tarifaRepository.existsById(id);
    }

    public float buscarTarifa(String fuente, String destino){return tarifaRepository.buscarTarifa(fuente, destino);}

    public List<Float> listaTarifas(String fuente, String destino){return tarifaRepository.listaTarifas(fuente, destino);}

}
