package com.nesaak.kolical.events;

import com.nesaak.kolical.world.GameInstance;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.utils.Position;

public class PlayerLoginCallback extends EventHandler<PlayerLoginEvent> {

    public PlayerLoginCallback() {
        super(PlayerLoginEvent.class);
    }

    @Override
    public void run(PlayerLoginEvent event) {
        event.setSpawningInstance(GameInstance.getMainInstance());
        event.getPlayer().setEnableRespawnScreen(false);
        event.getPlayer().setPermissionLevel(4);
        event.getPlayer().setRespawnPoint(new Position(0, 50, 0));
    }
}
