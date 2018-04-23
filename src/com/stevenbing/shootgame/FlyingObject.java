package com.stevenbing.shootgame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class FlyingObject {
	protected int width;
	protected int height;
	protected int x;
	protected int y;

	public static final int LIFE = 0;
	public static final int DEAD = 1;
	public static final int REMOVE = 2;
	protected int state = LIFE;

	public abstract void speed();
	public abstract BufferedImage getImage();
	public abstract boolean outOfBounds();

	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img = ImageIO
					.read(FlyingObject.class.getResource(fileName));
			return img;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void paint(Graphics g) {
		g.drawImage(getImage(), x, y, null);
	}

	public boolean isLife() {
		return state == LIFE;
	}

	public boolean isDead() {
		return state == DEAD;
	}

	public boolean isRemove() {
		return state == REMOVE;
	}

	public void goDead() {
		state = DEAD;
	}

	public boolean hit(FlyingObject obj) {
		int x1 = this.x + this.width;
		int x2 = this.x - obj.width;
		int y1 = this.y + this.height;
		int y2 = this.y - obj.height;
		int x = obj.x;
		int y = obj.y;

		return x <= x1 && x >= x2 && y <= y1 && y >= y2;
	}

}
