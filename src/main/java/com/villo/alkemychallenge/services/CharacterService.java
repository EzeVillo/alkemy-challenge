package com.villo.alkemychallenge.services;

import com.villo.alkemychallenge.dtos.requests.CreateCharacterRequestDTO;
import com.villo.alkemychallenge.dtos.requests.EditCharacterRequestDTO;
import com.villo.alkemychallenge.dtos.responses.FullCharacterResponseDTO;
import com.villo.alkemychallenge.entities.Character;
import com.villo.alkemychallenge.helpers.PropertyHelper;
import com.villo.alkemychallenge.repositories.CharacterRepository;
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
    private final ModelMapper modelMapper;
    private final CharacterRepository characterRepository;

    public ResponseEntity<FullCharacterResponseDTO> create(final CreateCharacterRequestDTO createCharacterRequestDTO) {
        var character = modelMapper.map(createCharacterRequestDTO, Character.class);
        character = characterRepository.save(character);

        return new ResponseEntity<>(modelMapper.map(character, FullCharacterResponseDTO.class), HttpStatus.CREATED);
    }

    public ResponseEntity<FullCharacterResponseDTO> edit(final Long id, final EditCharacterRequestDTO editCharacterRequestDTO) {
        var character = characterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personaje con id " + id + " no encontrado"));

        BeanUtils.copyProperties(editCharacterRequestDTO, character, PropertyHelper.getNullPropertyNames(editCharacterRequestDTO));

        character = characterRepository.save(character);
        return ResponseEntity.ok(modelMapper.map(character, FullCharacterResponseDTO.class));
    }

    public ResponseEntity<Void> delete(final Long id) {
        var character = characterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personaje con id " + id + " no encontrado"));
        character.getFilms().forEach(film -> film.getCharacters().remove(character));

        characterRepository.delete(character);
        return ResponseEntity.noContent().build();
    }
}
