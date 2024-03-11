package com.villo.alkemychallenge.configurations.errors;

import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class ErrorResponse {
    private final OffsetDateTime timestamp = OffsetDateTime.now();
    private final int status;
    private final String error;

    public ErrorResponse(int status, String error) {
        this.error = error;
        this.status = status;
    }
}
