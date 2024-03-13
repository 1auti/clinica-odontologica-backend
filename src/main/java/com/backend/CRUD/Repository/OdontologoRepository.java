package com.backend.CRUD.Repository;

import com.backend.CRUD.Entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OdontologoRepository  extends JpaRepository<Odontologo,Integer> {

    Optional<Odontologo> findByNombre(String nombre);
    Optional<Odontologo> findByEmail(String email);
    boolean existsByNombre(String nombre);


}
