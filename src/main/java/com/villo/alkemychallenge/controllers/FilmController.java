package com.villo.alkemychallenge.controllers;

import com.villo.alkemychallenge.dtos.requests.CreateFilmRequestDTO;
import com.villo.alkemychallenge.dtos.requests.EditFilmRequestDTO;
import com.villo.alkemychallenge.dtos.responses.FullFilmResponseDTO;
import com.villo.alkemychallenge.services.FilmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public ResponseEntity<FullFilmResponseDTO> create(@RequestBody @Valid final CreateFilmRequestDTO createFilmRequestDTO) {
        return filmService.create(createFilmRequestDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FullFilmResponseDTO> findById(@PathVariable final Long id) {
        return filmService.findById(id);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<FullFilmResponseDTO> edit(@PathVariable final Long id, @RequestBody final EditFilmRequestDTO editFilmRequestDTO) {
        return filmService.edit(id, editFilmRequestDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        return filmService.delete(id);
    }
}
