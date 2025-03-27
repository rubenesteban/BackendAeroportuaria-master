package com.backend.aeroportuaria.controller;

import com.backend.aeroportuaria.dto.ResponseCode;
import com.backend.aeroportuaria.dto.VueloRequest;
import com.backend.aeroportuaria.entity.Aerolinea;
import com.backend.aeroportuaria.entity.Vuelo;
import com.backend.aeroportuaria.service.VueloService;
import com.backend.aeroportuaria.serviceimpl.AerolineaServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/vuelo")
@CrossOrigin("*")
public class VueloController {

    @Autowired
    VueloService vueloService;

    @Autowired
    AerolineaServiceImpl aerolineaService;

    @GetMapping("/lista")
    public ResponseEntity<List<Vuelo>> list(){
        List<Vuelo> list = vueloService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Vuelo> getById(@PathVariable("id") String id){
        if(!vueloService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Vuelo vuelo = vueloService.getOne(id).get();
        return new ResponseEntity(vuelo, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')") //Roles autorizados para acceder a la petición de este método
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody VueloRequest vueloRequest){
        Integer numeroParadas = vueloRequest.getNumeroParadas(); //Pasa número de paradas a entero para poder validar que no sea negativo
        if(StringUtils.isBlank(vueloRequest.getIdVuelo()) || StringUtils.isBlank(vueloRequest.getFuente()) || StringUtils.isBlank(vueloRequest.getDestino()) || StringUtils.isBlank(vueloRequest.getEstado()) || StringUtils.isBlank(vueloRequest.getDuracion()) || StringUtils.isBlank(vueloRequest.getTipoVuelo()) || StringUtils.isBlank(vueloRequest.getIdVuelo()) || numeroParadas == null || numeroParadas < 0 || StringUtils.isBlank(vueloRequest.getClase()) || StringUtils.isBlank(vueloRequest.getIdAerolinea()))
            return new ResponseEntity(new ResponseCode(16, "Datos incompletos o negativos"), HttpStatus.BAD_REQUEST);
        if(!aerolineaService.existsById(vueloRequest.getIdAerolinea()))
            return new ResponseEntity(new ResponseCode(18, "No existe la Aerolínea ingresada"), HttpStatus.NOT_FOUND);
        if(!vueloRequest.getClase().equals("Negocios") && !vueloRequest.getClase().equals("Primera Clase") && !vueloRequest.getClase().equals("Economía"))
            return new ResponseEntity(new ResponseCode(22, "La clase de Vuelo sólo se permite Negocios, Primera Clase o Economía"), HttpStatus.NOT_FOUND);
        if(!vueloRequest.getTipoVuelo().equals("Sin escalas") && !vueloRequest.getTipoVuelo().equals("De conexión"))
            return new ResponseEntity(new ResponseCode(23, "El tipo de Vuelo sólo se permite Sin escalas o De conexión"), HttpStatus.NOT_FOUND);

        Aerolinea aerolineaBd = aerolineaService.getOne(vueloRequest.getIdAerolinea()).get();

        String idAerolinea = aerolineaBd.getIdAerolinea();

        if(vueloService.existsById(idAerolinea + vueloRequest.getIdVuelo()))
            return new ResponseEntity(new ResponseCode(23, "Ese id de Vuelo ya existe"), HttpStatus.NOT_FOUND);

/*
Referencia para tratar fecha
https://www.youtube.com/watch?v=M1S3LbCxD-M

La fech que se envía desde postan llega al al editor un día antes, se debe concatenar el 1 para que quede bien
 */
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fechaSalida = new Date(vueloRequest.getSalida().getYear(), vueloRequest.getSalida().getMonth(), vueloRequest.getSalida().getDate() + 1);
        System.out.println("La fecha salida convertida es: " + sdf.format(fechaSalida));

        Date fechaLlegada = new Date(vueloRequest.getLlegada().getYear(), vueloRequest.getLlegada().getMonth(), vueloRequest.getLlegada().getDate() + 1);
        System.out.println("La fecha llegada convertida es: " + sdf.format(fechaLlegada));

        if (fechaLlegada.getTime() < fechaSalida.getTime()){
            return new ResponseEntity(new ResponseCode(29, "La fecha de llegada no puede ser inferior a la fecha de salida"), HttpStatus.NOT_FOUND); }

        Vuelo vuelo = new Vuelo(idAerolinea + vueloRequest.getIdVuelo(),
                vueloRequest.getFuente(),
                vueloRequest.getDestino(),
                fechaLlegada,
                fechaSalida,
                vueloRequest.getEstado(),
                vueloRequest.getDuracion(),
                vueloRequest.getTipoVuelo(),
                vueloRequest.getNumeroParadas(),
                vueloRequest.getClase(),
                aerolineaBd);

        vueloService.save(vuelo);
        return new ResponseEntity(new ResponseCode(6, "Creado exitosamente"), HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')OR hasRole('EMPLEADO')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody VueloRequest vueloRequest){
        Integer numeroParadas = vueloRequest.getNumeroParadas(); //Pasa número de paradas a entero para poder validar que no sea negativo
        if(StringUtils.isBlank(vueloRequest.getIdVuelo()) || StringUtils.isBlank(vueloRequest.getFuente()) || StringUtils.isBlank(vueloRequest.getDestino()) || StringUtils.isBlank(vueloRequest.getEstado()) || StringUtils.isBlank(vueloRequest.getDuracion()) || StringUtils.isBlank(vueloRequest.getTipoVuelo()) || StringUtils.isBlank(vueloRequest.getIdVuelo()) || numeroParadas == null || numeroParadas < 0 || StringUtils.isBlank(vueloRequest.getClase()) || StringUtils.isBlank(vueloRequest.getIdAerolinea()))
            return new ResponseEntity(new ResponseCode(16, "Datos incompletos o negativos"), HttpStatus.BAD_REQUEST);
        if(!vueloService.existsById(vueloRequest.getIdVuelo()))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        if(!aerolineaService.existsById(vueloRequest.getIdAerolinea()))
            return new ResponseEntity(new ResponseCode(18, "No existe la Aerolínea ingresada"), HttpStatus.NOT_FOUND);
        if(!vueloRequest.getClase().equals("Negocios") && !vueloRequest.getClase().equals("Primera Clase") && !vueloRequest.getClase().equals("Economía"))
            return new ResponseEntity(new ResponseCode(22, "La clase de Vuelo sólo se permite Negocios, Primera Clase o Economía"), HttpStatus.NOT_FOUND);
        if(!vueloRequest.getTipoVuelo().equals("Sin escalas") && !vueloRequest.getTipoVuelo().equals("De conexión"))
            return new ResponseEntity(new ResponseCode(23, "El tipo de Vuelo sólo se permite Sin escalas o De conexión"), HttpStatus.NOT_FOUND);

        Vuelo vuelo = vueloService.getOne(vueloRequest.getIdVuelo()).get();

        Aerolinea aerolineaBd = aerolineaService.getOne(vueloRequest.getIdAerolinea()).get();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fechaSalida = new Date(vueloRequest.getSalida().getYear(), vueloRequest.getSalida().getMonth(), vueloRequest.getSalida().getDate() + 1);
        System.out.println("La fecha salida convertida es: " + sdf.format(fechaSalida));

        Date fechaLlegada = new Date(vueloRequest.getLlegada().getYear(), vueloRequest.getLlegada().getMonth(), vueloRequest.getLlegada().getDate() + 1);
        System.out.println("La fecha llegada convertida es: " + sdf.format(fechaLlegada));

        if (fechaLlegada.getTime() < fechaSalida.getTime()){
            return new ResponseEntity(new ResponseCode(29, "La fecha de llegada no puede ser inferior a la fecha de salida"), HttpStatus.NOT_FOUND); }

        vuelo.setIdVuelo(vueloRequest.getIdVuelo());
        vuelo.setFuente(vueloRequest.getFuente());
        vuelo.setDestino(vueloRequest.getDestino());
        vuelo.setLlegada(fechaLlegada);
        vuelo.setSalida(fechaSalida);
        vuelo.setEstado(vueloRequest.getEstado());
        vuelo.setDuracion(vueloRequest.getDuracion());
        vuelo.setTipoVuelo(vueloRequest.getTipoVuelo());
        vuelo.setNumeroParadas(vueloRequest.getNumeroParadas());
        vuelo.setClase(vueloRequest.getClase());
        vuelo.setAerolinea(aerolineaBd);

        vueloService.save(vuelo);
        return new ResponseEntity(new ResponseCode(8, "Actualizado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){

/*
PENDIENTE CORREGIR
Si se va a eliminar un Vuelo que ya tien asociado pasajeros, el sistema muestra error
intenté solucionarlo utilizando la propiedad (cascade = CascadeType.ALL) en el @ManyToOne
de la clase Pasajero pero no funcionó.
 */

        if(!vueloService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        vueloService.delete(id);
        return new ResponseEntity(new ResponseCode(9, "Eliminado exitosamente"), HttpStatus.OK);
    }

}
