package com.villo.alkemychallenge.utils.errors.custom;

import jakarta.validation.ConstraintDeclarationException;

public class NotFoundException extends ConstraintDeclarationException {
    public NotFoundException(String message) {
        super(message);
    }
}
