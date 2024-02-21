package com.villo.alkemychallenge.interfaces.specificationfilters.impl.films;

import com.villo.alkemychallenge.dtos.requests.FilmFilterRequestDTO;
import com.villo.alkemychallenge.entities.Film;
import com.villo.alkemychallenge.entities.Genre;
import com.villo.alkemychallenge.interfaces.specificationfilters.SpecificationFilter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class FilterFilmByGenreId implements SpecificationFilter<Film, FilmFilterRequestDTO> {
    @Override
    public Specification<Film> filter(FilmFilterRequestDTO criteria) {
        return (root, query, criteriaBuilder) -> {
            if (criteria.getGenre() != null) {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<Genre> subRoot = subquery.from(Genre.class);
                Join<Film, Genre> genres = subRoot.join("films");
                subquery.select(genres.get("id"))
                        .where(criteriaBuilder.equal(genres.get("id"), criteria.getGenre()));

                return criteriaBuilder.in(root.get("id")).value(subquery);
            }
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };
    }
}
