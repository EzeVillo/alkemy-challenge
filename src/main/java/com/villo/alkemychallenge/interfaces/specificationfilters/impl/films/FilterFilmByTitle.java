package com.villo.alkemychallenge.interfaces.specificationfilters.impl.films;

import com.villo.alkemychallenge.dtos.requests.FilmFilterRequestDTO;
import com.villo.alkemychallenge.entities.Film;
import com.villo.alkemychallenge.interfaces.specificationfilters.SpecificationFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class FilterFilmByTitle implements SpecificationFilter<Film, FilmFilterRequestDTO> {
    @Override
    public Specification<Film> filter(FilmFilterRequestDTO criteria) {
        return (root, query, criteriaBuilder) -> {
            if (criteria.getTitle() != null) {
                return criteriaBuilder.equal(root.get("title"), criteria.getTitle());
            }
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };
    }
}
