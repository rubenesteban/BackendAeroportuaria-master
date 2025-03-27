package com.backend.aeroportuaria.controller;

import com.backend.aeroportuaria.dto.PasajeroRequest;
import com.backend.aeroportuaria.dto.ResponseCode;
import com.backend.aeroportuaria.entity.Empleado;
import com.backend.aeroportuaria.entity.Pasajero;
import com.backend.aeroportuaria.entity.Vuelo;
import com.backend.aeroportuaria.service.PasajeroService;
import com.backend.aeroportuaria.service.VueloService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pasajero")
@CrossOrigin("*")
public class PasajeroController {

    @Autowired
    PasajeroService pasajeroService;

    @Autowired
    VueloService vueloService;

    @GetMapping("/lista")
    public ResponseEntity<List<Pasajero>> list(){
        List<Pasajero> list = pasajeroService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Pasajero> getById(@PathVariable("id") String id){
        if(!pasajeroService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Pasajero pasajero = pasajeroService.getOne(id).get();
        return new ResponseEntity(pasajero, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Empleado> getByNombre(@PathVariable("nombre") String nombre){
        if(!pasajeroService.existsByNombre(nombre))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Pasajero pasajero = pasajeroService.getByNombre(nombre).get();
        return new ResponseEntity(pasajero, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')") //Roles autorizados para acceder a la petición de este método
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PasajeroRequest pasajeroRequest){
        Integer edad = pasajeroRequest.getEdad();
        if(StringUtils.isBlank(pasajeroRequest.getIdPasajero()) || StringUtils.isBlank(pasajeroRequest.getNumeroPasaporte()) || StringUtils.isBlank(pasajeroRequest.getNombres()) || StringUtils.isBlank(pasajeroRequest.getApellidos()) || StringUtils.isBlank(pasajeroRequest.getGenero()) || StringUtils.isBlank(pasajeroRequest.getDireccion()) || StringUtils.isBlank(pasajeroRequest.getTelefono()) || edad < 0 || edad == null ||StringUtils.isBlank(pasajeroRequest.getIdVuelo()))
            return new ResponseEntity(new ResponseCode(16, "Datos incompletos o negativos"), HttpStatus.BAD_REQUEST);
        if (!vueloService.existsById(pasajeroRequest.getIdVuelo())){
            return new ResponseEntity(new ResponseCode(20, "No se encontró el Vuelo ingresado"), HttpStatus.BAD_REQUEST);
        }

        Vuelo vueloBd = vueloService.getOne(pasajeroRequest.getIdVuelo()).get();

        Pasajero pasajero = new Pasajero(pasajeroRequest.getIdPasajero(), pasajeroRequest.getNumeroPasaporte(), pasajeroRequest.getNombres(), pasajeroRequest.getApellidos(), pasajeroRequest.getGenero(), pasajeroRequest.getDireccion(), pasajeroRequest.getTelefono(), pasajeroRequest.getEdad(), vueloBd);
        pasajeroService.save(pasajero);
        return new ResponseEntity(new ResponseCode(6, "Creado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')OR hasRole('EMPLEADO')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")String id, @RequestBody PasajeroRequest pasajeroRequest){
        Integer edad = pasajeroRequest.getEdad();
        if(StringUtils.isBlank(pasajeroRequest.getIdPasajero()) || StringUtils.isBlank(pasajeroRequest.getNumeroPasaporte()) || StringUtils.isBlank(pasajeroRequest.getNombres()) || StringUtils.isBlank(pasajeroRequest.getApellidos()) || StringUtils.isBlank(pasajeroRequest.getGenero()) || StringUtils.isBlank(pasajeroRequest.getDireccion()) || StringUtils.isBlank(pasajeroRequest.getTelefono()) || edad < 0 || edad == null ||StringUtils.isBlank(pasajeroRequest.getIdVuelo()))
            return new ResponseEntity(new ResponseCode(16, "Datos incompletos o negativos"), HttpStatus.BAD_REQUEST);
        if(!pasajeroService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);

        Pasajero pasajero = pasajeroService.getOne(id).get();
        pasajero.setNumeroPasaporte(pasajeroRequest.getNumeroPasaporte());
        pasajero.setNombres(pasajeroRequest.getNombres());
        pasajero.setApellidos(pasajeroRequest.getApellidos());
        pasajero.setGenero(pasajeroRequest.getGenero());
        pasajero.setDireccion(pasajeroRequest.getDireccion());
        pasajero.setTelefono(pasajeroRequest.getTelefono());
        pasajero.setEdad(pasajeroRequest.getEdad());

        Vuelo vueloBd = vueloService.getOne(pasajeroRequest.getIdVuelo()).get();

        pasajero.setVuelo(vueloBd);

        pasajeroService.save(pasajero);
        return new ResponseEntity(new ResponseCode(8, "Actualizado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")String id){
        if(!pasajeroService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        pasajeroService.delete(id);
        return new ResponseEntity(new ResponseCode(9, "Eliminado exitosamente"), HttpStatus.OK);
    }

}
