package com.backend.aeroportuaria.controller;

import com.backend.aeroportuaria.dto.AeropuertoRequest;
import com.backend.aeroportuaria.dto.ResponseCode;
import com.backend.aeroportuaria.entity.Aerolinea;
import com.backend.aeroportuaria.entity.Aeropuerto;
import com.backend.aeroportuaria.entity.Ciudad;
import com.backend.aeroportuaria.service.CiudadService;
import com.backend.aeroportuaria.serviceimpl.AerolineaServiceImpl;
import com.backend.aeroportuaria.serviceimpl.AeropuertoServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/aeropuerto")
@CrossOrigin("*")
public class AeropuertoController {

    @Autowired
    AeropuertoServiceImpl aeropuertoService;

    @Autowired
    AerolineaServiceImpl aerolineaService;

    @Autowired
    CiudadService ciudadService;

    @GetMapping("/lista")
    public ResponseEntity<List<Aeropuerto>> list(){
        List<Aeropuerto> list = aeropuertoService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Aeropuerto> getById(@PathVariable("id") String id){
        if(!aeropuertoService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Aeropuerto aeropuerto = aeropuertoService.getOne(id).get();
        return new ResponseEntity(aeropuerto, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Aeropuerto> getByNombre(@PathVariable("nombre") String nombre){
        if(!aeropuertoService.existsByNombre(nombre))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Aeropuerto aeropuerto = aeropuertoService.getByNombre(nombre).get();
        return new ResponseEntity(aeropuerto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')") //Roles autorizados para acceder a la petición de este método
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AeropuertoRequest aeropuertoRequest){
        if(StringUtils.isBlank(aeropuertoRequest.getIdAeropuerto()) || StringUtils.isBlank(aeropuertoRequest.getNombre()) || StringUtils.isBlank(aeropuertoRequest.getEstado()) || StringUtils.isBlank(aeropuertoRequest.getPais()) || StringUtils.isBlank(aeropuertoRequest.getId_ciudad())) //Valida si los campos están vacíos
            return new ResponseEntity(new ResponseCode(16, "Datos incompletos o negativos"), HttpStatus.BAD_REQUEST);
        if(aeropuertoService.existsByNombre(aeropuertoRequest.getNombre()))
            return new ResponseEntity(new ResponseCode(5, "Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(!ciudadService.existsById(aeropuertoRequest.getId_ciudad()))
            return new ResponseEntity(new ResponseCode(17, "No existe la ciudad ingresada"), HttpStatus.BAD_REQUEST);

/*
PENDIENTE CORREGIR
Como se comentó el @OnetoOne en la clase ciudad, ahora se puede crear dos o más Aeropuertos en la misma ciudad

 Lo siguiente fue creado para tratar de encontrar el id de ciudad en la BD en la entidad Aeropuerto, si estaba
 entonces no se podría crear otro Aeropuerto en la misma cidad, no funcionó porque no pude acceder al atributo
 ID_CIUDAD de la base de datos ya que al realizar la búsqueda, el atributo ciudad en la clase Aeropuerto
 es un objeto y no un string

        List<Aeropuerto> ciudadesAeropuerto = aeropuertoService.buscarCiudadesAeropuerto(aeropuertoRequest.getId_ciudad());

        System.out.println("La ciudad encontrada en BD es: " + ciudadesAeropuerto);
*/

        //Se busca la ciudad para agregarla al Aeropuerto
        Ciudad ciudadBd = ciudadService.getOne(aeropuertoRequest.getId_ciudad()).get();

        //Crea el Aeropuerto con todos los datos
        Aeropuerto aeropuerto = new Aeropuerto(aeropuertoRequest.getIdAeropuerto(), aeropuertoRequest.getNombre(),
                aeropuertoRequest.getEstado(), aeropuertoRequest.getPais(), ciudadBd);

        Set<Aerolinea> aerolineas = new HashSet<>();

        //Pasa las Aerolíneas ingresadas por el cliente a una lista de Aerolíneas
        for (int i = 0; i < aeropuertoRequest.getAerolineas().size(); i++) {
            aerolineas.add(aerolineaService.getOne(aeropuertoRequest.getAerolineas().get(i)).get());
        }

        //Se asigna las Aerolineas al Aeropuerto.
        aeropuerto.setAerolineas(aerolineas);

        aeropuertoService.save(aeropuerto);

        return new ResponseEntity(new ResponseCode(6, "Creado exitosamente"), HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')OR hasRole('EMPLEADO')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody AeropuertoRequest aeropuertoRequest){
        if(StringUtils.isBlank(aeropuertoRequest.getIdAeropuerto()) || StringUtils.isBlank(aeropuertoRequest.getNombre()) || StringUtils.isBlank(aeropuertoRequest.getEstado()) || StringUtils.isBlank(aeropuertoRequest.getPais()) || StringUtils.isBlank(aeropuertoRequest.getId_ciudad())) //Valida si los campos están vacíos
            return new ResponseEntity(new ResponseCode(16, "Datos incompletos o negativos"), HttpStatus.BAD_REQUEST);
        if(!aeropuertoService.existsById(aeropuertoRequest.getIdAeropuerto()))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        if(!ciudadService.existsById(aeropuertoRequest.getId_ciudad()))
            return new ResponseEntity(new ResponseCode(17, "No existe la ciudad ingresada"), HttpStatus.BAD_REQUEST);

        Aeropuerto aeropuerto = aeropuertoService.getOne(aeropuertoRequest.getIdAeropuerto()).get();
        aeropuerto.setIdAeropuerto(aeropuertoRequest.getIdAeropuerto());
        aeropuerto.setNombre(aeropuertoRequest.getNombre());
        aeropuerto.setEstado(aeropuertoRequest.getEstado());
        aeropuerto.setPais(aeropuertoRequest.getPais());

        Ciudad ciudadBd = ciudadService.getOne(aeropuertoRequest.getId_ciudad()).get();

        aeropuerto.setCiudad(ciudadBd);

        Set<Aerolinea> aerolineas = new HashSet<>();

        for (int i = 0; i < aeropuertoRequest.getAerolineas().size(); i++) {
            aerolineas.add(aerolineaService.getOne(aeropuertoRequest.getAerolineas().get(i)).get());
        }

        aeropuerto.setAerolineas(aerolineas);

        aeropuertoService.save(aeropuerto);
        return new ResponseEntity(new ResponseCode(8, "Actualizado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        if(!aeropuertoService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        aeropuertoService.delete(id);
        return new ResponseEntity(new ResponseCode(9, "Eliminado exitosamente"), HttpStatus.OK);
    }

}
