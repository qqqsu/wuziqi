package com.game;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private static Map<String, User> userlists = new ConcurrentHashMap<>();

    public static HashSet<User> getUserlists() {
        Set<String> users= userlists.keySet();
        HashSet<User> res = new HashSet<>();
        for (String user:users) {
            res.add(userlists.get(user));
        }
        return res;
    }

    public static boolean isUserIn(String username){
        HashSet<User> users = getUserlists();
        for (User user: users) {
            System.out.println(user.getUsername());
            if(user.getUsername().equals(username)) return true;
        }
        return false;
    }

    public static void addUser(String username,User user){
        userlists.put(username,user);
    }

    public static void removeUser(String username){
        userlists.remove(username);
    }

    public static void setGamingUser(String username){
        userlists.get(username).setState("gaming");
    }

    public static void setOnlineUser(String username){
        userlists.get(username).setState("online");
    }

    public static User getUser(String username) {
        return userlists.get(username);
    }
}
