package com.nesaak.kolical.commands;

import com.nesaak.kolical.item.stat.ItemStat;
import com.nesaak.kolical.player.GamePlayer;
import net.minestom.server.chat.ChatColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import org.jetbrains.annotations.NotNull;

public class StatCommand extends Command {

    public StatCommand() {
        super("getstat", "stat");
        setCondition(this::isAllowed);
        setDefaultExecutor(this::usage);

        setArgumentCallback(this::blockCallback, ArgType.STAT);
        addSyntax(this::blockCommand, ArgType.STAT);
    }

    private void blockCallback(@NotNull CommandSender sender, @NotNull ArgumentSyntaxException e) {
        sender.sendMessage(ChatColor.RED + "'" + e.getInput() + "' is not a valid stat!");
    }

    private void blockCommand(CommandSender sender, Arguments arguments) {
        GamePlayer player = (GamePlayer) sender;

        String id = arguments.getWord("stat");

        ItemStat stat = ItemStat.fromID(id);

        if (stat == null) {
            player.sendMessage(ChatColor.RED + "Invalid stat.");
            return;
        }

        player.sendMessage(stat + ": " + player.getStat(stat));
    }

    private void usage(CommandSender sender, Arguments arguments) {
        sender.sendMessage(ChatColor.RED + "Usage: /stat <stat>");
    }

    private boolean isAllowed(CommandSender sender, String s) {
        return true;
    }
}
