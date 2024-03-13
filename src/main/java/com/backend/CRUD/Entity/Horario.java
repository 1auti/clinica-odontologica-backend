package com.backend.CRUD.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Entity
@Table(name = "horario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_horario;
    @Column(name="horario_inicio")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime horario_inicio;
    @Column(name="horario_fin")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime horario_fin;
    @Column(name = "dias")
    private String dias;
    @OneToOne
    @JoinColumn(name = "odontologo_id")
    @JsonIgnore
    private Odontologo odontologo;


}
