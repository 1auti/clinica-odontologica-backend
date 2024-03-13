package com.backend.CRUD.DTO;

import com.backend.CRUD.Entity.Odontologo;
import com.backend.CRUD.Entity.Paciente;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TurnoDto {

    @NotBlank
    private Integer id_turno;
   @NotBlank
    private LocalDateTime fechaHora;

    @NotBlank
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime hora;

    @NotBlank
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha;



   @NotBlank
    private String afeccion;

   @NotBlank
    private Odontologo odontologo;

  @NotBlank
    private Paciente paciente;
}
