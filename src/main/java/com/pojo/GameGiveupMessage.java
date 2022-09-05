package com.pojo;

import lombok.Data;

@Data
public class GameGiveupMessage {
    private String messageType = "gameGiveup";
    private String fromName;
    private int gameID;

}
