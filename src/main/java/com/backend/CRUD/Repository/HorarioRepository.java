package com.backend.CRUD.Repository;

import com.backend.CRUD.Entity.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HorarioRepository extends JpaRepository<Horario,Integer> {
}
