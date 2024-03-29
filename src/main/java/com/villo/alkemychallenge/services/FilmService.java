package com.villo.alkemychallenge.services;

import com.villo.alkemychallenge.dtos.requests.CreateFilmRequestDTO;
import com.villo.alkemychallenge.dtos.requests.EditFilmRequestDTO;
import com.villo.alkemychallenge.dtos.requests.FilmFilterRequestDTO;
import com.villo.alkemychallenge.dtos.responses.BasicFilmResponseDTO;
import com.villo.alkemychallenge.dtos.responses.FullFilmResponseDTO;
import com.villo.alkemychallenge.entities.Film;
import com.villo.alkemychallenge.helpers.FilmHelper;
import com.villo.alkemychallenge.helpers.PropertyHelper;
import com.villo.alkemychallenge.helpers.SpecificationHelper;
import com.villo.alkemychallenge.repositories.CharacterRepository;
import com.villo.alkemychallenge.repositories.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final ModelMapper modelMapper;
    private final FilmRepository filmRepository;
    private final FilmHelper filmHelper;
    private final CharacterRepository characterRepository;
    private final SpecificationHelper<Film, FilmFilterRequestDTO> specificationHelper;

    public ResponseEntity<FullFilmResponseDTO> create(final CreateFilmRequestDTO createFilmRequestDTO) {
        var film = modelMapper.map(createFilmRequestDTO, Film.class);

        film.getCharacters().forEach(characterRepository::save);
        film = filmRepository.save(film);

        return new ResponseEntity<>(modelMapper.map(film, FullFilmResponseDTO.class), HttpStatus.CREATED);
    }

    public ResponseEntity<FullFilmResponseDTO> findById(final Long id) {
        var film = filmHelper.findFilmByIdOrThrow(id);

        return ResponseEntity.ok(modelMapper.map(film, FullFilmResponseDTO.class));
    }

    public ResponseEntity<List<BasicFilmResponseDTO>> findByFilters(FilmFilterRequestDTO filmFilterRequestDTO) {
        var specification = specificationHelper.getAllSpecifications(filmFilterRequestDTO);

        var films = filmRepository.findAll(specification).stream()
                .map(film -> modelMapper.map(film, BasicFilmResponseDTO.class))
                .toList();

        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    public ResponseEntity<FullFilmResponseDTO> edit(final Long id, final EditFilmRequestDTO editFilmRequestDTO) {
        var film = filmHelper.findFilmByIdOrThrow(id);

        BeanUtils.copyProperties(editFilmRequestDTO, film, PropertyHelper.getNullPropertyNames(editFilmRequestDTO));

        film = filmRepository.save(film);
        return ResponseEntity.ok(modelMapper.map(film, FullFilmResponseDTO.class));
    }

    public ResponseEntity<Void> delete(final Long id) {
        var film = filmHelper.findFilmByIdOrThrow(id);

        filmRepository.delete(film);
        return ResponseEntity.noContent().build();
    }
}
