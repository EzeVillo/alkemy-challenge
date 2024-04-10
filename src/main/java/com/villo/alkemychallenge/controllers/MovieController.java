package com.villo.alkemychallenge.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.villo.alkemychallenge.dtos.MovieDTO;
import com.villo.alkemychallenge.dtos.requests.MovieSpecificationRequestDTO;
import com.villo.alkemychallenge.dtos.requests.PageRequestDTO;
import com.villo.alkemychallenge.dtos.responses.PageResponseDTO;
import com.villo.alkemychallenge.repositories.MovieRepository;
import com.villo.alkemychallenge.services.MovieService;
import com.villo.alkemychallenge.utils.Constants;
import com.villo.alkemychallenge.utils.ValidationGroups;
import com.villo.alkemychallenge.utils.Views;
import com.villo.alkemychallenge.utils.annotations.exist.Exist;
import com.villo.alkemychallenge.utils.errors.custom.ErrorResponse;
import com.villo.alkemychallenge.utils.helpers.PaginationHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.links.LinkParameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(Constants.MOVIE_PATH)
@RequiredArgsConstructor
public class MovieController {
    private final static String MOVIES = "movies.";
    private final static String MOVIE_SPECIFICATION_REQUEST_DTO = "movieSpecificationRequestDTO";

    private final HttpServletRequest httpServletRequest;
    private final MovieService movieService;

    @JsonView(Views.FullResponseView.class)
    @Operation(summary = Constants.CREATE_A + Constants.MOVIE, responses = {
            @ApiResponse(responseCode = Constants.CREATED, description = Constants.RESOURCE_SUCCESSFULLY_CREATED,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MovieDTO.class))},
                    links = @Link(name = HttpHeaders.LOCATION, operationId = Constants.OBTAIN_A_MOVIE,
                            parameters = @LinkParameter(name = Constants.ID, expression = Constants.ID))),
            @ApiResponse(responseCode = Constants.BAD_REQUEST, description = Constants.INVALID_RESOURCE_DATA,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping
    public ResponseEntity<MovieDTO> create(
            @RequestBody @Validated(ValidationGroups.CreateValidationGroup.class)
            @JsonView(Views.CreateRequestView.class) final MovieDTO movieDTO) {
        var movie = movieService.create(movieDTO);
        return ResponseEntity.created(URI.create(httpServletRequest.getRequestURI() + "/" + movie.getId())).body(movie);
    }

    @JsonView(Views.FullResponseView.class)
    @Operation(summary = Constants.OBTAIN_A_MOVIE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = Constants.RESOURCE_FETCHED_SUCCESSFULLY,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MovieDTO.class))}),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDTO> findById(
            @Exist(repositoryClass = MovieRepository.class, property = "id", hasToExistToPassValidation = true,
                    message = Constants.MOVIE_NOT_FOUND_MESSAGE)
            @PathVariable final Long id) {
        return ResponseEntity.ok(movieService.findById(id));
    }

    @JsonView(Views.BasicResponseView.class)
    @Operation(summary = Constants.OBTAIN_OR_FILTER + MOVIES, responses = {
            @ApiResponse(responseCode = Constants.OK, description = Constants.RESOURCE_FETCHED_SUCCESSFULLY,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PageResponseDTO.class))}),
            @ApiResponse(responseCode = Constants.NO_CONTENT, description = Constants.RESOURCE_EMPTY,
                    content = @Content),
            @ApiResponse(responseCode = Constants.PARTIAL_CONTENT, description = Constants.RESOURCE_PARTIAL_CONTENT,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PageResponseDTO.class)),
                    links = {
                            @Link(name = Constants.FIRST_PREV_NEXT_LAST, operationId = Constants.OBTAIN_OR_FILTER + MOVIES,
                                    parameters = {
                                            @LinkParameter(name = Constants.PAGE_NAME, expression = Constants.PAGE_EXPRESSION),
                                            @LinkParameter(name = Constants.SIZE_NAME, expression = Constants.SIZE_EXPRESSION),
                                            @LinkParameter(name = MOVIE_SPECIFICATION_REQUEST_DTO, expression = MOVIE_SPECIFICATION_REQUEST_DTO)
                                    })})})
    @GetMapping
    public ResponseEntity<PageResponseDTO<MovieDTO>> findByFilters(
            @Valid final PageRequestDTO page,
            @Valid final MovieSpecificationRequestDTO movieSpecificationRequestDTO) {
        var movie = movieService.findByFilters(PageRequest.of(page.getNumber(), page.getSize()), movieSpecificationRequestDTO);
        if (movie.isEmpty()) return ResponseEntity.noContent().build();

        if (movie.getTotalPages() > 1)
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(HttpHeaders.LINK, PaginationHelper
                            .createHeaderLink(movie,
                                    httpServletRequest.getRequestURI() + "?" + httpServletRequest.getQueryString()))
                    .body(movie);

        return ResponseEntity.ok(movie);
    }

    @JsonView(Views.FullResponseView.class)
    @Operation(summary = Constants.EDIT_A + Constants.MOVIE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = Constants.RESOURCE_SUCCESSFULLY_EDITED,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MovieDTO.class))}),
            @ApiResponse(responseCode = Constants.BAD_REQUEST, description = Constants.INVALID_RESOURCE_DATA,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @PatchMapping(value = "/{id}")
    public ResponseEntity<MovieDTO> edit(
            @Exist(repositoryClass = MovieRepository.class, property = "id", hasToExistToPassValidation = true,
                    message = Constants.MOVIE_NOT_FOUND_MESSAGE)
            @PathVariable final Long id,
            @RequestBody @Validated(ValidationGroups.EditValidationGroup.class)
            @JsonView(Views.EditRequestView.class) final MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.edit(id, movieDTO));
    }

    @Operation(summary = Constants.DELETE_A + Constants.MOVIE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.NO_CONTENT, description = Constants.RESOURCE_SUCCESSFULLY_DELETED),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @Exist(repositoryClass = MovieRepository.class, property = "id", hasToExistToPassValidation = true,
                    message = Constants.MOVIE_NOT_FOUND_MESSAGE)
            @PathVariable final Long id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
