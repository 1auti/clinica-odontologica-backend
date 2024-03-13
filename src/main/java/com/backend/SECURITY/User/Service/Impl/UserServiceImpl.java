package com.backend.SECURITY.User.Service.Impl;

import com.backend.SECURITY.Token.Token;
import com.backend.SECURITY.Token.TokenRepository;
import com.backend.SECURITY.User.Service.UserService;
import com.backend.SECURITY.User.User;
import com.backend.SECURITY.User.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;
    @Autowired
    TokenRepository tokenRepository;

    @Override
    public List<User> traerUser() {
        return repository.findAll();
    }

    @Override
    public void deleteUser(Integer id) {
        User user = repository.findById(id).orElse(null);

        if (user != null) {
            repository.deleteById(id);

            if (user.getTokens() != null && !user.getTokens().isEmpty()) {
                for (Token token : user.getTokens()) {
                    tokenRepository.deleteById(token.getId());
                }
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

}
