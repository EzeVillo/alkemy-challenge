package com.villo.alkemychallenge.services;

import com.villo.alkemychallenge.helpers.CharacterHelper;
import com.villo.alkemychallenge.helpers.FilmHelper;
import com.villo.alkemychallenge.repositories.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final FilmRepository filmRepository;
    private final CharacterHelper characterHelper;
    private final FilmHelper filmHelper;

    public ResponseEntity<Void> add(final Long idFilm, final Long idCharacter) {
        var film = filmHelper.findFilmByIdOrThrow(idFilm);
        var character = characterHelper.findCharacterByIdOrThrow(idCharacter);

        film.getCharacters().add(character);
        filmRepository.save(film);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> remove(final Long idFilm, final Long idCharacter) {
        var film = filmHelper.findFilmByIdOrThrow(idFilm);
        var character = characterHelper.findCharacterByIdOrThrow(idCharacter);

        film.getCharacters().remove(character);
        filmRepository.save(film);

        return ResponseEntity.noContent().build();
    }
}
