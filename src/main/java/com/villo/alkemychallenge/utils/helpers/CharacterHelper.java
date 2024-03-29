package com.villo.alkemychallenge.utils.helpers;

import com.villo.alkemychallenge.entities.Character;
import com.villo.alkemychallenge.repositories.CharacterRepository;
import com.villo.alkemychallenge.utils.Constants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterHelper {
    private final CharacterRepository characterRepository;

    public Character findCharacterByIdOrThrow(Long id) {
        return characterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constants.CHARACTER_NOT_FOUND_MESSAGE));
    }
}
