package com.villo.alkemychallenge.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BasicGenreResponseDTO {
    private Long id;
    private String name;
    private String image;
}
