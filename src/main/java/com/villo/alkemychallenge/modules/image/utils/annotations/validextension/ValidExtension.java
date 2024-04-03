package com.villo.alkemychallenge.modules.image.utils.annotations.validextension;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = ValidExtensionValidation.class)
public @interface ValidExtension {
    String message() default "Invalid file extension";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
