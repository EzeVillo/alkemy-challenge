package com.villo.alkemychallenge.modules.auth.services;

import com.villo.alkemychallenge.modules.auth.dtos.requests.LoginRequestDTO;
import com.villo.alkemychallenge.modules.auth.dtos.requests.RegisterRequestDTO;
import com.villo.alkemychallenge.modules.auth.dtos.responses.AuthResponseDTO;
import com.villo.alkemychallenge.modules.auth.entities.User;
import com.villo.alkemychallenge.modules.auth.events.UserCreatedEvent;
import com.villo.alkemychallenge.modules.auth.repositories.UserRepository;
import com.villo.alkemychallenge.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    private final ApplicationEventPublisher eventPublisher;

    public AuthResponseDTO login(final LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),
                loginRequestDTO.getPassword()));

        var user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new BadCredentialsException(Constants.THE_USERNAME + Constants.NOT_EXIST));
        var token = jwtService.getToken(user);

        return AuthResponseDTO.builder()
                .token(token)
                .build();
    }

    public AuthResponseDTO register(final RegisterRequestDTO registerRequestDTO) {
        var user = modelMapper.map(registerRequestDTO, User.class);
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        user = userRepository.save(user);

        eventPublisher.publishEvent(new UserCreatedEvent(user));

        return AuthResponseDTO.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
