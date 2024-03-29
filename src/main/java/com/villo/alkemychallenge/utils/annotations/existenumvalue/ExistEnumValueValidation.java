package com.villo.alkemychallenge.utils.annotations.existenumvalue;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Stream;

public class ExistEnumValueValidation implements ConstraintValidator<ExistEnumValue, Object> {
    private List<String> acceptedValues;

    @Override
    public void initialize(final ExistEnumValue constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) return true;

        return acceptedValues.contains(object.toString().toUpperCase());
    }
}
