package com.nesaak.kolical.item;

import net.minestom.server.item.ItemStack;
import net.minestom.server.item.rule.VanillaStackingRule;
import org.jetbrains.annotations.NotNull;

public class KolicalStackingRule extends VanillaStackingRule {

    public static KolicalStackingRule SINGLE = new KolicalStackingRule(1);
    public static KolicalStackingRule SMALL = new KolicalStackingRule(4);
    public static KolicalStackingRule MEDIUM = new KolicalStackingRule(16);
    public static KolicalStackingRule LARGE = new KolicalStackingRule(64);
    public static KolicalStackingRule HUGE = new KolicalStackingRule(99);


    public KolicalStackingRule(int maxSize) {
        super(maxSize);
    }

    @Override
    public boolean canBeStacked(@NotNull ItemStack item1, @NotNull ItemStack item2) {
        return item1.getClass().equals(item2.getClass()) && item1.isSimilar(item2);
    }
}
