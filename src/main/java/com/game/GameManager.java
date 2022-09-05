package com.game;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GameManager {
    private static Map<Integer,Game> games = new ConcurrentHashMap<>();

    public static int addNewGame(Game game){
        int ID = getNewID();
        games.put(ID,game);
        return ID;
    }
    public static void removeGame(int ID){
        games.remove(ID);
    }
    public static Set<Integer> getAllGameIDs(){
        return games.keySet();
    }
    public static int getNewID(){
        Set<Integer> IDs = getAllGameIDs();
        int i=1;
        while (true){
            if(IDs.contains(i)) i++;
            else return i;
        }
    }
    public static Game getGame(int ID){
        return games.get(ID);
    }
}

