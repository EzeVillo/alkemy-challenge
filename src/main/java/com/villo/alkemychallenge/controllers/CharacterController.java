package com.villo.alkemychallenge.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.villo.alkemychallenge.dtos.CharacterDTO;
import com.villo.alkemychallenge.dtos.requests.CharacterSpecificationRequestDTO;
import com.villo.alkemychallenge.dtos.requests.PageRequestDTO;
import com.villo.alkemychallenge.dtos.responses.PageResponseDTO;
import com.villo.alkemychallenge.repositories.CharacterRepository;
import com.villo.alkemychallenge.services.CharacterService;
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
@RequestMapping(Constants.CHARACTER_PATH)
@RequiredArgsConstructor
public class CharacterController {
    private static final String CHARACTER = "character.";
    private static final String CHARACTERS = "characters.";
    private static final String CHARACTER_SPECIFICATION_REQUEST_DTO = "characterSpecificationRequestDTO";
    private static final String OBTAIN_A_CHARACTER = Constants.OBTAIN_A + CHARACTER;

    private final HttpServletRequest httpServletRequest;
    private final CharacterService characterService;

    @JsonView(Views.FullResponseView.class)
    @Operation(summary = Constants.CREATE_A + CHARACTER, responses = {
            @ApiResponse(responseCode = Constants.CREATED, description = Constants.RESOURCE_SUCCESSFULLY_CREATED,
                    content = {@Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = CharacterDTO.class))},
                    links = @Link(name = HttpHeaders.LOCATION, operationId = OBTAIN_A_CHARACTER,
                            parameters = @LinkParameter(name = Constants.ID, expression = Constants.ID))),
            @ApiResponse(responseCode = Constants.BAD_REQUEST, description = Constants.INVALID_RESOURCE_DATA,
                    content = @Content)})
    @PostMapping
    public ResponseEntity<CharacterDTO> create(
            @RequestBody @Validated(ValidationGroups.CreateValidationGroup.class)
            @JsonView(Views.CreateRequestView.class) final CharacterDTO characterDTO) {
        var character = characterService.create(characterDTO);
        return ResponseEntity.created(URI.create(httpServletRequest.getRequestURI() + "/" + character.getId())).body(character);
    }

    @JsonView(Views.FullResponseView.class)
    @Operation(summary = OBTAIN_A_CHARACTER)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = Constants.RESOURCE_FETCHED_SUCCESSFULLY,
                    content = {@Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = CharacterDTO.class))}),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND,
                    content = @Content)})
    @GetMapping(value = "/{id}")
    public ResponseEntity<CharacterDTO> findById(
            @Exist(repositoryClass = CharacterRepository.class, property = "id", hasToExistToPassValidation = true,
                    message = Constants.CHARACTER_NOT_FOUND_MESSAGE)
            @PathVariable final Long id) {
        return ResponseEntity.ok(characterService.findById(id));
    }

    @JsonView(Views.BasicResponseView.class)
    @Operation(summary = Constants.OBTAIN_OR_FILTER + CHARACTERS, responses = {
            @ApiResponse(responseCode = Constants.OK, description = Constants.RESOURCE_FETCHED_SUCCESSFULLY,
                    content = {@Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = PageResponseDTO.class))}),
            @ApiResponse(responseCode = Constants.NO_CONTENT, description = Constants.RESOURCE_EMPTY,
                    content = @Content),
            @ApiResponse(responseCode = Constants.PARTIAL_CONTENT, description = Constants.RESOURCE_PARTIAL_CONTENT,
                    content = @Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = PageResponseDTO.class)),
                    links = {
                            @Link(name = Constants.FIRST_PREV_NEXT_LAST, operationId = Constants.OBTAIN_OR_FILTER + CHARACTERS,
                                    parameters = {
                                            @LinkParameter(name = Constants.PAGE_NAME, expression = Constants.PAGE_EXPRESSION),
                                            @LinkParameter(name = Constants.SIZE_NAME, expression = Constants.SIZE_EXPRESSION),
                                            @LinkParameter(name = CHARACTER_SPECIFICATION_REQUEST_DTO, expression = CHARACTER_SPECIFICATION_REQUEST_DTO)
                                    })})})
    @GetMapping
    public ResponseEntity<PageResponseDTO<CharacterDTO>> findByFilters(
            @Valid final PageRequestDTO page,
            @Valid final CharacterSpecificationRequestDTO characterSpecificationRequestDTO) {
        var characters = characterService.findByFilters(PageRequest.of(page.getNumber(), page.getSize()), characterSpecificationRequestDTO);
        if (characters.isEmpty()) return ResponseEntity.noContent().build();

        if (characters.getTotalPages() > 1)
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(HttpHeaders.LINK, PaginationHelper
                            .createHeaderLink(characters,
                                    httpServletRequest.getRequestURI() + "?" + httpServletRequest.getQueryString()))
                    .body(characters);

        return ResponseEntity.ok(characters);
    }

    @JsonView(Views.FullResponseView.class)
    @Operation(summary = Constants.EDIT_A + CHARACTER)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = Constants.RESOURCE_SUCCESSFULLY_EDITED,
                    content = {@Content(mediaType = Constants.APPLICATION_JSON,
                            schema = @Schema(implementation = CharacterDTO.class))}),
            @ApiResponse(responseCode = Constants.BAD_REQUEST, description = Constants.INVALID_RESOURCE_DATA,
                    content = @Content),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND,
                    content = @Content)})
    @PatchMapping(value = "/{id}")
    public ResponseEntity<CharacterDTO> edit(
            @Exist(repositoryClass = CharacterRepository.class, property = "id", hasToExistToPassValidation = true,
                    message = Constants.CHARACTER_NOT_FOUND_MESSAGE)
            @PathVariable final Long id,
            @RequestBody @Validated
            @JsonView(Views.EditRequestView.class) final CharacterDTO characterDTO) {
        return ResponseEntity.ok(characterService.edit(id, characterDTO));
    }

    @Operation(summary = Constants.DELETE_A + CHARACTER)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.NO_CONTENT, description = Constants.RESOURCE_SUCCESSFULLY_DELETED),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = Constants.RESOURCE_NOT_FOUND)})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @Exist(repositoryClass = CharacterRepository.class, property = "id", hasToExistToPassValidation = true,
                    message = Constants.CHARACTER_NOT_FOUND_MESSAGE)
            @PathVariable final Long id) {
        characterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
