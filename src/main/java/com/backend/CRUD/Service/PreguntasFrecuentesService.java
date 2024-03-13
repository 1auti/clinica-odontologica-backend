package com.backend.CRUD.Service;

import com.backend.CRUD.Entity.PreguntasFrecuentes;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PreguntasFrecuentesService {

    List<PreguntasFrecuentes> obtenerTodasLasPreguntas();
    List<PreguntasFrecuentes> obtenerPorRespondido(boolean respondido);
    PreguntasFrecuentes obtenerPreguntaPorId(Integer id);
    PreguntasFrecuentes crearPregunta(PreguntasFrecuentes preguntasFrecuentes);
    PreguntasFrecuentes responderPregunta(Integer id , String respueta);
    void eliminarPregunta(Integer id);





}
