package com.backend.CRUD.DTO;

import com.backend.CRUD.Entity.HistorialMedico;
import com.backend.CRUD.Entity.Odontologo;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DiagnosticoDto {


    @NotBlank
    private String diagnostico;
    @NotBlank
    private LocalDate fechaTratamiento;
    @NotBlank
    private Odontologo odontologo;

}
