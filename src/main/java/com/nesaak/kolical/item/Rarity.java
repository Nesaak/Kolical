package com.nesaak.kolical.item;

import net.minestom.server.chat.ChatColor;

public class Rarity {

    public static Rarity BASIC = new Rarity("Basic", ChatColor.WHITE);
    public static Rarity UNCOMMON = new Rarity("Uncommon", ChatColor.BRIGHT_GREEN);
    public static Rarity RARE = new Rarity("Rare", ChatColor.DARK_CYAN);
    public static Rarity SPECIAL = new Rarity("Special", ChatColor.PINK);

    private final String name;
    private final ChatColor color;

    public Rarity(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }
}
