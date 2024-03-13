package com.backend.CRUD.Repository;

import com.backend.CRUD.Entity.PreguntasFrecuentes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PreguntasFrecuentesRepository extends JpaRepository<PreguntasFrecuentes,Integer> {
    List<PreguntasFrecuentes> findByRespondido(boolean respondido);
    Optional<PreguntasFrecuentes> findByPregunta(String pregunta);
}
