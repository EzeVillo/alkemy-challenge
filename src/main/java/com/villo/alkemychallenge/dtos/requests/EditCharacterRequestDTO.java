package com.villo.alkemychallenge.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EditCharacterRequestDTO {
    private String image;
    private String name;
    private Integer age;
    private Float weight;
    private String history;
}
