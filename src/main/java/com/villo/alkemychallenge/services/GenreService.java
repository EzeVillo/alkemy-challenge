package com.villo.alkemychallenge.services;

import com.villo.alkemychallenge.dtos.requests.CreateGenreRequestDTO;
import com.villo.alkemychallenge.dtos.responses.BasicGenreResponseDTO;
import com.villo.alkemychallenge.dtos.responses.FullGenreResponseDTO;
import com.villo.alkemychallenge.entities.Genre;
import com.villo.alkemychallenge.repositories.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final ModelMapper modelMapper;
    private final GenreRepository genreRepository;

    public ResponseEntity<FullGenreResponseDTO> create(final CreateGenreRequestDTO createGenreRequestDTO) {
        var genre = modelMapper.map(createGenreRequestDTO, Genre.class);
        genre = genreRepository.save(genre);

        return new ResponseEntity<>(modelMapper.map(genre, FullGenreResponseDTO.class), HttpStatus.CREATED);
    }

    public ResponseEntity<FullGenreResponseDTO> findById(final Long id) {
        var genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genero con id " + id + " no encontrado"));

        return new ResponseEntity<>(modelMapper.map(genre, FullGenreResponseDTO.class), HttpStatus.OK);
    }

    public ResponseEntity<List<BasicGenreResponseDTO>> findAll() {
        var genres = genreRepository.findAll().stream()
                .map(genre -> modelMapper.map(genre, BasicGenreResponseDTO.class))
                .toList();

        return new ResponseEntity<>(genres, HttpStatus.OK);
    }
}
