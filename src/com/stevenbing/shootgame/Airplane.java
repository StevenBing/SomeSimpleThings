package com.stevenbing.shootgame;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Airplane extends FlyingObject implements Enemy {
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		for (int i = 0; i < images.length; i++) {
			images[i] = loadImage("airplane" + i + ".png");
		}
	}

	private int speed = 2;
	Random random = new Random();
	public Airplane() {
		width = 49;
		height = 36;
		x = random.nextInt(400 - this.width);
		y = -this.height;
	}

	int deadIndex = 0;
	@Override
	public BufferedImage getImage() {
		if (isLife()) {
			return images[0];
		} else if (isDead()) {
			BufferedImage img = images[deadIndex++];
			if (deadIndex == images.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;
	}

	@Override
	public int getScore() {
		return 1;
	}

	@Override
	public boolean outOfBounds() {
		return this.y >= World.HEIGHT;
	}

	@Override
	public void speed() {
		this.y += speed;
	}

}
