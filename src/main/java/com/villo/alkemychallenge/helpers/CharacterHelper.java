package com.villo.alkemychallenge.helpers;

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
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constants.NOT_FOUND_MESSAGE,
                        Constants.CHARACTER_ENTITY_NAME, id)));
    }
}
