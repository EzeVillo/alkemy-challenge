package com.villo.alkemychallenge.services;

import com.villo.alkemychallenge.converters.FromPageToPageDTO;
import com.villo.alkemychallenge.dtos.FilmDTO;
import com.villo.alkemychallenge.dtos.requests.FilmSpecificationRequestDTO;
import com.villo.alkemychallenge.dtos.responses.PageResponseDTO;
import com.villo.alkemychallenge.entities.Film;
import com.villo.alkemychallenge.events.EntityEditedEvent;
import com.villo.alkemychallenge.repositories.FilmRepository;
import com.villo.alkemychallenge.utils.SpecificationExecuter;
import com.villo.alkemychallenge.utils.helpers.CharacterHelper;
import com.villo.alkemychallenge.utils.helpers.FilmHelper;
import com.villo.alkemychallenge.utils.helpers.GenreHelper;
import com.villo.alkemychallenge.utils.helpers.PropertyHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    @Qualifier("modelMapperFilms")
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final CharacterService characterService;
    private final CharacterHelper characterHelper;
    private final GenreHelper genreHelper;
    private final FilmHelper filmHelper;
    private final FilmRepository filmRepository;
    private final FromPageToPageDTO<FilmDTO> fromPageToPageDTO;
    private final SpecificationExecuter<Film, FilmSpecificationRequestDTO> specificationExecuter;

    @Transactional
    public FilmDTO create(final FilmDTO filmDTO) {
        var film = modelMapper.map(filmDTO, Film.class);
        film.setCharacters(new HashSet<>());
        film.setGenres(new HashSet<>());

        if (!Objects.isNull(filmDTO.getGendersId()))
            film.setGenres(filmDTO.getGendersId().stream()
                    .map(genreHelper::findGenreByIdOrThrow)
                    .collect(Collectors.toSet()));

        if (!Objects.isNull(filmDTO.getCharacters()))
            film.setCharacters(filmDTO.getCharacters().stream()
                    .map(characterService::create)
                    .map(characterDTO -> characterHelper.findCharacterByIdOrThrow(characterDTO.getId()))
                    .collect(Collectors.toSet()));

        return modelMapper.map(filmRepository.save(film), FilmDTO.class);
    }

    public FilmDTO findById(final Long id) {
        return modelMapper.map(filmHelper.findFilmByIdOrThrow(id), FilmDTO.class);
    }

    public PageResponseDTO<FilmDTO> findByFilters(Pageable pageable, FilmSpecificationRequestDTO filmSpecificationRequestDTO) {
        return fromPageToPageDTO.convert(filmRepository
                .findAll(specificationExecuter.execute(filmSpecificationRequestDTO), pageable)
                .map(film -> modelMapper.map(film, FilmDTO.class)));
    }

    public FilmDTO edit(final Long id, final FilmDTO filmDTO) {
        var film = filmHelper.findFilmByIdOrThrow(id);
        var oldFilm = film.clone();

        BeanUtils.copyProperties(filmDTO, film, PropertyHelper.getNullPropertyNames(filmDTO));
        film = filmRepository.save(film);

        eventPublisher.publishEvent(new EntityEditedEvent(oldFilm, film));
        return modelMapper.map(film, FilmDTO.class);
    }

    public void delete(final Long id) {
        filmRepository.delete(filmHelper.findFilmByIdOrThrow(id));
    }
}
