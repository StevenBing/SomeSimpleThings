package com.stevenbing.algorithm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.junit.Test;

public class Search_DFS {
	/*给你一个2D的字符矩阵作为游戏板。. ‘M’代表未发现的地雷 , ‘E’ 代表着一个未发现的空白区域, ‘B’ 代表一个没有相邻（上，下，左，右和所有4个对角线）地雷的空白方块
	  ，数字（’1’至’8’）表示与这个显示的方形相邻的地雷数量，最后是’X’ 代表一个已发现的地雷。
	
	现在给出所有未显示的游戏版（’M’或’E’）中的下一个点击位置（行和列索引），根据以下规则显示该位置后返回主板：
	
	如果一个地雷（’M’）被揭开，那么这个游戏结束 
	如果没有相邻地雷的空方块（’E’）被显示出来，则将其改为显示空白（’B’），并且所有相邻的未显示的区域应该递归地显示。 
	如果与至少有一个相邻的地雷的方块（’E’），则将其改为一个数字（’1’至’8’），表示相邻地雷的数量。 
	返回主板，当没有更多区域可以被打开 
	Example 1: 
	Input:
	
	[‘E’, ‘E’, ‘E’, ‘E’, ‘E’], 
	[‘E’, ‘E’, ‘M’, ‘E’, ‘E’], 
	[‘E’, ‘E’, ‘E’, ‘E’, ‘E’], 
	[‘E’, ‘E’, ‘E’, ‘E’, ‘E’]
	
	Click : [3,0]
	
	Output:
	
	[‘B’, ‘1’, ‘E’, ‘1’, ‘B’], 
	[‘B’, ‘1’, ‘M’, ‘1’, ‘B’], 
	[‘B’, ‘1’, ‘1’, ‘1’, ‘B’], 
	[‘B’, ‘B’, ‘B’, ‘B’, ‘B’]
	
	Explanation:
	
	Example 2: 
	Input:
	
	[‘B’, ‘1’, ‘E’, ‘1’, ‘B’], 
	[‘B’, ‘1’, ‘M’, ‘1’, ‘B’], 
	[‘B’, ‘1’, ‘1’, ‘1’, ‘B’], 
	[‘B’, ‘B’, ‘B’, ‘B’, ‘B’]
	
	Click : [1,2]
	
	Output:
	
	[‘B’, ‘1’, ‘E’, ‘1’, ‘B’], 
	[‘B’, ‘1’, ‘X’, ‘1’, ‘B’], 
	[‘B’, ‘1’, ‘1’, ‘1’, ‘B’], 
	[‘B’, ‘B’, ‘B’, ‘B’, ‘B’]
	
	Explanation:
	
	Note: 
	输入矩阵的高度和宽度的范围是[1,50]。 
	点击位置只会是未显示的方块（’M’或’E’），这也意味着输入板至少包含一个可点击的方块。 
	输入板不会是游戏结束的一个阶段（一些地雷被揭示）。 
	为了简单起见，在此问题中不应忽略不提及的规则。 例如，当游戏结束时，您不需要显示所有未发现的矿井，考虑任何情况下，您将赢得比赛或标记任何方格。
	
	解决方法 
	BFS 
	DFS*/

	/*DFS方法*/

