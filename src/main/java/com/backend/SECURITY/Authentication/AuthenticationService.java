package com.backend.SECURITY.Authentication;


import com.backend.CRUD.Entity.Odontologo;
import com.backend.CRUD.Entity.Paciente;
import com.backend.CRUD.Service.OdontologoService;
import com.backend.CRUD.Service.PacienteService;
import com.backend.SECURITY.Config.JwtService;
import com.backend.SECURITY.Token.Token;
import com.backend.SECURITY.Token.TokenRepository;
import com.backend.SECURITY.Token.TokenType;
import com.backend.SECURITY.User.User;
import com.backend.SECURITY.User.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
    public class AuthenticationService {

        private final UserRepository repository;
        private final TokenRepository tokenRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private boolean esPaciente = false;
        private boolean esOdontologo = false;

        @Autowired
    PacienteService pacienteService;
        @Autowired
    OdontologoService odontologoService;


        public AuthenticationResponse register(RegisterRequest request) {
            var user = User.builder()
                    .nombre(request.getNombre())
                    .apellido(request.getApellido())
                    .email(request.getEmail())
                    .pass(passwordEncoder.encode(request.getPass()))
                    .rol(request.getRol())
                    .build();




            var savedUser = repository.save(user);

            buscarPaciente(request.getEmail());
            buscarOdontologo(request.getEmail());

            if(esPaciente == true){
                Paciente paciente = pacienteService.getByEmail(request.getEmail()).orElse(null);
                if(paciente != null){
                    paciente.setUser(user);
                }
                pacienteService.savePaciente(paciente);
            }

            if(esOdontologo == true){
                Odontologo odontologo = odontologoService.buscarPorEmail(request.getEmail()).orElse(null);
                if(odontologo != null){
                    odontologo.setUser(user);
                }
                odontologoService.saveOdontologo(odontologo);
            }

            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPass()
                    )
            );
            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        private void saveUserToken(User user, String jwtToken) {
            var token = Token.builder()
                    .user(user)
                    .token(jwtToken)
                    .tokenType(TokenType.BEARER)
                    .expired(false)
                    .revoked(false)
                    .build();
            tokenRepository.save(token);
        }

        private void revokeAllUserTokens(User user) {
            var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
            if (validUserTokens.isEmpty())
                return;
            validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }

        public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String refreshToken;
            final String userEmail;
            if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
                return;
            }
            refreshToken = authHeader.substring(7);
            userEmail = jwtService.extractUsername(refreshToken);
            if (userEmail != null) {
                var user = this.repository.findByEmail(userEmail)
                        .orElseThrow();
                if (jwtService.isTokenValid(refreshToken, user)) {
                    var accessToken = jwtService.generateToken(user);
                    revokeAllUserTokens(user);
                    saveUserToken(user, accessToken);
                    var authResponse = AuthenticationResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                    new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                }
            }
        }

        private void buscarPaciente(String email){
         Paciente paciente = pacienteService.getByEmail(email).orElse(null);
         if(paciente != null){
             esPaciente = true;
         }
        }

        private void buscarOdontologo(String email){
            Odontologo odontologo = odontologoService.buscarPorEmail(email).orElse(null);
            if(odontologo != null){
                esOdontologo = false;
            }
        }


    }


