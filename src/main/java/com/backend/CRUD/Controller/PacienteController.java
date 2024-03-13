package com.backend.CRUD.Controller;


import com.backend.CRUD.DTO.PacienteDto;
import com.backend.CRUD.Entity.HistorialMedico;
import com.backend.CRUD.Entity.Paciente;
import com.backend.CRUD.Service.PacienteService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/paciente")
public class PacienteController {

   @Autowired
   PacienteService service;


   @GetMapping("/traerPacientes")
   public ResponseEntity<List<Paciente>> traerPacients(){
        List<Paciente>  lista = service.getPacientes();
        return new ResponseEntity<>(lista, HttpStatus.OK);

   }

   @GetMapping("/traerPacientes/tipoSangre")
   public ResponseEntity<List<Paciente>> traerPacientesTipoSangre(@RequestParam String tipoSangre){
       List<Paciente> lista = service.traerSegunTipoDeSangre(tipoSangre);
       return new ResponseEntity<>(lista,HttpStatus.CREATED);
   }

   @GetMapping("/traerPacientes/tipoObraSocial")
   public ResponseEntity<List<Paciente>> traerPacientesTipoObraSocial(@RequestParam String tipoObraSocial){
       List<Paciente> lista = service.traerPacientesConXObraSocial(tipoObraSocial);
       return new ResponseEntity<>(lista,HttpStatus.CREATED);
   }

   @GetMapping("/traerPacientes/TenganObraSocial")
   public ResponseEntity<List<Paciente>> traerPacientesTenganObraSocial(@RequestParam boolean tieneObraSocial){
       List<Paciente> lista = service.traerPacientesConOS(tieneObraSocial);
       return new ResponseEntity<>(lista,HttpStatus.CREATED);
   }

   @GetMapping("/traerPacientes/traerMenores")
   public ResponseEntity<List<Paciente>> traerPacientesMenores(){
       List<Paciente> list = service.traerPacientesMenores();
       return new ResponseEntity<>(list,HttpStatus.CREATED);
   }

   @GetMapping("/traerPacientes/traerMayores")
   public ResponseEntity<List<Paciente>> traerPacientesMayores(){
       List<Paciente> list = service.traerPacientesMayores();
       return new ResponseEntity<>(list,HttpStatus.CREATED);
   }

    @GetMapping("/obtenerHistorialId/{pacienteId}")
    public ResponseEntity<Integer> obtenerHistorialId(@PathVariable Integer pacienteId) {
        Integer historialId = service.obtenerHistorialMedico(pacienteId);
        return new ResponseEntity<>(historialId, HttpStatus.OK);
    }

    @GetMapping("/obtenerHistorial/{pacienteId}")
    public ResponseEntity<HistorialMedico> obtenerHistorial(@PathVariable Integer pacienteId) {

        Paciente paciente = new Paciente();
        paciente = service.findPaciente(pacienteId);
        HistorialMedico historialMedico = paciente.getHistorialMedico();
        return new ResponseEntity<>(historialMedico, HttpStatus.OK);
    }


    @PostMapping( "/crearPaciente")
    public ResponseEntity<Paciente> crearPaciente(
            @RequestPart ("pacienteRequest") Paciente pacienteRequest,
            @RequestPart("file") MultipartFile file
    ) {
        try {
            Paciente paciente = service.crearPacienteConHistorial(pacienteRequest,file);
            return new ResponseEntity<>(paciente, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @DeleteMapping("/borrarPaciente/{id}")
    public ResponseEntity<?> borrarPaciente(@PathVariable Integer id){
    try{
        service.deletePaciente(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }catch (Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

   }

   @PutMapping("/editarPaciente/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody PacienteDto pacienteDto){

       if(!service.existsById(id)){
           return new ResponseEntity<>( HttpStatus.NOT_FOUND);
       }
       if(service.existsByNombre(pacienteDto.getNombre()) && !Objects.equals(service.getByNombre(pacienteDto.getNombre()).get().getId(), id)){
           return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
       }
       if(StringUtils.isBlank(pacienteDto.getNombre())){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }

       Paciente paciente = service.getOne(id).get();

       paciente.setNombre(pacienteDto.getNombre());
       paciente.setApellido(pacienteDto.getApellido());
       paciente.setTiene_OS(pacienteDto.isTiene_OS());
       paciente.setTurnos(pacienteDto.getTurnos());
       paciente.setTipo_sangre(pacienteDto.getTipo_sangre());
       paciente.setDireccion(pacienteDto.getDireccion());
       paciente.setDni(pacienteDto.getDni());
       paciente.setFechaNacimiento(pacienteDto.getFechaNacimiento());
       paciente.setTelefono(pacienteDto.getTelefono());

       service.savePaciente(paciente);


       return new ResponseEntity<>(HttpStatus.OK);

   }

    @GetMapping("/detallesPaciente/{id}")
    public ResponseEntity<Paciente> detallesPaciente(@PathVariable("id")Integer id){

       List<Paciente> pacienteList = service.getPacientes();

        if (service.existsByIdInList(id,pacienteList) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Paciente paciente = service.existsByIdInList(id,pacienteList);
        return new ResponseEntity<>(paciente, HttpStatus.OK);
    }

    @GetMapping("/buscarPaciente/{id}")
    public Paciente buscarPaciente(@PathVariable ("id") Integer id){
       return service.findPaciente(id);
    }

    @GetMapping("/buscarPorEmail")
    public ResponseEntity<Optional<Paciente>>buscarEmail(@RequestParam String email){
       Optional<Paciente> paciente = service.getByEmail(email);
       if(paciente.isEmpty()){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }else {
           return new ResponseEntity<>(paciente,HttpStatus.OK);
       }

    }


    }









