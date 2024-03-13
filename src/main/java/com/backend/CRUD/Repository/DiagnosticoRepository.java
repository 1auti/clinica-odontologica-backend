package com.backend.CRUD.Repository;

import com.backend.CRUD.Entity.Diagnostico;
import com.backend.CRUD.Entity.HistorialMedico;
import com.backend.CRUD.Entity.Odontologo;
import com.backend.CRUD.Entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico,Integer> {


    List<Diagnostico> findByFechaTratamiento(LocalDate fechaTratamiento);
    // Buscar historial médico por odontólogo
    List<Diagnostico> findByOdontologo(Odontologo odontologo);
    List<Diagnostico> findByDiagnostico(String diagnostico);
    // Buscar historiales médicos por rango de fechas de tratamiento
    List<Diagnostico> findByFechaTratamientoBetween(LocalDate fechaInicio, LocalDate fechaFin);

    List<Diagnostico> findByOdontologoId(Integer idOdontologo);

}
