package com.villo.alkemychallenge.modules.image.utils.annotations.existfile;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = ExistFileValidation.class)
public @interface ExistFile {
    String message() default "The requested file does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean hasRoot();
}
