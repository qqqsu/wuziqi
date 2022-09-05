package com.controller;

import com.dao.User;
import com.pojo.RegisterMessage;
import com.pojo.RegisterretMessage;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RequestMapping("/register")
@Controller
@CrossOrigin
public class RegisterController {
    @Autowired
    UserService userService;

    @Value("636250")
    private String registernumber;

    @PostMapping(value = "/register")
    @ResponseBody
    public RegisterretMessage login(@RequestBody RegisterMessage sourseRegisterMessage) throws SQLException {
        RegisterretMessage registerretretMessage =  new RegisterretMessage();
        User user = userService.selectUser(sourseRegisterMessage.getUsername());
        if(user == null && sourseRegisterMessage.getRegisternumber().equals(registernumber)){
            User usernew = new User();
            registerretretMessage.setFlag(true);
            usernew.setUsername(sourseRegisterMessage.getUsername());
            usernew.setPassword(sourseRegisterMessage.getPassword());
            usernew.setHeadID(sourseRegisterMessage.getHeadID());
            userService.addUser(usernew);
            System.out.println(sourseRegisterMessage.getHeadID()+" headID");
        }
        return registerretretMessage;  //返回给客户端
    }

}
