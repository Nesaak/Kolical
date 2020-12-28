package com.nesaak.kolical.events;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventCallback;

public abstract class EventHandler<E extends Event> implements EventCallback<E> {

    private Class<E> eventClass;

    public EventHandler(Class<E> claz) {
        eventClass = claz;
    }

    public Class<E> getEventClass() {
        return eventClass;
    }
}
