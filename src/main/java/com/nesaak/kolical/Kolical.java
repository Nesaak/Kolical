package com.nesaak.kolical;

import com.nesaak.kolical.item.registry.ItemRegistry;
import com.nesaak.kolical.mongo.Database;
import net.minestom.server.utils.NamespaceID;

public class Kolical {

    private static KolicalServer server;

    public static NamespaceID namespace(String key) {
        return NamespaceID.from("kolical", key);
    }

    public static KolicalServer get() {
        return server == null ? server = KolicalServer.server : server;
    }

    public static Database getDatabase() {
        return get().getDatabase();
    }

    public static ItemRegistry getItemRegistry() {
        return get().getItemRegistry();
    }

}
