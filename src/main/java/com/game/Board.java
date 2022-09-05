package com.game;

public class Board {
	private int WIDTH=15;
	private int HEIGHT=15;
	private Piece[][] pieces = new Piece[WIDTH][HEIGHT];
	
	public Board(){
		for(int i=0;i<WIDTH;i++) {
			for(int j=0;j<HEIGHT;j++) pieces[i][j]=new Piece(0);
		}
	}
	
	public void dropPiece(int x,int y,int type) {
		pieces[x][y].setType(type);

	}
	
	public int fiveConnect(int x,int y,int type) {
		int count=0;
		int i=x,j=y;
		for(i=x;i<WIDTH && count<5;i++) {
			if(!isValidPosition(i,j)) break;
			if(pieces[i][j].getType()==type) count++;
			else break;
		}
		for(i=x-1;i>=0 && count<5;i--) {
			if(!isValidPosition(i,j)) break;
			if(pieces[i][j].getType()==type) count++;
			else break;
		}
		if(count>=5) return type;
		
		count=0;
		i=x;j=y;	
		for(j=y;j<HEIGHT && count<5;j++) {
			if(!isValidPosition(i,j)) break;
			if(pieces[i][j].getType()==type) count++;
			else break;
		}
		for(j=y-1;j>=0 &count<5;j--) {
			if(!isValidPosition(i,j)) break;
			if(pieces[i][j].getType()==type) count++;
			else break;
		}
		if(count>=5) return type;
		
		count=0;
		i=x;j=y;
		for(i=x,j=y;x<WIDTH && j<HEIGHT && count<5;i++,j++) {
			if(!isValidPosition(i,j)) break;
			if(pieces[i][j].getType()==type) count++;
			else break;
		}	
		for(i=x-1,j=y-1;x>=0 && j>=0 && count<5;i--,j--) {
			if(!isValidPosition(i,j)) break;
			if(pieces[i][j].getType()==type) count++;
			else break;
		}
		if(count>=5) return type;
		count=0;
		i=x;j=y;
		for(i=x,j=y;x<WIDTH && j>=0 && count<5;i++,j--) {
			if(!isValidPosition(i,j)) break;
			if(pieces[i][j].getType()==type) count++;
			else break;
		}	
		for(i=x-1,j=y+1;x>=0 && j<HEIGHT && count<5;i--,j++) {
			if(!isValidPosition(i,j)) break;
			if(pieces[i][j].getType()==type) count++;
			else break;
		}
		if(count>=5) return type;
		return 0;
	}

	public boolean isEmpty(int x,int y) {
		if(pieces[x][y].getType()==0) return true;
		else return false;
	}

	public int[][] getChesses() {
		int[][] chesses = new int[15][15];
		for(int i=0;i<15;i++){
			for(int j=0;j<15;j++){
				if(getChessColor(i,j)==1) chesses[i][j]=1;
				else if(getChessColor(i,j)==-1) chesses[i][j]=-1;
				else chesses[i][j]=0;
			}
		}
		return chesses;
	}

	public void setPieces(Piece[][] pieces) {
		this.pieces = pieces;
	}

	public Piece[][] getPieces() {
		return pieces;
	}

	public int getChessColor(int i , int j) {
		return pieces[i][j].getType();
	}
	public boolean isValidPosition(int i,int j){
		if(i>0 && j>0 && i<15 && j<15) return true;
		return false;
	}

	public Board copyBoard(){
		Board ret = new Board();
		for(int i =0;i<15;i++){
			for(int j =0; j <15;j++){
				ret.pieces[i][j].setType(this.pieces[i][j].getType());
			}
		}
		return ret;
	}
}
