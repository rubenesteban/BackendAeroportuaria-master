package com.backend.aeroportuaria.serviceimpl;

import com.backend.aeroportuaria.entity.Pasajero;
import com.backend.aeroportuaria.repository.PasajeroRepository;
import com.backend.aeroportuaria.service.PasajeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PasajeroServiceImpl implements PasajeroService {

    @Autowired
    PasajeroRepository pasajeroRepository;

    public List<Pasajero> list(){
        return pasajeroRepository.findAll();
    }

    public Optional<Pasajero> getOne(String id){
        return pasajeroRepository.findById(id);
    }

    public Optional<Pasajero> getByNombre(String nombre){
        return pasajeroRepository.findByNombres(nombre);
    }

    public void  save(Pasajero pasajero){
        pasajeroRepository.save(pasajero);
    }

    public void delete(String id){
        pasajeroRepository.deleteById(id);
    }

    public boolean existsById(String id){
        return pasajeroRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre){
        return pasajeroRepository.existsByNombres(nombre);
    }

}
