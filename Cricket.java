package com.cricket.pavi.CricketDB;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "team")
public class Cricket {

    public ObjectId _id;
    public int runs;
    public String team;

   // public String playerName;


    public Cricket(){

    }

    public Cricket(ObjectId _id,int runs,String playerName,String team) {
        this._id=_id;
        this.runs=runs;
       // this.playerName=playerName;
        this.team=team;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Cricket{" +
                "_id=" + _id +
                ", runs=" + runs +
                ", team='" + team + '\'' +
                '}';
    }
}


