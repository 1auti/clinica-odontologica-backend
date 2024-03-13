package com.backend.CRUD.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="PreguntasFrecuentes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreguntasFrecuentes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pregunta",unique = true)
    private String pregunta;

    @Column(name = "respondido")
    private boolean respondido = false;

    @Column(name = "respuesta")
    private String respuesta;
    

}
