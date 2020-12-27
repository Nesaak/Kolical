package com.nesaak.kolical.events;

import net.minestom.server.event.EventCallback;
import net.minestom.server.event.player.PlayerLoginEvent;

public class PlayerLoginCallback implements EventCallback<PlayerLoginEvent> {

    @Override
    public void run(PlayerLoginEvent event) {
        //event.setSpawningInstance(container);
        event.getPlayer().setEnableRespawnScreen(false);
        event.getPlayer().setPermissionLevel(4);
    }
}
