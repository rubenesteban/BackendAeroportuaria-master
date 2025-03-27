package com.backend.aeroportuaria.serviceimpl;

import com.backend.aeroportuaria.entity.Empleado;
import com.backend.aeroportuaria.repository.EmpleadoRepository;
import com.backend.aeroportuaria.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    public List<Empleado> list(){
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> getOne(String id){
        return empleadoRepository.findById(id);
    }

    public Optional<Empleado> getByNombre(String nombre){
        return empleadoRepository.findByNombres(nombre);
    }

    public void  save(Empleado empleado){
        empleadoRepository.save(empleado);
    }

    public void delete(String id){
        empleadoRepository.deleteById(id);
    }

    public boolean existsById(String id){
        return empleadoRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre){
        return empleadoRepository.existsByNombres(nombre);
    }
/*

Creado para tratar de listar todos los empleados

    public List<EmpleadoResponse> buscarEmpleadoAeropuerto() {
        return empleadoRepository.buscarEmpleadoAeropuerto();
    }
*/

}
