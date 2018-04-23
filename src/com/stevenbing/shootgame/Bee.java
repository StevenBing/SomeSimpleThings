package com.stevenbing.shootgame;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Bee extends FlyingObject implements Award {

	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		for (int i = 0; i < images.length; i++) {
			images[i] = loadImage("bee" + i + ".png");
		}
	}
	Random random = new Random();
	private int xSpeed = 1;
	private int ySpeed = 2;
	private int awardType = random.nextInt(2);

	public Bee() {
		width = 60;
		height = 50;
		x = random.nextInt(400 - this.width);
		y = -this.height;
	}

	@Override
	public void speed() {
		this.x += xSpeed;
		this.y += ySpeed;
		if (this.x < 0 || this.x > World.WIDTH - this.width) {
			xSpeed *= -1;
		}
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
	public int getType() {
		return awardType;
	}

}
