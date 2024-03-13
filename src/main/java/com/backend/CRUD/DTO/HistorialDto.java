package com.backend.CRUD.DTO;

import com.backend.CRUD.Entity.Odontologo;
import com.backend.CRUD.Entity.Paciente;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistorialDto {


    @NotBlank
    private Paciente paciente;
}
