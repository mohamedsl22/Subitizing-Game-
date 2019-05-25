package com.example.moodisalman.subitizingadmin;

public class Result {
    int win, lose,level;
    String mode, date,timeInMillSec;

    public Result() {
    }

    public Result(int win, int lose, String mode, String date, int level, String id) {
        this.win = win;
        this.lose = lose;
        this.mode = mode;
        this.date = date;
        this.level = level;
        this.timeInMillSec = id;
    }

    public int getWin() {
        return win;
    }

    public int getLose() {
        return lose;
    }

    public String getMode() {
        return mode;
    }

    public String getDate() {
        return date;
    }

    public int getLevel() {
        return level;
    }

    public String getTimeInMillSec() {
        return timeInMillSec;
    }
}
