package com.villo.alkemychallenge.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateCharacterRequestDTO {
    private String image;

    @NotNull(message = "El nombre no debe ser nulo")
    private String name;

    private Integer age;

    private Float weight;

    private String history;
}
