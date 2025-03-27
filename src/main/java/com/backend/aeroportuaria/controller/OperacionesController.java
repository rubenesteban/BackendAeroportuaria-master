package com.backend.aeroportuaria.controller;

import com.backend.aeroportuaria.dto.CompraRequest;
import com.backend.aeroportuaria.dto.CompraResponse;
import com.backend.aeroportuaria.dto.ResponseCode;
import com.backend.aeroportuaria.service.TarifaService;
import com.backend.aeroportuaria.service.VueloService;
import com.backend.aeroportuaria.serviceimpl.AerolineaServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/operaciones")
@CrossOrigin("*")
public class OperacionesController {

    @Autowired
    VueloService vueloService;

    @Autowired
    AerolineaServiceImpl aerolineaService;

    @Autowired
    TarifaService tarifaService;

    @PostMapping("/listaropcionescompra")
    public ResponseEntity<List<CompraResponse>> listOpcionesCompra(@RequestBody CompraRequest compraRequest){
        if(StringUtils.isBlank(compraRequest.getIdAerolinea()) || StringUtils.isBlank(compraRequest.getFuente()) || StringUtils.isBlank(compraRequest.getDestino()) || compraRequest.getFechaViaje() == null || StringUtils.isBlank(compraRequest.getClase())) //Valida si los campos están vacíos
            return new ResponseEntity(new ResponseCode(16, "Datos incompletos o negativos"), HttpStatus.BAD_REQUEST);
        if (!aerolineaService.existsById(compraRequest.getIdAerolinea())){
            return new ResponseEntity(new ResponseCode(24, "La Aerolínea ingresada no existe"), HttpStatus.BAD_REQUEST); }
        if (!vueloService.existsByFuente(compraRequest.getFuente())){
            return new ResponseEntity(new ResponseCode(25, "Ninguna Aerolínea realiza vuelos desde la fuente ingresada"), HttpStatus.BAD_REQUEST); }
        if (!vueloService.existsByDestino(compraRequest.getDestino())){
            return new ResponseEntity(new ResponseCode(26, "Ninguna Aerolínea realiza vuelos hacia el destino ingresado"), HttpStatus.BAD_REQUEST); }
        if (!vueloService.existsByClase(compraRequest.getClase())){
            return new ResponseEntity(new ResponseCode(27, "Ninguna Aerolínea realiza vuelos con la clase ingresada"), HttpStatus.BAD_REQUEST); }

        List<String> idsVuelos = vueloService.validarDisponibilidad(compraRequest.getIdAerolinea(), compraRequest.getFuente(), compraRequest.getDestino(), compraRequest.getClase());
        System.out.println("Los id de vuelos disponibles con los datos ingresados son: " + idsVuelos);

        if (idsVuelos.isEmpty()){
            return new ResponseEntity(new ResponseCode(30, "La Aerolínea ingresada no realiza vuelos desde el origen o hacia el destino ingresado o no lo hace con la clase ingresada"), HttpStatus.BAD_REQUEST); }

        //Se establece formato de fecha a manejar
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fechaViaje = new Date(compraRequest.getFechaViaje().getYear(), compraRequest.getFechaViaje().getMonth(), compraRequest.getFechaViaje().getDate() + 1);
        System.out.println("La fecha de viaje convertida es: " + sdf.format(fechaViaje));
        System.out.println("El mes de la fecha de viaje es: " + fechaViaje.getMonth());

        //Si la fecha ingresada es de diciembre o enero es temprada alta entonces tiene costo adicional
        if (fechaViaje.getMonth() > 0 && fechaViaje.getMonth() < 11){
            System.out.println("Temporada baja");
        }else {
            System.out.println("Temporada alta");
        }

        //Valida que haya una tarifa base para el origen y destino ingresado
        if (tarifaService.listaTarifas(compraRequest.getFuente(), compraRequest.getDestino()).isEmpty()){
            return new ResponseEntity(new ResponseCode(31, "No se ha especificado una tarifa base para el origen y destino especificado"), HttpStatus.BAD_REQUEST); }

        //Busco la tarifa base para el origen y destino ingresado
        float tarifaBd = tarifaService.buscarTarifa(compraRequest.getFuente(), compraRequest.getDestino());

        List<CompraResponse> listaOpcionesCompra = new ArrayList<>();

        if (compraRequest.getClase().equals("Economía")){
            if (fechaViaje.getMonth() > 0 && fechaViaje.getMonth() < 11){
                float tarifaEconomia = tarifaBd;
                CompraResponse opcionCompra = new CompraResponse(compraRequest.getIdAerolinea(), compraRequest.getFuente(), compraRequest.getDestino(), compraRequest.getFechaViaje(), compraRequest.getClase(), tarifaEconomia);
                listaOpcionesCompra.add(opcionCompra);
            }else {
                float tarifaEconomia = (float) (tarifaBd * 1.5);
                CompraResponse opcionCompra = new CompraResponse(compraRequest.getIdAerolinea(), compraRequest.getFuente(), compraRequest.getDestino(), compraRequest.getFechaViaje(), compraRequest.getClase(), tarifaEconomia);
                listaOpcionesCompra.add(opcionCompra);
            }
        }

        if (compraRequest.getClase().equals("Negocios")){
            if (fechaViaje.getMonth() > 0 && fechaViaje.getMonth() < 11) {
                float tarifaEconomia = (float) (tarifaBd * 1.5);
                CompraResponse opcionCompra = new CompraResponse(compraRequest.getIdAerolinea(), compraRequest.getFuente(), compraRequest.getDestino(), compraRequest.getFechaViaje(), compraRequest.getClase(), tarifaEconomia);
                listaOpcionesCompra.add(opcionCompra);
            }else {
                float tarifaEconomia = (tarifaBd * 2);
                CompraResponse opcionCompra = new CompraResponse(compraRequest.getIdAerolinea(), compraRequest.getFuente(), compraRequest.getDestino(), compraRequest.getFechaViaje(), compraRequest.getClase(), tarifaEconomia);
                listaOpcionesCompra.add(opcionCompra);
            }
        }

        if (compraRequest.getClase().equals("Primera Clase")){
            if (fechaViaje.getMonth() > 0 && fechaViaje.getMonth() < 11) {
                float tarifaEconomia = tarifaBd * 2;
                CompraResponse opcionCompra = new CompraResponse(compraRequest.getIdAerolinea(), compraRequest.getFuente(), compraRequest.getDestino(), compraRequest.getFechaViaje(), compraRequest.getClase(), tarifaEconomia);
                listaOpcionesCompra.add(opcionCompra);
            }else {
                float tarifaEconomia = (tarifaBd * 3);
                CompraResponse opcionCompra = new CompraResponse(compraRequest.getIdAerolinea(), compraRequest.getFuente(), compraRequest.getDestino(), compraRequest.getFechaViaje(), compraRequest.getClase(), tarifaEconomia);
                listaOpcionesCompra.add(opcionCompra);
            }
        }

        return new ResponseEntity(listaOpcionesCompra, HttpStatus.OK);
    }

}
