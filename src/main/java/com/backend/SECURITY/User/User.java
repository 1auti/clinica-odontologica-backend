package com.backend.SECURITY.User;

import com.backend.CRUD.Entity.Persona;
import com.backend.SECURITY.Token.Token;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User" )
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"

)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "pass")
    private String pass;

    @Enumerated(EnumType.STRING)
    /*Los valores almacenados se almacen como cadenas Esto es más seguro en términos de integridad de datos, ya que los valores almacenados no cambiarán
    incluso si el orden de los valores enumerados se modifica.*/
    private Rol rol;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Token> tokens;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Persona persona;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*  SimpleGrantedAuthority es una clase que representa roles o autorizaciones en el contexto de Spring Security.
        Se utiliza para definir y configurar qué acciones o recursos puede acceder un usuario autenticado en una aplicación.
       return List.of(new SimpleGrantedAuthority(rol.name()));
       nos retorna una losta con los roles
       */

        return rol.getAuthorities();

    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true ;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true ;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true ;
    }

    @Override
    public boolean isEnabled() {
        return true ;
    }

    @Override
    public String getPassword() {
        return pass;
    }




}
