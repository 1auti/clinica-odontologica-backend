package com.backend.CRUD.Repository;

import com.backend.CRUD.Entity.HistorialMedico;
import com.backend.CRUD.Entity.Odontologo;
import com.backend.CRUD.Entity.Paciente;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico,Integer> {


    // Buscar historial médico por paciente
    List<HistorialMedico> findByPaciente(Paciente paciente);
    @Query("SELECT hm FROM HistorialMedico hm JOIN hm.diagnosticoList d WHERE d.odontologo.id = :odontologoId")
    List<HistorialMedico> findHistorialesMedicosByOdontologoId(@Param("odontologoId") Integer odontologoId);
}


