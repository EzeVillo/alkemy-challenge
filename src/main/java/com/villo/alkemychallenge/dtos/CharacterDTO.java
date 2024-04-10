package com.villo.alkemychallenge.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.villo.alkemychallenge.modules.image.utils.annotations.existfile.ExistFile;
import com.villo.alkemychallenge.repositories.CharacterRepository;
import com.villo.alkemychallenge.utils.Constants;
import com.villo.alkemychallenge.utils.ValidationGroups;
import com.villo.alkemychallenge.utils.Views;
import com.villo.alkemychallenge.utils.annotations.exist.Exist;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CharacterDTO {
    @JsonView({Views.BasicResponseView.class})
    @Schema(example = Constants.ID_EXAMPLE)
    private Long id;

    @JsonView({Views.CreateRequestView.class, Views.EditRequestView.class, Views.BasicResponseView.class})
    @Schema(example = Constants.CHARACTER_IMAGE_EXAMPLE)
    @ExistFile(hasRoot = true)
    @Exist(repositoryClass = CharacterRepository.class, property = "image", hasToExistToPassValidation = false,
            message = Constants.THE_IMAGE_IS_ALREADY_USED)
    private String image;

    @JsonView({Views.CreateRequestView.class, Views.EditRequestView.class, Views.BasicResponseView.class})
    @Schema(example = Constants.NAME_EXAMPLE)
    @NotNull(message = Constants.THE_NAME + Constants.NOT_BE_NULL_MESSAGE, groups = {ValidationGroups.CreateValidationGroup.class})
    @Size(min = Constants.MIN_SIZE_NAME, max = Constants.MAX_SIZE_NAME,
            message = Constants.THE_NAME + Constants.HAVE_VALID_LENGTH_MESSAGE)
    @Exist(repositoryClass = CharacterRepository.class, property = "name", hasToExistToPassValidation = false,
            message = Constants.THE_NAME + Constants.ALREADY_EXIST)
    private String name;

    @JsonView({Views.CreateRequestView.class, Views.EditRequestView.class, Views.FullResponseView.class})
    @Schema(example = Constants.AGE_EXAMPLE)
    @PositiveOrZero(message = Constants.THE_AGE + Constants.MUST_BE_ZERO_OR_GREATER)
    private Integer age;

    @JsonView({Views.CreateRequestView.class, Views.EditRequestView.class, Views.FullResponseView.class})
    @Schema(example = Constants.WEIGHT_EXAMPLE)
    @PositiveOrZero(message = Constants.THE_WEIGHT + Constants.MUST_BE_ZERO_OR_GREATER)
    private Float weight;

    @JsonView({Views.CreateRequestView.class, Views.EditRequestView.class, Views.FullResponseView.class})
    @Schema(example = Constants.HISTORY_EXAMPLE)
    @Size(min = Constants.MIN_SIZE_HISTORY, max = Constants.MAX_SIZE_HISTORY,
            message = Constants.THE_HISTORY + Constants.HAVE_VALID_LENGTH_MESSAGE)
    private String history;

    @JsonView({Views.FullResponseView.class})
    private Set<MovieDTO> movies;
}
