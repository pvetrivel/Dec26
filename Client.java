package com.cricket.pavi.CricketDB.CricketGame;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class Client extends MongoClient {

    public Client() {
        super();
    }

    public Client(String host, int port) {
        super(host, port);
    }
}
