package com.game;

import java.util.Random;

public class Player {
    private String name;
    private int color;
    private int headID;

    public Player(String name, int color,int headID) {
        this.name = name;
        this.color = color;
        this.headID = headID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getHeadID() {
        return headID;
    }

    public void setHeadID(int headID) {
        this.headID = headID;
    }
}
