package com.pojo;

import com.game.Game;
import lombok.Data;


@Data
public class GameretMessage {
    //用于反馈给玩家棋盘信息
    private String messageType = "gameret";
    private int gameID;
    private int[][] chesses;
    private int nextOrder;

    public GameretMessage(int ID,Game game) {
        this.gameID = ID;
        this.chesses = game.getBd().getChesses();
        this.nextOrder = game.getNextOrder();
    }
}
