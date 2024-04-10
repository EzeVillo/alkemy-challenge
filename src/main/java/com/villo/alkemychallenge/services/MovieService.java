package com.villo.alkemychallenge.services;

import com.villo.alkemychallenge.converters.FromPageToPageDTO;
import com.villo.alkemychallenge.dtos.MovieDTO;
import com.villo.alkemychallenge.dtos.requests.MovieSpecificationRequestDTO;
import com.villo.alkemychallenge.dtos.responses.PageResponseDTO;
import com.villo.alkemychallenge.entities.Movie;
import com.villo.alkemychallenge.events.EntityEditedEvent;
import com.villo.alkemychallenge.repositories.MovieRepository;
import com.villo.alkemychallenge.utils.SpecificationExecuter;
import com.villo.alkemychallenge.utils.helpers.CharacterHelper;
import com.villo.alkemychallenge.utils.helpers.MovieHelper;
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
public class MovieService {
    @Qualifier("modelMapperMovies")
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final CharacterService characterService;
    private final CharacterHelper characterHelper;
    private final GenreHelper genreHelper;
    private final MovieHelper movieHelper;
    private final MovieRepository movieRepository;
    private final FromPageToPageDTO<MovieDTO> fromPageToPageDTO;
    private final SpecificationExecuter<Movie, MovieSpecificationRequestDTO> specificationExecuter;

    @Transactional
    public MovieDTO create(final MovieDTO movieDTO) {
        var movie = modelMapper.map(movieDTO, Movie.class);
        movie.setCharacters(new HashSet<>());
        movie.setGenres(new HashSet<>());

        if (!Objects.isNull(movieDTO.getGendersId()))
            movie.setGenres(movieDTO.getGendersId().stream()
                    .map(genreHelper::findGenreByIdOrThrow)
                    .collect(Collectors.toSet()));

        if (!Objects.isNull(movieDTO.getCharacters()))
            movie.setCharacters(movieDTO.getCharacters().stream()
                    .map(characterService::create)
                    .map(characterDTO -> characterHelper.findCharacterByIdOrThrow(characterDTO.getId()))
                    .collect(Collectors.toSet()));

        return modelMapper.map(movieRepository.save(movie), MovieDTO.class);
    }

    public MovieDTO findById(final Long id) {
        return modelMapper.map(movieHelper.findMovieByIdOrThrow(id), MovieDTO.class);
    }

    public PageResponseDTO<MovieDTO> findByFilters(Pageable pageable, MovieSpecificationRequestDTO movieSpecificationRequestDTO) {
        return fromPageToPageDTO.convert(movieRepository
                .findAll(specificationExecuter.execute(movieSpecificationRequestDTO), pageable)
                .map(movie -> modelMapper.map(movie, MovieDTO.class)));
    }

    public MovieDTO edit(final Long id, final MovieDTO movieDTO) {
        var movie = movieHelper.findMovieByIdOrThrow(id);
        var oldMovie = movie.clone();

        BeanUtils.copyProperties(movieDTO, movie, PropertyHelper.getNullPropertyNames(movieDTO));
        movie = movieRepository.save(movie);

        eventPublisher.publishEvent(new EntityEditedEvent(oldMovie, movie));
        return modelMapper.map(movie, MovieDTO.class);
    }

    public void delete(final Long id) {
        movieRepository.delete(movieHelper.findMovieByIdOrThrow(id));
    }
}
