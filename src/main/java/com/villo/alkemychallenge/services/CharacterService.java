package com.villo.alkemychallenge.services;

import com.villo.alkemychallenge.dtos.requests.CharacterFilterRequestDTO;
import com.villo.alkemychallenge.dtos.requests.CreateCharacterRequestDTO;
import com.villo.alkemychallenge.dtos.requests.EditCharacterRequestDTO;
import com.villo.alkemychallenge.dtos.responses.BasicCharacterResponseDTO;
import com.villo.alkemychallenge.dtos.responses.FullCharacterResponseDTO;
import com.villo.alkemychallenge.entities.Character;
import com.villo.alkemychallenge.helpers.CharacterHelper;
import com.villo.alkemychallenge.helpers.PropertyHelper;
import com.villo.alkemychallenge.helpers.SpecificationHelper;
import com.villo.alkemychallenge.repositories.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final ModelMapper modelMapper;
    private final CharacterRepository characterRepository;
    private final CharacterHelper characterHelper;
    private final SpecificationHelper<Character, CharacterFilterRequestDTO> specificationHelper;

    public ResponseEntity<FullCharacterResponseDTO> create(final CreateCharacterRequestDTO createCharacterRequestDTO) {
        var character = modelMapper.map(createCharacterRequestDTO, Character.class);
        character = characterRepository.save(character);

        return new ResponseEntity<>(modelMapper.map(character, FullCharacterResponseDTO.class), HttpStatus.CREATED);
    }

    public ResponseEntity<FullCharacterResponseDTO> findById(final Long id) {
        var character = characterHelper.findCharacterByIdOrThrow(id);

        return ResponseEntity.ok(modelMapper.map(character, FullCharacterResponseDTO.class));
    }

    public ResponseEntity<List<BasicCharacterResponseDTO>> findByFilters(CharacterFilterRequestDTO characterFilterRequestDTO) {
        var specification = specificationHelper.getAllSpecifications(characterFilterRequestDTO);

        var characters = characterRepository.findAll(specification).stream()
                .map(character -> modelMapper.map(character, BasicCharacterResponseDTO.class))
                .toList();

        return new ResponseEntity<>(characters, HttpStatus.OK);
    }

    public ResponseEntity<FullCharacterResponseDTO> edit(final Long id, final EditCharacterRequestDTO editCharacterRequestDTO) {
        var character = characterHelper.findCharacterByIdOrThrow(id);

        BeanUtils.copyProperties(editCharacterRequestDTO, character, PropertyHelper.getNullPropertyNames(editCharacterRequestDTO));

        character = characterRepository.save(character);
        return ResponseEntity.ok(modelMapper.map(character, FullCharacterResponseDTO.class));
    }

    public ResponseEntity<Void> delete(final Long id) {
        var character = characterHelper.findCharacterByIdOrThrow(id);
        character.getFilms().forEach(film -> film.getCharacters().remove(character));

        characterRepository.delete(character);
        return ResponseEntity.noContent().build();
    }
}
