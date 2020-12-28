package com.nesaak.kolical.item.registry;

import com.nesaak.kolical.item.GameItem;
import com.nesaak.noreflection.NoReflection;
import com.nesaak.noreflection.access.DynamicCaller;
import org.bson.Document;

public class ItemInfo<T extends GameItem> {

    protected Class<T> clazz;
    private String id;

    private DynamicCaller newInstance;
    private DynamicCaller fromDocument;

    public ItemInfo(Class<T> clazz, T instance) {
        this.clazz = clazz;
        id = instance.getID();
        try {
            newInstance = NoReflection.shared().get(clazz.getConstructor());
            fromDocument = NoReflection.shared().get(clazz.getConstructor(Document.class));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public Class<T> getItemClass() {
        return clazz;
    }

    public T newInstance() {
        return (T) newInstance.call();
    }

    public T from(Document document) {
        return (T) fromDocument.call(document);
    }
}
