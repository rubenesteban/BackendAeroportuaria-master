package com.backend.aeroportuaria.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "AEROPUERTO")
public class Aeropuerto {

    @Id
    @NotNull
    private String idAeropuerto;

    private String nombre;

    private String estado;

    private String pais;

    @OneToOne(fetch = FetchType.EAGER) //Si se pone LAZI, muestra error No serializer found for class. cascade = CascadeType.ALL entonces cuando elimina un Aeropuerto también elimina la cidad, se quita para que la ciudad no se borre y quede disponible para crear otro aeropuerto
    @JoinColumn(name = "id_ciudad")
    private Ciudad ciudad;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "AEROPUERTO_AEROLINEA", joinColumns = @JoinColumn(name = "ID_AEROPUERTO"),
            inverseJoinColumns = @JoinColumn(name = "ID_AEROLINEA"))
    private Set<Aerolinea> aerolineas = new HashSet<>(); //Conjunto de aerolineas
/*
Se comenta para evitar que se forme un bucle y no funcione la petición de listar

    //Crea relación con la table Empleados, un Aeropuerto puede tener muchos Empleados
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aeropuerto")
    private List<Empleado> empleados;
*/
    //Se crea constructor lleno ya que este no debe llevar la colección de aerolíneas
    public Aeropuerto(@NotNull String idAeropuerto, String nombre, String estado, String pais, Ciudad ciudad) {
        this.idAeropuerto = idAeropuerto;
        this.nombre = nombre;
        this.estado = estado;
        this.pais = pais;
        this.ciudad = ciudad;
    }

}
