package com.backend.CRUD.DTO;

import com.backend.CRUD.Entity.HistorialMedico;
import com.backend.CRUD.Entity.Turno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PacienteDto extends PersonaDto {

    @NotNull
    private boolean tiene_OS;
    @NotBlank
    private String tipo_sangre;
    @NotNull
    private List<Turno> turnos;
    @NotNull
    private HistorialMedico historialMedicos;


}
