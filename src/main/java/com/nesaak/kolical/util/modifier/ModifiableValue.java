package com.nesaak.kolical.util.modifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ModifiableValue {

    private final Set<Modifier> modifiers = new HashSet<>();
    private final double base;

    public ModifiableValue(double base) {
        this.base = base;
    }

    public double calculate() {
        double add = 0;
        double multi = 1;
        double expo = 1;
        double base = this.base;
        for (Modifier modifier : modifiers) {
            switch (modifier.getOperation()) {
            case ADD:
                add += modifier.getValue();
                break;
            case MULTIPLY:
                multi += modifier.getValue() / 100;
                break;
            case EXPO:
                expo += modifier.getValue() / 100;
            }
        }

        base += Math.max(0, add);
        base *= Math.max(0, multi);
        base = Math.pow(base, Math.max(0, expo));
        return base;
    }

    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
    }

    public void addModifiers(Collection<Modifier> modifiers) {
        modifiers.addAll(modifiers);
    }

}
