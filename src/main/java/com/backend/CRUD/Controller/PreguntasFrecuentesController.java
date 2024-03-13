package com.backend.CRUD.Controller;

import com.backend.CRUD.Entity.PreguntasFrecuentes;
import com.backend.CRUD.Service.PreguntasFrecuentesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/preguntasFrecuentes")
@CrossOrigin(origins = "http://localhost:4200")
public class PreguntasFrecuentesController {

    @Autowired
    PreguntasFrecuentesService service;

    @GetMapping("/traerPreguntas")
    public ResponseEntity<List<PreguntasFrecuentes>> obtenerTodasLasPreguntas() {
        List<PreguntasFrecuentes> preguntas = service.obtenerTodasLasPreguntas();
        return new ResponseEntity<>(preguntas, HttpStatus.OK);
    }

    @GetMapping("/traerPreguntas/porRespondido/{respondido}")
    public ResponseEntity<List<PreguntasFrecuentes>> obtenerPreguntasPorRespondido(@PathVariable boolean respondido) {
        List<PreguntasFrecuentes> preguntas = service.obtenerPorRespondido(respondido);
        return new ResponseEntity<>(preguntas, HttpStatus.OK);
    }

    @GetMapping("/traerPregunta/{id}")
    public ResponseEntity<PreguntasFrecuentes> obtenerPreguntaPorId(@PathVariable Integer id) {
        PreguntasFrecuentes pregunta = service.obtenerPreguntaPorId(id);
        if (pregunta != null) {
            return new ResponseEntity<>(pregunta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/crearPregunta")
    public ResponseEntity<PreguntasFrecuentes> crearPregunta(@RequestBody PreguntasFrecuentes pregunta) {
        PreguntasFrecuentes preguntaCreada = service.crearPregunta(pregunta);
        return new ResponseEntity<>(preguntaCreada, HttpStatus.CREATED);
    }

    @PostMapping("/responderPregunta/{id}")
    public ResponseEntity<PreguntasFrecuentes> responderPregunta(
            @PathVariable Integer id,
            @RequestParam String respuesta) {
        System.out.println("ID recibido: " + id);
        System.out.println("Respuesta recibida: " + respuesta);
        PreguntasFrecuentes preguntaRespondida = service.responderPregunta(id, respuesta);
        if (preguntaRespondida != null) {
            return new ResponseEntity<>(preguntaRespondida, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminarPregunta/{id}")
    public ResponseEntity<Void> eliminarPregunta(@PathVariable Integer id) {
        service.eliminarPregunta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
