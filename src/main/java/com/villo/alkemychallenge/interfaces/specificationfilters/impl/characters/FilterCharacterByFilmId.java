package com.villo.alkemychallenge.interfaces.specificationfilters.impl.characters;

import com.villo.alkemychallenge.dtos.requests.CharacterFilterRequestDTO;
import com.villo.alkemychallenge.entities.Character;
import com.villo.alkemychallenge.interfaces.specificationfilters.SpecificationFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class FilterCharacterByFilmId implements SpecificationFilter<Character, CharacterFilterRequestDTO> {
    @Override
    public Specification<Character> filter(CharacterFilterRequestDTO criteria) {
        return (root, query, criteriaBuilder) -> {
            if (criteria.getFilm() != null) {
                return criteriaBuilder.equal(root.join("films").get("id"), criteria.getFilm());
            }
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };
    }
}
