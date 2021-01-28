package com.nesaak.kolical.block;

import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.CustomBlock;
import org.jetbrains.annotations.NotNull;

public abstract class GameBlock extends CustomBlock {

    public GameBlock(@NotNull Block block, @NotNull String identifier) {
        super(block, identifier);
    }
}
