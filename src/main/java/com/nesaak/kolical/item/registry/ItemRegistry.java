package com.nesaak.kolical.item.registry;

import com.nesaak.kolical.Kolical;
import com.nesaak.kolical.item.GameItem;
import org.bson.Document;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ItemRegistry {

    private final Map<String, ItemInfo<?>> idMap = new HashMap<>();
    private final Map<Class<? extends GameItem>, ItemInfo<?>> classMap = new HashMap<>();

    public void registerItems() {
        Kolical.get().getReflections().getSubTypesOf(GameItem.class).forEach(clz -> {
            if (clz.isInterface() || Modifier.isAbstract(clz.getModifiers())) return;
            register(clz);
        });
    }

    public <T extends GameItem> void register(Class<? extends T> clazz) {
        ItemInfo info;
        try {
            GameItem item = clazz.getConstructor().newInstance();
            info = new ItemInfo(clazz, item);
            idMap.put(item.getID(), info);
            classMap.put(clazz, info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Utility

    public Set<String> getItemKeys() {
        return idMap.keySet();
    }

    public <T extends GameItem> ItemInfo<T> getInfo(String ID) {
        return (ItemInfo<T>) idMap.get(ID);
    }

    public <T extends GameItem> ItemInfo<T> getInfo(Class<T> clz) {
        return (ItemInfo<T>) classMap.get(clz);
    }

    // Getting Fishing Items

    public <T extends GameItem> GameItem newInstance(Class<T> clz) {
        return getInfo(clz).newInstance();
    }

    public <T extends GameItem> GameItem newInstance(String id) {
        return getInfo(id).newInstance();
    }

    public GameItem from(Document document) {
        if (document == null) return null;
        String id = document.getString("id");
        if (id == null) return null;
        return getInfo(id).from(document);
    }

}
