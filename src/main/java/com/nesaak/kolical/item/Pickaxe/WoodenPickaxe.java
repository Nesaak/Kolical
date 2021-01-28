package com.nesaak.kolical.item.Pickaxe;

import com.nesaak.kolical.item.GameItem;
import com.nesaak.kolical.item.ItemType;
import com.nesaak.kolical.item.KolicalStackingRule;
import com.nesaak.kolical.item.Rarity;
import com.nesaak.kolical.item.stat.ItemModifier;
import com.nesaak.kolical.item.stat.ItemStat;
import com.nesaak.kolical.util.modifier.Oper;
import net.minestom.server.item.Material;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WoodenPickaxe extends GameItem {

    public WoodenPickaxe() {
        super();
    }

    public WoodenPickaxe(Document document) {
        super(document);
    }

    @Override
    public String getName() {
        return "Wooden Pickaxe";
    }

    @Override
    public String getID() {
        return "wooden_pick";
    }

    @Override
    public Material getMaterial() {
        return Material.WOODEN_PICKAXE;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }

    @Override
    public ItemType getType() {
        return ItemType.ITEM;
    }

    @Override
    public Map<ItemStat, Double> getStats() {
        return Map.of(ItemStat.MINING_SPEED, 2.0);
    }

    @Override
    public List<ItemModifier> getModifiers() {
        return Arrays.asList(new ItemModifier(Oper.MULTIPLY, ItemStat.DAMAGE, 10), new ItemModifier(Oper.ADD, ItemStat.DAMAGE, 50), new ItemModifier(Oper.EXPO, ItemStat.DAMAGE, -0.02));

    }

    @Override
    public KolicalStackingRule getMaxSize() {
        return KolicalStackingRule.SMALL;
    }

}
