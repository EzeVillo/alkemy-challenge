package com.villo.alkemychallenge.modules.image.services;

import com.villo.alkemychallenge.modules.image.dtos.ImageDTO;
import com.villo.alkemychallenge.modules.image.events.ImageDeletedEvent;
import com.villo.alkemychallenge.utils.Constants;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageService {
    @Value(Constants.ROOT)
    private final Path root;
    private final ApplicationEventPublisher eventPublisher;

    @SneakyThrows
    public void init() {
        Files.createDirectories(root);
    }

    @SneakyThrows
    public ImageDTO createImage(final MultipartFile image, final String name) {
        var newName = name + "." + StringUtils.getFilenameExtension(StringUtils
                .cleanPath(Objects.requireNonNull(image.getOriginalFilename())));

        if (Files.exists(this.root.resolve(newName)))
            throw new ValidationException("An image with the same name already exists");

        var stream = image.getInputStream();
        Files.copy(stream, this.root.resolve(newName),
                StandardCopyOption.REPLACE_EXISTING);
        return ImageDTO.builder().nameWithExtension(newName).build();
    }

    @SneakyThrows
    public Resource findByName(final String name) {
        return new UrlResource(this.root.resolve(name).toUri());
    }

    @SneakyThrows
    public void delete(final String name, final boolean hasRoot) {
        Path path;
        if (hasRoot) path = Path.of(name.substring(1));
        else path = this.root.resolve(name);

        Files.deleteIfExists(path);
        eventPublisher.publishEvent(new ImageDeletedEvent(path));
    }
}
