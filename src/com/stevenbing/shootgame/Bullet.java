package com.stevenbing.shootgame;

import java.awt.image.BufferedImage;

public class Bullet extends FlyingObject {

	private static BufferedImage image = loadImage("bullet.png");

	private int speed = 3;
	public Bullet(int x, int y) {
		width = 8;
		height = 14;
		this.x = x;
		this.y = y;
	}

	@Override
	public BufferedImage getImage() {
		if (isLife()) {
			return image;
		} else if (isDead()) {
			state = REMOVE;
		}
		return null;
	}

	@Override
	public boolean outOfBounds() {
		return this.y <= -this.height;
	}

	@Override
	public void speed() {
		this.y -= speed;
	}
}
