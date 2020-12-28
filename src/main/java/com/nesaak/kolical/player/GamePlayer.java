package com.nesaak.kolical.player;

import net.minestom.server.entity.Player;
import net.minestom.server.network.player.PlayerConnection;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GamePlayer extends Player {

    public GamePlayer(@NotNull UUID uuid, @NotNull String username, @NotNull PlayerConnection playerConnection) {
        super(uuid, username, playerConnection);
    }

    public void disconnect() {
    }


    public Document toDocument() {
        Document document = new Document();
        document.append("_id", uuid.toString());
        document.append("username", getUsername());
        return document;
    }

    public void fromDocument(Document document) {
    }


}
