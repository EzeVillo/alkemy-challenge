package com.villo.alkemychallenge.utils.annotations.exist;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = ExistValidation.class)
public @interface Exist {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends JpaRepository<?, ?>> repositoryClass();

    String property();

    boolean hasToExistToPassValidation();
}
