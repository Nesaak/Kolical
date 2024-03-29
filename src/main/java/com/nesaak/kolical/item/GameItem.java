package com.nesaak.kolical.item;

import com.nesaak.kolical.item.stat.AttributeHolder;
import com.nesaak.kolical.item.stat.ItemModifier;
import com.nesaak.kolical.item.stat.ItemStat;
import net.minestom.server.chat.ColoredText;
import net.minestom.server.chat.JsonMessage;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemFlag;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Direction;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public abstract class GameItem extends ItemStack implements AttributeHolder {

    public GameItem() {
        super(Material.AIR, (byte) 1);
    }

    public GameItem(Document document) {
        super(Material.AIR, (byte) document.getInteger("amount", 1));
    }

    private void init() {
        setMaterial(getMaterial());
        setDisplayName(generateDisplayName());
        setLore(generateLore());
        setStackingRule(getMaxSize());
        addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    }

    public abstract String getName();

    public abstract String getID();

    public abstract Material getMaterial();

    public abstract Rarity getRarity();

    public abstract ItemType getType();

    public abstract KolicalStackingRule getMaxSize();

    @Override
    public List<ItemModifier> getModifiers() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Map<ItemStat, Double> getStats() {
        return Collections.EMPTY_MAP;
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("id", getID());
        document.append("amount", getAmount());
        return document;
    }

    public ColoredText generateDisplayName() {
        return ColoredText.of(getRarity().getColor(), getName());
    }

    public ArrayList<JsonMessage> generateLore() {
        ArrayList<JsonMessage> lore = new ArrayList<>();
        lore.add(ColoredText.of(""));

        // Stats
        if (getStats().size() > 0) {
            getStats().entrySet().stream().sorted(Comparator.comparingInt(o -> o.getKey().ordinal())).forEach(entry -> {
                lore.add(entry.getKey().getDisplay(entry.getValue()));
            });
            lore.add(ColoredText.of(""));
        }

        // Modifiers
        if (getModifiers().size() > 0) {
            for (ItemModifier modifier : getModifiers()) {
                lore.add(modifier.getDisplay());
            }
            lore.add(ColoredText.of(""));
        }

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
