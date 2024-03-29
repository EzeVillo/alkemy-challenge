package com.villo.alkemychallenge.modules.auth.dtos.requests;

import com.villo.alkemychallenge.modules.auth.repositories.UserRepository;
import com.villo.alkemychallenge.utils.Constants;
import com.villo.alkemychallenge.utils.annotations.exist.Exist;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterRequestDTO {
    private static final String EMAIL_EXAMPLE = "admin@gmail.com";
    private static final String BE_VALID_EMAIL_MESSAGE = "The email must be correctly formatted.";
    private static final String THE_EMAIL = "The email";
    private static final int MIN_SIZE_EMAIL = 5;

    @Schema(example = Constants.USERNAME_EXAMPLE)
    @NotNull(message = Constants.THE_USERNAME + Constants.NOT_BE_NULL_MESSAGE)
    @Size(min = Constants.MIN_SIZE_USERNAME, max = Constants.MAX_SIZE_USERNAME,
            message = Constants.THE_USERNAME + Constants.HAVE_VALID_LENGTH_MESSAGE)
    @Exist(repositoryClass = UserRepository.class, property = "username", hasToExistToPassValidation = false,
            message = Constants.THE_USERNAME + Constants.ALREADY_EXIST)
    private String username;

    @Schema(example = EMAIL_EXAMPLE)
    @NotNull(message = THE_EMAIL + Constants.NOT_BE_NULL_MESSAGE)
    @Email(message = BE_VALID_EMAIL_MESSAGE)
    @Size(min = MIN_SIZE_EMAIL, max = Constants.MAX_SIZE_EMAIL,
            message = THE_EMAIL + Constants.HAVE_VALID_LENGTH_MESSAGE)
    @Exist(repositoryClass = UserRepository.class, property = "mail", hasToExistToPassValidation = false,
            message = THE_EMAIL + Constants.ALREADY_EXIST)
    private String mail;

    @Schema(example = Constants.PASSWORD_EXAMPLE)
    @NotNull(message = Constants.THE_PASSWORD + Constants.NOT_BE_NULL_MESSAGE)
    @Size(min = Constants.MIN_SIZE_PASSWORD, max = Constants.MAX_SIZE_PASSWORD,
            message = Constants.THE_PASSWORD + Constants.HAVE_VALID_LENGTH_MESSAGE)
    private String password;
}
