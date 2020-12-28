package com.nesaak.kolical.item;

public class ItemType {

    public static ItemType ITEM = new ItemType("Item");

    private final String name;

    public ItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
