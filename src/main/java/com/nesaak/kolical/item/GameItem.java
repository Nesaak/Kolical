package com.nesaak.kolical.item;

import net.minestom.server.chat.ColoredText;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Direction;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class GameItem extends ItemStack {

    public GameItem() {
        super(Material.AIR, (byte) 1);
        setMaterial(getMaterial());
        setLore(generateLore());
        setDisplayName(generateDisplayName());
        setStackingRule(getMaxSize());
    }

    public GameItem(Document document) {
        super(Material.AIR, (byte) document.getInteger("amount", 1));
        setMaterial(getMaterial());
        setDisplayName(generateDisplayName());
        setLore(generateLore());
        setStackingRule(getMaxSize());
    }

    public abstract String getName();

    public abstract String getID();

    public abstract Material getMaterial();

    public abstract Rarity getRarity();

    public abstract ItemType getType();

    public abstract KolicalStackingRule getMaxSize();

    public Document toDocument() {
        Document document = new Document();
        document.append("id", getID());
        document.append("amount", getAmount());
        return document;
    }

    public ColoredText generateDisplayName() {
        return ColoredText.of(getRarity().getColor(), getName());
    }

    public ArrayList<ColoredText> generateLore() {
        ArrayList<ColoredText> lore = new ArrayList<>();
        lore.add(ColoredText.of(""));

        // Rarity Footer
        lore.add(ColoredText.of(getRarity().getColor(), getRarity().getName() + " " + getType().getName()));
        return lore;
    }

    @Override
    public boolean isSimilar(@NotNull ItemStack itemStack) {
        return itemStack instanceof GameItem && ((GameItem) itemStack).getID().equals(getID());
    }

    @Override
    public boolean onUseOnBlock(@NotNull Player player, Player.@NotNull Hand hand, @NotNull BlockPosition position, @NotNull Direction blockFace) {
        player.sendMessage("right clicked on block");
        return true;
    }

    @Override
    public void onRightClick(@NotNull Player player, Player.@NotNull Hand hand) {
        player.sendMessage("right clicked");
    }
}
