package com.backend.aeroportuaria.controller;

import com.backend.aeroportuaria.dto.BoletoRequest;
import com.backend.aeroportuaria.dto.ResponseCode;
import com.backend.aeroportuaria.entity.Boleto;
import com.backend.aeroportuaria.entity.Pasajero;
import com.backend.aeroportuaria.service.BoletoService;
import com.backend.aeroportuaria.service.PasajeroService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/boleto")
@CrossOrigin("*")
public class BoletoController {

    @Autowired
    BoletoService boletoService;

    @Autowired
    PasajeroService pasajeroService;

    @GetMapping("/lista")
    public ResponseEntity<List<Boleto>> list(){
        List<Boleto> list = boletoService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Boleto> getById(@PathVariable("id") String id){
        if(!boletoService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Boleto boleto = boletoService.getOne(id).get();
        return new ResponseEntity(boleto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')") //Roles autorizados para acceder a la petición de este método
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody BoletoRequest boletoRequest){
        if(StringUtils.isBlank(boletoRequest.getIdBoleto()) || StringUtils.isBlank(boletoRequest.getNumeroAsiento()) || boletoRequest.getTarifa() < 0  || StringUtils.isBlank(boletoRequest.getIdPasajero()))
            return new ResponseEntity(new ResponseCode(16, "Datos incompletos o negativos"), HttpStatus.BAD_REQUEST);
        if (!pasajeroService.existsById(boletoRequest.getIdPasajero())){
            return new ResponseEntity(new ResponseCode(21, "No se encontró el Pasajero ingresado"), HttpStatus.BAD_REQUEST);
        }

        Pasajero pasajeroBd = pasajeroService.getOne(boletoRequest.getIdPasajero()).get();

/*
Referencia para tratar fecha
https://www.youtube.com/watch?v=M1S3LbCxD-M

La fech que se envía desde postan llega al al editor un día antes, se debe concatenar el 1 para que quede bien
 */

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fechaReserva = new Date(boletoRequest.getFechaReserva().getYear(), boletoRequest.getFechaReserva().getMonth(), boletoRequest.getFechaReserva().getDate() + 1);
        System.out.println("La fecha reserva convertida es: " + sdf.format(fechaReserva));

        Date fechaCancelacion = new Date(boletoRequest.getFechaCancelacion().getYear(), boletoRequest.getFechaCancelacion().getMonth(), boletoRequest.getFechaCancelacion().getDate() + 1);
        System.out.println("La fecha cancelación convertida es: " + sdf.format(fechaCancelacion));

        if (fechaCancelacion.getTime() < fechaReserva.getTime()){
            return new ResponseEntity(new ResponseCode(28, "La fecha de cancelación no puede ser inferior a la fecha de reserva"), HttpStatus.NOT_FOUND); }

        Boleto boleto = new Boleto(
                pasajeroBd.getVuelo().getAerolinea().getIdAerolinea() + boletoRequest.getIdBoleto(),
                pasajeroBd.getNombres(),
                pasajeroBd.getVuelo().getFuente(),
                pasajeroBd.getVuelo().getDestino(),
                fechaReserva,
                pasajeroBd.getVuelo().getSalida(),
                boletoRequest.getNumeroAsiento(),
                pasajeroBd.getVuelo().getClase(),
                fechaCancelacion,
                pasajeroBd.getVuelo().getAerolinea().getIdAerolinea(),
                boletoRequest.getTarifa(),
                pasajeroBd.getNumeroPasaporte(),
                pasajeroBd);

        boletoService.save(boleto);
        return new ResponseEntity(new ResponseCode(6, "Creado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')OR hasRole('EMPLEADO')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")String id, @RequestBody BoletoRequest boletoRequest){
        if(!boletoService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);

        Boleto boleto = boletoService.getOne(id).get();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fechaReserva = new Date(boletoRequest.getFechaReserva().getYear(), boletoRequest.getFechaReserva().getMonth(), boletoRequest.getFechaReserva().getDate() + 1);
        System.out.println("La fecha reserva convertida es: " + sdf.format(fechaReserva));

        Date fechaCancelacion = new Date(boletoRequest.getFechaCancelacion().getYear(), boletoRequest.getFechaCancelacion().getMonth(), boletoRequest.getFechaCancelacion().getDate() + 1);
        System.out.println("La fecha cancelación convertida es: " + sdf.format(fechaCancelacion));

        if (fechaCancelacion.getTime() < fechaReserva.getTime()){
            return new ResponseEntity(new ResponseCode(28, "La fecha de cancelación no puede ser inferior a la fecha de reserva"), HttpStatus.NOT_FOUND); }


        boleto.setFechaReserva(fechaReserva);
        boleto.setNumeroAsiento(boletoRequest.getNumeroAsiento());
        boleto.setFechaCancelacion(fechaCancelacion);
        boleto.setTarifa(boletoRequest.getTarifa());

        Pasajero pasajeroBd = pasajeroService.getOne(boletoRequest.getIdPasajero()).get();

        boleto.setPasajero(pasajeroBd);

        boletoService.save(boleto);
        return new ResponseEntity(new ResponseCode(8, "Actualizado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")String id){
        if(!boletoService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        boletoService.delete(id);
        return new ResponseEntity(new ResponseCode(9, "Eliminado exitosamente"), HttpStatus.OK);
    }

}
