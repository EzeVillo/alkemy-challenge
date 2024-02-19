package com.villo.alkemychallenge.services;

import com.villo.alkemychallenge.dtos.requests.CreateCharacterRequestDTO;
import com.villo.alkemychallenge.dtos.requests.EditCharacterRequestDTO;
import com.villo.alkemychallenge.dtos.responses.FullCharacterResponseDTO;
import com.villo.alkemychallenge.entities.Character;
import com.villo.alkemychallenge.helpers.PropertyHelper;
import com.villo.alkemychallenge.repositories.CharacterRepository;
import com.villo.alkemychallenge.utils.Constants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private static final String CHARACTER = "Personaje";
    private final ModelMapper modelMapper;
    private final CharacterRepository characterRepository;

    public ResponseEntity<FullCharacterResponseDTO> create(final CreateCharacterRequestDTO createCharacterRequestDTO) {
        var character = modelMapper.map(createCharacterRequestDTO, Character.class);
        character = characterRepository.save(character);

        return new ResponseEntity<>(modelMapper.map(character, FullCharacterResponseDTO.class), HttpStatus.CREATED);
    }

    public ResponseEntity<FullCharacterResponseDTO> findById(final Long id) {
        var character = findCharacterById(id);

        return ResponseEntity.ok(modelMapper.map(character, FullCharacterResponseDTO.class));
    }

    public ResponseEntity<FullCharacterResponseDTO> edit(final Long id, final EditCharacterRequestDTO editCharacterRequestDTO) {
        var character = findCharacterById(id);

        BeanUtils.copyProperties(editCharacterRequestDTO, character, PropertyHelper.getNullPropertyNames(editCharacterRequestDTO));

        character = characterRepository.save(character);
        return ResponseEntity.ok(modelMapper.map(character, FullCharacterResponseDTO.class));
    }

    public ResponseEntity<Void> delete(final Long id) {
        var character = findCharacterById(id);
        character.getFilms().forEach(film -> film.getCharacters().remove(character));

        characterRepository.delete(character);
        return ResponseEntity.noContent().build();
    }

    private Character findCharacterById(Long id) {
        return characterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(Constants.NOT_FOUND_MESSAGE, CHARACTER, id)));
    }
}
