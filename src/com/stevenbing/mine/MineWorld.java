package com.stevenbing.mine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 * FileName:MineWord.java 使用该类创建一个窗口
 * 
 * @author sk
 * @Date 2018-04-18
 * @version 1.0.1
 * 
 *          JPanel 修改为 JFrame
 */

public class MineWorld extends JPanel {

	/* private JMenu menu;
	 private JMenuBar menuBar;
	 private JMenuItem lowLevel;
	 private JMenuItem midLevel;
	 private JMenuItem heightLevel;
	 private JMenuItem restart;
	 private Toolkit tk = Toolkit.getDefaultToolkit();
	 private Image icon = tk.getImage("icon.jpg");
	 menu=new JMenu("参数设置");
	 menuBar=new JMenuBar();
	 lowLevel=new JMenuItem("初级（10个雷）");
	 midLevel=new JMenuItem("中级（44个雷）");
	 heightLevel=new JMenuItem("高级（99个雷）");
	 restart=new JMenuItem("重新开始");
	 lowLevel.addActionListener(new ActionListener() {
	
	 public void actionPerformed(ActionEvent e) {
	 // TODO Auto-generated method stub
	 dispose();
	// new MineClient(225,305,10);
	 }
	 });
	 midLevel.addActionListener(new ActionListener() {
	
	 public void actionPerformed(ActionEvent e) {
	 // TODO Auto-generated method stub
	 dispose();
	// new MineClient(380,460,44);
	 }
	 });
	 heightLevel.addActionListener(new ActionListener() {
	
	 public void actionPerformed(ActionEvent e) {
	 // TODO Auto-generated method stub
	 dispose();
	// new MineClient(660,460,99);
	 }
	 });
	 restart.addActionListener(new ActionListener() {
	
	 public void actionPerformed(ActionEvent e) {
	 // TODO Auto-generated method stub
	 dispose();
	// new MineClient(screenWidth,screenHeight,mineNum);
	 }
	 });
	 menu.add(restart);
	 menu.add(new JSeparator());
	 menu.add(lowLevel);
	 menu.add(midLevel);
	 menu.add(heightLevel);
	 menuBar.add(menu);
	 setJMenuBar(menuBar);*/

	// 图片的宽高都为20
	public static final int SIZE = 22;
	// 地雷数默认为10个
	public static int rank = 10;
	// 得到地图转化为图片
	private Landmine landmine = new Landmine();
	// 从鼠标点击的地方开始搜索
	public int[] click = new int[]{Integer.MAX_VALUE};
	// DFS搜索算法对目标地图进行处理
	private FindSweepingByDFS find = new FindSweepingByDFS();
	// 得到随机生成的地图
	private char[][] board = new SweepingMap().getMap();
	// 标记游戏是否结束
	boolean flagMap = true;

	// 所用时间：当前时间减开始时间
	private int time = 0;
	// 剩余地雷数
	private int restMine = rank;

	private MineClient mc = new MineClient();

	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	public static final int WIN = 4;
	private int state = RUNNING;

	// 结果数组
	// private char[][] result = new char[landmine.getLength()[0]][landmine
	// .getLength()[1]];

