package com.pojo;

import lombok.Data;

@Data
public class RegisterMessage {
    private String username;
    private String password;
    private int headID;
    private String registernumber;
}
