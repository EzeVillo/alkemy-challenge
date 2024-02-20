package com.villo.alkemychallenge.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CharacterFilterRequestDTO {
    private String name;
    private Integer age;
    private Integer film;
}
