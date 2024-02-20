package com.villo.alkemychallenge.interfaces.specificationfilters.impl.characters;

import com.villo.alkemychallenge.dtos.requests.CharacterFilterRequestDTO;
import com.villo.alkemychallenge.entities.Character;
import com.villo.alkemychallenge.interfaces.specificationfilters.SpecificationFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class FilterCharacterByName implements SpecificationFilter<Character, CharacterFilterRequestDTO> {
    @Override
    public Specification<Character> filter(CharacterFilterRequestDTO criteria) {
        return (root, query, criteriaBuilder) -> {
            if (criteria.getName() != null) {
                return criteriaBuilder.equal(root.get("name"), criteria.getName());
            }
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };
    }
}
