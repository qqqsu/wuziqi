package com.game;

import com.pojo.GameMessage;

import java.util.Random;

public class Game {
	private Board bd;
	private Positions positions= new Positions();  //用于存储下棋记录
	private Player playerA;
	private Player playerB;
	private int nextOrder; //1为黑 -1为白
	private boolean gameOn=true;
	private int winner;
	private Times times;


	public Player getPlayerA() {
		return playerA;
	}

	public void setPlayerA(Player playerA) {
		this.playerA = playerA;
	}

	public Player getPlayerB() {
		return playerB;
	}

	public void setPlayerB(Player playerB) {
		this.playerB = playerB;
	}

	public Game(String nameA, String nameB) {
		int randomColor = new Random().nextInt(10);
		if(randomColor>4) randomColor =1;
		else randomColor=-1;
 		playerA = new Player(nameA,randomColor,UserManager.getUser(nameA).getHeadID());
		playerB = new Player(nameB,randomColor*(-1),UserManager.getUser(nameB).getHeadID());
		gameOn = true;
		nextOrder = 1;
		bd = new Board();
		times = new Times();
	}

	public void changeOrder() {
		this.nextOrder = this.nextOrder*(-1);
	}

	public int getNextOrder() {
		return nextOrder;
	}


	public void play(GameMessage gameMessage) {
		int x = gameMessage.getXPosition();
		int y = gameMessage.getYPosition();
		int color = gameMessage.getColor();
		this.getTimes().getBsTime().setTime(0,1,0);
		this.getTimes().getWsTime().setTime(0,1,0);
		this.positions.push(new Position(x,y));
		bd.dropPiece(x, y, color);
		int res=bd.fiveConnect(x, y, color);
		changeOrder();
		if(res==color) {
			gameOn=false;
			winner = color;
		}
	}

	public Board getBd() {
		return bd;
	}

	public boolean isGameOn() {
		return gameOn;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public void regret() {
		Position position = this.positions.pop();
		bd.dropPiece(position.getxPosition(), position.getyPosition(),0 );
		changeOrder();
	}

	public Times getTimes() {
		return times;
	}

	public void setTimes(Times times) {
		this.times = times;
	}

	public String getWinnerName(){
		if(this.getWinner() ==  this.getPlayerA().getColor()) {
			return this.getPlayerA().getName();
		}
		else {
			return this.getPlayerB().getName();
		}
	}
	public String getLoserName(){
		if(this.getWinner() ==  this.getPlayerA().getColor()) {
			return this.getPlayerB().getName();
		}
		else {
			return this.getPlayerA().getName();
		}
	}

}
