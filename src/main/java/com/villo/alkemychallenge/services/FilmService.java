package com.villo.alkemychallenge.services;

import com.villo.alkemychallenge.dtos.requests.CreateFilmRequestDTO;
import com.villo.alkemychallenge.dtos.requests.EditFilmRequestDTO;
import com.villo.alkemychallenge.dtos.responses.FullFilmResponseDTO;
import com.villo.alkemychallenge.entities.Film;
import com.villo.alkemychallenge.helpers.PropertyHelper;
import com.villo.alkemychallenge.repositories.CharacterRepository;
import com.villo.alkemychallenge.repositories.FilmRepository;
import com.villo.alkemychallenge.utils.Constants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilmService {
    private static final String FILM = "Pelicula";
    private final ModelMapper modelMapper;
    private final FilmRepository filmRepository;
    private final CharacterRepository characterRepository;

    public ResponseEntity<FullFilmResponseDTO> create(final CreateFilmRequestDTO createFilmRequestDTO) {
        var film = modelMapper.map(createFilmRequestDTO, Film.class);

        film.getCharacters().forEach(characterRepository::save);
        film = filmRepository.save(film);

        return new ResponseEntity<>(modelMapper.map(film, FullFilmResponseDTO.class), HttpStatus.CREATED);
    }

    public ResponseEntity<FullFilmResponseDTO> findById(final Long id) {
        var film = findFilmById(id);

        return ResponseEntity.ok(modelMapper.map(film, FullFilmResponseDTO.class));
    }

    public ResponseEntity<FullFilmResponseDTO> edit(final Long id, final EditFilmRequestDTO editFilmRequestDTO) {
        var film = findFilmById(id);

        BeanUtils.copyProperties(editFilmRequestDTO, film, PropertyHelper.getNullPropertyNames(editFilmRequestDTO));

        film = filmRepository.save(film);
        return ResponseEntity.ok(modelMapper.map(film, FullFilmResponseDTO.class));
    }

    public ResponseEntity<Void> delete(final Long id) {
        var film = findFilmById(id);

        filmRepository.delete(film);
        return ResponseEntity.noContent().build();
    }

    private Film findFilmById(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constants.NOT_FOUND_MESSAGE, FILM, id)));
    }
}
