package com.backend.CRUD.Service;

import com.backend.CRUD.Entity.Diagnostico;
import com.backend.CRUD.Entity.HistorialMedico;
import com.backend.CRUD.Entity.Odontologo;
import com.backend.CRUD.Entity.Turno;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiagnosticoService {

    List<Diagnostico> traerDiagnosticos();
    void save(Diagnostico diagnostico);
    void delete(Integer id);
    boolean existsById(Integer id);
    Diagnostico findDiagnostico(Integer id);
    List<Diagnostico> findByFechaTratamiento(LocalDate fechaTratamiento);
    List<Diagnostico> findByFechaTratamientoBetween(LocalDate fechaInicio, LocalDate fechaFin);

    List<Diagnostico> findByOdontologo(Odontologo odontologo);
    List<Diagnostico> findByDiagnostico(String diagnostico);
    Optional<Diagnostico> getOne(Integer id);
    List<Diagnostico> findByOdontologoId(Integer idOdontologo);
    List<Diagnostico> obtenerDiagnosticosPorOdontologo(Integer odontologoId);
    List<Diagnostico> obtenerDiagnosticosPorPaciente(Integer pacienteId);






}
