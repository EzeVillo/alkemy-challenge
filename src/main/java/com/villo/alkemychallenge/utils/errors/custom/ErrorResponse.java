package com.villo.alkemychallenge.utils.errors.custom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class ErrorResponse {
    private final OffsetDateTime timestamp = OffsetDateTime.now();

    @Schema(example = "Error message")
    private final String error;

    public ErrorResponse(final String error) {
        this.error = error;
    }
}
