package com.backend.aeroportuaria.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity (name = "TARIFAS")
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idTarifa;
    String fuente;
    String destino;
    float tarifa;

    public Tarifa(String fuente, String destino, float tarifa) {
        this.fuente = fuente;
        this.destino = destino;
        this.tarifa = tarifa;
    }
}
