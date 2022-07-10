package game;
import java.awt.*;
import java.io.*;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
class Creat extends Thread{
	private JPanel panel, scene, ui;
	private JButton blockBtn, keyBtn, playerBtn, nextBtn, clearBtn, saveBtn, backBtn, newStageBtn, loadBtn;
	private JLabel nowSel, nowHint;
	private boolean threadCtrl = true;
	private String loadNowName = "", loadNextName = "";
	public static char selState = 0;
	private CreatPanel creatPanel;
	Creat(JPanel _panel)
	{
		panel = _panel;
		threadCtrl = true;
		
		initial();
	}
	
	private void initial()
	{
		scene = new JPanel();
		scene.setBounds(0, 0, 900, 450);
		scene.setLayout(null);
		ui = new JPanel();
		ui.setBounds(0, 450, 900, 180);
		ui.setLayout(null);
		panel.add(scene);
		creatPanel = new CreatPanel(scene);
		loadNowName = "";
		loadNextName = "";
		
		nowHint = new JLabel("Select");
		nowHint.setBounds(0, 0, 50, 50);
		nowHint.setFont(new Font("Serief", Font.BOLD, 17));
		nowSel = new JLabel();
		nowSel.setBounds(0, 50, 50, 50);
		
		playerBtn = new JButton();
		playerBtn.setBounds(100, 50, 50, 50);
		playerBtn.setIcon(drawIcon("wait.png"));
		playerBtn.setBackground(Color.WHITE);
		playerBtn.setEnabled(true);
		playerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				selState = 'c';
			}
		});
		
		blockBtn = new JButton();
		blockBtn.setBounds(170, 50, 50, 50);
		blockBtn.setIcon(drawIcon("basic.png"));
		blockBtn.setBackground(Color.WHITE);
		blockBtn.setEnabled(true);
		//blockBtn.setFont(new Font("Serief", Font.BOLD, 24));
		blockBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				selState = '1';
			}
		});
		
		keyBtn = new JButton();
		keyBtn.setBounds(240, 50, 50, 50);
		keyBtn.setIcon(drawIcon("key.png"));
		keyBtn.setBackground(Color.WHITE);
		keyBtn.setEnabled(true);
		//blockBtn.setFont(new Font("Serief", Font.BOLD, 24));
		keyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				selState = '2';
			}
		});
		
		nextBtn = new JButton();
		nextBtn.setBounds(310, 50, 50, 50);
		nextBtn.setIcon(drawIcon("next.png"));
		nextBtn.setBackground(Color.WHITE);
		nextBtn.setEnabled(true);
		//blockBtn.setFont(new Font("Serief", Font.BOLD, 24));
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				selState = '3';
			}
		});
		
		clearBtn = new JButton();
		clearBtn.setBounds(380, 50, 50, 50);
		//clearBtn.setIcon(drawIcon("next.png"));
		clearBtn.setBackground(Color.WHITE);
		clearBtn.setEnabled(true);
		clearBtn.setFont(new Font("Clear", Font.BOLD, 12));
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				selState = '0';
			}
		});
		
		loadBtn = new JButton("Load");
		loadBtn.setBounds(460, 20, 100, 110);
		loadBtn.setBackground(Color.LIGHT_GRAY);
		loadBtn.setFont(new Font("Serief", Font.BOLD, 24));
		loadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				loadWin();
			}
		});
		
		saveBtn = new JButton("Save");
		saveBtn.setBounds(580, 20, 100, 110);
		saveBtn.setBackground(Color.LIGHT_GRAY);
		saveBtn.setFont(new Font("Serief", Font.BOLD, 24));
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				if(CreatPanel.next && CreatPanel.player)
					saveWin();
				else
				{
					tipsWin("請放入 [角色] 及 [下一關] 方塊!");
				}
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
				threadCtrl = false;
				Game.switchPanel(0, true);
			}
		});
		
		newStageBtn = new JButton("NEW Stage!");
		newStageBtn.setBounds(700, 20, 200, 50);
		newStageBtn.setBackground(Color.LIGHT_GRAY);
		newStageBtn.setFont(new Font("Serief", Font.BOLD, 24));
		newStageBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				scene.removeAll();
				scene.repaint();
				creatPanel = new CreatPanel(scene);
				loadNowName = "";
				loadNextName = "";
			}
		});
		ui.add(nowHint);
		ui.add(nowSel);
		ui.add(playerBtn);
		ui.add(blockBtn);
		ui.add(keyBtn);
		ui.add(nextBtn);
		ui.add(clearBtn);
		ui.add(loadBtn);
		ui.add(saveBtn);
		ui.add(backBtn);
		ui.add(newStageBtn);
		panel.add(ui);
		
	}
	private ImageIcon drawIcon(String fileName)
	{
		ImageIcon img = new ImageIcon(Game.path + "\\images\\" + fileName);
		return img;
	}
	
	private void tipsWin(String msg)
	{
		JDialog tips = new JDialog(Game.frmGame ,"Tips!");
		tips.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		tips.setBounds(ui.getLocationOnScreen().x, ui.getLocationOnScreen().y, 300, 70);					
		JLabel txt = new JLabel(msg);
		txt.setFont(new Font("Serief", Font.ITALIC, 20));
		tips.add(txt);
		tips.setVisible(true);
	}
	
	private void saveWin()
	{
		JDialog popWin = new JDialog(Game.frmGame, "Save stage!");
		popWin.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		popWin.setBounds(ui.getLocationOnScreen().x, ui.getLocationOnScreen().y, 500, 300);
		popWin.setLayout(null);
		JLabel nowHint, nextHint;
		JTextField nowName, nextName;
		JButton yesBtn;
		
		nowHint = new JLabel("Stage Name : ");
		nowHint.setBounds(20, 30, 200, 50);
		nowHint.setFont(new Font("Serief", Font.BOLD, 24));
		
		nowName = new JTextField(loadNowName);
		nowName.setBounds(200, 35, 230, 40);
		nowName.setFont(new Font("Serief", Font.ITALIC, 22));
		
		nextHint = new JLabel("Next Name : ");
		nextHint.setBounds(20, 100, 200, 50);
		nextHint.setFont(new Font("Serief", Font.BOLD, 24));
		
		nextName = new JTextField(loadNextName);
		nextName.setBounds(200, 105, 230, 40);
		nextName.setFont(new Font("Serief", Font.ITALIC, 22));
		
		yesBtn = new JButton("Save!");
		yesBtn.setBounds(250, 170, 180, 50);
		yesBtn.setBackground(Color.LIGHT_GRAY);
		yesBtn.setFont(new Font("Serief", Font.BOLD, 24));
		yesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				try {
					if(!(nowName.getText().length() == 0))
					{
						
						saveStage(nowName.getText(), ((nextName.getText().length() == 0) ? "END" : nextName.getText()));
					}
					else
					{
						tipsWin("Stage Name 不可為空!");
					}
				} catch (IOException e) {
					tipsWin("不要亂輸入!");
				}
				popWin.dispose();
			}
		});
		
		
		popWin.add(nowHint);
		popWin.add(nowName);
		popWin.add(nextHint);
		popWin.add(nextName);
		popWin.add(yesBtn);
		
		popWin.setVisible(true);
	}
	
	private void loadWin()
	{
		JDialog popWin = new JDialog(Game.frmGame, "Load stage!");
		popWin.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		popWin.setBounds(ui.getLocationOnScreen().x, ui.getLocationOnScreen().y, 500, 300);
		popWin.setLayout(null);
		JLabel nowHint;
		JTextField nowName;
		JButton yesBtn;
		
		nowHint = new JLabel("Stage Name : ");
		nowHint.setBounds(20, 70, 200, 50);
		nowHint.setFont(new Font("Serief", Font.BOLD, 24));
		
		nowName = new JTextField();
		nowName.setBounds(200, 75, 230, 40);
		nowName.setFont(new Font("Serief", Font.ITALIC, 22));
		
		yesBtn = new JButton("Load!");
		yesBtn.setBounds(250, 170, 180, 50);
		yesBtn.setBackground(Color.LIGHT_GRAY);
		yesBtn.setFont(new Font("Serief", Font.BOLD, 24));
		yesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				try {
					if(!(nowName.getText().length() == 0))
					{
						loadStage(nowName.getText());
					}
					else
					{
						tipsWin("Stage Name 不可為空!");
					}
				} catch (IOException e) {
					tipsWin("不要亂輸入!");
				}
				popWin.dispose();
			}
		});
		
		
		popWin.add(nowHint);
		popWin.add(nowName);
		popWin.add(yesBtn);
		
		popWin.setVisible(true);
	}
	
	private void saveStage(String nowName, String nextName) throws IOException
	{
		//System.out.println(nowName + " / " + nextName);
		nowName += "stage";
		nextName += "stage";
		FileWriter fw = new FileWriter(Game.path + "\\stage\\" + nowName + ".txt");
		BufferedWriter bfw = new BufferedWriter(fw);

		for(int i = 0; i < creatPanel.row; i++)
		{
			for(int j = 0; j < creatPanel.col; j++)
			{
				bfw.write(creatPanel.blockEncode[i][j]);
				//System.out.println(i + " " + j + " "+ creatPanel.blockEncode[i][j]);
			}
			bfw.newLine();
		}
		bfw.write(nextName);
		bfw.flush();
		fw.close();
	}
	
	private void loadStage(String nowName) throws IOException
	{
		FileReader fr;
		String str, nextName = "";
		int row = 0;
		scene.removeAll();
		scene.repaint();
		creatPanel = new CreatPanel(scene);
		nowName += "stage";
		
		fr = new FileReader(Game.path + "\\stage\\" + nowName + ".txt");
		BufferedReader bfr = new BufferedReader(fr);
		while((str = bfr.readLine()) != null)
		{
			if(str.contains("stage"))
			{
				nextName = str;
				break;
			}
			for(int col = 0; col < str.length(); col++)
			{
				creatPanel.setBlock(col, row, str.charAt(col));
			}
			row++;
		}
		fr.close();
		loadNowName = nowName.substring(0, nowName.length() - 5);
		loadNextName = nextName.substring(0, nextName.length() - 5);
	}
	
	public void run()
	{
		while(threadCtrl)
		{
			//System.out.println("creat thread!");
			playerBtn.setEnabled(!CreatPanel.player);
			nextBtn.setEnabled(!CreatPanel.next);
			switch(selState)
			{
			case '1':
				nowSel.setIcon(drawIcon("basic.png"));
				break;
			case '2':
				nowSel.setIcon(drawIcon("key.png"));
				break;
			case '3':
				nowSel.setIcon(drawIcon("next.png"));
				break;
			case 'c':
				nowSel.setIcon(drawIcon("wait.png"));
				break;
			default:
				nowSel.setIcon(null);
			}
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
