package com.nesaak.kolical.item.stat;

import net.minestom.server.chat.ChatColor;
import net.minestom.server.chat.ColoredText;
import net.minestom.server.chat.JsonMessage;
import net.minestom.server.chat.RichMessage;

public enum ItemStat {

    DAMAGE("Damage", 1);

    private String name;
    private double baseValue;

    ItemStat(String name, double baseValue) {
        this.name = name;
        this.baseValue = baseValue;
    }

    public JsonMessage getDisplay(double value) {
        ChatColor color = value > 0 ? ChatColor.BRIGHT_GREEN : ChatColor.RED;
        String sign = value > 0 ? "+" : "";
        return RichMessage.of(ColoredText.of(ChatColor.GRAY, getName() + ": ")).append(ColoredText.of(color, sign + value));
    }

    public String getName() {
        return name;
    }

    public double getBaseValue() {
        return baseValue;
    }
}
