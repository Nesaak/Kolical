package com.nesaak.kolical.commands;

import com.nesaak.kolical.Kolical;
import com.nesaak.kolical.item.GameItem;
import net.minestom.server.chat.ChatColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiveCommand extends Command {

    public GiveCommand() {
        super("give", "i");
        setCondition(this::isAllowed);
        setDefaultExecutor(this::usage);

        setArgumentCallback(this::itemCallback, ArgType.ITEM);

        addSyntax(this::itemCommand, ArgType.ITEM);
    }

    private void itemCallback(@NotNull CommandSender sender, @NotNull ArgumentSyntaxException e) {
        sender.sendMessage(ChatColor.RED + "'" + e.getInput() + "' is not a valid item!");
    }

    private void itemCommand(CommandSender sender, Arguments arguments) {
        Player player = (Player) sender;

        String id = arguments.getWord("itemID");
        GameItem item = Kolical.getItemRegistry().getInfo(id).newInstance();
        player.getInventory().addItemStack(item);
        player.sendMessage(ChatColor.BRIGHT_GREEN + "You have been given a " + ChatColor.GRAY + item.getName());
    }

    private void usage(CommandSender sender, Arguments arguments) {
        sender.sendMessage(ChatColor.RED + "Usage: /give <item>");
    }

    private boolean isAllowed(CommandSender sender, String s) {
        return true;
    }
}
