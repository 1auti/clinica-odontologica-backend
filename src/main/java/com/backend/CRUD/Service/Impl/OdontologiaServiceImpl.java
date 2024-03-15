package com.backend.CRUD.Service.Impl;

import com.backend.CRUD.Entity.*;
import com.backend.CRUD.Repository.HorarioRepository;
import com.backend.CRUD.Repository.OdontologoRepository;
import com.backend.CRUD.Service.OdontologoService;
import com.backend.SECURITY.User.Rol;
import com.backend.SECURITY.User.Service.UserService;
import com.backend.SECURITY.User.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class OdontologiaServiceImpl implements OdontologoService {

    @Autowired
    OdontologoRepository repository;
    @Autowired
    HorarioRepository horarioRepository;
    @Autowired
    UserService userService;


    @Override
    public List<Odontologo> getOdontologos() {
        return repository.findAll();
    }

    @Override
    public void saveOdontologo(Odontologo odontologo) {
        repository.save(odontologo);
    }

    @Override
    public void delateOdontologo(Integer id) {

        try {
            Odontologo odontologo = findOdontologo(id);
            if(odontologo.getId() != null && odontologo.getUser().getId() != null ){
                horarioRepository.deleteById(odontologo.getHorario().getId_horario());
                userService.deleteUser(odontologo.getUser().getId());
                repository.deleteById(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Odontologo findOdontologo(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return repository.existsByNombre(nombre);
    }

    @Override
    public Optional<Odontologo> getOne(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Odontologo> getByNombre(String nombre) {
        return repository.findByNombre(nombre);
    }

    @Override
    public List<Odontologo> getOdontologosTipoEspecialidad(String especialidad) {
        return repository.findAll()
                .stream()
                .filter(odontologo -> odontologo.getEspecialidad() != null && odontologo.getEspecialidad().equalsIgnoreCase(especialidad))
                .collect(Collectors.toList());
    }

    @Override
    public Odontologo asignarOdontologoPorEspecialidad(String especialidad) {
        List<Odontologo> odontologoList = getOdontologosTipoEspecialidad(especialidad);

        Odontologo odontologo = pickRandomItem(odontologoList);

        return odontologo;

    }

    public static <T> T pickRandomItem(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null; // Retorna null si la lista está vacía o es nula
        }

        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    @Override
    public boolean existsByIdListOdontologo(Integer id, List<Odontologo> odontologoList) {
        int i = 0;
        Boolean odontologo = false;
        while (i < odontologoList.size()) {
            if (Objects.equals(odontologoList.get(i).getId(), id)) {
                odontologo = true;
                i = odontologoList.size() + 1;
            }
            i++;
        }

        return odontologo;
    }

    @Override
    public Odontologo existsByIdInList(Integer id, List<Odontologo> odontologoList) {
        int i = 0;
        Odontologo odontologo = null;
        while (i < odontologoList.size()) {
            if (Objects.equals(odontologoList.get(i).getId(), id)) {
                odontologo = odontologoList.get(i);
                i = odontologoList.size() + 1;
            }
            i++;
        }

        return odontologo;
    }

    @Override
    public Odontologo crearOdontologo(Odontologo odontologo, MultipartFile file) {

        //USER
        User user = userService.findByEmail(odontologo.getEmail()).orElse(null);

        if(user != null){
            user.setRol(Rol.MANAGER);
            odontologo.setUser(user);
        }

        //HORARIO
        // Crear un nuevo Horario y establecer los valores
        Horario horario = new Horario();

        // Asignar valores al horario
        horario.setHorario_inicio(odontologo.getHorario().getHorario_inicio());
        horario.setHorario_fin(odontologo.getHorario().getHorario_fin());
        horario.setDias(odontologo.getHorario().getDias());

        // Establecer la relación bidireccional
        odontologo.setHorario(horario);
        horario.setOdontologo(odontologo);

        // Guardar el Horario antes de establecer la relación bidireccional
        horarioRepository.save(horario);

        //IMAGEN
        if(!file.isEmpty()){
            //Path directorioImagen = Paths.get("C:/Users/Lautaro/Desktop/Proyectos/proyecto-clinica-odontologica/frontend/src/imagenes");
            String rutaAbsoluta = "C://Users//Lautaro//Desktop//Desarrollo-web//Proyectos//proyecto-clinica-odontologica//imagenes";

            try {
                byte[] byteImg = file.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
                Files.write(rutaCompleta,byteImg);
                odontologo.setImg(file.getOriginalFilename());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Guardar el Odontologo (y se guardará también el Horario debido a la cascada)
        repository.save(odontologo);

        return odontologo;
    }

    @Override
    public Optional<Odontologo> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }


}

