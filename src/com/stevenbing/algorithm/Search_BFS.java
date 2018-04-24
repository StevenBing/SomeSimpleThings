package com.stevenbing.algorithm;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;
/*
 * 给你一个2D的字符矩阵作为游戏板。. ‘M’代表未发现的地雷 , ‘E’ 代表着一个未发现的空白区域, ‘B’ 代表一个没有相邻（上，下，左，右和所有4个对角线）地雷的空白方块，数字（’1’至’8’）表示与这个显示的方形相邻的地雷数量，最后是’X’ 代表一个已发现的地雷。

现在给出所有未显示的游戏版（’M’或’E’）中的下一个点击位置（行和列索引），根据以下规则显示该位置后返回主板：

如果一个地雷（’M’）被揭开，那么这个游戏结束 
如果没有相邻地雷的空方块（’E’）被显示出来，则将其改为显示空白（’B’），并且所有相邻的未显示的区域应该递归地显示。 
如果与至少有一个相邻的地雷的方块（’E’），则将其改为一个数字（’1’至’8’），表示相邻地雷的数量。 
返回主板，当没有更多区域可以被打开 
Example 1: 
Input:

[[‘E’, ‘E’, ‘E’, ‘E’, ‘E’], 
[‘E’, ‘E’, ‘M’, ‘E’, ‘E’], 
[‘E’, ‘E’, ‘E’, ‘E’, ‘E’], 
[‘E’, ‘E’, ‘E’, ‘E’, ‘E’]]

Click : [3,0]

Output:

[[‘B’, ‘1’, ‘E’, ‘1’, ‘B’], 
[‘B’, ‘1’, ‘M’, ‘1’, ‘B’], 
[‘B’, ‘1’, ‘1’, ‘1’, ‘B’], 
[‘B’, ‘B’, ‘B’, ‘B’, ‘B’]]

Explanation:

Example 2: 
Input:

[[‘B’, ‘1’, ‘E’, ‘1’, ‘B’], 
[‘B’, ‘1’, ‘M’, ‘1’, ‘B’], 
[‘B’, ‘1’, ‘1’, ‘1’, ‘B’], 
[‘B’, ‘B’, ‘B’, ‘B’, ‘B’]]

Click : [1,2]

Output:

[[‘B’, ‘1’, ‘E’, ‘1’, ‘B’], 
[‘B’, ‘1’, ‘X’, ‘1’, ‘B’], 
[‘B’, ‘1’, ‘1’, ‘1’, ‘B’], 
[‘B’, ‘B’, ‘B’, ‘B’, ‘B’]]

Explanation:

Note: 
输入矩阵的高度和宽度的范围是[1,50]。 
点击位置只会是未显示的方块（’M’或’E’），这也意味着输入板至少包含一个可点击的方块。 
输入板不会是游戏结束的一个阶段（一些地雷被揭示）。 
为了简单起见，在此问题中不应忽略不提及的规则。 例如，当游戏结束时，您不需要显示所有未发现的矿井，考虑任何情况下，您将赢得比赛或标记任何方格。

解决方法 
BFS 
DFS
*/

public class Search_BFS {
	/*BFS解决此问题*/

	public char[][] updateBoard(char[][] board, int[] click) {
		// 获取地图的长宽
		int m = board.length;
		int n = board[0].length;
		// 广度优先遍历 利用队列
		Queue<int[]> queue = new LinkedList<>();
		// 将起点加入队列
		queue.add(click);
		// 栈不为空 则未遍历完全
		while (!queue.isEmpty()) {
			// 按照队列先进先出的原则依次遍历
			int[] poll = queue.poll();
			int row = poll[0], col = poll[1];
			// 点到炸弹区域
			if (board[row][col] == 'M') { // Mine
				board[row][col] = 'X';
			} else {
				// 点到非炸弹区域 可能是数字区域也可能是空白区域
				int count = 0;
				// 将该点的周围全部访问
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						// 遍历到自己
						if (i == 0 && j == 0)
							continue;
						int r = row + i, c = col + j;
						// 遍历超出边界
						if (r < 0 || r >= m || c < 0 || c < 0 || c >= n)
							continue;
						// 发现有炸弹
						if (board[r][c] == 'M' || board[r][c] == 'X')
							count++;
					}
				}

				// 如果周围有炸弹则这个区域不是空白区域 停止BFS
				if (count > 0) {
					// 该区域周围的炸弹数量就是该区域的数字大小
					board[row][col] = (char) (count + '0');
					// 周围无炸弹 是空白区域
				} else {
					board[row][col] = 'B';
					// 将该区域周围全部遍历
					for (int i = -1; i < 2; i++) {
						for (int j = -1; j < 2; j++) {
							if (i == 0 && j == 0)
								continue;
							int r = row + i, c = col + j;
							if (r < 0 || r >= m || c < 0 || c < 0 || c >= n)
								continue;
							// 发现未被访问区域 加入队列
							if (board[r][c] == 'E') {
								queue.add(new int[]{r, c});
								board[r][c] = 'B';
							}
						}
					}
				}
			}
		}
		return board;
	}
	/*测试*/

	@Test
	public void test() {
		char[][] board = {{'E', 'E', 'E', 'E', 'E'}, {'E', 'E', 'M', 'E', 'E'},
				{'E', 'E', 'E', 'E', 'E'}, {'E', 'E', 'E', 'E', 'E'},
				{'E', 'E', 'E', 'E', 'E'}, {'E', 'E', 'E', 'E', 'E'}};

		for (char[] cs : board) {
			for (char c : cs) {
				System.out.print(c + " ");
			}
			System.out.println();
		}
		System.out.println("-------------");
		int[] click = {3, 0};
		Search_BFS search_BFS = new Search_BFS();
		char[][] updateBoard = search_BFS.updateBoard(board, click);
		for (char[] cs : updateBoard) {
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
	B B B B B */
}
