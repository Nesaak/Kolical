package com.nesaak.kolical.tasks;

import net.minestom.server.MinecraftServer;
import net.minestom.server.benchmark.ThreadResult;
import net.minestom.server.chat.ChatColor;
import net.minestom.server.chat.ColoredText;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.MathUtils;

import java.util.Map;

public class ResourceUsage implements Runnable {

    @Override
    public void run() {
        long ramUsage = MinecraftServer.getBenchmarkManager().getUsedMemory();
        ramUsage /= 1e6; // bytes to MB
        String benchmarkMessage = "";
        for (Map.Entry<String, ThreadResult> resultEntry : MinecraftServer.getBenchmarkManager().getResultMap().entrySet()) {
            String name = resultEntry.getKey();
            ThreadResult result = resultEntry.getValue();
            benchmarkMessage += ChatColor.GRAY + name;
            benchmarkMessage += ": ";
            benchmarkMessage += ChatColor.YELLOW.toString() + MathUtils.round(result.getCpuPercentage(), 2) + "% CPU ";
            benchmarkMessage += ChatColor.RED.toString() + MathUtils.round(result.getUserPercentage(), 2) + "% USER ";
            benchmarkMessage += ChatColor.PINK.toString() + MathUtils.round(result.getBlockedPercentage(), 2) + "% BLOCKED ";
            benchmarkMessage += ChatColor.BRIGHT_GREEN.toString() + MathUtils.round(result.getWaitedPercentage(), 2) + "% WAITED ";
            benchmarkMessage += "\n";
        }
        for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
            ColoredText header = ColoredText.of("RAM USAGE: " + ramUsage + " MB");
            ColoredText footer = ColoredText.of(benchmarkMessage);
            player.sendHeaderFooter(header, footer);
        }
    }
}
