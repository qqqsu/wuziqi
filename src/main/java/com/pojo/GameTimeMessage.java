package com.pojo;

import com.game.Times;
import lombok.Data;

@Data
public class GameTimeMessage {
    private String messageType = "gameTime";
    private Times times;
    public GameTimeMessage(Times times) {
        this.times = times;
    }
}
