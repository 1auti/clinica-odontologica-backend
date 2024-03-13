package com.backend.SECURITY.User.Service;

import com.backend.SECURITY.User.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> traerUser();
    void deleteUser(Integer id);
    Optional<User> findByEmail(String email);

}
