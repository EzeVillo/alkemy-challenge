package com.villo.alkemychallenge.helpers;

import com.villo.alkemychallenge.entities.Film;
import com.villo.alkemychallenge.repositories.FilmRepository;
import com.villo.alkemychallenge.utils.Constants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilmHelper {
    private static final String FILM_ENTITY_NAME = "Pelicula";

    private final FilmRepository filmRepository;

    public Film findFilmByIdOrThrow(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constants.NOT_FOUND_MESSAGE,
                        FILM_ENTITY_NAME, id)));
    }
}
