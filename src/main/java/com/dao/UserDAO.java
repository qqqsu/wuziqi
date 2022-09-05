package com.dao;


import java.util.List;

public interface UserDAO {
    public void add(User user);
    public void delete(String username);
    public void updateUser(User user);
    public User selectUser(String name);
    public List<User> selectAllUsers();
    public void updateGame(String username,int gameResult); //1 win -1lose 0 runaway
}
