package com.pojo;

import lombok.Data;

@Data
public class GameRegretretMessage {
    private String messageType = "gameRegretret";
    private int GameID;
    private String fromName;
    private String toName;
    private boolean accept;

}
