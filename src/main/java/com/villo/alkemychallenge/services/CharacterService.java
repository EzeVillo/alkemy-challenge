package com.villo.alkemychallenge.services;

import com.villo.alkemychallenge.converters.FromPageToPageDTO;
import com.villo.alkemychallenge.dtos.CharacterDTO;
import com.villo.alkemychallenge.dtos.requests.CharacterSpecificationRequestDTO;
import com.villo.alkemychallenge.dtos.responses.PageResponseDTO;
import com.villo.alkemychallenge.entities.Character;
import com.villo.alkemychallenge.events.EntityEditedEvent;
import com.villo.alkemychallenge.repositories.CharacterRepository;
import com.villo.alkemychallenge.utils.SpecificationExecuter;
import com.villo.alkemychallenge.utils.helpers.CharacterHelper;
import com.villo.alkemychallenge.utils.helpers.PropertyHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterService {
    @Qualifier("modelMapperCharacters")
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final CharacterRepository characterRepository;
    private final CharacterHelper characterHelper;
    private final FromPageToPageDTO<CharacterDTO> fromPageToPageDTO;
    private final SpecificationExecuter<Character, CharacterSpecificationRequestDTO> specificationExecuter;

    public CharacterDTO create(final CharacterDTO characterDTO) {
        var character = modelMapper.map(characterDTO, Character.class);
        return modelMapper.map(characterRepository.save(character), CharacterDTO.class);
    }

    public CharacterDTO findById(final Long id) {
        return modelMapper.map(characterHelper.findCharacterByIdOrThrow(id), CharacterDTO.class);
    }

    public PageResponseDTO<CharacterDTO> findByFilters(Pageable pageable, CharacterSpecificationRequestDTO characterSpecificationRequestDTO) {
        return fromPageToPageDTO.convert(characterRepository
                .findAll(specificationExecuter.execute(characterSpecificationRequestDTO), pageable)
                .map(character -> modelMapper.map(character, CharacterDTO.class)));
    }

    public CharacterDTO edit(final Long id, final CharacterDTO characterDTO) {
        var character = characterHelper.findCharacterByIdOrThrow(id);
        var oldCharacter = character.clone();

        BeanUtils.copyProperties(characterDTO, character, PropertyHelper.getNullPropertyNames(characterDTO));
        character = characterRepository.save(character);

        eventPublisher.publishEvent(new EntityEditedEvent(oldCharacter, character));
        return modelMapper.map(character, CharacterDTO.class);
    }

    public void delete(final Long id) {
        var character = characterHelper.findCharacterByIdOrThrow(id);
        character.getMovies().forEach(movie -> movie.getCharacters().remove(character));

        characterRepository.delete(character);
    }
}
