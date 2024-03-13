package com.backend.CRUD.Controller;

import com.backend.CRUD.DTO.HistorialDto;
import com.backend.CRUD.Entity.Diagnostico;
import com.backend.CRUD.Entity.HistorialMedico;
import com.backend.CRUD.Entity.Odontologo;
import com.backend.CRUD.Entity.Paciente;
import com.backend.CRUD.Repository.OdontologoRepository;
import com.backend.CRUD.Repository.PacienteRepository;
import com.backend.CRUD.Service.HistorialMedicoService;
import com.backend.CRUD.Service.OdontologoService;
import com.backend.CRUD.Service.PacienteService;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/historial")
@CrossOrigin(origins = "http://localhost:4200")
@Transactional
public class HistorialMedicoController {

    @Autowired
    HistorialMedicoService service;
    @Autowired
    PacienteService pacienteService;


    @GetMapping("/traerHistorialMedico")
    public ResponseEntity<List<HistorialMedico>> traerHistorial(){
        List<HistorialMedico> lista = service.getHistorialMedico();
        return new ResponseEntity<>(lista, HttpStatus.CREATED);
    }



    @PostMapping("/crearHistorialConDiagnostico")
    public ResponseEntity<?> crearHistorialConDiagnostico(@RequestBody HistorialMedico historialMedico) {
        try {
            Paciente paciente = pacienteService.findPaciente(historialMedico.getPaciente().getId());
            historialMedico.setPaciente(paciente);


            // Asegúrate de que la lista de diagnósticos no sea nula antes de guardar
            if (historialMedico.getDiagnosticoList() != null) {
                historialMedico.getDiagnosticoList().forEach(diagnostico -> diagnostico.setHistorialMedico(historialMedico));
            }

            service.saveHistorial(historialMedico);

            return new ResponseEntity<>(historialMedico, HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejar la excepción y enviar un error
            return new ResponseEntity<>("Error al crear el historial médico", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






    @DeleteMapping("/borrarHistorial/{id}")
    public ResponseEntity<?> borrarHistorial(@PathVariable Integer id) {
        service.deleteHistorial(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /*
    @PutMapping("/editarHistorial/{id}")
    public ResponseEntity<?> updateHistorial(@PathVariable Integer id, @RequestBody HistorialDto historialDto){

        if(!service.existsById(id)){
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
        if(StringUtils.isBlank(historialDto.getDiagnostico())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        HistorialMedico historialMedico = service.getOne(id).get();


        historialMedico.setPaciente(historialDto.getPaciente());

        service.saveHistorial(historialMedico);

        return new ResponseEntity<>(HttpStatus.OK);

    }

*/

    @GetMapping("/detallesHistorial/{id}")
    public ResponseEntity<HistorialMedico> detallesHistorial(@PathVariable("id")Integer id){
        if(!service.existsById(id)){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

        HistorialMedico historialMedico = service.getOne(id).get();
        return new ResponseEntity<>(historialMedico,HttpStatus.OK);
    }



    @GetMapping("/traerHistorial/PorPaciente/{pacienteId}")
    public ResponseEntity<HistorialMedico> traerHistorialMedicoPorPaciente(@PathVariable Integer pacienteId) {
        Paciente paciente = new Paciente();
        paciente = pacienteService.findPaciente(pacienteId);

        //List<HistorialMedico> lista = service.findByPaciente(paciente);
        HistorialMedico historialMedico = paciente.getHistorialMedico();
        return new ResponseEntity<>(historialMedico, HttpStatus.CREATED);
    }

    @GetMapping("/traerHistorial/PorOdontologo/{odontologoId}")
    public ResponseEntity<List<HistorialMedico>> traerHistorialPorOdontologo(@PathVariable Integer odontologoId) {
        // Lógica para obtener el historial médico por el ID del odontólogo
        List<HistorialMedico> historiales = service.getHisotorialMedicoOdontologos(odontologoId);
        return new ResponseEntity<>(historiales, HttpStatus.OK);
    }


    @GetMapping("/traerDiagnostico/PorPaciente/{pacienteId}")
    public ResponseEntity<List<Diagnostico>> traerDiagnosticosPorPaciente(@PathVariable Integer pacienteId) {
        Optional<Paciente> pacienteOptional = pacienteService.getOne(pacienteId);

        if (pacienteOptional.isPresent()) {
            HistorialMedico historialMedico = pacienteOptional.get().getHistorialMedico();

            if (historialMedico != null) {
                List<Diagnostico> listaDiagnostico = historialMedico.getDiagnosticoList();
                return new ResponseEntity<>(listaDiagnostico, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }








}
    