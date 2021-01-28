package com.nesaak.kolical.commands;

import com.nesaak.kolical.entity.TestMob;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

public class TestCommand extends Command {

    public TestCommand() {
        super("tes");
        setCondition(this::isAllowed);
        setDefaultExecutor(this::usage);
    }

    private void usage(CommandSender sender, Arguments arguments) {
        Player player = (Player) sender;

       TestMob mob = new TestMob(player.getPosition());

       mob.setInstance(player.getInstance());

    }

    private boolean isAllowed(CommandSender sender, String s) {
        return true;
    }
}
