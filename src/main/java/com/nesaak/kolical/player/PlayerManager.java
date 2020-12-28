package com.nesaak.kolical.player;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.nesaak.kolical.Kolical;
import org.bson.Document;

import java.util.UUID;

public class PlayerManager {
    private static ReplaceOptions replace = new ReplaceOptions().upsert(true);
    private static MongoCollection<Document> db = Kolical.getDatabase().getPlayers();

    public static Document getPlayerData(UUID id) {
        return db.find(Filters.all("_id", id.toString())).first();
    }

    public static void loadPlayerData(GamePlayer player) {
        Document data = getPlayerData(player.getUuid());
        if (data == null) {
            data = player.toDocument();
            db.insertOne(data);
        }
        player.fromDocument(data);
    }

    public static void savePlayerData(GamePlayer player) {
        savePlayerData(player.getUuid(), player.toDocument());
    }

    public static void savePlayerData(UUID uuid, Document document) {
        db.replaceOne(Filters.all("_id", uuid.toString()), document, replace);
    }
}
