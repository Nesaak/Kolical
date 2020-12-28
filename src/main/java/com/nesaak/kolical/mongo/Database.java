package com.nesaak.kolical.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Database {

    private MongoDatabase database;
    private MongoCollection<Document> players;

    public Database(MongoDatabase db) {
        this.database = db;
        players = database.getCollection("players");
    }

    public MongoCollection<Document> getPlayers() {
        return players;
    }



}
