package com.backend.CRUD.Service.Impl;

import com.backend.CRUD.Entity.HistorialMedico;
import com.backend.CRUD.Entity.Paciente;
import com.backend.CRUD.Repository.PacienteRepository;
import com.backend.CRUD.Service.HistorialMedicoService;
import com.backend.CRUD.Service.PacienteService;
import com.backend.SECURITY.User.Rol;
import com.backend.SECURITY.User.Service.UserService;
import com.backend.SECURITY.User.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {


    @Value("${file.upload-dir}")
    private String uploadDir;
    @Autowired
    PacienteRepository repository;
    @Autowired
    HistorialMedicoService historialMedicoService;
    @Autowired
    UserService userService;







    @Override
    public List<Paciente> getPacientes() {
        return repository.findAll();
    }

    @Override
    public void savePaciente(Paciente paciente) {
         repository.save(paciente);
    }

    @Override
    public void deletePaciente(Integer id) {
        try {
            Paciente paciente = findPaciente(id);

            // Verifica si hay un historial médico antes de intentar eliminarlo
            if (paciente.getHistorialMedico() != null) {
                userService.deleteUser(paciente.getUser().getId());
                historialMedicoService.deleteHistorial(paciente.getHistorialMedico().getId_historial());
                repository.deleteById(id);
            }

        } catch (Exception e) {
            // Maneja la excepción o imprime el stack trace para depurar
            e.printStackTrace();
        }
    }




    @Override
    public Paciente findPaciente(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Paciente existsByIdInList(Integer id,List <Paciente> pacienteList) {

        int i  = 0 ;
        Paciente paciente = null;
        while(i < pacienteList.size()){
            if(Objects.equals(pacienteList.get(i).getId(), id)){
                paciente = pacienteList.get(i);
                i = pacienteList.size() +1 ;
            }
            i++;
        }

        return  paciente;
    }

    @Override
    public boolean existsByIdListPaciente(Integer id, List<Paciente> pacienteList) {
        int i  = 0 ;
        Boolean paciente = false;
        while(i < pacienteList.size()){
            if(Objects.equals(pacienteList.get(i).getId(), id)){
                paciente = true;
                i = pacienteList.size() +1 ;
            }
            i++;
        }

        return  paciente;
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
    public Optional<Paciente> getOne(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Paciente> getByNombre(String nombre) {
        return repository.findByNombre(nombre);
    }

    @Override
    public Optional<Paciente> getByEmail(String email) {
        return repository.findByEmail(email);
    }


    private int calcularEdad(LocalDate fechaNacimiento, LocalDate fechaHoy) {
        return fechaHoy.minusYears(fechaNacimiento.getYear())
                .minusMonths(fechaNacimiento.getMonthValue())
                .minusDays(fechaNacimiento.getDayOfMonth())
                .getYear();
    }

    @Override
    public List<Paciente> traerPacientesConOS(boolean conObraSocial) {
        return repository.findAll()
                .stream()
                .filter(paciente -> ObraSocial(paciente) == conObraSocial)
                .collect(Collectors.toList());
    }

    public boolean ObraSocial(Paciente paciente) {
        // Implementa la lógica para determinar si el paciente tiene obra social o no
        return paciente.isTiene_OS();
    }


    @Override
    public List<Paciente> traerPacientesConXObraSocial(String obraSocial) {
        return repository.findAll()
                .stream()
                .filter(paciente -> paciente.getObraSocial() != null && paciente.getObraSocial().equalsIgnoreCase(obraSocial))
                .collect(Collectors.toList());
    }

    @Override
    public List<Paciente> traerSegunTipoDeSangre(String tipoDeSangre) {
        return repository.findAll()
                .stream()
                .filter(paciente -> paciente.getTipo_sangre() != null && paciente.getTipo_sangre().equalsIgnoreCase(tipoDeSangre))
                .collect(Collectors.toList());
    }
    @Override
    public List<Paciente> traerPacientesMenores() {
        LocalDate fechaHoy = LocalDate.now();
        return repository.findAll().stream()
                .filter(paciente -> calcularEdad(paciente.getFechaNacimiento(), fechaHoy) < 18)
                .collect(Collectors.toList());
    }

    @Override
    public List<Paciente> traerPacientesMayores() {
        LocalDate fechaHoy = LocalDate.now();
        return repository.findAll().stream()
                .filter(paciente -> calcularEdad(paciente.getFechaNacimiento(), fechaHoy) >= 18)
                .collect(Collectors.toList());
    }

    @Override
    public Paciente crearPacienteConHistorial(Paciente paciente,MultipartFile file) {
        User user = userService.findByEmail(paciente.getEmail()).orElse(null);

        if(user != null){
            user.setRol(Rol.USER);
            paciente.setUser(user);
        }

        // Crear y guardar el historial médico
        HistorialMedico historialMedico = new HistorialMedico();
        // Configurar propiedades específicas del historial médico
        historialMedico.setPaciente(paciente); // Establecer la relación inversa al paciente

        // Guardar el historial médico en la base de datos
        historialMedicoService.saveHistorial(historialMedico);

        // Configurar otras propiedades del paciente según los datos en pacienteRequest
        paciente.setHistorialMedico(historialMedico);

        if(!file.isEmpty()){
            //Path directorioImagen = Paths.get("C:/Users/Lautaro/Desktop/Proyectos/proyecto-clinica-odontologica/frontend/src/imagenes");
            String rutaAbsoluta = "C://Users//Lautaro//Desktop//Proyectos//proyecto-clinica-odontologica//imagenes";

            try {
                byte[] byteImg = file.getBytes();
               Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
                Files.write(rutaCompleta,byteImg);
                paciente.setImg(file.getOriginalFilename());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Guardar el paciente en la base de datos
        return repository.save(paciente);

    }

    @Override
    public Integer obtenerHistorialMedico(Integer idPaciente) {
        Optional<Paciente> pacienteOptional = repository.findById(idPaciente);

        if (pacienteOptional.isPresent()) {
            Paciente paciente = pacienteOptional.get();
            HistorialMedico historialMedico = paciente.getHistorialMedico();

            if (historialMedico != null) {
                return historialMedico.getId_historial();
            }
        }

        return null;
    }




    }