	public char[][] updateBoard(char[][] board, int[] click) {
		int m = board.length, n = board[0].length;
		int row = click[0], col = click[1];
		// 发现炸弹
		if (board[row][col] == 'M') {
			board[row][col] = 'X';
			// 不是炸弹
		} else {
			int count = 0;
			// 遍历该区域周围区域 查找是否有炸弹
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (i == 0 && j == 0)
						continue;
					int r = row + i, c = col + j;
					if (r < 0 || r >= m || c < 0 || c >= n)
						continue;
					if (board[r][c] == 'M' || board[r][c] == 'X')
						count++;
				}
			}
			// 周围有炸弹 则该区域不是空白区域 停止 DFS
			if (count > 0) {
				board[row][col] = (char) (count + '0');
			} else {
				// 该区域是空白区域
				board[row][col] = 'B';
				// 遍历此区域周围区域
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						if (i == 0 && j == 0)
							continue;
						int r = row + i, c = col + j;
						if (r < 0 || r >= m || c < 0 || c < 0 || c >= n)
							continue;
						// 遇到没有访问的区域 开始递归访问
						if (board[r][c] == 'E')
							updateBoard(board, new int[]{r, c});
					}
				}
			}
		}

		return board;
	}

	// 非递归版 利用栈
	public char[][] updateBoardByStack(char[][] board, int[] click) {
		// 创建栈
		Stack<int[]> stack = new Stack<>();
		int m = board.length, n = board[0].length;
		// 将起始点压入栈
		stack.push(click);
		// 栈不为空
		while (!stack.isEmpty()) {
			int[] peek = stack.peek();
			int row = peek[0], col = peek[1];
			// 发现炸弹
			if (board[row][col] == 'M') {
				board[row][col] = 'X';
				stack.pop();
				// 不是炸弹
			} else {
				int count = 0;
				// 遍历该区域周围区域 查找是否有炸弹
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						if (i == 0 && j == 0)
							continue;
						int r = row + i, c = col + j;
						if (r < 0 || r >= m || c < 0 || c >= n)
							continue;
						if (board[r][c] == 'M' || board[r][c] == 'X')
							count++;
					}
				}
				// 周围有炸弹 则该区域不是空白区域 停止 DFS
				if (count > 0) {
					board[row][col] = (char) (count + '0');
					stack.pop();
				} else {
					// 该区域是空白区域
					board[row][col] = 'B';
					int[] findNext = findNext(row, col, board);
					// 周围没有可以访问的
					if (findNext[0] == -1 && findNext[1] == -1)
						stack.pop();
					else {
						stack.push(findNext);
					}
				}
			}
		}
		return board;
	}
	// 查找下一个可访问边
	int[] findNext(int row, int col, char[][] board) {
		int[] next = {-1, -1};
		// 遍历此区域周围区域
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (i == 0 && j == 0)
					continue;
				int r = row + i, c = col + j;
				if (r < 0 || r >= board.length || c < 0
						|| c >= board[0].length)
					continue;
				// 遇到没有访问的区域
				if (board[r][c] == 'E') {
					next[0] = r;
					next[1] = c;
					return next;
				}
			}
		}
		return next;
	}

	/*测试*/

	@Test
	public void testDFS() {
		char[][] board = {
				{'E', 'E', 'E', 'E', 'E'},
				{'E', 'M', 'M', 'E', 'E'},
				{'E', 'E', 'E', 'E', 'E'},
				{'E', 'E', 'E', 'E', 'E'},
				{'E', 'E', 'E', 'M', 'E'}, 
				{'E', 'E', 'E', 'E', 'E'}};
		for (char[] cs : board) {
			for (char c : cs) {
				System.out.print(c + " ");
			}
			System.out.println();
		}
		System.out.println("-------------");
		int[] click = {3, 0};
		Search_DFS search_DFS = new Search_DFS();
		// 递归版
		char[][] updateBoard = search_DFS.updateBoard(board, click);
		// 非递归版
		char[][] updateBoardByStack = search_DFS.updateBoardByStack(board,
				click);
		for (char[] cs : updateBoard) {
			for (char c : cs) {
				System.out.print(c + " ");
			}
			System.out.println();
		}
		System.out.println("-------------");
		for (char[] cs : updateBoardByStack) {
			for (char c : cs) {
				System.out.print(c + " ");
			}
			System.out.println();
		}
	}
	/*结果
	 E E E E E 
	 E E M E E 
	 E E E E E 
	 E E E E E 
	 E E E E E 
	 E E E E E 
	 -------------
	 B 1 E 1 B 
	 B 1 M 1 B 
	 B 1 1 1 B 
	 B B B B B 
	 B B B B B 
	 B B B B B 
	 -------------
	 B 1 E 1 B 
	 B 1 M 1 B 
	 B 1 1 1 B 
	 B B B B B 
	 B B B B B 
	 B B B B B */
}
