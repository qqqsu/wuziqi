package com.pojo;

import lombok.Data;

@Data
public class LoginMessage {
    private String username;
    private String password;
    private int headID;
}
