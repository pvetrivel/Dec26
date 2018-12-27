package com.cricket.pavi.CricketDB.CricketGame;

import java.net.UnknownHostException;

public interface DBListener {

   public void updatePlayerRuns(Client mongo,CricketGameResult currentResult,ScoreBoard inningsScoreBoard);
   public void queryResult(Client mongo,CricketGameResult currentResult);
}
