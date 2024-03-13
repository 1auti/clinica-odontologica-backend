package com.backend.CRUD.DTO;

import com.backend.CRUD.Entity.Horario;
import com.backend.CRUD.Entity.Turno;
import com.backend.SECURITY.User.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OdontologiaDto extends PersonaDto {

    @NotBlank
    private String especialidad;
    @NotBlank
    private List<Turno> turnoList;
    @NotBlank
    private User usuario;
    @NotBlank
    private Horario horario;


}
