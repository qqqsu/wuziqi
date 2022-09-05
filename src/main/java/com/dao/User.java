package com.dao;


import lombok.Data;

import java.text.DecimalFormat;

@Data
public class User {
    private int ID;
    private int headID;
    private String username;
    private String password;
    private int winGames;
    private int runAwayGames;
    private int totalGames;
    private double winRate;
    private double runAwayRate;
    public void calRate(){
        winRate = winGames/totalGames;
        runAwayRate = runAwayGames /totalGames;
        DecimalFormat format = new DecimalFormat("#.00");
        String str = format.format(winGames/totalGames);
        winRate = Double.parseDouble(str);
        str = format.format(runAwayGames/totalGames);
        runAwayRate = Double.parseDouble(str);
    }
}
