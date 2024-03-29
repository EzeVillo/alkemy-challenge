package com.villo.alkemychallenge.dtos.requests;

import com.villo.alkemychallenge.utils.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PageRequestDTO {
    @Schema(example = Constants.SIZE_EXAMPLE)
    @Positive(message = Constants.THE_SIZE + Constants.MUST_BE_POSITIVE)
    private int size;

    @Schema(example = Constants.PAGE_EXAMPLE)
    @PositiveOrZero(message = Constants.THE_NUMBER + Constants.MUST_BE_ZERO_OR_GREATER)
    private int number;
}
