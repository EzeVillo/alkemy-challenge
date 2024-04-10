package com.villo.alkemychallenge.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.villo.alkemychallenge.dtos.CharacterDTO;
import com.villo.alkemychallenge.dtos.MovieDTO;
import com.villo.alkemychallenge.dtos.GenreDTO;
import com.villo.alkemychallenge.utils.Constants;
import com.villo.alkemychallenge.utils.Views;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonView(Views.BasicResponseView.class)
public class PageResponseDTO<T> {
    @Schema(oneOf = {CharacterDTO.class, MovieDTO.class, GenreDTO.class})
    private List<T> content;

    @Schema(example = Constants.SIZE_EXAMPLE)
    private int size;

    @Schema(example = Constants.PAGE_EXAMPLE)
    private int number;

    @Schema(example = Constants.TOTAL_PAGES_EXAMPLE)
    private int totalPages;

    @Schema(example = Constants.TOTAL_ELEMENTS_EXAMPLE)
    private long totalElements;

    @Schema(example = Constants.TRUE)
    @JsonProperty("isFirst")
    private boolean isFirst;

    @Schema(example = Constants.TRUE)
    @JsonProperty("isLast")
    private boolean isLast;

    @Schema(example = Constants.FALSE)
    private boolean hasNext;

    @Schema(example = Constants.FALSE)
    private boolean hasPrevious;

    @Schema(example = Constants.FALSE)
    @JsonProperty("isEmpty")
    private boolean isEmpty;
}
