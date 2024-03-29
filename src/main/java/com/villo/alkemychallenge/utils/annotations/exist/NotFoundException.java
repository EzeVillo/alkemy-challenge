package com.villo.alkemychallenge.utils.annotations.exist;

import jakarta.validation.ConstraintDeclarationException;

public class NotFoundException extends ConstraintDeclarationException {
    public NotFoundException(String message) {
        super(message);
    }
}
