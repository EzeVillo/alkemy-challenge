package com.villo.alkemychallenge.modules.auth.dtos.requests;

import com.villo.alkemychallenge.modules.auth.repositories.UserRepository;
import com.villo.alkemychallenge.utils.Constants;
import com.villo.alkemychallenge.utils.annotations.exist.Exist;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequestDTO {
    @Schema(example = Constants.USERNAME_EXAMPLE)
    @NotNull(message = Constants.THE_USERNAME + Constants.NOT_BE_NULL_MESSAGE)
    @Size(min = Constants.MIN_SIZE_USERNAME, max = Constants.MAX_SIZE_USERNAME,
            message = Constants.THE_USERNAME + Constants.HAVE_VALID_LENGTH_MESSAGE)
    @Exist(repositoryClass = UserRepository.class, property = "username", hasToExistToPassValidation = true,
            message = Constants.THE_USERNAME + Constants.NOT_EXIST)
    private String username;

    @Schema(example = Constants.PASSWORD_EXAMPLE)
    @NotNull(message = Constants.THE_PASSWORD + Constants.NOT_BE_NULL_MESSAGE)
    @Size(min = Constants.MIN_SIZE_PASSWORD, max = Constants.MAX_SIZE_PASSWORD,
            message = Constants.THE_PASSWORD + Constants.HAVE_VALID_LENGTH_MESSAGE)
    private String password;
}
