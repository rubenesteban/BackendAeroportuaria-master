package com.backend.aeroportuaria.controller;

import com.backend.aeroportuaria.dto.AerolineaRequest;
import com.backend.aeroportuaria.dto.ResponseCode;
import com.backend.aeroportuaria.entity.Aerolinea;
import com.backend.aeroportuaria.serviceimpl.AerolineaServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aerolinea")
@CrossOrigin("*")
public class AerolineaController {

    @Autowired
    AerolineaServiceImpl aerolineaService;

    @GetMapping("/lista")
    public ResponseEntity<List<Aerolinea>> list(){
        List<Aerolinea> list = aerolineaService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Aerolinea> getById(@PathVariable("id") String id){
        if(!aerolineaService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Aerolinea aerolinea = aerolineaService.getOne(id).get(); //Como getOne trae un opcional entonces debe poner get
        return new ResponseEntity(aerolinea, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Aerolinea> getByNombre(@PathVariable("nombre") String nombre){
        if(!aerolineaService.existsByNombre(nombre))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Aerolinea aerolinea = aerolineaService.getByNombre(nombre).get();
        return new ResponseEntity(aerolinea, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')") //Roles autorizados para acceder a la petición de este método
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AerolineaRequest aerolineaRequest){
        if(StringUtils.isBlank(aerolineaRequest.getIdAerolinea()) || StringUtils.isBlank(aerolineaRequest.getNombre()) || StringUtils.isBlank(aerolineaRequest.getCodigoTresDigitos())) //Valida si los campos están vacíos
            return new ResponseEntity(new ResponseCode(16, "Datos incompletos o negativos"), HttpStatus.BAD_REQUEST);
        if(aerolineaService.existsByNombre(aerolineaRequest.getNombre()))
            return new ResponseEntity(new ResponseCode(5, "Ese nombre ya existe"), HttpStatus.BAD_REQUEST);

        Aerolinea aerolinea = new Aerolinea(aerolineaRequest.getIdAerolinea(), aerolineaRequest.getNombre(), aerolineaRequest.getCodigoTresDigitos());
        aerolineaService.save(aerolinea);
        return new ResponseEntity(new ResponseCode(6, "Creado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')OR hasRole('EMPLEADO')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody AerolineaRequest aerolineaRequest){
        if(StringUtils.isBlank(aerolineaRequest.getIdAerolinea()) || StringUtils.isBlank(aerolineaRequest.getNombre()) || StringUtils.isBlank(aerolineaRequest.getCodigoTresDigitos())) //Valida si los campos están vacíos
            return new ResponseEntity(new ResponseCode(16, "Datos incompletos o negativos"), HttpStatus.BAD_REQUEST);
        if(!aerolineaService.existsById(aerolineaRequest.getIdAerolinea()))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);

        Aerolinea aerolinea = aerolineaService.getOne(aerolineaRequest.getIdAerolinea()).get();
        aerolinea.setNombre(aerolineaRequest.getNombre());
        aerolinea.setCodigoTresDigitos(aerolineaRequest.getCodigoTresDigitos());

        aerolineaService.save(aerolinea);
        return new ResponseEntity(new ResponseCode(8, "Actualizado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        if(!aerolineaService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        aerolineaService.delete(id);
        return new ResponseEntity(new ResponseCode(9, "Eliminado exitosamente"), HttpStatus.OK);
    }

}
