package com.backend.CRUD.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "Turno")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Turno {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_turno")
        private Integer id_turno;

        @Column(name = "fecha")
        private LocalDate fecha;

        @Column(name = "horario")
        @DateTimeFormat(pattern = "HH:mm:ss")
        private LocalTime horario;

        @Column(name = "afeccion")
        private String afeccion;

        @ManyToOne
        @JoinColumn(name = "id_odontologo")
        private Odontologo odontologo;

        @ManyToOne
        @JoinColumn(name = "id_paciente")
        private Paciente paciente;



    }


