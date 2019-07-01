package com.example.moodisalman.subitizing;

/**
 * This class used to save data as an object in the firebase , the object has information about total wins
 * and loses and total of game plays for each user **/

public class ResultsInfo {
    int wins,loses,numOfPlays;

    public ResultsInfo() {
    }

    public ResultsInfo(int wins, int loses, int numOfPlays) {
        this.wins = wins;
        this.loses = loses;
        this.numOfPlays = numOfPlays;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public int getNumOfPlays() {
        return numOfPlays;
    }
}
