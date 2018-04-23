package com.stevenbing.shootgame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sky extends FlyingObject{
	private static BufferedImage image;
	static {
		image = loadImage("background.png");
	}
	private int y1;
	private int speed = 1;
	
	public Sky(){
		width = World.WIDTH;
		height = World.HEIGHT;
		y1 = -this.height;
		x = 0;
		y = 0;
	}

	public void paint(Graphics g){
		g.drawImage(getImage(),x,y,null);
		g.drawImage(getImage(),x,y1,null);
	}
	public void step() {
		System.out.println("天空的y和y1坐标移动了： " + speed);
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public boolean outOfBounds() {
		return false;
	}

	@Override
	public void speed() {
		this.y += speed;
		this.y1 += speed;
		if (y >= World.HEIGHT){
			y = -this.height;
		}
		if(y1 >= World.HEIGHT){
			y1 = -this.height; 
		}
	}
}
