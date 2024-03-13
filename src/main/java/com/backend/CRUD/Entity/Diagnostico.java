package com.backend.CRUD.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="diagnosticos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "diagnostico")
    private String diagnostico;

    @ManyToOne
    @JoinColumn(name = "odontologo_id")
    private Odontologo odontologo;

    @Column(name="fechaTratamiento")
    private LocalDate fechaTratamiento;

    @ManyToOne
    @JoinColumn(name = "historial_medico_id")
    @JsonIgnore
    private HistorialMedico historialMedico;



}
