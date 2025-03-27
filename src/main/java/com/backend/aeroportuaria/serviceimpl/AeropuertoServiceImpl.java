package com.backend.aeroportuaria.serviceimpl;

import com.backend.aeroportuaria.entity.Aeropuerto;
import com.backend.aeroportuaria.repository.AeropuertoRepository;
import com.backend.aeroportuaria.service.AeropuertoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AeropuertoServiceImpl implements AeropuertoService {

    @Autowired
    AeropuertoRepository aeropuertoRepository;

    public List<Aeropuerto> list(){
        return aeropuertoRepository.findAll();
    }

    public Optional<Aeropuerto> getOne(String id){
        return aeropuertoRepository.findById(id);
    }

    public Optional<Aeropuerto> getByNombre(String nombre){
        return aeropuertoRepository.findByNombre(nombre);
    }

    public void  save(Aeropuerto aeropuerto){
        aeropuertoRepository.save(aeropuerto);
    }

    public void delete(String id){
        aeropuertoRepository.deleteById(id);
    }

    public boolean existsById(String id){
        return aeropuertoRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre){
        return aeropuertoRepository.existsByNombre(nombre);
    }
/*
 Creado para tratar de encontrar el id de ciudad en la BD

    public List<Aeropuerto> buscarCiudadesAeropuerto(String idCiudad){
        return aeropuertoRepository.buscarCiudadesAeropuerto(idCiudad);
    }
*/
}
