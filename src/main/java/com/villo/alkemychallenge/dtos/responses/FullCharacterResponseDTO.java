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
public class FullCharacterResponseDTO {
    private Long id;
    private String image;
    private String name;
    private Integer age;
    private Float weight;
    private String history;
    private Set<FullCharacterFilmReponseDTO> films;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class FullCharacterFilmReponseDTO {
        private Long id;
        private String image;
        private String title;
        private LocalDate creationDate;
        private Integer score;
    }
}
