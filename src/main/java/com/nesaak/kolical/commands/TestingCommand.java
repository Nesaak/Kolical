package com.nesaak.kolical.commands;

import com.nesaak.kolical.item.weapon.Stick;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;

public class TestingCommand extends Command {

    public TestingCommand() {
        super("test");
        setDefaultExecutor(this::usage);
        setCondition(this::isAllowed);
    }

    private void usage(CommandSender sender, Arguments arguments) {
        sender.asPlayer().getInventory().addItemStack(new Stick());
        sender.asPlayer().getInventory().update();
    }

    private boolean isAllowed(CommandSender sender, String s) {
        return true;
    }
}
