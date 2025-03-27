package com.backend.aeroportuaria.controller;

import com.backend.aeroportuaria.dto.ResponseCode;
import com.backend.aeroportuaria.dto.TarifaRequest;
import com.backend.aeroportuaria.entity.Tarifa;
import com.backend.aeroportuaria.service.TarifaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarifa")
@CrossOrigin("*")
public class TarifaController {

    @Autowired
    TarifaService tarifaService;

    @GetMapping("/lista")
    public ResponseEntity<List<Tarifa>> list(){
        List<Tarifa> list = tarifaService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Tarifa> getById(@PathVariable("id") int id){
        if(!tarifaService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Tarifa tarifa = tarifaService.getOne(id).get(); //Como getOne trae un opcional entonces debe poner get
        return new ResponseEntity(tarifa, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')") //Roles autorizados para acceder a la petición de este método
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TarifaRequest tarifaRequest){
        if(StringUtils.isBlank(tarifaRequest.getFuente()) || StringUtils.isBlank(tarifaRequest.getDestino()) || tarifaRequest.getTarifa() < 0 || tarifaRequest.getTarifa() == null)
            return new ResponseEntity(new ResponseCode(16, "Datos incompletos o negativos"), HttpStatus.BAD_REQUEST);

        Tarifa tarifa = new Tarifa(tarifaRequest.getFuente(), tarifaRequest.getDestino(), tarifaRequest.getTarifa());
        tarifaService.save(tarifa);
        return new ResponseEntity(new ResponseCode(6, "Creado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody TarifaRequest tarifaRequest){
        if(StringUtils.isBlank(tarifaRequest.getFuente()) || StringUtils.isBlank(tarifaRequest.getDestino()) || tarifaRequest.getTarifa() < 0 || tarifaRequest.getTarifa() == null)
            return new ResponseEntity(new ResponseCode(16, "Datos incompletos o negativos"), HttpStatus.BAD_REQUEST);
        if(!tarifaService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);

        Tarifa tarifa = tarifaService.getOne(id).get();
        tarifa.setFuente(tarifaRequest.getFuente());
        tarifa.setDestino(tarifaRequest.getDestino());
        tarifa.setTarifa(tarifaRequest.getTarifa());

        tarifaService.save(tarifa);
        return new ResponseEntity(new ResponseCode(8, "Actualizado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!tarifaService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        tarifaService.delete(id);
        return new ResponseEntity(new ResponseCode(9, "Eliminado exitosamente"), HttpStatus.OK);
    }

}
