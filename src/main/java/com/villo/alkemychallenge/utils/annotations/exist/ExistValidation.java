package com.villo.alkemychallenge.utils.annotations.exist;

import com.villo.alkemychallenge.utils.errors.custom.NotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ExistValidation implements ConstraintValidator<Exist, Object> {

    private final Map<String, JpaRepository<?, ?>> repositories;
    private String repositoryName;
    private String propertyName;
    private boolean hasToExistToPassValidation;
    private String message;

    @Override
    public void initialize(final Exist constraintAnnotation) {
        this.repositoryName = StringUtils.uncapitalize(constraintAnnotation.repositoryClass().getSimpleName());
        this.propertyName = constraintAnnotation.property();
        this.hasToExistToPassValidation = constraintAnnotation.hasToExistToPassValidation();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        if (value == null) return true;
        boolean isCollection = value instanceof Collection<?>;
        if (isCollection) {
            if (((Collection<?>) value).isEmpty()) return true;
        }

        var repository = Optional.ofNullable(repositories.get(repositoryName));
        if (repository.isPresent()) {
            try {
                var valueType = isCollection ? (((Collection<?>) value).iterator().next().getClass()) : value.getClass();
                var existsByMethod = repository.get().getClass().getMethod("existsBy" + StringUtils.capitalize(propertyName),
                        valueType);

                Boolean result;
                if (value instanceof Collection<?> list) {
                    for (var listItem : list) {
                        result = (Boolean) existsByMethod.invoke(repository.get(), listItem);
                        if (hasToExistToPassValidation && !result) {
                            throw new NotFoundException(message);
                        }
                        if (!hasToExistToPassValidation && result)
                            return false;
                    }
                    return true;
                }

                result = (Boolean) existsByMethod.invoke(repository.get(), value);
                if (hasToExistToPassValidation)
                    if (!result) throw new NotFoundException(message);
                    else return true;
                return !result;

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Repository not found: " + repositoryName);
        }
    }
}
