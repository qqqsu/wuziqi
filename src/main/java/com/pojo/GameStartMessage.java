package com.pojo;

import com.game.Game;
import com.game.Player;
import lombok.Data;

@Data
public class GameStartMessage {
    private String messageType = "gameStart";
    private int gameID;
    private Player playerA;
    private Player playerB;
    public GameStartMessage(Game game,int gameID) {
        this.gameID = gameID;
        this.playerA = game.getPlayerA();
        this.playerB = game.getPlayerB();
    }
}
