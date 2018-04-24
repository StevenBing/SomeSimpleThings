package com.stevenbing.mine;

import java.util.Random;

public class SweepingMap {
	/* public SweepingMap(){
	   super();
	 }
	 
	 public SweepingMap(int rank){
	   this.rank = rank;
	 }*/

	public char[][] getMap() {
		System.out.println("map's rank:" + MineWorld.rank);
		char[][] board;
		if (MineWorld.rank == 10) {
			board = new char[15][10];
			return makeMap(board, MineWorld.rank);
		} else if (MineWorld.rank == 30) {
			board = new char[20][15];
			return makeMap(board, MineWorld.rank);
		} else {
			board = new char[50][35];
			return makeMap(board, MineWorld.rank);
		}
	}

	/**
	 * 获得地图的长宽
	 */
	public int[] getSizeOfMap() {
		char[][] board = getMap();
		return new int[]{board.length, board[0].length};
	}

	private char[][] makeMap(char[][] board, int rank) {
		Random random = new Random();
		int num = 0;
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[i].length; j++){
				board[i][j] = 'E'; 
			}
		}
		while (num < rank) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (random.nextInt(40) < 4 && board[i][j] == 'E') {
						num++;
						board[i][j] = 'M';
					}
					if (num == rank){
						break;
					}
				}
			}
		}
		return board;
	}
}
