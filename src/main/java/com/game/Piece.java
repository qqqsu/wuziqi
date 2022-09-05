package com.game;

public class Piece {
	private int type; //1 黑 -1白 0空

	public Piece(int type) {
		this.type = type;
	}

	public void setType(int type) {
		this.type=type;
	}
	public int getType() {
		return type;
	}

}
