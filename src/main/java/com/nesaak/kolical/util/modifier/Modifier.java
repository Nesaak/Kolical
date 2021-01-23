package com.nesaak.kolical.util.modifier;

public class Modifier {

    final protected Oper operation;
    final protected double value;

    public Modifier(Oper operation, double value) {
        this.operation = operation;
        this.value = value;
    }

    public Oper getOperation() {
        return operation;
    }

    public double getValue() {
        return value;
    }
}
