package com.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.Game;
import com.game.UserManager;
import com.pojo.*;

public class MessageUtils {
    public static String getUserlistMessage() throws JsonProcessingException {
        UserlistMessage userlistMessage = new UserlistMessage();
        userlistMessage.setUserlist(UserManager.getUserlists());
        String res = "";
        ObjectMapper objectMapper = new ObjectMapper();
        res = objectMapper.writeValueAsString(userlistMessage);
        return res;
    }
    public static String getInviteMessage(InviteMessage inviteMessage) throws JsonProcessingException {
        String res = "";
        ObjectMapper objectMapper = new ObjectMapper();
        res = objectMapper.writeValueAsString(inviteMessage);
        return res;
    }

    public static String getInviteretMessage(InviteretMessage inviteretMessage) throws JsonProcessingException {
        String res = "";
        ObjectMapper objectMapper = new ObjectMapper();
        res = objectMapper.writeValueAsString(inviteretMessage);
        return res;
    }

    public static String getGameStartMessage(GameStartMessage gameStartMessage) throws JsonProcessingException {
        String res = "";
        ObjectMapper objectMapper = new ObjectMapper();
        res = objectMapper.writeValueAsString(gameStartMessage);
        return res;
    }

    public static String getGameretMessage(GameretMessage gameretMessage) throws JsonProcessingException {
        String res = "";
        ObjectMapper objectMapper = new ObjectMapper();
        res = objectMapper.writeValueAsString(gameretMessage);
        return res;
    }

    public static String getGameEndMessage(GameEndMessage gameEndMessage) throws JsonProcessingException {
        String res = "";
        ObjectMapper objectMapper = new ObjectMapper();
        res = objectMapper.writeValueAsString(gameEndMessage);
        return res;
    }


    public static String getGameRegretMessage(GameRegretMessage gameRegretMessage) throws JsonProcessingException {
        String res = "";
        ObjectMapper objectMapper = new ObjectMapper();
        res = objectMapper.writeValueAsString(gameRegretMessage);
        return res;
    }

    public static String getGameRegretretMessage(GameRegretretMessage gameRegretretMessage) throws JsonProcessingException {
        String res = "";
        ObjectMapper objectMapper = new ObjectMapper();
        res = objectMapper.writeValueAsString(gameRegretretMessage);
        return res;
    }

    public static String getGameTimeMessage(Game game) throws JsonProcessingException{
        String res = "";
        ObjectMapper objectMapper = new ObjectMapper();
        GameTimeMessage gameTimeMessage = new GameTimeMessage(game.getTimes());
        res = objectMapper.writeValueAsString(gameTimeMessage);
        return res;
    }
}
