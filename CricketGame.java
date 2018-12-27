package com.cricket.pavi.CricketDB.CricketGame;

import java.util.ArrayList;
import java.util.List;

public class CricketGame implements Game {

    private CricketTeam teamA ;
    private CricketTeam teamB;
    private int target;
    private CricketGameResult currentResult=new CricketGameResult();
    private ScoreBoard curerntInningScoreBoard = new ScoreBoard();
    private CricketMatchController matchController= new CricketMatchController();
    private MatchListener listener;
    private DBListener dbListener;
    private Client mongo;
  //  private List<MatchListener> listeners;

    public  void setDbListener(DBListener dbListener){
        this.dbListener=dbListener;
    }

    public void setListener(MatchListener listener) {
        this.listener = listener;
    }

    public static class MatchStatus{
        boolean running;
        boolean finished;
    }
    private MatchStatus status;

    @Override
    public void startGame() {
        mongo = new Client("localhost", 27017);

        // Seting up Teams

        status = new MatchStatus();
        setupTeams();
        // Tossing
        doToss();
        status.running = true;
        //start innings 1.
        playInnings(currentResult,currentResult.getInnint1Team(),mongo);
        target=curerntInningScoreBoard.getCurrentTotal();
        curerntInningScoreBoard=new ScoreBoard();
        //start innings2
       playInnings(currentResult,currentResult.getInnit2Team(),mongo);
        // Match Result
       matchController.teamScoreCompare(currentResult,teamA,teamB,listener);
        status.finished = true;
dbListener.queryResult(mongo,currentResult);

    }

    public void setupTeams(){
        teamA=new CricketTeam();
        teamB=new CricketTeam();
        setTeam(teamA,"India");
        setTeam(teamB,"Pakistan");
        notifySetupTeams();
    }



    @Override
    public void doToss(){
        doTheToss();
    }

    @Override
    public GameResult currentResult() {
        return currentResult;

    }

    @Override
    public void pauseGame() {
        doPause();
    }

    @Override
    public void resumeGame() {
        if(status.finished){
            throw new RuntimeException("Game is already finished");
        }
        if(status.running){
            throw new RuntimeException("Game already running");
        }
        status.running = true;
        // startPlayingInnings(curerntInningScoreBoard);
    }

    @Override
    public boolean hasGameEnded() {
        return status.finished;
    }

    //final Result


// Assigning score to each team
    private void setTeamScore(CricketTeam battingTeam,ScoreBoard curerntInningScoreBoard) {
        if (battingTeam.getTeamName().equals(teamA.getTeamName())) {
            currentResult.setTeamAScore(curerntInningScoreBoard);

        } else
            {
            currentResult.setTeamBScore(curerntInningScoreBoard);
        }
    }


    // Setting Teams

    private void setTeam(CricketTeam team,String teamName) {
        team.setTeamName(teamName);
        List<CricketTeam.CricketPlayer> teamAPlayers = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            CricketTeam.CricketPlayer p = new CricketTeam.CricketPlayer();
            p.setId(String.valueOf(i));
            p.setName("Player-" + i);
            teamAPlayers.add(p);
        }
    }
    private void notifySetupTeams() {
        listener.teamSetupCompleted(teamA,teamB);
    }


    // Do Toss
    private void doTheToss() {

        int toss = (int) (Math.random() * 2);
        if (toss == 0) {

            currentResult.setTossWinner(teamA);
            currentResult.setInnint1Team(teamA);
            currentResult.setInnit2Team(teamB);
            listener.tossCompleted(currentResult.getTossWinner());
        } else {

            currentResult.setTossWinner(teamB);
            currentResult.setInnint1Team(teamB);
            currentResult.setInnit2Team(teamA);
            listener.tossCompleted(currentResult.getTossWinner());
        }

    }

    private void playInnings(CricketGameResult currentResult,CricketTeam currentInningTeam,Client mongo) {
        currentResult.setCurrentInningTeam(currentInningTeam);
        matchController.startPlayingInnings(curerntInningScoreBoard, currentResult, status, target, listener,dbListener,mongo);
        setTeamScore(currentInningTeam, curerntInningScoreBoard);
    }

      // Assigning players into team


    private void doPause() {
        if(status.finished){
            throw new RuntimeException("Game is already finished");
        }
        if(!status.running){
            throw new RuntimeException("Game is already paused");
        }
        status.running = false;
    }


}

