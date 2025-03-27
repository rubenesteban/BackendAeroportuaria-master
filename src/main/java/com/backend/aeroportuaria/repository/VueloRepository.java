package com.backend.aeroportuaria.repository;

import com.backend.aeroportuaria.entity.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VueloRepository extends JpaRepository<Vuelo, String> {

    boolean existsByFuente(String fuente);

    boolean existsByDestino(String destino);

    boolean existsByClase(String clase);
/*
    @Query(value = "SELECT tarifa FROM Tarifas t WHERE t.FUENTE = :fuente AND t.DESTINO = :destino", nativeQuery = true) //funcionó SELECT o FROM Tarifa WHERE fuente = fuente
    float buscarTarifa(String fuente, String destino);
*/
    @Query(value = "SELECT id_vuelo FROM Vuelo v WHERE v.id_aerolinea = :idAerolinea AND fuente = :fuente AND destino = :destino AND clase = :clase", nativeQuery = true)
    List<String> validarDisponibilidad(String idAerolinea, String fuente, String destino, String clase);

}
