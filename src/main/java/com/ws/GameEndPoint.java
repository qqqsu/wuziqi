package com.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.Game;
import com.game.GameManager;
import com.game.User;
import com.game.UserManager;
import com.pojo.*;
import com.service.UserService;
import com.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/game", configurator = GetHttpSessionConfigurator.class)
@Component
public class GameEndPoint {
    private String username;
    private Session session;
    private HttpSession httpSession;
    private static UserService userService;

    // 注入的时候,给类的 service 注入,注意:这个set方法一定不能是静态的
    @Autowired
    public void setChatService(UserService userService) {
        GameEndPoint.userService = userService;
    }



    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws JsonProcessingException {
        //通过httpsession获取用户名并存储
        this.session = session;
        HttpSession httpSession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        String username = (String) httpSession.getAttribute("username");
        int headID = (int) httpSession.getAttribute("headID");
        System.out.println(headID);
        this.username = username;

        //分别在容器内添加用户和会话
        ServerManager.add(username, this);
        User user = new User();
        user.setState("online");
        user.setUsername(username);
        user.setHeadID(headID);
        UserManager.addUser(username,user);
        //推送所有用户用户列表
        String Alluserlistmsg = MessageUtils.getUserlistMessage();
        ServerManager.broadCast(Alluserlistmsg);
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("get"+message);
        String winnerName = "";
        String loserName = "";
        try {
//            收到邀请信息并转发给对应玩家
            if(message.startsWith("{\"messageType\":\"invite\"")){
                InviteMessage msg = objectMapper.readValue(message, InviteMessage.class);
                GameEndPoint gameEndPoint = ServerManager.getGameEndPoint(msg.getToName());
                gameEndPoint.sendMessage(MessageUtils.getInviteMessage(msg));
            }

//            收到邀请反馈，若对方同意则创建存储游戏并发送游戏信息，不同意则忽略
            else if(message.startsWith("{\"messageType\":\"inviteret\"")){
                InviteretMessage msg = objectMapper.readValue(message, InviteretMessage.class);
                if(msg.isAccept()){
                    Game game = new Game(msg.getFromName(),msg.getToName());
                    int ID = GameManager.addNewGame(game);
                    GameStartMessage gameStartMessage = new GameStartMessage(game,ID);
                    UserManager.getUser(msg.getFromName()).setGameID(ID);
                    UserManager.getUser(msg.getToName()).setGameID(ID);
                    GameEndPoint gameEndPoint1 = ServerManager.getGameEndPoint(game.getPlayerA().getName());
                    GameEndPoint gameEndPoint2 = ServerManager.getGameEndPoint(game.getPlayerB().getName());
                    gameEndPoint1.sendMessage(MessageUtils.getGameStartMessage(gameStartMessage));
                    gameEndPoint2.sendMessage(MessageUtils.getGameStartMessage(gameStartMessage));
                    UserManager.setGamingUser(game.getPlayerA().getName());
                    UserManager.setGamingUser(game.getPlayerB().getName());
                    ServerManager.broadCast(MessageUtils.getUserlistMessage());
                }
            }

//            玩家下棋，接受到下棋的位置和颜色，并判断游戏状态，发送整个棋盘信息和游戏信息
            else if(message.startsWith("{\"messageType\":\"game\"")){
                GameMessage msg = objectMapper.readValue(message, GameMessage.class);
                Game game = GameManager.getGame(msg.getGameID());
                game.play(msg);
                GameEndPoint gameEndPoint1 = ServerManager.getGameEndPoint(game.getPlayerA().getName());
                GameEndPoint gameEndPoint2 = ServerManager.getGameEndPoint(game.getPlayerB().getName());
                GameretMessage gameretMessage = new GameretMessage(msg.getGameID(),game);
                gameEndPoint1.sendMessage(MessageUtils.getGameretMessage(gameretMessage));
                gameEndPoint2.sendMessage(MessageUtils.getGameretMessage(gameretMessage));
                if(!game.isGameOn()) {
                    winnerName = game.getWinnerName();
                    loserName = game.getLoserName();
                    userService.updateUserGame(winnerName,1);
                    userService.updateUserGame(loserName,-1);
                    GameEndMessage gameEndMessage = new GameEndMessage("gameover",game.getWinner());
                    gameEndPoint1.sendMessage(MessageUtils.getGameEndMessage(gameEndMessage));
                    gameEndPoint2.sendMessage(MessageUtils.getGameEndMessage(gameEndMessage));
                    UserManager.setOnlineUser(game.getPlayerA().getName());
                    UserManager.setOnlineUser(game.getPlayerB().getName());
                    GameManager.removeGame(msg.getGameID());
                    ServerManager.broadCast(MessageUtils.getUserlistMessage());
                }
            }

//            收到放弃消息,设置游戏结束,并发送游戏结束消息
            else if(message.startsWith("{\"messageType\":\"gameGiveup\"")){
                GameGiveupMessage msg = objectMapper.readValue(message, GameGiveupMessage.class);
                Game game = GameManager.getGame(msg.getGameID());
                GameretMessage gameretMessage = new GameretMessage(msg.getGameID(),game);
                GameEndPoint gameEndPoint1 = ServerManager.getGameEndPoint(game.getPlayerA().getName());
                GameEndPoint gameEndPoint2 = ServerManager.getGameEndPoint(game.getPlayerB().getName());
                int winner;
                loserName = msg.getFromName();
                if(loserName.equals(game.getPlayerB().getName())) {
                    winner = game.getPlayerA().getColor();
                    winnerName = game.getPlayerA().getName();
                }
                else {
                    winner = game.getPlayerB().getColor();
                    winnerName =game.getPlayerB().getName();
                }
                userService.updateUserGame(winnerName,1);
                userService.updateUserGame(loserName,-1);
                GameEndMessage gameEndMessage = new GameEndMessage("gameover",winner);
                gameEndPoint1.sendMessage(MessageUtils.getGameEndMessage(gameEndMessage));
                gameEndPoint2.sendMessage(MessageUtils.getGameEndMessage(gameEndMessage));
                gameEndPoint1.sendMessage(MessageUtils.getGameretMessage(gameretMessage));
                gameEndPoint2.sendMessage(MessageUtils.getGameretMessage(gameretMessage));
                UserManager.setOnlineUser(game.getPlayerA().getName());
                UserManager.setOnlineUser(game.getPlayerB().getName());
                GameManager.removeGame(msg.getGameID());
                ServerManager.broadCast(MessageUtils.getUserlistMessage());
            }
//        收到悔棋信息,转发给对手
        else if(message.startsWith("{\"messageType\":\"gameRegret\"")) {
            GameRegretMessage msg = objectMapper.readValue(message, GameRegretMessage.class);
            GameEndPoint gameEndPoint = ServerManager.getGameEndPoint(msg.getToName());
            gameEndPoint.sendMessage(MessageUtils.getGameRegretMessage(msg));
        }

//        悔棋反馈,若同意则棋盘回到上一次下棋的状态,并发送游戏信息
        else if(message.startsWith("{\"messageType\":\"gameRegretret\"")) {
            GameRegretretMessage msg = objectMapper.readValue(message, GameRegretretMessage.class);
            GameEndPoint gameEndPoint1 = ServerManager.getGameEndPoint(msg.getToName());
            GameEndPoint gameEndPoint2 = ServerManager.getGameEndPoint(msg.getFromName());
            if(msg.isAccept()){
                Game game = GameManager.getGame(msg.getGameID());
                game.regret();
                GameretMessage gameretMessage = new GameretMessage(msg.getGameID(),game);
                gameEndPoint1.sendMessage(MessageUtils.getGameretMessage(gameretMessage));
                gameEndPoint2.sendMessage(MessageUtils.getGameretMessage(gameretMessage));
            }
        }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {

//        用户处于游戏中,则像对手发送游戏结束信息,更新用户列表,否则正常退出
        if(UserManager.getUser(username).getState()=="gaming"){
            int ID = UserManager.getUser(username).getGameID();
            Game game = GameManager.getGame(ID);
            int winner;
            String counterusername;
            if(username.equals(game.getPlayerA().getName())) {
                winner = game.getPlayerB().getColor();
                counterusername = game.getPlayerB().getName();
            }
            else {
                winner = game.getPlayerA().getColor();
                counterusername = game.getPlayerA().getName();
            }
            GameEndPoint gameEndPoint = ServerManager.getGameEndPoint(counterusername);
            GameretMessage gameretMessage = new GameretMessage(ID,game);
            gameEndPoint.sendMessage(MessageUtils.getGameretMessage(gameretMessage));
            GameEndMessage gameEndMessage = new GameEndMessage("runaway",winner);
            gameEndPoint.sendMessage(MessageUtils.getGameEndMessage(gameEndMessage));
            GameManager.removeGame(ID);
            UserManager.getUser(counterusername).setGameID(0);
            UserManager.getUser(counterusername).setState("online");
            userService.updateUserGame(username,0);
            userService.updateUserGame(counterusername,1);
        }
        ServerManager.remove(username);
        UserManager.removeUser(username);
        String Alluserlistmesg = MessageUtils.getUserlistMessage();
        ServerManager.broadCast(Alluserlistmesg);
    }

}
