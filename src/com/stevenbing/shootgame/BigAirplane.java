package com.stevenbing.shootgame;

import java.awt.image.BufferedImage;
import java.util.Random;

public class BigAirplane extends FlyingObject implements Enemy {
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		for (int i = 0; i < images.length; i++) {
			images[i] = loadImage("bigplane" + i + ".png");
		}
	}

	private int speed = 2;
	Random rand = new Random();
	public BigAirplane() {
		width = 69;
		height = 99;
		y = -this.height;
		x = rand.nextInt(World.WIDTH - this.width);
	}

	@Override
	public int getScore() {
		return 3;
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
	public boolean outOfBounds() {
		return this.y >= World.HEIGHT;
	}

	@Override
	public void speed() {
		this.y += speed;
	}

}
