package com.villo.alkemychallenge.utils.annotations.fieldvalidated;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = FieldValidatedValidation.class)
//El Validated de spring tiene @Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER}) y no el ElementType.FIELD :(
public @interface FieldValidated {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?>[] target() default {};
}
