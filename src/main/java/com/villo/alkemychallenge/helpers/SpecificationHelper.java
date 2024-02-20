package com.villo.alkemychallenge.helpers;

import com.villo.alkemychallenge.interfaces.specificationfilters.SpecificationFilter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.typetools.TypeResolver;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpecificationHelper<R, T> {
    private final Set<SpecificationFilter<R, T>> specificationFilters;

    public Specification<R> getAllSpecifications(T t) {
        Specification<R> specification = Specification.where(null);

        for (SpecificationFilter<R, T> filterImpl : specificationFilters) {
            Class<?>[] typeArguments = TypeResolver.resolveRawArguments(SpecificationFilter.class, filterImpl.getClass());

            if (typeArguments[1].equals(t.getClass()))
                specification = specification.and(filterImpl.filter(t));
        }

        return specification;
    }
}
