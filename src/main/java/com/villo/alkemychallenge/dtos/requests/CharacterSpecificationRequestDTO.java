package com.villo.alkemychallenge.dtos.requests;

import com.villo.alkemychallenge.utils.Constants;
import com.villo.alkemychallenge.utils.annotations.specificationfield.SpecificationField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CharacterSpecificationRequestDTO {
    @Schema(example = Constants.NAME_EXAMPLE)
    @Size(min = Constants.MIN_SIZE_NAME, max = Constants.MAX_SIZE_NAME,
            message = Constants.THE_NAME + Constants.HAVE_VALID_LENGTH_MESSAGE)
    @SpecificationField(value = "name")
    private String name;

    @Schema(example = Constants.AGE_EXAMPLE)
    @PositiveOrZero(message = Constants.THE_AGE + Constants.MUST_BE_ZERO_OR_GREATER)
    @SpecificationField(value = "age")
    private Integer age;

    @Schema(example = Constants.ID_EXAMPLE)
    @PositiveOrZero(message = Constants.THE_MOVIE_ID + Constants.MUST_BE_ZERO_OR_GREATER)
    @SpecificationField(join = {"movies"}, value = "id")
    private Integer movies;
}
