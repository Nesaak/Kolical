package com.nesaak.kolical.item.weapon;

import com.nesaak.kolical.item.GameItem;
import com.nesaak.kolical.item.ItemType;
import com.nesaak.kolical.item.KolicalStackingRule;
import com.nesaak.kolical.item.Rarity;
import net.minestom.server.entity.Player;
import net.minestom.server.item.Material;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

public class Stick extends GameItem {

    private int hits = 0;

    public Stick() {
        super();
    }

    public Stick(Document document) {
        super(document);
        hits = document.getInteger(hits);
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    @Override
    public String getName() {
        return "Stick";
    }

    @Override
    public String getID() {
        return "stick";
    }

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.BASIC;
    }

    @Override
    public ItemType getType() {
        return ItemType.ITEM;
    }

    @Override
    public KolicalStackingRule getMaxSize() {
        return KolicalStackingRule.SINGLE;
    }

    @Override
    public void onRightClick(@NotNull Player player, Player.@NotNull Hand hand) {
        super.onRightClick(player, hand);
    }

    @Override
    public void onLeftClick(@NotNull Player player, Player.@NotNull Hand hand) {
        super.onLeftClick(player, hand);
        hits++;
        player.sendMessage(hits + "");
    }
}
