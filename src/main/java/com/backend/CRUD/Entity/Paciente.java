    package com.backend.CRUD.Entity;

    import com.fasterxml.jackson.annotation.*;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.List;

    @Entity
    @Table(name="Paciente")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Paciente extends Persona{


        private boolean tiene_OS;
        private String obraSocial;
        private String tipo_sangre;
        @OneToMany(mappedBy = "paciente",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
        @JsonIgnore
        private List<Turno> turnos;
        @OneToOne(mappedBy = "paciente",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
        @JsonIgnore
        private HistorialMedico historialMedico;


    }
