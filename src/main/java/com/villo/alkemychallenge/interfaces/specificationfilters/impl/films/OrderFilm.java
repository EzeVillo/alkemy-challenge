package com.villo.alkemychallenge.interfaces.specificationfilters.impl.films;

import com.villo.alkemychallenge.dtos.requests.FilmFilterRequestDTO;
import com.villo.alkemychallenge.entities.Film;
import com.villo.alkemychallenge.interfaces.specificationfilters.SpecificationFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class OrderFilm implements SpecificationFilter<Film, FilmFilterRequestDTO> {
    private static final String ASCENDING_ORDER = "ASC";
    private static final String DESCENDING_ORDER = "DESC";
    private static final String CREATION_DATE = "creationDate";

    @Override
    public Specification<Film> filter(FilmFilterRequestDTO criteria) {
        return (root, query, criteriaBuilder) -> {
            if (criteria.getOrder() != null) {
                if (criteria.getOrder().equalsIgnoreCase(ASCENDING_ORDER)) {
                    query.orderBy(criteriaBuilder.asc(root.get(CREATION_DATE)));
                } else if (criteria.getOrder().equalsIgnoreCase(DESCENDING_ORDER)) {
                    query.orderBy(criteriaBuilder.desc(root.get(CREATION_DATE)));
                }
            }
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };
    }
}
