package com.villo.alkemychallenge.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.villo.alkemychallenge.dtos.FilmDTO;
import com.villo.alkemychallenge.dtos.requests.FilmSpecificationRequestDTO;
import com.villo.alkemychallenge.dtos.requests.PageRequestDTO;
import com.villo.alkemychallenge.dtos.responses.PageResponseDTO;
import com.villo.alkemychallenge.repositories.FilmRepository;
import com.villo.alkemychallenge.services.FilmService;
import com.villo.alkemychallenge.utils.Constants;
import com.villo.alkemychallenge.utils.ValidationGroups;
import com.villo.alkemychallenge.utils.Views;
import com.villo.alkemychallenge.utils.annotations.exist.Exist;
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
@RequestMapping(Constants.FILM_PATH)
@RequiredArgsConstructor
public class FilmController {
    private final static String FILMS = "films.";
    private final static String FILM_SPECIFICATION_REQUEST_DTO = "filmSpecificationRequestDTO";

    private final HttpServletRequest httpServletRequest;
    private final FilmService filmService;

    @JsonView(Views.FullResponseView.class)
    @Operation(summary = Constants.CREATE_A + Constants.FILM, responses = {
            @ApiResponse(responseCode = Constants.CREATED, description = Constants.RESOURCE_SUCCESSFULLY_CREATED,
                    content = {@Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = FilmDTO.class))},
                    links = @Link(name = HttpHeaders.LOCATION, operationId = Constants.OBTAIN_A_FILM,
                            parameters = @LinkParameter(name = Constants.ID, expression = Constants.ID))),
            @ApiResponse(responseCode = Constants.BAD_REQUEST, description = Constants.INVALID_RESOURCE_DATA,
                    content = @Content),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND,
                    content = @Content)})
    @PostMapping
    public ResponseEntity<FilmDTO> create(
            @RequestBody @Validated(ValidationGroups.CreateValidationGroup.class)
            @JsonView(Views.CreateRequestView.class) final FilmDTO filmDTO) {
        var film = filmService.create(filmDTO);
        return ResponseEntity.created(URI.create(httpServletRequest.getRequestURI() + "/" + film.getId())).body(film);
    }

    @JsonView(Views.FullResponseView.class)
    @Operation(summary = Constants.OBTAIN_A_FILM)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = Constants.RESOURCE_FETCHED_SUCCESSFULLY,
                    content = {@Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = FilmDTO.class))}),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND,
                    content = @Content)})
    @GetMapping(value = "/{id}")
    public ResponseEntity<FilmDTO> findById(
            @Exist(repositoryClass = FilmRepository.class, property = "id", hasToExistToPassValidation = true,
                    message = Constants.FILM_NOT_FOUND_MESSAGE)
            @PathVariable final Long id) {
        return ResponseEntity.ok(filmService.findById(id));
    }

    @JsonView(Views.BasicResponseView.class)
    @Operation(summary = Constants.OBTAIN_OR_FILTER + FILMS, responses = {
            @ApiResponse(responseCode = Constants.OK, description = Constants.RESOURCE_FETCHED_SUCCESSFULLY,
                    content = {@Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = PageResponseDTO.class))}),
            @ApiResponse(responseCode = Constants.NO_CONTENT, description = Constants.RESOURCE_EMPTY,
                    content = @Content),
            @ApiResponse(responseCode = Constants.PARTIAL_CONTENT, description = Constants.RESOURCE_PARTIAL_CONTENT,
                    content = @Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = PageResponseDTO.class)),
                    links = {
                            @Link(name = Constants.FIRST_PREV_NEXT_LAST, operationId = Constants.OBTAIN_OR_FILTER + FILMS,
                                    parameters = {
                                            @LinkParameter(name = Constants.PAGE_NAME, expression = Constants.PAGE_EXPRESSION),
                                            @LinkParameter(name = Constants.SIZE_NAME, expression = Constants.SIZE_EXPRESSION),
                                            @LinkParameter(name = FILM_SPECIFICATION_REQUEST_DTO, expression = FILM_SPECIFICATION_REQUEST_DTO)
                                    })})})
    @GetMapping
    public ResponseEntity<PageResponseDTO<FilmDTO>> findByFilters(
            @Valid final PageRequestDTO page,
            @Valid final FilmSpecificationRequestDTO filmSpecificationRequestDTO) {
        var films = filmService.findByFilters(PageRequest.of(page.getNumber(), page.getSize()), filmSpecificationRequestDTO);
        if (films.isEmpty()) return ResponseEntity.noContent().build();

        if (films.getTotalPages() > 1)
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(HttpHeaders.LINK, PaginationHelper
                            .createHeaderLink(films,
                                    httpServletRequest.getRequestURI() + "?" + httpServletRequest.getQueryString()))
                    .body(films);

        return ResponseEntity.ok(films);
    }

    @JsonView(Views.FullResponseView.class)
    @Operation(summary = Constants.EDIT_A + Constants.FILM)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = Constants.RESOURCE_SUCCESSFULLY_EDITED,
                    content = {@Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = FilmDTO.class))}),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND,
                    content = @Content)})
    @PatchMapping(value = "/{id}")
    public ResponseEntity<FilmDTO> edit(
            @Exist(repositoryClass = FilmRepository.class, property = "id", hasToExistToPassValidation = true,
                    message = Constants.FILM_NOT_FOUND_MESSAGE)
            @PathVariable final Long id,
            @RequestBody @Validated(ValidationGroups.EditValidationGroup.class)
            @JsonView(Views.EditRequestView.class) final FilmDTO filmDTO) {
        return ResponseEntity.ok(filmService.edit(id, filmDTO));
    }

    @Operation(summary = Constants.DELETE_A + Constants.FILM)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.NO_CONTENT, description = Constants.RESOURCE_SUCCESSFULLY_DELETED),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND)})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @Exist(repositoryClass = FilmRepository.class, property = "id", hasToExistToPassValidation = true,
                    message = Constants.FILM_NOT_FOUND_MESSAGE)
            @PathVariable final Long id) {
        filmService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
