package com.stevenbing.algorithm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.junit.Test;

public class Search_DFS {
	/*����һ��2D���ַ�������Ϊ��Ϸ�塣. ��M������δ���ֵĵ��� , ��E�� ������һ��δ���ֵĿհ�����, ��B�� ����һ��û�����ڣ��ϣ��£����Һ�����4���Խ��ߣ����׵Ŀհ׷���
	  �����֣���1������8������ʾ�������ʾ�ķ������ڵĵ�������������ǡ�X�� ����һ���ѷ��ֵĵ��ס�
	
	���ڸ�������δ��ʾ����Ϸ�棨��M����E�����е���һ�����λ�ã��к������������������¹�����ʾ��λ�ú󷵻����壺
	
	���һ�����ף���M�������ҿ�����ô�����Ϸ���� 
	���û�����ڵ��׵Ŀշ��飨��E��������ʾ�����������Ϊ��ʾ�հף���B�����������������ڵ�δ��ʾ������Ӧ�õݹ����ʾ�� 
	�����������һ�����ڵĵ��׵ķ��飨��E�����������Ϊһ�����֣���1������8��������ʾ���ڵ��׵������� 
	�������壬��û�и���������Ա��� 
	Example 1: 
	Input:
	
	[��E��, ��E��, ��E��, ��E��, ��E��], 
	[��E��, ��E��, ��M��, ��E��, ��E��], 
	[��E��, ��E��, ��E��, ��E��, ��E��], 
	[��E��, ��E��, ��E��, ��E��, ��E��]
	
	Click : [3,0]
	
	Output:
	
	[��B��, ��1��, ��E��, ��1��, ��B��], 
	[��B��, ��1��, ��M��, ��1��, ��B��], 
	[��B��, ��1��, ��1��, ��1��, ��B��], 
	[��B��, ��B��, ��B��, ��B��, ��B��]
	
	Explanation:
	
	Example 2: 
	Input:
	
	[��B��, ��1��, ��E��, ��1��, ��B��], 
	[��B��, ��1��, ��M��, ��1��, ��B��], 
	[��B��, ��1��, ��1��, ��1��, ��B��], 
	[��B��, ��B��, ��B��, ��B��, ��B��]
	
	Click : [1,2]
	
	Output:
	
	[��B��, ��1��, ��E��, ��1��, ��B��], 
	[��B��, ��1��, ��X��, ��1��, ��B��], 
	[��B��, ��1��, ��1��, ��1��, ��B��], 
	[��B��, ��B��, ��B��, ��B��, ��B��]
	
	Explanation:
	
	Note: 
	�������ĸ߶ȺͿ�ȵķ�Χ��[1,50]�� 
	���λ��ֻ����δ��ʾ�ķ��飨��M����E��������Ҳ��ζ����������ٰ���һ���ɵ���ķ��顣 
	����岻������Ϸ������һ���׶Σ�һЩ���ױ���ʾ���� 
	Ϊ�˼�������ڴ������в�Ӧ���Բ��ἰ�Ĺ��� ���磬����Ϸ����ʱ��������Ҫ��ʾ����δ���ֵĿ󾮣������κ�����£�����Ӯ�ñ��������κη���
	
	������� 
	BFS 
	DFS*/

	/*DFS����*/

	public char[][] updateBoard(char[][] board, int[] click) {
		int m = board.length, n = board[0].length;
		int row = click[0], col = click[1];
		// ����ը��
		if (board[row][col] == 'M') {
			board[row][col] = 'X';
			// ����ը��
		} else {
			int count = 0;
			// ������������Χ���� �����Ƿ���ը��
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
			// ��Χ��ը�� ��������ǿհ����� ֹͣ DFS
			if (count > 0) {
				board[row][col] = (char) (count + '0');
			} else {
				// �������ǿհ�����
				board[row][col] = 'B';
				// ������������Χ����
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						if (i == 0 && j == 0)
							continue;
						int r = row + i, c = col + j;
						if (r < 0 || r >= m || c < 0 || c < 0 || c >= n)
							continue;
						// ����û�з��ʵ����� ��ʼ�ݹ����
						if (board[r][c] == 'E')
							updateBoard(board, new int[]{r, c});
					}
				}
			}
		}

		return board;
	}

	// �ǵݹ�� ����ջ
	public char[][] updateBoardByStack(char[][] board, int[] click) {
		// ����ջ
		Stack<int[]> stack = new Stack<>();
		int m = board.length, n = board[0].length;
		// ����ʼ��ѹ��ջ
		stack.push(click);
		// ջ��Ϊ��
		while (!stack.isEmpty()) {
			int[] peek = stack.peek();
			int row = peek[0], col = peek[1];
			// ����ը��
			if (board[row][col] == 'M') {
				board[row][col] = 'X';
				stack.pop();
				// ����ը��
			} else {
				int count = 0;
				// ������������Χ���� �����Ƿ���ը��
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
				// ��Χ��ը�� ��������ǿհ����� ֹͣ DFS
				if (count > 0) {
					board[row][col] = (char) (count + '0');
					stack.pop();
				} else {
					// �������ǿհ�����
					board[row][col] = 'B';
					int[] findNext = findNext(row, col, board);
					// ��Χû�п��Է��ʵ�
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
	// ������һ���ɷ��ʱ�
	int[] findNext(int row, int col, char[][] board) {
		int[] next = {-1, -1};
		// ������������Χ����
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (i == 0 && j == 0)
					continue;
				int r = row + i, c = col + j;
				if (r < 0 || r >= board.length || c < 0
						|| c >= board[0].length)
					continue;
				// ����û�з��ʵ�����
				if (board[r][c] == 'E') {
					next[0] = r;
					next[1] = c;
					return next;
				}
			}
		}
		return next;
	}

	/*����*/

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
		// �ݹ��
		char[][] updateBoard = search_DFS.updateBoard(board, click);
		// �ǵݹ��
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
	/*���
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
