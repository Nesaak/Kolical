package com.nesaak.kolical.player;

import com.nesaak.kolical.item.GameItem;
import com.nesaak.kolical.item.stat.AttributeHolder;
import com.nesaak.kolical.item.stat.ItemModifier;
import com.nesaak.kolical.item.stat.ItemStat;
import com.nesaak.kolical.util.modifier.ModifiableValue;
import com.nesaak.kolical.util.modifier.Modifier;
import com.nesaak.kolical.util.modifier.Oper;
import net.minestom.server.attribute.Attributes;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.event.Event;
import net.minestom.server.event.item.ArmorEquipEvent;
import net.minestom.server.event.player.PlayerChangeHeldSlotEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.server.play.EntityEquipmentPacket;
import net.minestom.server.network.player.PlayerConnection;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GamePlayer extends Player {

    private Map<ItemStat, Double> stats = new ConcurrentHashMap<>();

    public GamePlayer(@NotNull UUID uuid, @NotNull String username, @NotNull PlayerConnection playerConnection) {
        super(uuid, username, playerConnection);
        addEventCallback(ArmorEquipEvent.class, this::equipmentChanged);
        addEventCallback(PlayerChangeHeldSlotEvent.class, this::equipmentChanged);
    }

    public void equipmentChanged(Event event) {
        sendMessage("Equipment Changed");
        stats.clear();
        setMaxHealth(getStat(ItemStat.HEALTH));
    }

    @Override
    public boolean damage(@NotNull DamageType type, float value) {
        float defense = (float) getStat(ItemStat.DEFENSE);
        value = value * (1 - defense / (defense + 200)); // Handle Defense
        return super.damage(type, value);
    }

    public double getStat(ItemStat stat) {
        Double value = stats.get(stat);
        if (value != null) return value;
        ModifiableValue baseValue = new ModifiableValue(stat.getBaseValue());

        for (AttributeHolder holder : getEquipment()) {
            if (holder == null) continue;
            Double val = holder.getStats().get(stat);
            if (val != null) {
                baseValue.addModifier(new Modifier(Oper.ADD, val));
            }

            for (ItemModifier mod : holder.getModifiers()) {
                if (mod.getStat() == stat) {
                    baseValue.addModifier(mod);
                }
            }
        }
        double result = baseValue.calculate();
        stats.put(stat, result);
        return result;
    }

    public void disconnect() {
    }

    public void setMaxHealth(double value) {
        getAttribute(Attributes.MAX_HEALTH).setBaseValue((float) value);
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("_id", uuid.toString());
        document.append("username", getUsername());
        return document;
    }

    public void fromDocument(Document document) {
    }

    public List<GameItem> getEquipment() {
        List<GameItem> items = new ArrayList<>();
        for (EntityEquipmentPacket.Slot value : EntityEquipmentPacket.Slot.values()) {
            ItemStack stack = getEquipment(value);
            if (stack instanceof GameItem) items.add((GameItem) stack);
        }
        return items;
    }
}
