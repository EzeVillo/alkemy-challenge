package com.villo.alkemychallenge.controllers;

import com.villo.alkemychallenge.dtos.requests.CreateGenreRequestDTO;
import com.villo.alkemychallenge.dtos.responses.BasicGenreResponseDTO;
import com.villo.alkemychallenge.dtos.responses.FullGenreResponseDTO;
import com.villo.alkemychallenge.services.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<FullGenreResponseDTO> create(@RequestBody @Valid final CreateGenreRequestDTO createGenreRequestDTO) {
        return genreService.create(createGenreRequestDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FullGenreResponseDTO> findById(@PathVariable final Long id) {
        return genreService.findById(id);
    }

    @GetMapping
    public ResponseEntity<List<BasicGenreResponseDTO>> findAll() {
        return genreService.findAll();
    }
}
