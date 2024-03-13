package com.backend.CRUD.Controller;

import com.backend.CRUD.DTO.TurnoDto;
import com.backend.CRUD.Entity.*;
import com.backend.CRUD.Service.OdontologoService;
import com.backend.CRUD.Service.PacienteService;
import com.backend.CRUD.Service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/turno")
@CrossOrigin(origins = "http://localhost:4200")
public class TurnoController {

    @Autowired
    TurnoService service;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    OdontologoService odontologoService;


    @GetMapping("/traerTurnos")
    public ResponseEntity<List<Turno>>  traerTurno(){
        List<Turno> lista = service.getTurnos();
        return new ResponseEntity<>(lista, HttpStatus.CREATED);
    }

    @GetMapping("/traerTurnos/traerPorOdontologo/{idOdontologo}")
    public ResponseEntity<List<Turno>>  traerTurnosPorOdontologo(@PathVariable Integer idOdontologo){
        List<Turno> lista = service.obtenerTurnosOdontologo(idOdontologo);
        return new ResponseEntity<>(lista, HttpStatus.CREATED);
    }

    @GetMapping("/traerTurnos/traerPorPaciente/{idPaciente}")
    public ResponseEntity<List<Turno>> traerTurnosPorPaciente(@PathVariable Integer idPaciente){
        List<Turno> lista = service.obtenerTurnosPaciente(idPaciente);
        return new ResponseEntity<>(lista, HttpStatus.CREATED);
    }


    @PostMapping("/crear")  
    public ResponseEntity<?> crearTurno(
            @RequestPart("turno") Turno turno,
            @RequestPart("file") MultipartFile file
    ) {
        // Obtiene el paciente y el odontólogo desde el servicio
        Paciente paciente = pacienteService.getByEmail(turno.getPaciente().getEmail()).orElse(null);
        Odontologo odontologo = odontologoService.asignarOdontologoPorEspecialidad(turno.getOdontologo().getEspecialidad());
        // Verifica si el paciente y el odontólogo existen
        if (paciente == null) {
            pacienteService.crearPacienteConHistorial(turno.getPaciente(),file);
        }
        if( odontologo == null){
            String mensaje = "No hay odontologos disponibles para este tipo de especialidad,Nos disculpamos";
            return new ResponseEntity<>(mensaje,HttpStatus.NOT_FOUND);
        }

        // Asigna el paciente y el odontólogo al turno

        turno.setOdontologo(odontologo);


        if(!service.generarTurno(turno)){
            String mensajeError = "No se ha podido crear el turno";
            return new ResponseEntity<>(mensajeError,HttpStatus.BAD_REQUEST);
        }





        try {
            // Guarda el turno
            Turno turnoGuardado = service.saveTurno(turno);
            return new ResponseEntity<>(turnoGuardado, HttpStatus.OK);
        } catch (Exception e) {
            String mensajeError = "Error al crear el turno: " + e.getMessage();
            return new ResponseEntity<>(mensajeError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> borrarTurno(@PathVariable Integer id){
        service.delateTurno(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/editar/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody TurnoDto turnoDto){

        if(!service.existsById(id)){
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }




        Turno turno = service.getOne(id).get(); 

        turno.setFecha(turnoDto.getFecha());
        turno.setHorario(turnoDto.getHora());
        turno.setOdontologo(turnoDto.getOdontologo());
        turno.setPaciente(turnoDto.getPaciente());
        turno.setAfeccion(turnoDto.getAfeccion());

        service.saveTurno(turno);


        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/detalles/{id}")
    public ResponseEntity<Turno> getById(@PathVariable("id")Integer id){
        if(!service.existsById(id)){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

        Turno turno = service.getOne(id).get();
        return new ResponseEntity<>(turno,HttpStatus.OK);


    }





}
