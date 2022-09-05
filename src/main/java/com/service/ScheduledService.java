package com.service;

import com.game.Game;
import com.game.GameManager;
import com.game.UserManager;
import com.pojo.GameEndMessage;
import com.util.MessageUtils;
import com.ws.GameEndPoint;
import com.ws.ServerManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
//该服务用于实现倒计时功能，每秒对所有游戏进行时间更新，如果超时则游戏结束。
public class ScheduledService {
    @Scheduled(fixedDelay=1000)
    public void sendTime() throws IOException {
        Set<Integer> gameIDs = GameManager.getAllGameIDs();
        Game game;
        GameEndPoint gameEndPoint1;
        GameEndPoint gameEndPoint2;
        for(int gameID:gameIDs){
            game = GameManager.getGame(gameID);
            gameEndPoint1 = ServerManager.getGameEndPoint(game.getPlayerA().getName());
            gameEndPoint2 = ServerManager.getGameEndPoint(game.getPlayerB().getName());
            if(game.getNextOrder()==-1){ //此时该白方行棋
                game.getTimes().getWsTime().timeGo();
                game.getTimes().getWtTime().timeGo();
            }
            else { //此时该黑方行棋
                game.getTimes().getBsTime().timeGo();
                game.getTimes().getBtTime().timeGo();
            }
            if(game.getTimes().TimeOutColor()!=0) {
                game.setWinner(game.getTimes().TimeOutColor());
                GameEndMessage gameEndMessage = new GameEndMessage("timeout",game.getWinner());
                gameEndPoint1.sendMessage(MessageUtils.getGameEndMessage(gameEndMessage));
                gameEndPoint2.sendMessage(MessageUtils.getGameEndMessage(gameEndMessage));
                UserManager.setOnlineUser(game.getPlayerA().getName());
                UserManager.setOnlineUser(game.getPlayerB().getName());
                GameManager.removeGame(gameID);
                ServerManager.broadCast(MessageUtils.getUserlistMessage());
            }
            else {
                gameEndPoint1.sendMessage(MessageUtils.getGameTimeMessage(game));
                gameEndPoint2.sendMessage(MessageUtils.getGameTimeMessage(game));
            }
        }
    }
}
