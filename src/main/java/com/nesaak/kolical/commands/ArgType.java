package com.nesaak.kolical.commands;

import com.nesaak.kolical.Kolical;
import com.nesaak.kolical.item.stat.ItemStat;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.instance.block.CustomBlock;

import java.util.Arrays;

public class ArgType {

    public static final Argument BLOCK = ArgumentType.Word("blockID").from(MinecraftServer.getBlockManager().getCustomBlocks().stream().map(CustomBlock::getIdentifier).toArray(String[]::new));
    public static final Argument STAT = ArgumentType.Word("stat").from(Arrays.asList(ItemStat.values()).stream().map(Object::toString).toArray(String[]::new));
    public static final Argument ITEM = ArgumentType.Word("itemID").from(Kolical.getItemRegistry().getItemKeys().stream().map(String::toLowerCase).toArray(String[]::new));
    public static final Argument GAMEMODE = ArgumentType.Word("mode").from(Arrays.asList(GameMode.values()).stream().map(gamemode -> gamemode.toString().toLowerCase()).toArray(String[]::new));
}
