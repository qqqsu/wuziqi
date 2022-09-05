package com.pojo;


import lombok.Data;

@Data
public class GameMessage {
    //用于接受玩家下棋信息
    private String messageType = "game";
    private int gameID;
    private String fromName;
    int xPosition;
    int yPosition;
    int color;
}
