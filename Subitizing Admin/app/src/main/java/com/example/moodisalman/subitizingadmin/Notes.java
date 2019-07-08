package com.example.moodisalman.subitizingadmin;

public class Notes {
    private String note,date,userName,uid;
    private boolean read;

    public Notes() {
    }

    public Notes(String note, String date, boolean read,String n,String uid) {
        this.note = note;
        this.date = date;
        this.read = read;
        userName=n;
        this.uid=uid;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public boolean isRead() {
        return read;
    }

    public String getUserName() {
        return userName;
    }

    public String getUid() {
        return uid;
    }
}
