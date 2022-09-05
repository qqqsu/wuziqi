package com.pojo;

import lombok.Data;

@Data
public class GameEndMessage {
    private String messageType = "gameEnd";
    private String reason;
    private int winner;

    public GameEndMessage(String reason, int winner) {
        this.reason = reason;
        this.winner = winner;
    }

    public GameEndMessage(String reason) {
        this.reason= reason;
    }

}
