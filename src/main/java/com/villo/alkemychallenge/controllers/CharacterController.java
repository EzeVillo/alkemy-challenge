package com.villo.alkemychallenge.controllers;

import com.villo.alkemychallenge.dtos.requests.CreateCharacterRequestDTO;
import com.villo.alkemychallenge.dtos.requests.EditCharacterRequestDTO;
import com.villo.alkemychallenge.dtos.responses.FullCharacterResponseDTO;
import com.villo.alkemychallenge.services.CharacterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    @PostMapping
    public ResponseEntity<FullCharacterResponseDTO> create(@RequestBody @Valid final CreateCharacterRequestDTO createCharacterRequestDTO) {
        return characterService.create(createCharacterRequestDTO);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<FullCharacterResponseDTO> edit(@PathVariable final Long id, @RequestBody final EditCharacterRequestDTO editCharacterRequestDTO) {
        return characterService.edit(id, editCharacterRequestDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        return characterService.delete(id);
    }
}
