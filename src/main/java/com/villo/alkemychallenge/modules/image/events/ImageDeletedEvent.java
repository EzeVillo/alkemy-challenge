package com.villo.alkemychallenge.modules.image.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.nio.file.Path;

@Getter
public class ImageDeletedEvent extends ApplicationEvent {
    private final Path path;

    public ImageDeletedEvent(final Path path) {
        super(path);
        this.path = path;
    }
}
