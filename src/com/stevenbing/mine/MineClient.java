package com.stevenbing.mine;

import java.util.Timer;

import javax.swing.JFrame;

/**
 * �������������һ���µĲ�ͬ��JFrame���Ҵ���ǰ�ر���һ��JFrame
 */
public class MineClient extends JFrame {
	public void mouse(int toolbar, Timer timer) {
		switch (toolbar) {
			case 0 :
				timer.cancel(); 
				this.dispose();
				new CreatorClient();
				break;
			case 1 :
				MineWorld.rank = 10;
				timer.cancel(); 
				this.dispose();
				new CreatorClient();
				break;
			case 2 :
				MineWorld.rank = 30;
				timer.cancel();
				this.dispose();
				new CreatorClient();
				break;
			default :
				MineWorld.rank = 90;
				timer.cancel(); 
				this.dispose();
				new CreatorClient();
		}
	}
}
