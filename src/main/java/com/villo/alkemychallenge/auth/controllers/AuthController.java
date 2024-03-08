package com.villo.alkemychallenge.auth.controllers;

import com.villo.alkemychallenge.auth.dtos.requests.LoginRequestDTO;
import com.villo.alkemychallenge.auth.dtos.requests.RegisterRequestDTO;
import com.villo.alkemychallenge.auth.dtos.responses.AuthResponseDTO;
import com.villo.alkemychallenge.auth.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid final LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid final RegisterRequestDTO registerRequestDTO) {
        return authService.register(registerRequestDTO);
    }
}
