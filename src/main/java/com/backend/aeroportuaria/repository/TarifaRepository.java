package com.backend.aeroportuaria.repository;

import com.backend.aeroportuaria.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Integer> {
/*

Los siguiente funciona pero trae una lista con todo y sólo necesito la tarifa
    @Query(value = "SELECT * FROM Tarifas t WHERE t.FUENTE = :fuente AND t.DESTINO = :destino", nativeQuery = true) //funcionó SELECT o FROM Tarifa WHERE fuente = fuente
    List<Tarifa> buscarTarifas(String fuente, String destino);
 */

    @Query(value = "SELECT tarifa FROM Tarifas t WHERE t.FUENTE = :fuente AND t.DESTINO = :destino", nativeQuery = true) //funcionó SELECT o FROM Tarifa WHERE fuente = fuente
    float buscarTarifa(String fuente, String destino);

    @Query(value = "SELECT tarifa FROM Tarifas t WHERE t.FUENTE = :fuente AND t.DESTINO = :destino", nativeQuery = true) //funcionó SELECT o FROM Tarifa WHERE fuente = fuente
    List<Float> listaTarifas(String fuente, String destino);
}
