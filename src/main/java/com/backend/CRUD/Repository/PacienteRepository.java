package com.backend.CRUD.Repository;

import com.backend.CRUD.Entity.HistorialMedico;
import com.backend.CRUD.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Integer> {

    Optional<Paciente> findByNombre(String nombre);
    Optional<Paciente> findByEmail(String email);
    boolean existsByNombre(String nombre);





}
