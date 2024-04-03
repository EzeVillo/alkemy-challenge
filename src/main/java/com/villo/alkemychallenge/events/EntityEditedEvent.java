package com.villo.alkemychallenge.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EntityEditedEvent extends ApplicationEvent {
    private final Object oldEntity;
    private final Object newEntity;

    public EntityEditedEvent(final Object oldEntity, final Object newEntity) {
        super(oldEntity);
        this.oldEntity = oldEntity;
        this.newEntity = newEntity;
    }
}
