package com.villo.alkemychallenge.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.villo.alkemychallenge.dtos.GenreDTO;
import com.villo.alkemychallenge.dtos.requests.PageRequestDTO;
import com.villo.alkemychallenge.dtos.responses.PageResponseDTO;
import com.villo.alkemychallenge.repositories.GenreRepository;
import com.villo.alkemychallenge.services.GenreService;
import com.villo.alkemychallenge.utils.Constants;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final static String GENRE = "genre.";
    private final static String GENRES = "genres.";
    private final static String OBTAIN_A_GENRE = Constants.OBTAIN_A + GENRE;

    private final HttpServletRequest httpServletRequest;
    private final GenreService genreService;

    @JsonView(Views.FullResponseView.class)
    @Operation(summary = Constants.CREATE_A + GENRE, responses = {
            @ApiResponse(responseCode = Constants.CREATED, description = Constants.RESOURCE_SUCCESSFULLY_CREATED,
                    content = {@Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = GenreDTO.class))},
                    links = @Link(name = HttpHeaders.LOCATION, operationId = OBTAIN_A_GENRE,
                            parameters = @LinkParameter(name = Constants.ID, expression = Constants.ID))),
            @ApiResponse(responseCode = Constants.BAD_REQUEST, description = Constants.INVALID_RESOURCE_DATA,
                    content = @Content)})
    @PostMapping
    public ResponseEntity<GenreDTO> create(
            @RequestBody @Valid
            @JsonView(Views.CreateRequestView.class) final GenreDTO genreRequestDTO) {
        var genre = genreService.create(genreRequestDTO);
        return ResponseEntity.created(URI.create(httpServletRequest.getRequestURI() + "/" + genre.getId())).body(genre);
    }

    @JsonView(Views.FullResponseView.class)
    @Operation(summary = OBTAIN_A_GENRE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = Constants.RESOURCE_FETCHED_SUCCESSFULLY,
                    content = {@Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = GenreDTO.class))}),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND,
                    content = @Content)})
    @GetMapping(value = "/{id}")
    public ResponseEntity<GenreDTO> findById(
            @Exist(repositoryClass = GenreRepository.class, property = "id", hasToExistToPassValidation = true,
                    message = Constants.GENRE_NOT_FOUND_MESSAGE)
            @PathVariable final Long id) {
        return ResponseEntity.ok(genreService.findById(id));
    }

    @JsonView(Views.BasicResponseView.class)
    @Operation(summary = Constants.OBTAIN_OR_FILTER + GENRES, responses = {
            @ApiResponse(responseCode = Constants.OK, description = Constants.RESOURCE_FETCHED_SUCCESSFULLY,
                    content = {@Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = PageResponseDTO.class))}),
            @ApiResponse(responseCode = Constants.NO_CONTENT, description = Constants.RESOURCE_EMPTY,
                    content = @Content),
            @ApiResponse(responseCode = Constants.PARTIAL_CONTENT, description = Constants.RESOURCE_PARTIAL_CONTENT,
                    content = @Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = PageResponseDTO.class)),
                    links = {
                            @Link(name = Constants.FIRST_PREV_NEXT_LAST, operationId = Constants.OBTAIN_OR_FILTER + GENRES,
                                    parameters = {
                                            @LinkParameter(name = Constants.PAGE_NAME, expression = Constants.PAGE_EXPRESSION),
                                            @LinkParameter(name = Constants.SIZE_NAME, expression = Constants.SIZE_EXPRESSION)
                                    })})})
    @GetMapping
    public ResponseEntity<PageResponseDTO<GenreDTO>> findAll(@Valid final PageRequestDTO page) {
        var genres = genreService.findAll(PageRequest.of(page.getNumber(), page.getSize()));
        if (genres.isEmpty()) return ResponseEntity.noContent().build();

        if (genres.getTotalPages() > 1)
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(HttpHeaders.LINK, PaginationHelper
                            .createHeaderLink(genres,
                                    httpServletRequest.getRequestURI() + "?" + httpServletRequest.getQueryString()))
                    .body(genres);

        return ResponseEntity.ok(genres);
    }
}
