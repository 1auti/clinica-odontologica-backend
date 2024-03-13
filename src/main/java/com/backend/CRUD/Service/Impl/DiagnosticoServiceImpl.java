package com.backend.CRUD.Service.Impl;

import com.backend.CRUD.Entity.Diagnostico;
import com.backend.CRUD.Entity.Odontologo;
import com.backend.CRUD.Entity.Paciente;
import com.backend.CRUD.Entity.Turno;
import com.backend.CRUD.Repository.DiagnosticoRepository;
import com.backend.CRUD.Service.DiagnosticoService;
import com.backend.CRUD.Service.OdontologoService;
import com.backend.CRUD.Service.PacienteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DiagnosticoServiceImpl implements DiagnosticoService {

    @Autowired
    DiagnosticoRepository repository;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    OdontologoService odontologoService;

    @Override
    public List<Diagnostico> traerDiagnosticos() {
        return repository.findAll();
    }

    @Override
    public void save(Diagnostico diagnostico) {
        repository.save(diagnostico);
    }

    @Override
    public void delete(Integer id) {
         repository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public Diagnostico findDiagnostico(Integer id) {
       return repository.findById(id).orElse(null);
    }

    @Override
    public List<Diagnostico> findByFechaTratamiento(LocalDate fechaTratamiento) {
        return repository.findByFechaTratamiento(fechaTratamiento);
    }

    @Override
    public List<Diagnostico> findByFechaTratamientoBetween(LocalDate fechaInicio, LocalDate fechaFin) {
        return repository.findByFechaTratamientoBetween(fechaInicio,fechaFin);
    }



    @Override
    public List<Diagnostico> findByOdontologo(Odontologo odontologo) {
        return repository.findByOdontologo(odontologo);
    }

    @Override
    public List<Diagnostico> findByDiagnostico(String diagnostico) {
        return repository.findByDiagnostico(diagnostico);
    }

    @Override
    public Optional<Diagnostico> getOne(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Diagnostico> findByOdontologoId(Integer idOdontologo) {
         return repository.findByOdontologoId(idOdontologo);
    }

    @Override
    public List<Diagnostico> obtenerDiagnosticosPorOdontologo(Integer odontologoId) {
        Odontologo odontologo = odontologoService.getOne(odontologoId).orElse(null);

        if(odontologo != null && odontologo.getDiagnosticos() != null){
            return odontologo.getDiagnosticos();
        }

        return Collections.emptyList();
    }

    @Override
    public List<Diagnostico> obtenerDiagnosticosPorPaciente(Integer pacienteId) {
        Paciente paciente = pacienteService.getOne(pacienteId).orElse(null);

        if (paciente != null && paciente.getHistorialMedico() != null) {
            return paciente.getHistorialMedico().getDiagnosticoList();
        }

        return Collections.emptyList();
    }





    }



