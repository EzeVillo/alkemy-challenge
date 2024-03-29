package com.villo.alkemychallenge.utils;

import com.villo.alkemychallenge.utils.annotations.specificationfield.SpecificationField;
import com.villo.alkemychallenge.utils.enums.OrderDirection;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SpecificationExecuter<T, D> {
    public Specification<T> execute(D criteria) {
        return (root, query, criteriaBuilder) -> {
            var predicate = criteriaBuilder.isTrue(criteriaBuilder.literal(true));

            for (var field : criteria.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                try {
                    var value = field.get(criteria);
                    if (value != null) {
                        if (field.isAnnotationPresent(SpecificationField.class)) {
                            var specificationField = field.getAnnotation(SpecificationField.class);

                            Join<?, ?> join = null;
                            if (specificationField.join().length != 0) {
                                join = root.join(specificationField.join()[0]);
                                for (int i = 1; i < specificationField.join().length; i++)
                                    join = join.join(specificationField.join()[i]);
                            }

                            if (specificationField.order()) {
                                if (OrderDirection.fromValue(value.toString()) == OrderDirection.ASC) {
                                    query.orderBy(criteriaBuilder.asc(join != null ?
                                            join.get(specificationField.value()) :
                                            root.get(specificationField.value())));
                                } else if (OrderDirection.fromValue(value.toString()) == OrderDirection.DESC) {
                                    query.orderBy(criteriaBuilder.desc(join != null ?
                                            join.get(specificationField.value()) :
                                            root.get(specificationField.value())));
                                }
                            } else {
                                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(join != null ?
                                        join.get(specificationField.value()) :
                                        root.get(specificationField.value()), value));
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            return predicate;
        };
    }
}
