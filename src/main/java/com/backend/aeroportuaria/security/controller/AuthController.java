package com.backend.aeroportuaria.security.controller;

import com.backend.aeroportuaria.security.dto.UsuarioRequest;
import com.backend.aeroportuaria.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.backend.aeroportuaria.dto.ResponseCode;
import com.backend.aeroportuaria.security.dto.LoginResponse;
import com.backend.aeroportuaria.security.dto.LoginRequest;
import com.backend.aeroportuaria.security.entity.Rol;
import com.backend.aeroportuaria.security.entity.Usuario;
import com.backend.aeroportuaria.security.enums.RolNombre;
import com.backend.aeroportuaria.security.service.RolService;
import com.backend.aeroportuaria.security.service.UsuarioService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*") //Indica que se puede acceder desde cualquier url
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtUtil jwtUtil; //Para crear los token

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody UsuarioRequest usuarioRequest, BindingResult bindingResult){ //BindingResult es para validarlo
        if(bindingResult.hasErrors())
            return new ResponseEntity(new ResponseCode(10, "Campos mal diligenciados o email inválido"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombreUsuario(usuarioRequest.getNombreUsuario()))
            return new ResponseEntity(new ResponseCode(5, "Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByEmail(usuarioRequest.getEmail()))
            return new ResponseEntity(new ResponseCode(11, "Ese email ya existe"), HttpStatus.BAD_REQUEST);
        
        //Si en las anteriores validaciones está bien entonces crea el usuario. 
        
        Usuario usuario =
                new Usuario(usuarioRequest.getNombre(), usuarioRequest.getNombreUsuario(), usuarioRequest.getEmail(),
                        passwordEncoder.encode(usuarioRequest.getPassword()));
        
        //Se asigna los roles. usuarioRequest los roles que tiene son tipo cadena. Por defecto todos van a ser clientes
        
        Set<Rol> roles = new HashSet<>();
        
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_PASAJERO).get()); //Trae el ROLE_CLIENTE de la BD
        
        if(usuarioRequest.getRoles().contains("admin")) //Valida si en los roles enviados aparece admin
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get()); //Si se cumple la condición, al set de roles le asigna el nuevo de admin

        if(usuarioRequest.getRoles().contains("empleado"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_EMPLEADO).get());

        usuario.setRoles(roles); //Le asigna los roles al usuario
        
        usuarioService.save(usuario); //Guarda el usuario en la BD
        
        return new ResponseEntity(new ResponseCode(12, "Usuario guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){
    	
        if(bindingResult.hasErrors())
            return new ResponseEntity(new ResponseCode(13, "Campos mal diligenciados"), HttpStatus.BAD_REQUEST);

        if(usuarioService.existsByNombreUsuario(loginRequest.getNombreUsuario())) {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getNombreUsuario(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication); //Autentica el usuario

            String jwt = jwtUtil.generateToken(authentication); //Envía los datos a la clase JwtUtil para crear el token y lo guarda en un string

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            LoginResponse loginResponse = new LoginResponse("Bearer " + jwt, userDetails.getUsername(), userDetails.getAuthorities());

            return new ResponseEntity(loginResponse, HttpStatus.OK);
        }else {
            return new ResponseEntity(new ResponseCode(14, "Usuario invalido"), HttpStatus.BAD_REQUEST);
        }
    }
}
