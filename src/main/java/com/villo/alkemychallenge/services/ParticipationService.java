package com.villo.alkemychallenge.services;

import com.villo.alkemychallenge.repositories.FilmRepository;
import com.villo.alkemychallenge.utils.helpers.CharacterHelper;
import com.villo.alkemychallenge.utils.helpers.FilmHelper;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final static String CHARACTER_IS_ALREADY_ASSOCIATED_WITH_FILM = "The character is already associated with the film";
    private final static String CHARACTER_IS_NOT_ASSOCIATED_WITH_FILM = "The character is not associated with the film";

    private final FilmRepository filmRepository;
    private final CharacterHelper characterHelper;
    private final FilmHelper filmHelper;

    public void add(final Long idFilm, final Long idCharacter) {
        var film = filmHelper.findFilmByIdOrThrow(idFilm);
        var character = characterHelper.findCharacterByIdOrThrow(idCharacter);

        if (film.getCharacters().contains(character))
            throw new ValidationException(CHARACTER_IS_ALREADY_ASSOCIATED_WITH_FILM);

        film.getCharacters().add(character);
        filmRepository.save(film);
    }

    public void remove(final Long idFilm, final Long idCharacter) {
        var film = filmHelper.findFilmByIdOrThrow(idFilm);
        var character = characterHelper.findCharacterByIdOrThrow(idCharacter);

        if (!film.getCharacters().contains(character))
            throw new ValidationException(CHARACTER_IS_NOT_ASSOCIATED_WITH_FILM);

        film.getCharacters().remove(character);
        filmRepository.save(film);
    }
}
