package com.service;


import com.dao.User;
import com.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//该服务提供用于在数据库中修改用户信息的接口
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public void addUser(User user){
        userDAO.add(user);
    }
    public void deleteUser(String username){
        userDAO.delete(username);
    }
    public void updateUser(User user){
        userDAO.add(user);
    }
    public User selectUser(String name){
       return userDAO.selectUser(name);
    }
    public boolean userExit(String name){
        if(selectUser(name)!=null) return true;
        return false;
    }
    public List<User> selectAllUsers(){
        return userDAO.selectAllUsers();
    }
    public void updateUserGame(String username,int gameResult){
        userDAO.updateGame(username,gameResult);
    }
}

