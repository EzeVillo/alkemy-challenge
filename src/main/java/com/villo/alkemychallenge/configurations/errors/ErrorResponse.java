package com.villo.alkemychallenge.configurations.errors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ErrorResponse {
    private OffsetDateTime timestamp;
    private int status;
    private String error;
}
