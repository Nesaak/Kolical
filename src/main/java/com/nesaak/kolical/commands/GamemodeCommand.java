package com.nesaak.kolical.commands;

import net.minestom.server.chat.ChatColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GamemodeCommand extends Command {

    public GamemodeCommand() {
        super("gamemode", "gm");
        setCondition(this::isAllowed);
        setDefaultExecutor(this::usage);

        setArgumentCallback(this::gameModeCallback, ArgType.GAMEMODE);

        addSyntax(this::executeOnSelf, ArgType.GAMEMODE);
    }

    private void gameModeCallback(@NotNull CommandSender sender, @NotNull ArgumentSyntaxException e) {
        sender.sendMessage(ChatColor.RED + "'" + e.getInput() + "' is not a valid gamemode!");
    }

    private void executeOnSelf(CommandSender sender, Arguments arguments) {
        Player player = (Player) sender;

        String gamemodeName = arguments.getWord("mode");
        GameMode mode = GameMode.valueOf(gamemodeName.toUpperCase());
        player.setGameMode(mode);
        player.sendMessage("You are now playing in " + gamemodeName);
    }

    private void usage(CommandSender sender, Arguments arguments) {
        sender.sendMessage(ChatColor.RED + "Usage: /gamemode [player] <gamemode>");
    }

    private boolean isAllowed(CommandSender sender, String s) {
        return true;
    }

}
