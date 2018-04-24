package com.stevenbing.algorithm;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;
/*
 * ����һ��2D���ַ�������Ϊ��Ϸ�塣. ��M������δ���ֵĵ��� , ��E�� ������һ��δ���ֵĿհ�����, ��B�� ����һ��û�����ڣ��ϣ��£����Һ�����4���Խ��ߣ����׵Ŀհ׷��飬���֣���1������8������ʾ�������ʾ�ķ������ڵĵ�������������ǡ�X�� ����һ���ѷ��ֵĵ��ס�

���ڸ�������δ��ʾ����Ϸ�棨��M����E�����е���һ�����λ�ã��к������������������¹�����ʾ��λ�ú󷵻����壺

���һ�����ף���M�������ҿ�����ô�����Ϸ���� 
���û�����ڵ��׵Ŀշ��飨��E��������ʾ�����������Ϊ��ʾ�հף���B�����������������ڵ�δ��ʾ������Ӧ�õݹ����ʾ�� 
�����������һ�����ڵĵ��׵ķ��飨��E�����������Ϊһ�����֣���1������8��������ʾ���ڵ��׵������� 
�������壬��û�и���������Ա��� 
Example 1: 
Input:

[[��E��, ��E��, ��E��, ��E��, ��E��], 
[��E��, ��E��, ��M��, ��E��, ��E��], 
[��E��, ��E��, ��E��, ��E��, ��E��], 
[��E��, ��E��, ��E��, ��E��, ��E��]]

Click : [3,0]

Output:

[[��B��, ��1��, ��E��, ��1��, ��B��], 
[��B��, ��1��, ��M��, ��1��, ��B��], 
[��B��, ��1��, ��1��, ��1��, ��B��], 
[��B��, ��B��, ��B��, ��B��, ��B��]]

Explanation:

Example 2: 
Input:

[[��B��, ��1��, ��E��, ��1��, ��B��], 
[��B��, ��1��, ��M��, ��1��, ��B��], 
[��B��, ��1��, ��1��, ��1��, ��B��], 
[��B��, ��B��, ��B��, ��B��, ��B��]]

Click : [1,2]

Output:

[[��B��, ��1��, ��E��, ��1��, ��B��], 
[��B��, ��1��, ��X��, ��1��, ��B��], 
[��B��, ��1��, ��1��, ��1��, ��B��], 
[��B��, ��B��, ��B��, ��B��, ��B��]]

Explanation:

Note: 
�������ĸ߶ȺͿ�ȵķ�Χ��[1,50]�� 
���λ��ֻ����δ��ʾ�ķ��飨��M����E��������Ҳ��ζ����������ٰ���һ���ɵ���ķ��顣 
����岻������Ϸ������һ���׶Σ�һЩ���ױ���ʾ���� 
Ϊ�˼�������ڴ������в�Ӧ���Բ��ἰ�Ĺ��� ���磬����Ϸ����ʱ��������Ҫ��ʾ����δ���ֵĿ󾮣������κ�����£�����Ӯ�ñ��������κη���

������� 
BFS 
DFS
*/

public class Search_BFS {
	/*BFS���������*/

	public char[][] updateBoard(char[][] board, int[] click) {
		// ��ȡ��ͼ�ĳ���
		int m = board.length;
		int n = board[0].length;
		// ������ȱ��� ���ö���
		Queue<int[]> queue = new LinkedList<>();
		// �����������
		queue.add(click);
		// ջ��Ϊ�� ��δ������ȫ
		while (!queue.isEmpty()) {
			// ���ն����Ƚ��ȳ���ԭ�����α���
			int[] poll = queue.poll();
			int row = poll[0], col = poll[1];
			// �㵽ը������
			if (board[row][col] == 'M') { // Mine
				board[row][col] = 'X';
			} else {
				// �㵽��ը������ ��������������Ҳ�����ǿհ�����
				int count = 0;
				// ���õ����Χȫ������
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						// �������Լ�
						if (i == 0 && j == 0)
							continue;
						int r = row + i, c = col + j;
						// ���������߽�
						if (r < 0 || r >= m || c < 0 || c < 0 || c >= n)
							continue;
						// ������ը��
						if (board[r][c] == 'M' || board[r][c] == 'X')
							count++;
					}
				}

				// �����Χ��ը������������ǿհ����� ֹͣBFS
				if (count > 0) {
					// ��������Χ��ը���������Ǹ���������ִ�С
					board[row][col] = (char) (count + '0');
					// ��Χ��ը�� �ǿհ�����
				} else {
					board[row][col] = 'B';
					// ����������Χȫ������
					for (int i = -1; i < 2; i++) {
						for (int j = -1; j < 2; j++) {
							if (i == 0 && j == 0)
								continue;
							int r = row + i, c = col + j;
							if (r < 0 || r >= m || c < 0 || c < 0 || c >= n)
								continue;
							// ����δ���������� �������
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
	/*����*/

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
	B B B B B */
}
