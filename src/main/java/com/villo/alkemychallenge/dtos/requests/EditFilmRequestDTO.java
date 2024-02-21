package com.villo.alkemychallenge.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EditFilmRequestDTO {
    private String image;
    private String title;
    private LocalDate creationDate;
    private Integer score;
}
