package com.villo.alkemychallenge.modules.image.utils.annotations.existfile;

import com.villo.alkemychallenge.utils.Constants;
import com.villo.alkemychallenge.utils.errors.custom.NotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class ExistFileValidation implements ConstraintValidator<ExistFile, Object> {
    @Value(Constants.ROOT)
    private final Path root;
    private boolean hasRoot;
    private String message;

    @Override
    public void initialize(final ExistFile constraintAnnotation) {
        this.hasRoot = constraintAnnotation.hasRoot();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        if (value == null) return true;
        boolean isValid;
        if (hasRoot) isValid = Files.exists(Path.of(value.toString().substring(1)));
        else isValid = Files.exists(this.root.resolve(value.toString()));

        if (isValid) return true;
        throw new NotFoundException(message);
    }
}
