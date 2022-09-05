package com.pojo;


import lombok.Data;

@Data
public class GameRegretMessage {
    private String messageType = "gameRegret";
    private int GameID;
    private String fromName;
    private String toName;
}
