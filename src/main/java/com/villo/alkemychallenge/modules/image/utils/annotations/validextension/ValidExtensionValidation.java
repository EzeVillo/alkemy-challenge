package com.villo.alkemychallenge.modules.image.utils.annotations.validextension;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

public class ValidExtensionValidation implements ConstraintValidator<ValidExtension, MultipartFile> {
    private final List<String> extensions = List.of("jpeg", "png");

    @Override
    public boolean isValid(final MultipartFile value, final ConstraintValidatorContext context) {
        if (value == null) return true;
        var extension = StringUtils.getFilenameExtension(StringUtils
                .cleanPath(Objects.requireNonNull(value.getOriginalFilename())));
        return extensions.contains(extension);
    }
}
