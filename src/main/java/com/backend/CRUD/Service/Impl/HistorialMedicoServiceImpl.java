package com.backend.CRUD.Service.Impl;

import com.backend.CRUD.Entity.HistorialMedico;
import com.backend.CRUD.Entity.Odontologo;
import com.backend.CRUD.Entity.Paciente;
import com.backend.CRUD.Repository.HistorialMedicoRepository;
import com.backend.CRUD.Service.HistorialMedicoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HistorialMedicoServiceImpl implements HistorialMedicoService {

    @Autowired
    HistorialMedicoRepository repository;

    @Override
    public void saveHistorial(HistorialMedico historialMedico) {
       repository.save(historialMedico);
    }

    @Override
    @Transactional
    public void deleteHistorial(Integer id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            // Maneja la excepción, puedes imprimir el stack trace para depurar
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar el historial médico");
        }
    }


    @Override
    public List<HistorialMedico> getHistorialMedico() {
       return repository.findAll();
    }

    @Override
    public List<HistorialMedico> getHisotorialMedicoOdontologos(Integer odontologoId) {
        return repository.findHistorialesMedicosByOdontologoId(odontologoId);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }



    @Override
    public List<HistorialMedico> findByPaciente(Paciente paciente) {
        return repository.findByPaciente(paciente);
    }



    @Override
    public Optional<HistorialMedico> getOne(Integer id) {
        return repository.findById(id);
    }
}
