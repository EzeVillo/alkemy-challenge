package com.villo.alkemychallenge.utils.helpers;

import com.villo.alkemychallenge.entities.Genre;
import com.villo.alkemychallenge.repositories.GenreRepository;
import com.villo.alkemychallenge.utils.Constants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenreHelper {
    private final GenreRepository genreRepository;

    public Genre findGenreByIdOrThrow(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constants.GENRE_NOT_FOUND_MESSAGE));
    }
}
