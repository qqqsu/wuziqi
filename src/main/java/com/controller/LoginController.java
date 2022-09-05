package com.controller;

import com.dao.User;
import com.game.UserManager;
import com.pojo.LoginMessage;
import com.pojo.LoginretMessage;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@RequestMapping("/login")
@Controller
@CrossOrigin
public class LoginController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/login")
    @ResponseBody
    public LoginretMessage login(@RequestBody LoginMessage sourseLoginMessage, HttpSession session) throws SQLException {
        LoginretMessage loginretMessage = new LoginretMessage();
        User user = userService.selectUser(sourseLoginMessage.getUsername());
        if(user == null){
            System.out.println("null");
            loginretMessage.setFlag(false);
            loginretMessage.setMessage("notexit");
        }
        else if(session!= null && user.getPassword().equals(sourseLoginMessage.getPassword())) {
            if(UserManager.isUserIn(sourseLoginMessage.getUsername())){
                loginretMessage.setFlag(false);
                loginretMessage.setMessage("alreadyin");
            }
            else {
                loginretMessage.setFlag(true);
                loginretMessage.setMessage("success");
                session.setAttribute("username", user.getUsername());
                session.setAttribute("headID", user.getHeadID());
            }
        }else {
            loginretMessage.setMessage("pwdwrong");
            loginretMessage.setFlag(false);
        }
        return loginretMessage;  //返回给客户端
    }


    @GetMapping("/getUserInfo")
    @ResponseBody
    public String getUsername(HttpSession session){
        return (String) session.getAttribute("username");
    }

}
