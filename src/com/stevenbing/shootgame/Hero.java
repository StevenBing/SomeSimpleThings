package com.stevenbing.shootgame;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject {

	private static BufferedImage[] image;
	static {
		image = new BufferedImage[6];
		for (int i = 0; i < image.length; i++) {
			image[i] = loadImage("hero" + i + ".png");
		}
	}

	private int life = 3;
	private int doubleFire = 0;
	private int index = 0;
	private int deadIndex = 2;

	public Hero() {
		width = 97;
		height = 124;
		x = 140;
		y = 400;
	}

	public Bullet[] shoot(){
		int x = this.width / 4;
		int y = 20;
		if(doubleFire > 0){
			Bullet[] bullets = new Bullet[2];
			bullets[0] = new Bullet(this.x + 1 * x, this.y - y);
			bullets[1] = new Bullet(this.x + 3 * x, this.y - y);
			doubleFire -= 2;
			return bullets;
		}else{
			Bullet[] bullets = new Bullet[1];
			bullets[0] = new Bullet(this.x + 2 * x, this.y - y);
			return bullets;
		}
	}
	void moveTo(int x, int y) {
		this.x = x - this.width / 2;
		this.y = y - this.height / 2;
	}

	public int getLife() {
		return life;
	}

	public void addDoubleFire() {
		this.doubleFire += 40;
	}

	public void addLife() {
		this.life++;
	}

	public void subtractLife() {
		life--;
	}

	public int getDoubleFire() {
		return doubleFire;
	}
	
	public void clearDoubleFire(){
		doubleFire = 0;
	}
	@Override
	public BufferedImage getImage() {
		if (isLife()) {
			return image[index++ % 2];
		} else if (isDead()) {
			BufferedImage img = image[deadIndex++];
			if (deadIndex == image.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;
	}

	@Override
	public boolean outOfBounds() {
		return false;
	}

	@Override
	public void speed() {
	}


}
