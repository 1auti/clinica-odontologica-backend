package com.backend.CRUD.Service;

import com.backend.CRUD.Entity.Odontologo;
import com.backend.CRUD.Entity.Paciente;
import com.backend.CRUD.Entity.Turno;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TurnoService {

    List<Turno> getTurnos();
    Turno saveTurno(Turno turno);
    void delateTurno(Integer id);
    boolean existsById(Integer id);
    boolean existsByNombre(String nombre);
    Optional<Turno> getOne(Integer id);
    Optional<Turno> getByNombre(String nombre);
    boolean generarTurno(Turno turno);
    List<Turno> obtenerTurnosPaciente(Integer idPaciente);
    List<Turno> obtenerTurnosOdontologo(Integer idOdontologo);

}
