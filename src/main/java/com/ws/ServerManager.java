package com.ws;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServerManager {

    private static Map<String, GameEndPoint > onlineUsers = new ConcurrentHashMap<>();

    public static void broadCast(String msg){
        Set<String> AllUsers = getAllUsers();
        for (String User:AllUsers) {
            try {
                GameEndPoint gameEndPoint = onlineUsers.get(User);
                gameEndPoint.sendMessage(msg);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static int getTotal(){
        return onlineUsers.size();
    }
    public static void add(String Username,GameEndPoint gameEndPoint){
        onlineUsers.put(Username,gameEndPoint);
        System.out.println("new user, now "+ onlineUsers.size());
    }
    public static void remove(String username){
        onlineUsers.remove(username);
        System.out.println("remove user, now "+ onlineUsers.size());

    }

    public static Set<String> getAllUsers(){
        return onlineUsers.keySet();
    }

    public static GameEndPoint getGameEndPoint(String username){
        return onlineUsers.get(username);
    }

}