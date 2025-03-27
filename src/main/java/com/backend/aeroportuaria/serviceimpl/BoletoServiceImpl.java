package com.backend.aeroportuaria.serviceimpl;

import com.backend.aeroportuaria.entity.Boleto;
import com.backend.aeroportuaria.entity.Pasajero;
import com.backend.aeroportuaria.repository.BoletoRepository;
import com.backend.aeroportuaria.repository.PasajeroRepository;
import com.backend.aeroportuaria.service.BoletoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BoletoServiceImpl implements BoletoService {

    @Autowired
    BoletoRepository boletoRepository;

    public List<Boleto> list(){
        return boletoRepository.findAll();
    }

    public Optional<Boleto> getOne(String id){
        return boletoRepository.findById(id);
    }

    public void  save(Boleto boleto){
        boletoRepository.save(boleto);
    }

    public void delete(String id){
        boletoRepository.deleteById(id);
    }

    public boolean existsById(String id){
        return boletoRepository.existsById(id);
    }

}
