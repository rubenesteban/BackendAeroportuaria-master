package com.backend.aeroportuaria.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class AeropuertoRequest {

    private String idAeropuerto;

    private String nombre;

    private String estado;

    private String pais;

    private String id_ciudad;

    //private Set<String> aerolineas = new HashSet<>();

    List<String> aerolineas = new ArrayList<>();

}
