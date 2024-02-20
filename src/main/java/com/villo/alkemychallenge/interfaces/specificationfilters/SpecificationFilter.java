package com.villo.alkemychallenge.interfaces.specificationfilters;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationFilter<R, T> {
    Specification<R> filter(T criteria);
}
