package com.backend.CRUD.Service;

import com.backend.CRUD.Entity.Odontologo;
import com.backend.CRUD.Entity.Paciente;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface OdontologoService {

    List<Odontologo> getOdontologos();
    void saveOdontologo(Odontologo odontologo);
    void delateOdontologo(Integer id);
    Odontologo findOdontologo(Integer id);
    boolean existsById(Integer id);
    boolean existsByNombre(String nombre);
    Optional<Odontologo> getOne(Integer id);
    Optional<Odontologo> getByNombre(String nombre);
    List<Odontologo> getOdontologosTipoEspecialidad(String especialidad);
    Odontologo asignarOdontologoPorEspecialidad(String especialidad);
    boolean existsByIdListOdontologo(Integer id,List<Odontologo> odontologoList);
    Odontologo existsByIdInList(Integer id,List<Odontologo> odontologoList);
    Odontologo crearOdontologo(Odontologo odontologo, MultipartFile file);
    Optional<Odontologo> buscarPorEmail(String email);


}
