package com.nesaak.kolical.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.chat.ChatColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BlockCommand extends Command {

    public BlockCommand() {
        super("block");
        setCondition(this::isAllowed);
        setDefaultExecutor(this::usage);

        setArgumentCallback(this::blockCallback, ArgType.BLOCK);
        addSyntax(this::blockCommand, ArgType.BLOCK);
    }

    private void blockCallback(@NotNull CommandSender sender, @NotNull ArgumentSyntaxException e) {
        sender.sendMessage(ChatColor.RED + "'" + e.getInput() + "' is not a valid block!");
    }

    private void blockCommand(CommandSender sender, Arguments arguments) {
        Player player = (Player) sender;

        String id = arguments.getWord("blockID");

        if (MinecraftServer.getBlockManager().getCustomBlock(id) == null) {
            player.sendMessage(ChatColor.RED + "Invalid block.");
            return;
        }

        player.getInstance().setCustomBlock(player.getTargetBlockPosition(10), id);
        player.sendMessage(ChatColor.BRIGHT_GREEN + "Updated block.");
    }

    private void usage(CommandSender sender, Arguments arguments) {
        sender.sendMessage(ChatColor.RED + "Usage: /block <block>");
    }

    private boolean isAllowed(CommandSender sender, String s) {
        return true;
    }
}
