package com.nesaak.kolical.item.weapon;

import com.nesaak.kolical.item.GameItem;
import com.nesaak.kolical.item.KolicalStackingRule;
import net.minestom.server.entity.Player;
import net.minestom.server.item.Material;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

public class Stick extends GameItem {

    private int hits = 0;

    public Stick() {
        super();
        setAmount((byte) 32);
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
    public KolicalStackingRule getMaxSize() {
        return KolicalStackingRule.HUGE;
    }

    @Override
    public void onLeftClick(@NotNull Player player, Player.@NotNull Hand hand) {
        super.onLeftClick(player, hand);
        hits++;
        player.sendMessage(hits + "");
    }
}
