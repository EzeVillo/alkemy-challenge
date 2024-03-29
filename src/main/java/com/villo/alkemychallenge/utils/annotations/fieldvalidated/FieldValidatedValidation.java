package com.villo.alkemychallenge.utils.annotations.fieldvalidated;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FieldValidatedValidation implements ConstraintValidator<FieldValidated, Object> {

    private final Validator validator;
    private Class<?>[] target;

    @Override
    public void initialize(final FieldValidated constraintAnnotation) {
        this.target = constraintAnnotation.target();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(object)) return true;

        if (object instanceof Collection<?> list)
            for (var listItem : list)
                validate(listItem);
        else
            validate(object);

        return true;
    }

    private void validate(final Object object) {
        var violations = validator.validate(object, target);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", ")));
        }
    }
}
