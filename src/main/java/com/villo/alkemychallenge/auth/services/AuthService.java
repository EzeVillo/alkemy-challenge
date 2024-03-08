package com.villo.alkemychallenge.auth.services;

import com.villo.alkemychallenge.auth.dtos.requests.LoginRequestDTO;
import com.villo.alkemychallenge.auth.dtos.requests.RegisterRequestDTO;
import com.villo.alkemychallenge.auth.dtos.responses.AuthResponseDTO;
import com.villo.alkemychallenge.auth.entities.User;
import com.villo.alkemychallenge.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<AuthResponseDTO> login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),
                loginRequestDTO.getPassword()));

        var user = userRepository.findByUsername(loginRequestDTO.getUsername()).orElseThrow();
        var token = jwtService.getToken(user);

        return ResponseEntity.ok(AuthResponseDTO.builder()
                .token(token)
                .build());
    }

    public ResponseEntity<AuthResponseDTO> register(RegisterRequestDTO registerRequestDTO) {
        var user = modelMapper.map(registerRequestDTO, User.class);
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        user = userRepository.save(user);
        return ResponseEntity.ok(AuthResponseDTO.builder()
                .token(jwtService.getToken(user))
                .build());
    }
}
