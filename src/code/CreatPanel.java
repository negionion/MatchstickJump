package game;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
class CreatPanel extends Thread {
	private JPanel panel;
	private boolean threadState = true;
	private JButton[][] selBlock;
	public char[][] blockEncode;
	public int col, row;
	public static boolean player = false, next = false;
	CreatPanel(JPanel _panel)
	{
		panel = _panel;
		player = false;
		next = false;
		col = panel.getBounds().width / 50;
		row = panel.getBounds().height / 50;
		selBlock = new JButton[9][18];
		blockEncode = new char[9][18];
		for(int i = 0; i < row; i++)
		{
			for(int j = 0; j < col; j++)
			{
				blockEncode[i][j] = '0';
				selBlock[i][j] = new JButton();
				selBlock[i][j].setBounds(j * 50, i * 50, 50, 50);
				selBlock[i][j].setBackground(Color.WHITE);				
				selBlock[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0)
					{
						JButton btn = (JButton)arg0.getSource();
						if(blockEncode[btn.getBounds().y / 50][btn.getBounds().x / 50] == '3' && Creat.selState != '3')
						{
							next = false;
						}
						
						if(blockEncode[btn.getBounds().y / 50][btn.getBounds().x / 50] == 'c' && Creat.selState != 'c')
						{
							player = false;
						}
						
						blockEncode[btn.getBounds().y / 50][btn.getBounds().x / 50] = Creat.selState;
						//System.out.println(btn.getBounds().y / 50 + " : " + btn.getBounds().x / 50);
						switch(Creat.selState)
						{
						case '1':
							btn.setIcon(drawIcon("basic.png"));
							break;
						case '2':
							btn.setIcon(drawIcon("key.png"));
							break;
						case '3':
							next = true;
							Creat.selState = '0';
							btn.setIcon(drawIcon("next.png"));
							break;
						case 'c':
							player = true;
							Creat.selState = '0';
							btn.setIcon(drawIcon("wait.png"));
							break;
						default:
							btn.setIcon(null);
						}
					}
				});
				panel.add(selBlock[i][j]);
			}
		}
	}
	
	private ImageIcon drawIcon(String fileName)
	{
		ImageIcon img = new ImageIcon(Game.path + "\\images\\" + fileName);
		return img;
	}
	
	public void setBlock(int x, int y, char sel)
	{		
		JButton btn = (JButton)panel.getComponentAt(x * 50, y * 50);
		blockEncode[y][x] = sel;
		//System.out.println(y + " : " + x);
		switch(sel)
		{
		case '1':
			btn.setIcon(drawIcon("basic.png"));
			break;
		case '2':
			btn.setIcon(drawIcon("key.png"));
			break;
		case '3':
			next = true;
			btn.setIcon(drawIcon("next.png"));
			break;
		case 'c':
			player = true;
			btn.setIcon(drawIcon("wait.png"));
			break;
		default:
			btn.setIcon(null);
		}
	}
	
	public void run()
	{
		while(threadState)
		{
			
		}
	}
}
