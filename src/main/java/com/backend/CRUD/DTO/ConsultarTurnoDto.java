package com.backend.CRUD.DTO;

import com.backend.CRUD.Entity.Odontologo;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConsultarTurnoDto {

    @NotBlank
    private Odontologo odontologo;
    @NotBlank
    private LocalDateTime fecha;

}
