package com.backend.CRUD.Service;

import com.backend.CRUD.Entity.HistorialMedico;
import com.backend.CRUD.Entity.Odontologo;
import com.backend.CRUD.Entity.Paciente;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HistorialMedicoService {

    void saveHistorial(HistorialMedico historialMedico);
    void deleteHistorial(Integer id);
    List<HistorialMedico> getHistorialMedico();
    List<HistorialMedico> getHisotorialMedicoOdontologos(Integer odontologoId);
    boolean existsById(Integer id);
    List<HistorialMedico> findByPaciente(Paciente paciente);
    Optional<HistorialMedico> getOne(Integer id);

}
