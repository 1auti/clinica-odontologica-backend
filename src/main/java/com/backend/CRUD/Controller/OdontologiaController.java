package com.backend.CRUD.Controller;


import com.backend.CRUD.DTO.OdontologiaDto;
import com.backend.CRUD.Entity.Odontologo;
import com.backend.CRUD.Repository.HorarioRepository;
import com.backend.CRUD.Service.OdontologoService;
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
@RequestMapping("api/v1/odontologos")
public class OdontologiaController {

    @Autowired
    OdontologoService service;
    @Autowired
    HorarioRepository horarioRepository;

    @GetMapping("/traerOdontologos")
    public ResponseEntity<List<Odontologo>> traerOdontologos(){
        List<Odontologo> lista = service.getOdontologos();
        return new ResponseEntity<>(lista, HttpStatus.CREATED);
    }

    @GetMapping("/traerOdontologos/tipoEspecialidad")
    public ResponseEntity<List<Odontologo>> traerOdontologosEspecialidad(@RequestParam String especialidad){
        List<Odontologo> lista = service.getOdontologosTipoEspecialidad(especialidad);
        return new ResponseEntity<>(lista,HttpStatus.CREATED);
    }


    @PostMapping("/crearOdontologos")
    public ResponseEntity<Odontologo> crearOdontologo(
            @RequestPart("odontologo") Odontologo odontologo,
            @RequestPart("file")MultipartFile file
    ) {
        try {

            service.crearOdontologo(odontologo,file);
            return new ResponseEntity<>(odontologo, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping("/borrarOdontologo/{id}")
    public ResponseEntity<?> borrarOdontologo(@PathVariable Integer id){
        service.delateOdontologo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/editarOdontolgo/{id}")
    public ResponseEntity<?> updateOdontologo(@PathVariable Integer id, @RequestBody OdontologiaDto odontologia){

        if(!service.existsById(id)){
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
        if(service.existsByNombre(odontologia.getNombre()) && !Objects.equals(service.getByNombre(odontologia.getNombre()).get().getId(), id)){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(odontologia.getNombre())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        Odontologo odontologo = service.getOne(id).get();

        odontologo.setNombre(odontologia.getNombre());
        odontologo.setApellido(odontologo.getApellido());
        odontologo.setEspecialidad(odontologia.getEspecialidad());
        odontologo.getHorario().setHorario_inicio(odontologia.getHorario().getHorario_inicio());
        odontologo.getHorario().setHorario_fin(odontologia.getHorario().getHorario_fin());
        odontologo.getHorario().setDias(odontologia.getHorario().getDias());
        odontologo.setDireccion(odontologia.getDireccion());
        odontologo.setDni(odontologia.getDni());
        odontologo.setTurnoList(odontologia.getTurnoList());
        odontologo.setFechaNacimiento(odontologia.getFechaNacimiento());
        odontologo.setTelefono(odontologia.getTelefono());

        service.saveOdontologo(odontologo);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/detallesOdontologo/{id}")
    public ResponseEntity<Odontologo> detallesOdontologo(@PathVariable("id")Integer id){
        if(!service.existsById(id)){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

        Odontologo odontologo = service.getOne(id).get();
        return new ResponseEntity<>(odontologo,HttpStatus.OK);

    }
    @GetMapping("/buscarOdontologo/{id}")
    public Odontologo buscarOdontologo(@PathVariable("id") Integer id){
        return service.findOdontologo(id);
    }

    @GetMapping("/buscarOdontologoEmail")
    public ResponseEntity<Optional<Odontologo>> buscarEmail(@RequestParam String email){
        Optional<Odontologo> odontolgo = service.buscarPorEmail(email);
        if(odontolgo.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(odontolgo,HttpStatus.OK);
        }
    }

}
