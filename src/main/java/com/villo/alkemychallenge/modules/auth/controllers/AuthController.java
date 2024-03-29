package com.villo.alkemychallenge.modules.auth.controllers;

import com.villo.alkemychallenge.modules.auth.dtos.requests.LoginRequestDTO;
import com.villo.alkemychallenge.modules.auth.dtos.requests.RegisterRequestDTO;
import com.villo.alkemychallenge.modules.auth.dtos.responses.AuthResponseDTO;
import com.villo.alkemychallenge.modules.auth.services.AuthService;
import com.villo.alkemychallenge.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(Constants.AUTH_PATH)
@RequiredArgsConstructor
@SecurityRequirements()
public class AuthController {
    private final static String LOGIN = "Log in.";
    private final static String LOGGED_IN_SUCCESSFULLY = "Logged in successfully.";
    private final static String REGISTER = "Register.";
    private final static String ACCOUNT_CREATED_SUCCESSFULLY = "Account created successfully.";

    private final AuthService authService;

    @Operation(summary = LOGIN)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = LOGGED_IN_SUCCESSFULLY,
                    content = {@Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = AuthResponseDTO.class))}),
            @ApiResponse(responseCode = Constants.BAD_REQUEST, description = Constants.INVALID_RESOURCE_DATA,
                    content = @Content),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND,
                    content = @Content)})
    @PostMapping(value = Constants.LOGIN_PATH)
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid final LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @Operation(summary = REGISTER, responses = {
            @ApiResponse(responseCode = Constants.CREATED, description = ACCOUNT_CREATED_SUCCESSFULLY,
                    content = {@Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = AuthResponseDTO.class))},
                    links = @Link(name = HttpHeaders.LOCATION, operationId = LOGIN)),
            @ApiResponse(responseCode = Constants.BAD_REQUEST, description = Constants.INVALID_RESOURCE_DATA,
                    content = @Content)})
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid final RegisterRequestDTO registerRequestDTO) {
        var auth = authService.register(registerRequestDTO);
        return ResponseEntity.created(URI.create(Constants.AUTH_PATH + Constants.LOGIN_PATH)).body(auth);
    }
}
