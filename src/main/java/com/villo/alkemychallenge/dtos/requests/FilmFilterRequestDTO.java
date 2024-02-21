package com.villo.alkemychallenge.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FilmFilterRequestDTO {
    private String title;
    private Integer genre;
    private String order;
}
