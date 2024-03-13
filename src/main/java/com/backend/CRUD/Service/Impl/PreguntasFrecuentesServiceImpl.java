package com.backend.CRUD.Service.Impl;

import com.backend.CRUD.Entity.PreguntasFrecuentes;
import com.backend.CRUD.Repository.PreguntasFrecuentesRepository;
import com.backend.CRUD.Service.PreguntasFrecuentesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PreguntasFrecuentesServiceImpl implements PreguntasFrecuentesService {

    @Autowired
    PreguntasFrecuentesRepository repository;


    @Override
    public List<PreguntasFrecuentes> obtenerTodasLasPreguntas() {
        return repository.findAll();
    }

    @Override
    public List<PreguntasFrecuentes> obtenerPorRespondido(boolean respondido) {
        return repository.findByRespondido(respondido);
    }

    @Override
    public PreguntasFrecuentes obtenerPreguntaPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }



    @Override
    public PreguntasFrecuentes crearPregunta(PreguntasFrecuentes preguntasFrecuentes) {
       return repository.save(preguntasFrecuentes);
    }

    @Override
    public PreguntasFrecuentes responderPregunta(Integer id, String respueta) {

        PreguntasFrecuentes pregunta = repository.findById(id).orElse(null);

        if(pregunta != null){
            pregunta.setRespuesta(respueta);
            pregunta.setRespondido(true);
            return repository.save(pregunta);
        }else{
            return null;
        }
    }

    @Override
    public void eliminarPregunta(Integer id) {
           repository.deleteById(id);
    }
}
