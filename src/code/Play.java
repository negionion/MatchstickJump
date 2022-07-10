package game;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
class Play extends Thread{
	private JPanel panel;
	private JPanel scene, ui;
	private String stageName, nextStage;
	private Player player;
	private ArrayList<Block> blockList;
	private ArrayList<Block> keyList;
	private Block next;
	
	private JLabel keyLab, stageLab;
	private JButton againBtn, backBtn;
	Play(JPanel _panel, String stage)
	{
		initial(_panel, stage);
	}
	
	private void initial(JPanel _panel, String stage)
	{
		panel = _panel;
		stageName = stage;
		blockList = new ArrayList<>();
		keyList = new ArrayList<>();
		scene = new JPanel();
		scene.setBounds(0, 0, 900, 450);
		scene.setLayout(null);
		panel.add(scene);
		
		loadStage();
				
		ui = new JPanel();
		ui.setBounds(0, 450, 900, 180);
		ui.setLayout(null);
		stageLab = new JLabel(stageName.substring(0, stageName.length() - 5));
		stageLab.setBounds(0, 20, 200, 50);
		stageLab.setFont(new Font("Serief", Font.BOLD + Font.ITALIC,24));
		keyLab = new JLabel("Key:");
		keyLab.setBounds(0, 80, 200, 50);
		keyLab.setFont(new Font("Serief", Font.BOLD + Font.ITALIC,24));
		ui.add(stageLab);
		ui.add(keyLab);
		
		againBtn = new JButton("Again");
		againBtn.setBounds(700, 20, 200, 50);
		againBtn.setBackground(Color.LIGHT_GRAY);
		againBtn.setFont(new Font("Serief", Font.BOLD, 24));
		againBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{				
				reLoadStage(stageName);
			}
		});
		backBtn = new JButton("Back");
		backBtn.setBounds(700, 80, 200, 50);
		backBtn.setBackground(Color.LIGHT_GRAY);
		backBtn.setFont(new Font("Serief", Font.BOLD, 24));
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				panel.removeAll();
				panel.repaint();
				player.threadCtrl = false;
				Game.isPlay = false;
				Game.switchPanel(0, true);
			}
		});
		
		ui.add(againBtn);
		ui.add(backBtn);
		panel.add(ui);
	}
	
	public void loadStage()
	{
		FileReader fr;
		String str;
		int x = 0, y = 0;
		int playX = 0, playY = 0;
		try {
			fr = new FileReader(Game.path + "\\stage\\" + stageName + ".txt");
			BufferedReader bfr = new BufferedReader(fr);
			while((str = bfr.readLine()) != null)
			{
				x = 0;
				if(str.contains("stage"))
				{
					nextStage = str;
				}
				else
				{
					for(int i = 0; i < str.length(); i++)
					{
						switch (str.charAt(i))
						{
						case '1':
							blockList.add(new Block(x, y, 1, scene));
							break;
						case '2':
							keyList.add(new Block(x, y, 2, scene));
							break;
						case '3':
							next = new Block(x, y, 3, scene);
							break;
						case 'c':
							playX = x;
							playY = y;
							break;
						
						}					
						x += 50;
					}
					y += 50;
				}
			}
			fr.close();
			player = new Player(playX, playY, scene, blockList, keyList, next);
			player.start();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void run()
	{
		while(Game.isPlay)
		{
			//System.out.println("play thread!");
			if(player == null)
			{
				continue;
			}
			keyLab.setText("Key:" + (player.keySum - keyList.size()) + " / " + player.keySum);
			if(player.playFinish)
			{
				//System.out.println("finish");
				if(!nextStage.contains("END"))
					reLoadStage(nextStage);
				else
				{
					panel.removeAll();
					panel.repaint();
					Game.isPlay = false;
					Game.switchPanel(0, true);
				}
			}else if(player.isGameover)
			{
				reLoadStage(stageName);
			}
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void reLoadStage(String stage)
	{
		synchronized(player)
		{
			player.threadCtrl = false;
			panel.removeAll();	
			initial(panel, stage);
		}
		panel.repaint();
		panel.revalidate();
	}
}
