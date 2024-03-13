package com.backend.CRUD.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name="Odontologo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Odontologo extends Persona {


    private String especialidad;
    @OneToMany(mappedBy = "odontologo",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Turno> turnoList;
    @OneToOne(mappedBy = "odontologo",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Horario horario;
    @OneToMany(mappedBy = "odontologo",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Diagnostico> diagnosticos ;



}
