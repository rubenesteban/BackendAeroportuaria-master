package com.backend.aeroportuaria.controller;

import com.backend.aeroportuaria.dto.EmpleadoRequest;
import com.backend.aeroportuaria.dto.ResponseCode;
import com.backend.aeroportuaria.entity.Aeropuerto;
import com.backend.aeroportuaria.entity.Empleado;
import com.backend.aeroportuaria.service.EmpleadoService;
import com.backend.aeroportuaria.serviceimpl.AeropuertoServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleado")
@CrossOrigin("*")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    AeropuertoServiceImpl aeropuertoService;

    @GetMapping("/lista")
    public ResponseEntity<List<Empleado>> list(){
        List<Empleado> list = empleadoService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    /*
    PENDIENTE CORREGIR
    Al listar los Empleados aparece error, tal vez sea por la relación que tiene con Aeropuerto
    Creado para tratar de listar todos los empleados

    @GetMapping("/lista")
    public ResponseEntity<List<Empleado>> list(){
        List<Empleado> list = empleadoService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<EmpleadoRequest>> buscarEmpleadoAeropuerto(){
        List<EmpleadoResponse> list = empleadoService.buscarEmpleadoAeropuerto();
        return new ResponseEntity(list, HttpStatus.OK);
    }
*/
    @GetMapping("/detail/{id}")
    public ResponseEntity<Empleado> getById(@PathVariable("id") String id){
        if(!empleadoService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Empleado empleado = empleadoService.getOne(id).get();
        return new ResponseEntity(empleado, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Empleado> getByNombre(@PathVariable("nombre") String nombre){
        if(!empleadoService.existsByNombre(nombre))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Empleado empleado = empleadoService.getByNombre(nombre).get();
        return new ResponseEntity(empleado, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')") //Roles autorizados para acceder a la petición de este método
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody EmpleadoRequest empleadoRequest){
        Integer edad = empleadoRequest.getEdad();
        if(StringUtils.isBlank(empleadoRequest.getIdEmpleado()) || StringUtils.isBlank(empleadoRequest.getNombres()) || StringUtils.isBlank(empleadoRequest.getApellidos()) || StringUtils.isBlank(empleadoRequest.getDireccion()) || StringUtils.isBlank(empleadoRequest.getTelefono()) || edad < 0 || edad == null || StringUtils.isBlank(empleadoRequest.getGenero()) || StringUtils.isBlank(empleadoRequest.getCargo()) || StringUtils.isBlank(empleadoRequest.getIdAeropuerto()))
            return new ResponseEntity(new ResponseCode(16, "Datos incompletos o negativos"), HttpStatus.BAD_REQUEST);
        if (!aeropuertoService.existsById(empleadoRequest.getIdAeropuerto())){
            return new ResponseEntity(new ResponseCode(19, "No se encontró el Aeropuerto ingresado"), HttpStatus.BAD_REQUEST);
        }

        Aeropuerto aeropuertoBd = aeropuertoService.getOne(empleadoRequest.getIdAeropuerto()).get();

        Empleado empleado = new Empleado(empleadoRequest.getIdEmpleado(), empleadoRequest.getNombres(), empleadoRequest.getApellidos(), empleadoRequest.getDireccion(), empleadoRequest.getTelefono(), empleadoRequest.getEdad(), empleadoRequest.getGenero(), empleadoRequest.getCargo(), aeropuertoBd);
        empleadoService.save(empleado);
        return new ResponseEntity(new ResponseCode(6, "Creado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")String id, @RequestBody EmpleadoRequest empleadoRequest){
        Integer edad = empleadoRequest.getEdad();
        if(StringUtils.isBlank(empleadoRequest.getIdEmpleado()) || StringUtils.isBlank(empleadoRequest.getNombres()) || StringUtils.isBlank(empleadoRequest.getApellidos()) || StringUtils.isBlank(empleadoRequest.getDireccion()) || StringUtils.isBlank(empleadoRequest.getTelefono()) || edad < 0 || edad == null || StringUtils.isBlank(empleadoRequest.getGenero()) || StringUtils.isBlank(empleadoRequest.getCargo()) || StringUtils.isBlank(empleadoRequest.getIdAeropuerto()))
            return new ResponseEntity(new ResponseCode(16, "Datos incompletos o negativos"), HttpStatus.BAD_REQUEST);
        if(!empleadoService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);

        Empleado empleado = empleadoService.getOne(id).get();
        empleado.setNombres(empleadoRequest.getNombres());
        empleado.setApellidos(empleadoRequest.getApellidos());
        empleado.setDireccion(empleadoRequest.getDireccion());
        empleado.setTelefono(empleadoRequest.getTelefono());
        empleado.setEdad(empleadoRequest.getEdad());
        empleado.setGenero(empleadoRequest.getGenero());
        empleado.setCargo(empleadoRequest.getCargo());

        Aeropuerto aeropuertoBd = aeropuertoService.getOne(empleadoRequest.getIdAeropuerto()).get();

        empleado.setAeropuerto(aeropuertoBd);

        empleadoService.save(empleado);
        return new ResponseEntity(new ResponseCode(8, "Actualizado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")String id){
        if(!empleadoService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        empleadoService.delete(id);
        return new ResponseEntity(new ResponseCode(9, "Eliminado exitosamente"), HttpStatus.OK);
    }

}
