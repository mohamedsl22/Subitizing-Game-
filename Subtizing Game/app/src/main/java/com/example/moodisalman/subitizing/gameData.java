package com.example.moodisalman.subitizing;


public class gameData {
    /**
     * This class contains objects that is used in the whole project in many places:
     * objNum=num of objects
     * outlevel=num of objNum out of 6
     * repeats=how many times did the outlevel occurred
     * gameMode= 0 -> regular mode , 1 -> random mode
     * xArr , yArr = saves random x,y for objects in random mode.
     * howManyApprnce = to check how many appearance of each objNum.
     * millesecDiffrence = to decrease or increase the amount of milliseconds each time
     * chosenGame = the chosen game theme by the player
     */
    static int objNum ;
    static int outLevel;
    static int repeats;
    static int gameMode;
    static int[] xArr ,yArr;
    static  int[] howManyApprnce;
    static int millesecDiffrence=0;
    static int chosenGame;

}
