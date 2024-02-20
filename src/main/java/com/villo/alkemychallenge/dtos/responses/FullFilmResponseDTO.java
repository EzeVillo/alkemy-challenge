package com.villo.alkemychallenge.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FullFilmResponseDTO {
    private Long id;
    private String image;
    private String title;
    private LocalDate creationDate;
    private Integer score;
    private Set<FullFilmCharacterReponseDTO> characters;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class FullFilmCharacterReponseDTO {
        private Long id;
        private String image;
        private String name;
        private Integer age;
        private Float weight;
        private String history;
    }
}
