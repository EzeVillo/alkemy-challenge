package com.villo.alkemychallenge.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.villo.alkemychallenge.repositories.FilmRepository;
import com.villo.alkemychallenge.repositories.GenreRepository;
import com.villo.alkemychallenge.utils.Constants;
import com.villo.alkemychallenge.utils.ValidationGroups;
import com.villo.alkemychallenge.utils.Views;
import com.villo.alkemychallenge.utils.annotations.exist.Exist;
import com.villo.alkemychallenge.utils.annotations.fieldvalidated.FieldValidated;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FilmDTO {
    @JsonView({Views.BasicResponseView.class})
    @Schema(example = Constants.ID_EXAMPLE)
    private Long id;

    @JsonView({Views.CreateRequestView.class, Views.EditRequestView.class, Views.BasicResponseView.class})
    @Schema(example = Constants.FILM_IMAGE_EXAMPLE)
    private String image;

    @JsonView({Views.CreateRequestView.class, Views.EditRequestView.class, Views.BasicResponseView.class})
    @Schema(example = Constants.TITLE_EXAMPLE)
    @NotNull(message = Constants.THE_TITLE + Constants.NOT_BE_NULL_MESSAGE, groups = {ValidationGroups.CreateValidationGroup.class})
    @Size(min = Constants.MIN_SIZE_TITLE, max = Constants.MAX_SIZE_TITLE, message = Constants.THE_TITLE + Constants.HAVE_VALID_LENGTH_MESSAGE)
    @Exist(repositoryClass = FilmRepository.class, property = "title", hasToExistToPassValidation = false,
            message = Constants.THE_TITLE + Constants.ALREADY_EXIST)
    private String title;

    @JsonView({Views.CreateRequestView.class, Views.EditRequestView.class, Views.BasicResponseView.class})
    @Schema(example = Constants.CREATION_DATE_EXAMPLE)
    @NotNull(message = Constants.THE_CREATION_DATE + Constants.NOT_BE_NULL_MESSAGE, groups = {ValidationGroups.CreateValidationGroup.class})
    @Past(message = Constants.THE_CREATION_DATE + Constants.MUST_BE_A_PAST_DATE)
    private LocalDate creationDate;

    @JsonView({Views.CreateRequestView.class, Views.EditRequestView.class, Views.FullResponseView.class})
    @Schema(example = Constants.SCORE_EXAMPLE)
    @NotNull(message = Constants.THE_SCORE + Constants.NOT_BE_NULL_MESSAGE, groups = {ValidationGroups.CreateValidationGroup.class})
    @Range(min = Constants.MIN_SCORE, max = Constants.MAX_SCORE, message = Constants.THE_SCORE + Constants.MUST_BE_WITHIN_A_VALID_RANGE)
    private Integer score;

    @JsonView({Views.CreateRequestView.class, Views.EditRequestView.class})
    @ArraySchema(schema = @Schema(example = Constants.ID_EXAMPLE))
    @Exist(repositoryClass = GenreRepository.class, property = "id", hasToExistToPassValidation = true,
            message = Constants.GENRE_NOT_FOUND_MESSAGE)
    private Set<Long> gendersId;

    @JsonView(Views.FullResponseView.class)
    private Set<GenreDTO> genres;

    @JsonView({Views.CreateRequestView.class, Views.FullResponseView.class})
    @FieldValidated(target = {ValidationGroups.CreateValidationGroup.class}, groups = {ValidationGroups.CreateValidationGroup.class})
    private Set<CharacterDTO> characters;
}
