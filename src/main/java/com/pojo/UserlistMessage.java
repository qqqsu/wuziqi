package com.pojo;

import com.game.User;
import lombok.Data;

import java.util.HashSet;

@Data
public class UserlistMessage {
    private String messageType ="userlist";
    private HashSet<User> userlist;

}
