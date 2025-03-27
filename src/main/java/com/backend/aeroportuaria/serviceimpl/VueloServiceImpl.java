package com.backend.aeroportuaria.serviceimpl;

import com.backend.aeroportuaria.entity.Empleado;
import com.backend.aeroportuaria.entity.Vuelo;
import com.backend.aeroportuaria.repository.EmpleadoRepository;
import com.backend.aeroportuaria.repository.VueloRepository;
import com.backend.aeroportuaria.service.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VueloServiceImpl implements VueloService {

    @Autowired
    VueloRepository vueloRepository;

    public List<Vuelo> list(){
        return vueloRepository.findAll();
    }

    public Optional<Vuelo> getOne(String id){
        return vueloRepository.findById(id);
    }

    public void  save(Vuelo vuelo){
        vueloRepository.save(vuelo);
    }

    public void delete(String id){
        vueloRepository.deleteById(id);
    }

    public boolean existsById(String id){
        return vueloRepository.existsById(id);
    }

    public boolean existsByFuente(String fuente){ return vueloRepository.existsByFuente(fuente);}

    public boolean existsByDestino(String destino){ return vueloRepository.existsByDestino(destino);}

    public boolean existsByClase(String clase){return vueloRepository.existsByClase(clase);}

    public List<String> validarDisponibilidad(String idAerolinea, String fuente, String destino, String clase){
        return vueloRepository.validarDisponibilidad(idAerolinea, fuente, destino, clase);
    }

}
