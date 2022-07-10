package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSplitPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game {

	public static JFrame frmGame;
	private static JPanel[] playPanel = new JPanel[3];
	public static boolean UP, LEFT, RIGHT, GET;
	public static String path;
	private static Play play;
	private static Creat creat;
	public static boolean isPlay = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		path = System.getProperty("user.dir") + "\\src";
		System.out.println(path);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game window = new Game();
					window.frmGame.setVisible(true);
					window.frmGame.setTitle("Java_Game_nfu_40643208_NEGI");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Game() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGame = new JFrame();
		frmGame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_UP)
				{
					UP = true;
				}
				else if(e.getKeyCode() == KeyEvent.VK_LEFT)
				{
					LEFT = true;
				}else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
				{
					RIGHT = true;
				}else if(e.getKeyCode() == KeyEvent.VK_SPACE)
				{
					GET = true;
				}
			}
			public void keyReleased(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_UP)
				{
					UP = false;
				}
				else if(e.getKeyCode() == KeyEvent.VK_LEFT)
				{
					LEFT = false;
				}else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
				{
					RIGHT = false;
				}else if(e.getKeyCode() == KeyEvent.VK_SPACE)
				{
					GET = false;
				}
			}
		});
		frmGame.setResizable(false);
		frmGame.setTitle("Game");
		frmGame.setBounds(100, 100, 1000, 700);
		frmGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGame.getContentPane().setLayout(null);
		frmGame.setFocusable(true);
		frmGame.setVisible(true);
		frmGame.requestFocus();
		
		playPanel[0] = new JPanel();
		playPanel[0].setBounds(50, 50, 900, 500);
		playPanel[0].setLayout(null);
		frmGame.getContentPane().add(playPanel[0]);
		playPanel[1] = new JPanel();
		playPanel[1].setBounds(50, 50, 900, 650);
		playPanel[1].setLayout(null);
		frmGame.getContentPane().add(playPanel[1]);
		playPanel[2] = new JPanel();
		playPanel[2].setBounds(50, 50, 900, 650);
		playPanel[2].setLayout(null);
		frmGame.getContentPane().add(playPanel[2]);
		menuUI();
		switchPanel(0, true);
	}
	private void menuUI()
	{
		JButton startBtn = new JButton();
		startBtn.setText("Game Start!");
		startBtn.setBackground(Color.LIGHT_GRAY);
		startBtn.setBounds(350, 150, 200, 100);
		startBtn.setFont(new Font("Serief", Font.BOLD,24));
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				play = new Play(playPanel[1], "start" + "stage");
				play.start();
				isPlay = true;
				switchPanel(1, true);
			}
		});
		playPanel[0].add(startBtn);
		
		JButton creatBtn = new JButton();
		creatBtn.setText("Game Create!");
		creatBtn.setBackground(Color.LIGHT_GRAY);
		creatBtn.setBounds(350, 300, 200, 100);
		creatBtn.setFont(new Font("Serief", Font.BOLD,24));
		creatBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				creat = new Creat(playPanel[2]);
				creat.start();
				switchPanel(2, true);
			}
		});
		playPanel[0].add(creatBtn);
		
	}
	
	public static void switchPanel(int panelID, boolean active)
	{
		for(int i = 0; i < 3; i++)
		{
			if(i == panelID)
				playPanel[i].setVisible(active);
			else
				playPanel[i].setVisible(false);
		}
	}
}
