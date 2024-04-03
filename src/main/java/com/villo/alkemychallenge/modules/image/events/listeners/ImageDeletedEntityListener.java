package com.villo.alkemychallenge.modules.image.events.listeners;

import com.villo.alkemychallenge.modules.image.events.ImageDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class ImageDeletedEntityListener {
    private final Set<JpaRepository<?, ?>> repositories;

    @EventListener
    @Async
    public void handleImageDeletedEvent(final ImageDeletedEvent event) {
        repositories.forEach(repository -> {
            try {
                var findByImageMethod = repository.getClass().getMethod("findByImage", String.class);
                Optional<?> optionalEntity = (Optional<?>) findByImageMethod
                        .invoke(repository, "/" + event.getPath().toString());
                optionalEntity.ifPresent(entity -> {
                    try {
                        var setImageMethod = entity.getClass().getMethod("setImage", String.class);
                        setImageMethod.invoke(entity, (String) null);
                        repository.getClass().getMethod("save", Class.forName(entity.getClass().getName()))
                                .invoke(repository, entity);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                             ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException ignored) {
            }
        });
    }
}
