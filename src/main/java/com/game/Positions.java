package com.game;

import java.util.ArrayList;

public class Positions {
    private ArrayList<Position> positions = new ArrayList<Position>();
    private int lastindex = 0 ;
    public void push(Position position){
        positions.add(lastindex,position);
        lastindex++;
    }

    public Position pop(){
        lastindex--;
        Position position = positions.get(lastindex);
        positions.remove(lastindex);
        return position;
    }

}
