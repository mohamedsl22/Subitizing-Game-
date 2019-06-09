package com.example.moodisalman.subitizingadmin;

public class Result {
    String mode, date,timeInMillSec,win, lose,level;

    public Result() {
    }

    public Result(String win, String lose, String mode, String date, String level, String id) {
        this.win = win;
        this.lose = lose;
        this.mode = mode;
        this.date = date;
        this.level = level;
        this.timeInMillSec = id;
    }

    public String getWin() {
        return win;
    }

    public String getLose() {
        return lose;
    }

    public String getMode() {
        return mode;
    }

    public String getDate() {
        return date;
    }

    public String getLevel() {
        return level;
    }

    public String getTimeInMillSec() {
        return timeInMillSec;
    }
}
