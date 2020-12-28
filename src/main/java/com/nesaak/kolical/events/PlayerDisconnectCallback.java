package com.nesaak.kolical.events;

import com.nesaak.kolical.player.GamePlayer;
import com.nesaak.kolical.player.PlayerManager;
import net.minestom.server.event.player.PlayerDisconnectEvent;

import java.util.concurrent.CompletableFuture;

public class PlayerDisconnectCallback extends EventHandler<PlayerDisconnectEvent> {

    public PlayerDisconnectCallback() {
        super(PlayerDisconnectEvent.class);
    }

    @Override
    public void run(PlayerDisconnectEvent event) {
        ((GamePlayer) event.getPlayer()).disconnect();
        CompletableFuture.runAsync(() -> {
            PlayerManager.savePlayerData((GamePlayer) event.getPlayer());
        });
    }
}
