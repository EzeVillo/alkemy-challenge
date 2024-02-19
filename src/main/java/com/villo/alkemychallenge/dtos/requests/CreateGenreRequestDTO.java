package com.villo.alkemychallenge.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateGenreRequestDTO {
    @NotNull(message = "El nombre no debe ser nulo")
    private String name;

    private String image;
}
