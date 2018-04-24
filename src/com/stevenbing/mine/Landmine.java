package com.stevenbing.mine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Landmine {
	public static int listWedth = 85;
	public static int listHeight = 38;
	SweepingMap sweepingMap = new SweepingMap();
	int[] sizeOfMap = sweepingMap.getSizeOfMap();

	private static BufferedImage[] list;
	private static BufferedImage[] num;
	private static BufferedImage[] bomb;
	private static BufferedImage[] flag;
	private static BufferedImage lid;
	static {
		list = new BufferedImage[4];
		for (int i = 0; i < list.length; i++) {
			list[i] = loadImage("list" + i + ".png");
		}
		num = new BufferedImage[9];
		for (int i = 0; i < num.length; i++) {
			num[i] = loadImage("num" + i + ".jpg");
		}
		bomb = new BufferedImage[]{loadImage("bomb0.jpg"),
				loadImage("bomb1.jpg")};
		flag = new BufferedImage[]{loadImage("flag0.jpg"),
				loadImage("flag1.jpg")};
		lid = loadImage("lid.jpg");
	}

	private BufferedImage[][] images = new BufferedImage[sizeOfMap[0]][sizeOfMap[1]];
	private int[][] flagNum = new int[sizeOfMap[0]][sizeOfMap[1]];
	{
		for (int i = 0; i < images.length; i++) {
			for (int j = 0; j < images[0].length; j++) {
				images[i][j] = lid;
				flagNum[i][j] = 0;
			}
		}

	}

	private int[] x = new int[sizeOfMap[0]];
	private int[] y = new int[sizeOfMap[1]];
	{
		for (int i = 0; i < x.length; i++) {
			x[i] = i * MineWorld.SIZE + 10;
		}
		for (int i = 0; i < y.length; i++) {
			y[i] = i * MineWorld.SIZE + 10;
		}
	}

	public int[] getLength() {
		return new int[]{images.length, images[0].length};
	}
	// 右键点击是改变图片方式
	public void setImageInButton3(int x, int y) {
		if (flagNum[x][y] == 0) {
			images[x][y] = flag[0];
			flagNum[x][y]+=2;
		} else if (flagNum[x][y] == 2) {
			images[x][y] = flag[1];
			flagNum[x][y]++;
		} else if(flagNum[x][y] == 3){
			images[x][y] = lid;
			flagNum[x][y] = 0;
		}
	}

	//获得剩余地雷数：总数 - 标记为旗子数
	
	public int getRestMine(){
		int count = 0;
		for (int[] rests : flagNum){
			for (int rest : rests){
				if (rest == 2){
					count ++;
				}
			}
		}
		return count;
	}
	
	// 左键改变图片方式
	public void setImageInButton1(int x, int y, int count) {
			images[x][y] = num[count];
			flagNum[x][y]++;
	}

	//获得flagNum，看该方块是否已经被右键点击
	public int getFlagNum(int x, int y){
		return flagNum[x][y];
	}
	
	public void setImageInButton1(int x, int y) {
			images[x][y] = bomb[0];
			flagNum[x][y]++;
	}

	public BufferedImage getImage(int x, int y) {
		return images[x][y];
	}

	public void paint(Graphics g) {
		for (int i = 0; i < list.length; i++) {
			g.drawImage(list[i], i * listWedth + 10, 2, null);
		}
		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < y.length; j++) {
				g.drawImage(getImage(i, j), x[i], y[j] + listHeight, null);
			}
		}
	}

	public static BufferedImage loadImage(String fileName) {
		BufferedImage image;
		try {
			image = ImageIO.read(Landmine.class.getResource(fileName));
			return image;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
