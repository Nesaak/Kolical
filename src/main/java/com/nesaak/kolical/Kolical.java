package com.nesaak.kolical;

import net.minestom.server.utils.NamespaceID;

public class Kolical {

    public static NamespaceID namespace(String key) {
        return NamespaceID.from("kolical", key);
    }

    public static KolicalServer get() {
        return KolicalServer.server;
    }

}
