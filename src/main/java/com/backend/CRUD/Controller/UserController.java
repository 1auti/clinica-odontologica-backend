package com.backend.CRUD.Controller;

import com.backend.SECURITY.User.Service.UserService;
import com.backend.SECURITY.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    UserService service;


    @GetMapping("/traerUsuarios")
    public ResponseEntity<List<User>> traerUsuarios(){
        List<User> userList = service.traerUser();
        return new ResponseEntity<>(userList, HttpStatus.CREATED);
    }

    @DeleteMapping("/borrarUsuario/{id}")
    public ResponseEntity<String> borrarUsuario(@PathVariable Integer id){
        service.deleteUser(id);
        return new ResponseEntity<>("Se ha borrado con exito",HttpStatus.OK);
    }

    @GetMapping("/traerUsuario")
    public ResponseEntity<Optional<User>> traerUsuario(@RequestParam String email){
        Optional<User> user = service.findByEmail(email);
        return new ResponseEntity<>(user,HttpStatus.OK);

    }


}
