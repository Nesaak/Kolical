package com.nesaak.kolical;

import com.nesaak.kolical.mongo.Database;
import net.minestom.server.utils.NamespaceID;

public class Kolical {

    private static Database database;

    public static NamespaceID namespace(String key) {
        return NamespaceID.from("kolical", key);
    }

    public static KolicalServer get() {
        return KolicalServer.server;
    }

    public static Database getDatabase() {
        return database == null ? database = new Database(KolicalServer.server.getDatabase()) : database;
    }

}
