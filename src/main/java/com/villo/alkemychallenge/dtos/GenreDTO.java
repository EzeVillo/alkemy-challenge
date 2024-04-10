package com.villo.alkemychallenge.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.villo.alkemychallenge.modules.image.utils.annotations.existfile.ExistFile;
import com.villo.alkemychallenge.repositories.GenreRepository;
import com.villo.alkemychallenge.utils.Constants;
import com.villo.alkemychallenge.utils.Views;
import com.villo.alkemychallenge.utils.annotations.exist.Exist;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class GenreDTO {
    @JsonView({Views.BasicResponseView.class})
    @Schema(example = Constants.ID_EXAMPLE)
    private Long id;

    @JsonView({Views.CreateRequestView.class, Views.BasicResponseView.class})
    @Schema(example = Constants.GENRE_NAME_EXAMPLE)
    @NotNull(message = Constants.THE_NAME + Constants.NOT_BE_NULL_MESSAGE)
    @Size(min = Constants.MIN_SIZE_NAME, max = Constants.MAX_SIZE_NAME, message = Constants.THE_NAME + Constants.HAVE_VALID_LENGTH_MESSAGE)
    @Exist(repositoryClass = GenreRepository.class, property = "name", hasToExistToPassValidation = false,
            message = Constants.THE_NAME + Constants.ALREADY_EXIST)
    private String name;

    @JsonView({Views.CreateRequestView.class, Views.BasicResponseView.class})
    @Schema(example = Constants.GENRE_IMAGE_EXAMPLE)
    @ExistFile(hasRoot = true)
    @Exist(repositoryClass = GenreRepository.class, property = "image", hasToExistToPassValidation = false,
            message = Constants.THE_IMAGE_IS_ALREADY_USED)
    private String image;

    @JsonView({Views.FullResponseView.class})
    private Set<MovieDTO> movies;
}
