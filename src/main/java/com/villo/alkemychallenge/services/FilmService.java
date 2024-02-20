package com.villo.alkemychallenge.services;

import com.villo.alkemychallenge.dtos.responses.FullFilmResponseDTO;
import com.villo.alkemychallenge.entities.Film;
import com.villo.alkemychallenge.repositories.FilmRepository;
import com.villo.alkemychallenge.utils.Constants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilmService {
    private static final String FILM = "Pelicula";
    private final ModelMapper modelMapper;
    private final FilmRepository filmRepository;

    public ResponseEntity<FullFilmResponseDTO> findById(final Long id) {
        var film = findFilmById(id);

        return ResponseEntity.ok(modelMapper.map(film, FullFilmResponseDTO.class));
    }

    private Film findFilmById(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constants.NOT_FOUND_MESSAGE, FILM, id)));
    }
}
