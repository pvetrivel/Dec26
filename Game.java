package com.cricket.pavi.CricketDB.CricketGame;

public interface Game {
    public void startGame();
    public void doToss();
    public GameResult currentResult();
    public void pauseGame();
    public void resumeGame();
    public boolean hasGameEnded();
}
