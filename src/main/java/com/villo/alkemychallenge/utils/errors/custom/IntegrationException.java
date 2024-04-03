package com.villo.alkemychallenge.utils.errors.custom;

import lombok.Getter;

@Getter
public class IntegrationException extends RuntimeException {
    private final int status;

    public IntegrationException(String message, int status) {
        super(message);
        this.status = status;
    }
}
