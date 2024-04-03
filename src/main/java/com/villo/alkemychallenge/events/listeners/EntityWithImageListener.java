package com.villo.alkemychallenge.events.listeners;

import com.villo.alkemychallenge.modules.image.services.ImageService;
import jakarta.persistence.PostRemove;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class EntityWithImageListener {
    private final ImageService imageService;

    @PostRemove
    @SneakyThrows
    public void postRemove(final Object entity) {
        var image = entity.getClass().getMethod("getImage").invoke(entity);
        if (image != null)
            imageService.delete(image.toString(), true);
    }
}
