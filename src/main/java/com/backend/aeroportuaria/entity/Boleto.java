package com.backend.aeroportuaria.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOLETO")
public class Boleto {

    @Id
    @NotNull
    private String idBoleto;

    private String nombresPasajero;

    private String fuente;

    private String destino;

    private Date fechaReserva;

    private Date fechaViaje;

    private String numeroAsiento;

    private String clase;

    private Date fechaCancelacion;

    private String idAerolinea;

    private Integer tarifa;

    private String numeroPasaporte;

    //Crea relación con la tabla Pasajero, muchos Boletos pueden pertenecer a un pasajero (ida y vuelta)
    //Referencia tomada de https://www.oscarblancarteblog.com/2018/12/20/relaciones-onetomany/
    @ManyToOne
    @JoinColumn(name = "ID_PASAJERO", nullable = false) //Si se pone updatable = false entonces no se podra actualizar el ID_AEROPUERTO en la entidad Empleados, la quito simulando que un Empleado puede pasar de trabajar de un Aeropuerto a otro.
    private Pasajero pasajero;

}
