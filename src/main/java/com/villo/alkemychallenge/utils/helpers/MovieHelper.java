package com.villo.alkemychallenge.utils.helpers;

import com.villo.alkemychallenge.entities.Movie;
import com.villo.alkemychallenge.repositories.MovieRepository;
import com.villo.alkemychallenge.utils.Constants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieHelper {
    private final MovieRepository movieRepository;

    public Movie findMovieByIdOrThrow(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constants.MOVIE_NOT_FOUND_MESSAGE));
    }
}
