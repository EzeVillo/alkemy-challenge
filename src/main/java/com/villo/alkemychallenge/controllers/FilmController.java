package com.villo.alkemychallenge.controllers;

import com.villo.alkemychallenge.dtos.responses.FullFilmResponseDTO;
import com.villo.alkemychallenge.services.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<FullFilmResponseDTO> findById(@PathVariable final Long id) {
        return filmService.findById(id);
    }
}
