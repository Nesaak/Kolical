package com.nesaak.kolical.item.weapon;

import com.nesaak.kolical.item.GameItem;
import com.nesaak.kolical.item.ItemType;
import com.nesaak.kolical.item.KolicalStackingRule;
import com.nesaak.kolical.item.Rarity;
import com.nesaak.kolical.util.Cooldown;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ObjectEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.item.Material;
import net.minestom.server.utils.Position;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class Stick extends GameItem {

    private int hits = 0;
    private Cooldown cooldown = new Cooldown(TimeUnit.MILLISECONDS,500);

    public Stick() {
        super();
    }

    public Stick(Document document) {
        super(document);
        test: {

        }
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
        if (cooldown.useIfReady()) {
            player.setVelocity(player.getPosition().getDirection().normalize().multiply(50).add(0f, 5f, 0));
            player.sendMessage("used ability");
        } else {
            player.sendMessage("cannot use this for " + cooldown.getTimeRemaining() + "ms");
        }
    }

    @Override
    public void onLeftClick(@NotNull Player player, Player.@NotNull Hand hand) {
        super.onLeftClick(player, hand);
        hits++;
        Position spawn = player.getPosition().clone().add(0f, 1.5f, 0).add(player.getPosition().getDirection().normalize().multiply(2).toPosition());
        {
            Entity arrow = new ObjectEntity(EntityType.ARROW, spawn) {

                @Override
                public void update(long time) {
                }

                @Override
                public void spawn() {
                }

                @Override
                public int getObjectData() {
                    return 2;
                }

                @Override
                public void tick(long time) {
                    super.tick(time);
                    //sendPacketsToViewers(ParticleCreator.createParticlePacket(Particle.CLOUD, this.lastX, this.lastY, this.lastZ, 0f, 0f, 0f, 3));
                }

            };
            arrow.setGravity(0.01f, 0.04f, 5f);
            arrow.setNoGravity(true);
            arrow.setInstance(player.getInstance());
            arrow.setVelocity(player.getPosition().getDirection().normalize().add(0f, 0.2f, 0).multiply(20));
        }
    }
}
