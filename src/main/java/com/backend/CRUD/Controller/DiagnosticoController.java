package com.backend.CRUD.Controller;


import com.backend.CRUD.DTO.DiagnosticoDto;
import com.backend.CRUD.DTO.HistorialDto;
import com.backend.CRUD.Entity.Diagnostico;
import com.backend.CRUD.Entity.HistorialMedico;
import com.backend.CRUD.Entity.Odontologo;
import com.backend.CRUD.Entity.Turno;
import com.backend.CRUD.Service.DiagnosticoService;
import com.backend.CRUD.Service.HistorialMedicoService;
import com.backend.CRUD.Service.OdontologoService;
import com.backend.CRUD.Service.TurnoService;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import jdk.jshell.Diag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/historial/diagnostico")
@CrossOrigin(origins = "http://localhost:4200")
@Transactional
public class DiagnosticoController {

    @Autowired
    DiagnosticoService service;
    @Autowired
    OdontologoService odontologoService;
    @Autowired
    HistorialMedicoService historialMedicoService;


    @GetMapping("/traerDiagnosticos")
    public ResponseEntity<List<Diagnostico>> traerDiagnosticos(){

        List<Diagnostico> diagnosticoList = service.traerDiagnosticos();
        return new ResponseEntity<>(diagnosticoList,HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/crearDiagnostico/{historialId}")
    public ResponseEntity<?> crearDiagnostico(@PathVariable Integer historialId,
                                              @RequestBody Diagnostico diagnostico) {
        try {
            // Obtener el historial médico existente
            HistorialMedico historialMedico = historialMedicoService.getOne(historialId).orElse(null);
            //Obtenemos el odontolgo existente
            Odontologo odontologo = odontologoService.findOdontologo(diagnostico.getOdontologo().getId());


            if (historialMedico == null) {
                return new ResponseEntity<>("No se ha encontrado el historial médico", HttpStatus.NOT_FOUND);
            }

            if(odontologo == null){
                return new ResponseEntity<>("No se ha encontrado el odontologo",HttpStatus.NOT_FOUND);
            }




            // Asociar el diagnóstico con el historial médico
            diagnostico.setHistorialMedico(historialMedico);
            // Asociar el diagnistico con el odontologo
            diagnostico.setOdontologo(odontologo);


            // Agregar el diagnóstico a la lista y establecer la relación inversa
            List<Diagnostico> diagnosticoList = historialMedico.getDiagnosticoList();
            if (diagnosticoList == null) {
                diagnosticoList = new ArrayList<>();
                historialMedico.setDiagnosticoList(diagnosticoList);
            }
            diagnosticoList.add(diagnostico);

            historialMedico.setDiagnosticoList(diagnosticoList);

            // Guardar el diagnóstico y actualizar el historial médico
            historialMedicoService.saveHistorial(historialMedico);
            service.save(diagnostico);


            return new ResponseEntity<>(diagnostico, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            // Manejar la excepción y enviar un error
            return new ResponseEntity<>("Error al crear el diagnóstico", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/borrarDiagnostico/{id}")
    public ResponseEntity<?> borrarDiagnostico(@PathVariable Integer id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/editarDiagnostico/{id}")
    public ResponseEntity<?> updateDiagnostico(@PathVariable Integer id, @RequestBody DiagnosticoDto diagnosticoDto){

        if(!service.existsById(id)){
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
        if(StringUtils.isBlank(diagnosticoDto.getDiagnostico())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Diagnostico diagnostico = service.getOne(id).get();

        diagnostico.setDiagnostico(diagnosticoDto.getDiagnostico());
        diagnostico.setOdontologo(diagnosticoDto.getOdontologo());
        diagnostico.setFechaTratamiento(diagnosticoDto.getFechaTratamiento());



        service.save(diagnostico);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/detallesDiagnostico/{id}")
    public ResponseEntity<Diagnostico> detallesDiagnostico(@PathVariable("id")Integer id){
        if(!service.existsById(id)){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

        Diagnostico diagnostico = service.getOne(id).get();
        return new ResponseEntity<>(diagnostico,HttpStatus.OK);
    }

    @GetMapping("/traerDiagnostico/PorDiagnostico")
    public ResponseEntity<List<Diagnostico>> traerPorDiagnostico(@RequestParam String diagnostico) {
        try {
            List<Diagnostico> lista = service.findByDiagnostico(diagnostico);

            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            // Manejar excepciones y devolver un código de estado apropiado
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @   GetMapping("/traerDiagnostico/PorFechas")
    public ResponseEntity<List<Diagnostico>> traerDiagnosticoPorFechas(
            @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin) {
        List<Diagnostico> lista = service.findByFechaTratamientoBetween(fechaInicio, fechaFin);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


    @GetMapping("/traerDiagnostico/fechaTratamiento/{fechaTratamiento}")
    public ResponseEntity<List<Diagnostico>> traerDiagnosticoMedicoFechaTratamiento(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaTratamiento) {
        List<Diagnostico> lista = service.findByFechaTratamiento(fechaTratamiento);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


    @GetMapping("/traerDiagnostico/PorOdontologo/{idOdontologo}")
    public ResponseEntity<List<Diagnostico>> traerDiagnosticoPorOdontologo(@PathVariable Integer idOdontologo) {
        List<Diagnostico> lista = service.findByOdontologoId(idOdontologo);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    /*
    @GetMapping("/traerDiagnostico/PorFiltro")
    public ResponseEntity<List<Diagnostico>> traerDiagnosticoPorFiltro(
            @RequestParam(required = false) Integer odontologoId,
            @RequestParam(required = false) String diagnostico,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin) {

        List<Diagnostico> lista = null;

        if (odontologoId != null) {
            // Realiza la búsqueda por odontólogo
            lista = service.findByOdontologoId(odontologoId);
        } else if (diagnostico != null) {
            // Realiza la búsqueda por diagnóstico
            lista = service.findByDiagnostico(diagnostico);
        } else if (fechaInicio != null && fechaFin != null) {
            // Realiza la búsqueda por fechas
            lista = service.findByFechaTratamientoBetween(fechaInicio, fechaFin);
        }

        if (lista == null || lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

     */


    @Transactional
    @GetMapping("traerDiagnosticos/porPaciente/{pacienteId}")
    public ResponseEntity<List<Diagnostico>> obtenerDiagnosticosPorPaciente(@PathVariable Integer pacienteId) {
        try {
            List<Diagnostico> diagnosticos = service.obtenerDiagnosticosPorPaciente(pacienteId);
            return new ResponseEntity<>(diagnosticos, HttpStatus.OK);
        } catch (Exception e) {
            // Registra la excepción para diagnóstico
            e.printStackTrace(); // Puedes usar un logger en lugar de imprimir en la consola
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("traerDiagnostico/{diagnosticoId}")
    public ResponseEntity<Diagnostico> obtenerDiagnostico(@PathVariable Integer diagnosticoId){

        Diagnostico diagnostico = service.findDiagnostico(diagnosticoId);
        if(diagnostico == null){
            return new ResponseEntity<Diagnostico>(diagnostico,HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }






}
