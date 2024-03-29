package com.villo.alkemychallenge.configurations.errors;

import lombok.Getter;

@Getter
public class IntegrationException extends RuntimeException {
    private final int status;

    public IntegrationException(String message, int status) {
        super(message);
        this.status = status;
    }
}
