package com.villo.alkemychallenge.controllers;

import com.villo.alkemychallenge.repositories.CharacterRepository;
import com.villo.alkemychallenge.repositories.MovieRepository;
import com.villo.alkemychallenge.services.ParticipationService;
import com.villo.alkemychallenge.utils.Constants;
import com.villo.alkemychallenge.utils.annotations.exist.Exist;
import com.villo.alkemychallenge.utils.errors.custom.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.links.LinkParameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(Constants.MOVIE_PATH + "/{idMovie}" + Constants.CHARACTER_PATH + "/{idCharacter}")
@RequiredArgsConstructor
public class ParticipationController {
    private final static String ADD_A_CHARACTER_TO_A_MOVIE = "Add a character to a movie.";
    private final static String REMOVE_A_CHARACTER_FROM_A_MOVIE = "Remove a character from a movie.";

    private final ParticipationService participationService;

    @Operation(summary = ADD_A_CHARACTER_TO_A_MOVIE, responses = {
            @ApiResponse(responseCode = Constants.CREATED, description = Constants.RESOURCE_SUCCESSFULLY_RELATION_CREATED,
                    links = @Link(name = HttpHeaders.LOCATION, operationId = Constants.OBTAIN_A_MOVIE,
                            parameters = @LinkParameter(name = Constants.ID, expression = Constants.ID))),
            @ApiResponse(responseCode = Constants.BAD_REQUEST, description = Constants.INVALID_RESOURCE_DATA,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping
    public ResponseEntity<Void> add(
            @Exist(repositoryClass = MovieRepository.class, property = "id", hasToExistToPassValidation = true,
                    message = Constants.MOVIE_NOT_FOUND_MESSAGE)
            @PathVariable final Long idMovie,
            @Exist(repositoryClass = CharacterRepository.class, property = "id", hasToExistToPassValidation = true,
                    message = Constants.CHARACTER_NOT_FOUND_MESSAGE)
            @PathVariable final Long idCharacter) {
        participationService.add(idMovie, idCharacter);
        return ResponseEntity.created(URI.create(Constants.MOVIE_PATH + "/" + idMovie)).build();
    }

    @Operation(summary = REMOVE_A_CHARACTER_FROM_A_MOVIE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.NO_CONTENT, description = Constants.RESOURCE_SUCCESSFULLY_RELATION_DELETED),
            @ApiResponse(responseCode = Constants.BAD_REQUEST, description = Constants.INVALID_RESOURCE_DATA,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})

    @DeleteMapping
    public ResponseEntity<Void> remove(
            @Exist(repositoryClass = MovieRepository.class, property = "id", hasToExistToPassValidation = true,
                    message = Constants.MOVIE_NOT_FOUND_MESSAGE)
            @PathVariable final Long idMovie,
            @Exist(repositoryClass = CharacterRepository.class, property = "id", hasToExistToPassValidation = true,
                    message = Constants.CHARACTER_NOT_FOUND_MESSAGE)
            @PathVariable final Long idCharacter) {
        participationService.remove(idMovie, idCharacter);
        return ResponseEntity.noContent().build();
    }
}
