package com.nesaak.kolical.item;

import net.minestom.server.chat.ColoredText;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Direction;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

public abstract class GameItem extends ItemStack {

    public GameItem() {
        super(Material.AIR, (byte) 1);
        setMaterial(getMaterial());
        setDisplayName(ColoredText.of(getName()));
        setStackingRule(getMaxSize());
    }

    public GameItem(Document document) {
        super(Material.AIR, (byte) document.getInteger("amount", 1));
        setMaterial(getMaterial());
        setDisplayName(ColoredText.of(getName()));
        setStackingRule(getMaxSize());
    }

    public abstract String getName();

    public abstract String getID();

    public abstract Material getMaterial();

    public abstract KolicalStackingRule getMaxSize();

    public Document toDocument() {
        Document document = new Document();
        document.append("id", getID());
        document.append("amount", getAmount());
        return document;
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
