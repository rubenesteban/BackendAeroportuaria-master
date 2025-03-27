package com.backend.aeroportuaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoletoRequest {

    private String idBoleto;

    private Date fechaReserva;

    private String numeroAsiento;

    private Date fechaCancelacion;

    private Integer tarifa;

    private String idPasajero;

}