	// 游戏胜利后加载一张胜利图片
	private static BufferedImage win;
	static {
		try {
			win = ImageIO.read(MineWorld.class.getResource("win.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	// 标记鼠标是否度该矿井进行点击，当右键点击且为左键不可点击状态时，左键无法点击
	boolean[][] clickFlag = new boolean[landmine.getLength()[0]][landmine
			.getLength()[1]];

	int index = 0;
	public void checkGameOverAction() {
		index++;
		if (index % 10 == 0) {
			int count = 0;
			if (flagMap) {
				// char[][] board = new SweepingMap().getMap();
				for (char[] ch : board) {
					for (char res : ch) {
						if (res == 'E') {
							count++;
						}
					}
				}
				if (count == 0) {
					state = WIN;
				}
			} else {
				state = GAME_OVER;
			}
		}
	}

	/**
	 * 从鼠标左键获得坐标，从该点开始搜索，并返货搜索结果改变图片显示
	 */
	public void clickMap() {
		System.out.println(board + "\t" + click);
		board = find.updateBoard(board, click);
		for (char[] ch : board) {
			for (char res : ch) {
				if (res == 'X') {
					flagMap = false;
				}
			}
		}
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 'X') {
					landmine.setImageInButton1(i, j);
				} else if (board[i][j] == 'M' && !flagMap) {
					board[i][j] = 'X';
					landmine.setImageInButton1(i, j);
				} else if (board[i][j] - '0' >= 0 && board[i][j] - '0' <= 8) {
					landmine.setImageInButton1(i, j, board[i][j] - '0');
				}
			}
		}

		/*for (char[] ch : board) {
			for (char res : ch) {
				if (res == 'X') {
					System.out.print(res + " ");
					flagMap = false;
				} else if (res == 'M' && flagMap) {
					res = 'O';
					System.out.print(res + " ");
				} else if (res == 'M' && !flagMap) {
					res = 'X';
					System.out.print(res + " ");
				} else {
					System.out.print(res + " ");
				}
			}
			System.out.println();
		}*/

		/*for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[i].length; j++){
				result
			}
		}*/
	}

	public void action() {
		Timer timer = new Timer();
		MouseAdapter l = new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				// 鼠标事件MouseEvent.BUTTON3为右键点击事件，MouseEvent.BUTTON1为左键点击事件
				int x = e.getX(), y = e.getY() - landmine.listHeight - 10;
				int y1 = e.getY();
				if (state != GAME_OVER && e.getButton() == MouseEvent.BUTTON3
						&& x >= 10 && x <= landmine.getLength()[0] * SIZE + 10
						&& y > 0 && y <= landmine.getLength()[1] * SIZE - 1) {
					landmine.setImageInButton3((x - 10) / SIZE, y / SIZE);
					clickFlag[(x - 10) / SIZE][y / SIZE] = true;
					// 左键点击为矿区
				} else if (state != GAME_OVER
						&& e.getButton() == MouseEvent.BUTTON1 && x >= 10
						&& x <= landmine.getLength()[0] * SIZE + 10 && y > 0
						&& y <= landmine.getLength()[1] * SIZE - 1 && landmine
								.getFlagNum((x - 10) / SIZE, y / SIZE) == 0) {
					click = new int[]{(x - 10) / SIZE, y / SIZE};
					clickMap();
					// 左键点击到工具栏，工具栏图片数组长度写死，为4
				} else if (e.getButton() == MouseEvent.BUTTON1 && x > 10
						&& x < (Landmine.listWedth * 4 + 10) && y1 > 2
						&& y1 < (Landmine.listHeight - 2)) {
					System.out.println("OKOKOKOKOKOKOKOK");
					mc.mouse((x - 10) / Landmine.listWedth, timer);

					/*switch ((x - 10) / Landmine.listWedth) {
						case 0 :
							timer.cancel(); 
							new CreatorClient();
							break;
						case 1 :
							rank = 10;
							timer.cancel(); 
							new CreatorClient();
							break;
						case 2 :
							rank = 30;
							timer.cancel(); 
							new CreatorClient();
							break;
						default :
							rank = 90;
							timer.cancel(); 
							new CreatorClient();
					}*/
				}
			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);

		int intervel = 10;
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (state == RUNNING) {
					checkGameOverAction();

					time += 10;
				}
				repaint();
			}
		}, intervel, intervel);
	}

	public void paint(Graphics g) {
		landmine.paint(g);
		if (state == WIN) {
			g.drawImage(win, 0, 0, this.getWidth(), this.getHeight(), this);
			Font font1 = new Font("华文行楷", Font.BOLD, 40);
			g.setFont(font1);
			g.setColor(new Color(248, 29, 56));
			g.drawString("YOU WIN!!!", this.getWidth() / 2 - 100, 30);
		}
		if (state == GAME_OVER) {
			Font font = new Font("微软雅黑", Font.BOLD, 20);
			g.setFont(font);
			g.setColor(new Color(255, 0, 255));
			g.drawString("GAME OVER!!", this.getWidth() / 2 - 80,
					this.getHeight() / 2);
		}
		Font font = new Font("微软雅黑", Font.BOLD, 15);
		g.setFont(font);
		g.setColor(Color.orange);
		g.drawString("已用时：" + (time / 1000) + " 秒", 0, this.getHeight() - 20);
		g.drawString("未扫雷：" + (restMine - landmine.getRestMine()) + " 个", this.getWidth() - 100,
				this.getHeight() - 20);

	}

	public static void main(String[] args) {

		new CreatorClient();

		/*Landmine landmine = new Landmine();
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
		frame.setSize(length[0] * SIZE + 35, length[1] * SIZE + 190);
		frame.setLocationRelativeTo(null);
		frame.setTitle("扫雷");
		frame.setVisible(true);
		frame.setBackground(Color.BLACK);
		
		mineWorld.action();*/
	}
}
