package com.backend.aeroportuaria.repository;

import com.backend.aeroportuaria.entity.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoletoRepository extends JpaRepository<Boleto, String> {

}
