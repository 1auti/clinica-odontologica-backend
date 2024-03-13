package com.backend.CRUD.Service.Impl;

import com.backend.CRUD.Entity.Odontologo;
import com.backend.CRUD.Entity.Paciente;
import com.backend.CRUD.Entity.Turno;
import com.backend.CRUD.Repository.OdontologoRepository;
import com.backend.CRUD.Repository.PacienteRepository;
import com.backend.CRUD.Repository.TurnoRepository;
import com.backend.CRUD.Service.TurnoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TurnoServiceImpl implements TurnoService {

    @Autowired
    TurnoRepository repository;
    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    OdontologoRepository odontologoRepository;

    @Override
    public List<Turno> getTurnos() {
        return repository.findAll();
    }

    @Override
    public Turno saveTurno(Turno turno) {
       repository.save(turno);
       return turno;
    }

    @Override
    public void delateTurno(Integer id) {
          repository.deleteById(id);
    }


    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return false;
    }


    @Override
    public Optional<Turno> getOne(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Turno> getByNombre(String nombre) {
        return Optional.empty();
    }

    @Override
    public boolean generarTurno(Turno turno) {
        Paciente paciente = pacienteRepository.findById(turno.getPaciente().getId()).orElse(null);
        Odontologo odontologo = odontologoRepository.findById(turno.getOdontologo().getId()).orElse(null);

        if(paciente != null && odontologo != null && !repository.existsByOdontologoIdAndFechaAndHorario(turno.getOdontologo().getId(), turno.getFecha(), turno.getHorario())){
           return true;
        }else{
            String mensaje = "No cumple las condicines para generar el turno";
            return false;
        }
    }


    @Override
    public List<Turno> obtenerTurnosPaciente(Integer idPaciente) {
        Paciente paciente = pacienteRepository.findById(idPaciente).orElse(null);
        return paciente != null ? paciente.getTurnos() : Collections.emptyList();
    }

    @Override
     public List<Turno> obtenerTurnosOdontologo(Integer idOdontologo){
        Odontologo odontologo = odontologoRepository.findById(idOdontologo).orElse(null);
        return odontologo != null ? odontologo.getTurnoList() : Collections.emptyList();
    }

/*
    @Override
    public List<Turno> obtenerTurnosDePaciente(Paciente paciente) {
           return repository.findByPaciente(paciente);
    }

    @Override
    public List<Turno> obtenerTurnosDeOdontologo(Odontologo odontologo) {
        return repository.findByOdontologo(odontologo);
    }

    /*

    @Override
    public List<Turno> generarTurnosParaDia(LocalDateTime fecha) {
        List<Turno> turnosPosibles = new ArrayList<>();

        // Establece el horario de inicio y fin del día para los turnos
        LocalDateTime inicioDelDia = fecha.with(LocalTime.MIN);
        LocalDateTime finDelDia = fecha.with(LocalTime.MAX);

        // Define la duración de los turnos (por ejemplo, 30 minutos)
        Duration duracionDelTurno = Duration.ofMinutes(30);

        // Crea turnos en intervalos regulares dentro del día
        LocalDateTime turnoActual = inicioDelDia;
        while (turnoActual.isBefore(finDelDia)) {
            Turno turno = new Turno();
            turno.s(turnoActual);

            // Agrega el turno a la lista de turnos posibles
            turnosPosibles.add(turno);

            // Avanza al siguiente intervalo de turno
            turnoActual = turnoActual.plus(duracionDelTurno);
        }

        return turnosPosibles;
    }


     */

    /*
    @Override
    public List<Turno> consultarTurnosDisponiblesPorEspecialidad(String especialidad, LocalTime fechaInicio, LocalTime fechaFin) {
        return repository.consultarTurnosDisponiblesPorEspecialidad(especialidad,fechaInicio,fechaFin);
    }

    @Override
    public List<Turno> consultarOdontologoYFecha(Odontologo odontologo, LocalTime inicio, LocalTime fin) {
        return repository.consultarOdontologoYFecha(odontologo,inicio,fin);
    }

    @Override
    public List<Turno> consultarPacienteYFecha(Paciente paciente, LocalTime inicio, LocalTime fin) {
        return repository.consultarTurnoPorPacienteYFecha(paciente,inicio,fin);
    }

    @Override
    public Optional<Turno> getTurnoEspecifico(Integer pacienteId, Integer turnoId) {
        return repository.findTurnoEspecifico(pacienteId,turnoId);
    }

     */
}
