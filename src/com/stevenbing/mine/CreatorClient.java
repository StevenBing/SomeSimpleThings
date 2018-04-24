package com.stevenbing.mine;

import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class CreatorClient {

	public CreatorClient(){
		Landmine landmine = new Landmine();
		int[] length = landmine.getLength();

//		JFrame frame = new JFrame();
		MineClient frame = new MineClient();
		MineWorld mineWorld = new MineWorld();
		frame.add(mineWorld);
		try {
			frame.setIconImage(
					ImageIO.read(MineWorld.class.getResource("icon.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(length[0] * MineWorld.SIZE + 35, length[1] * MineWorld.SIZE + 190);
		frame.setLocationRelativeTo(null);
		frame.setTitle("É¨À×");
		frame.setVisible(true);
		frame.setBackground(Color.BLACK);

		System.out.println("rank :" + MineWorld.rank);
		
		mineWorld.action();
		
	}
		
	
	/*public static void main(String[] args) {
		Landmine landmine = new Landmine();
		int[] length = landmine.getLength();

		JFrame frame = new JFrame();
		MineWorld mineWorld = new MineWorld();
		frame.add(mineWorld);

		try {
			frame.setIconImage(
					ImageIO.read(MineWorld.class.getResource("icon.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(length[0] * MineWorld.SIZE + 35, length[1] * MineWorld.SIZE + 190);
		frame.setLocationRelativeTo(null);
		frame.setTitle("É¨À×");
		frame.setVisible(true);
		frame.setBackground(Color.BLACK);

		mineWorld.action();
	}*/

}
