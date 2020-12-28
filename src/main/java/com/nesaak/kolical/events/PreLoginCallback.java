package com.nesaak.kolical.events;

import com.nesaak.kolical.player.GamePlayer;
import com.nesaak.kolical.player.PlayerManager;
import net.minestom.server.event.player.AsyncPlayerPreLoginEvent;

public class PreLoginCallback extends EventHandler<AsyncPlayerPreLoginEvent> {

    public PreLoginCallback() {
        super(AsyncPlayerPreLoginEvent.class);
    }

    @Override
    public void run(AsyncPlayerPreLoginEvent event) {
        PlayerManager.loadPlayerData((GamePlayer) event.getPlayer());
    }
}
