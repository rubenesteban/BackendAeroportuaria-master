package com.backend.aeroportuaria.serviceimpl;

import com.backend.aeroportuaria.entity.Ciudad;
import com.backend.aeroportuaria.repository.CiudadRepository;
import com.backend.aeroportuaria.service.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CiudadServiceImpl implements CiudadService {

    @Autowired
    CiudadRepository ciudadRepository;

    public List<Ciudad> list(){
        return ciudadRepository.findAll();
    }

    public Optional<Ciudad> getOne(String id){
        return ciudadRepository.findById(id);
    }

    public Optional<Ciudad> getByNombre(String nombre){
        return ciudadRepository.findByNombre(nombre);
    }

    public void  save(Ciudad ciudad){
        ciudadRepository.save(ciudad);
    }

    public void delete(String id){
        ciudadRepository.deleteById(id);
    }

    public boolean existsById(String id){
        return ciudadRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre){
        return ciudadRepository.existsByNombre(nombre);
    }

}
