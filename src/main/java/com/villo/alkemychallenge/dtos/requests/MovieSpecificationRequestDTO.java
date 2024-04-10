package com.villo.alkemychallenge.dtos.requests;

import com.villo.alkemychallenge.utils.Constants;
import com.villo.alkemychallenge.utils.annotations.existenumvalue.ExistEnumValue;
import com.villo.alkemychallenge.utils.annotations.specificationfield.SpecificationField;
import com.villo.alkemychallenge.utils.enums.OrderDirection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MovieSpecificationRequestDTO {
    @Schema(example = Constants.TITLE_EXAMPLE)
    @Size(min = Constants.MIN_SIZE_TITLE, max = Constants.MAX_SIZE_TITLE,
            message = Constants.THE_TITLE + Constants.HAVE_VALID_LENGTH_MESSAGE)
    @SpecificationField(value = "title")
    private String title;

    @Schema(example = Constants.ID_EXAMPLE)
    @PositiveOrZero(message = Constants.THE_GENRE_ID + Constants.MUST_BE_ZERO_OR_GREATER)
    @SpecificationField(join = {"genres"}, value = "id")
    private Integer genre;

    @Schema(example = Constants.ORDER_EXAMPLE)
    @ExistEnumValue(enumClass = OrderDirection.class, message = Constants.THE_ORDER_MUST_BE)
    @SpecificationField(order = true, value = "creationDate")
    private String order;
}
