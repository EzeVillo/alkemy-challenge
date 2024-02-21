package com.villo.alkemychallenge.dtos.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
public class CreateFilmRequestDTO {

    private Long id;

    private String image;

    @NotNull
    private String title;

    @NotNull
    private LocalDate creationDate;

    @NotNull
    private Integer score;

    @Valid
    private Set<CreateCharacterRequestDTO> characters;
}
