package com.villo.alkemychallenge.events.listeners;

import com.villo.alkemychallenge.events.EntityEditedEvent;
import com.villo.alkemychallenge.modules.image.services.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EntityEditedImageListener {
    private final ImageService imageService;

    @EventListener
    @Async
    @SneakyThrows
    public void handleEntityEditedEvent(final EntityEditedEvent event) {
        try {
            var oldImage = event.getOldEntity().getClass().getMethod("getImage").invoke(event.getOldEntity());
            if (oldImage == null)
                return;

            var newImage = event.getNewEntity().getClass().getMethod("getImage").invoke(event.getNewEntity());
            if (newImage == null || oldImage != newImage)
                imageService.delete(oldImage.toString(), true);

        } catch (NoSuchMethodException ignored) {
        }
    }
}
