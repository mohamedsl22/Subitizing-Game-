package com.example.moodisalman.subitizing;

public class User {


    public String username;
    public String age;
    public String id;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getUsername() {
        return username;
    }

    public String getAge() {
        return age;
    }

    public String getId() {
        return id;
    }

    public User(String username, String email, String Id) {
        this.username = username;
        this.age = email;
        id=Id;
    }


}
