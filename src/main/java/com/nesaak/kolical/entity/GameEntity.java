package com.nesaak.kolical.entity;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.utils.Position;
import org.jetbrains.annotations.NotNull;

public class GameEntity extends Entity {

    public GameEntity(@NotNull EntityType entityType, @NotNull Position spawnPosition) {
        super(entityType, spawnPosition);
    }

    @Override
    public void update(long time) {
    }

    @Override
    public void spawn() {
    }
}
