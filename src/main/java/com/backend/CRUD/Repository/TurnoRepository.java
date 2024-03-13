package com.backend.CRUD.Repository;

import com.backend.CRUD.Entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface TurnoRepository extends JpaRepository<Turno,Integer> {

    boolean existsByOdontologoIdAndFechaAndHorario(Integer idOdontologo, LocalDate fecha, LocalTime horario);

}

