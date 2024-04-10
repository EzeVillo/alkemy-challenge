package com.villo.alkemychallenge.services;

import com.villo.alkemychallenge.repositories.MovieRepository;
import com.villo.alkemychallenge.utils.helpers.CharacterHelper;
import com.villo.alkemychallenge.utils.helpers.MovieHelper;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final static String CHARACTER_IS_ALREADY_ASSOCIATED_WITH_MOVIE = "The character is already associated with the movie";
    private final static String CHARACTER_IS_NOT_ASSOCIATED_WITH_MOVIE = "The character is not associated with the movie";

    private final MovieRepository movieRepository;
    private final CharacterHelper characterHelper;
    private final MovieHelper movieHelper;

    public void add(final Long idMovie, final Long idCharacter) {
        var movie = movieHelper.findMovieByIdOrThrow(idMovie);
        var character = characterHelper.findCharacterByIdOrThrow(idCharacter);

        if (movie.getCharacters().contains(character))
            throw new ValidationException(CHARACTER_IS_ALREADY_ASSOCIATED_WITH_MOVIE);

        movie.getCharacters().add(character);
        movieRepository.save(movie);
    }

    public void remove(final Long idMovie, final Long idCharacter) {
        var movie = movieHelper.findMovieByIdOrThrow(idMovie);
        var character = characterHelper.findCharacterByIdOrThrow(idCharacter);

        if (!movie.getCharacters().contains(character))
            throw new ValidationException(CHARACTER_IS_NOT_ASSOCIATED_WITH_MOVIE);

        movie.getCharacters().remove(character);
        movieRepository.save(movie);
    }
}
