package com.stevenbing.minesweeping;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class MineSweeping_DFS {

	public char[][] updateBoard(char[][] board, int[] click) {
		int m = board.length, n = board[0].length;
		int row = click[0], col = click[1];
		if (board[row][col] == 'M') {
			board[row][col] = 'X';
		} else {
			int count = 0;
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (i == 0 && j == 0) {
						continue;
					}
					int r = row + i, c = col + j;
					if (r < 0 || r >= m || c < 0 || c >= n) {
						continue;
					}
					if (board[r][c] == 'M') {
						count++;
					}
				}
			}
			if (count > 0) {
				board[row][col] = (char) (count + '0');
			} else {
				board[row][col] = 'B';
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						if (i == 0 && j == 0) {
							continue;
						}
						int r = row + i, c = col + j;
						if (r < 0 || r >= m || c < 0 || c >= n) {
							continue;
						}
						if (board[r][c] == 'E') {
							updateBoard(board, new int[]{r, c});
						}
					}
				}
			}
		}
		return board;
	}

	public char[][] updateBoardByStack(char[][] board, int[] click) {
		Stack<int[]> stack = new Stack<>();
		int m = board.length, n = board[0].length;
		stack.push(click);
		while (!stack.isEmpty()) {
			int[] peek = stack.peek();
			int row = peek[0], col = peek[1];
			if (board[row][col] == 'M') {
				board[row][col] = 'X';
				stack.pop();
			} else {
				int count = 0;
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						if (i == 0 && j == 0) {
							continue;
						}
						int r = row + i, c = col + j;
						if (r < 0 || r >= m || c < 0 || c >= n) {
							continue;
						}
						if (board[r][c] == 'M') {
							count++;
						}
					}
				}
				if (count > 0) {
					board[row][col] = (char) (count + '0');
					stack.pop();
				} else {
					board[row][col] = 'B';
					int[] findNext = findNext(row, col, board);
					if (findNext[0] == -1 && findNext[1] == -1) {
						stack.pop();
					} else {
						stack.push(findNext);
					}
				}
			}
		}
		return board;
	}

	public int[] findNext(int row, int col, char[][] board) {
		int[] next = new int[]{-1, -1};
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (i == 0 && j == 0) {
					continue;
				}
				int r = row + i, c = col + j;
				if (r < 0 || r >= board.length || c < 0
						|| c > board[0].length) {
					continue;
				}
				if (board[r][c] == 'E') {
					next = new int[]{r, c};
					return next;
				}
			}
		}
		return next;
	}
	public static void main(String[] args) {
		int num01, num02;
		Random random = new Random();
		MineSweeping_DFS mineSweeping_DFS = new MineSweeping_DFS();
		Scanner scanner = new Scanner(System.in);
		int[] click;
		char[][] board = new char[10][10];

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (random.nextInt(50) < 10) {
					board[i][j] = 'M';
				} else {
					board[i][j] = 'O';
				}
			}
		}

		char[][] print = new char[11][11];
		for (int i = 0; i < print.length; i++) {
			print[0][i] = (char) (i + '0');
			print[i][0] = (char) (i + '0');

		}
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				print[i + 1][j + 1] = board[i][j];
			}
		}
		System.out.println("作弊大佬！");
		for (char[] ch : print) {
			for (char res : ch) {
				System.out.print(res + " ");
			}
			System.out.println();
		}
		System.out.println("-----SHOW TIME！！-----");
		for (char[] ch : print) {
			for (char res : ch) {
				if (res == 'O' || res == 'M') {
					res = 'O';
				}
				System.out.print(res + " ");
			}
			System.out.println();
		}

		boolean flag = true;
		do {
			System.out.println("请输入你要点击的地方！");
//			char[] input = scanner.next().toCharArray();
			num01 = scanner.nextInt();
			num02 = scanner.nextInt();
			click = new int[]{num01 - 1, num02 - 1};
			try{
				char[][] updateBoard = mineSweeping_DFS.updateBoard(board, click);
			}catch(Exception e){
				System.out.println("你怕是个傻逼哦，越界了！！！");
				break;
			}
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					print[i + 1][j + 1] = board[i][j];
				}
			}
			
			for (char[] ch : print) {
				for (char res : ch) {
					if (res == 'X') {
						flag = false;
					}
				}
			}
			for (char[] ch : print) {
				for (char res : ch) {
					if (res == 'X') {
						System.out.print(res + " ");
						flag = false;
					} else if (res == 'M' && flag) {
						res = 'O';
						System.out.print(res + " ");
					} else if (res == 'M' && !flag) {
						res = 'X';
						System.out.print(res + " ");
					} else {
						System.out.print(res + " ");
					}
				}
				System.out.println();
			}
		} while (flag);
		System.out.println("GAME_OVER!!!!!!!!!");
	}

}
