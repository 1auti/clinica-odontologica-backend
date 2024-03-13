package com.backend.CRUD.Service;

import com.backend.CRUD.Entity.Paciente;
import com.backend.CRUD.Entity.Turno;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PacienteService {

    List<Paciente> getPacientes();
    void savePaciente(Paciente paciente);
    void deletePaciente(Integer id);
    Paciente findPaciente(Integer id);
    Paciente existsByIdInList(Integer id,List<Paciente> pacienteList);
    boolean existsByIdListPaciente(Integer id,List<Paciente> pacienteList);
    boolean existsById(Integer id);
    boolean existsByNombre(String nombre);
    Optional<Paciente> getOne(Integer id);
    Optional<Paciente> getByNombre(String nombre);
    Optional<Paciente> getByEmail(String email);
    List<Paciente> traerPacientesConOS(boolean conObraSocial);
    List<Paciente> traerPacientesConXObraSocial(String obraSocial);
    List<Paciente> traerSegunTipoDeSangre(String tipoDeSangre);
    List<Paciente> traerPacientesMenores();
    List<Paciente> traerPacientesMayores();
    Paciente crearPacienteConHistorial(Paciente pacienteRequest,MultipartFile file);
    Integer obtenerHistorialMedico(Integer idPaciente);























}
