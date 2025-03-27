package com.backend.aeroportuaria.security.service;

import com.backend.aeroportuaria.security.entity.Rol;
import com.backend.aeroportuaria.security.enums.RolNombre;

import java.util.Optional;

public interface RolService {

    public Optional<Rol> getByRolNombre(RolNombre rolNombre);

    public void save(Rol rol);
}
