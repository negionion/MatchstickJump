package game;
import java.awt.*;
import java.io.*;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;

class Player extends Thread{
	public Point position = new Point(0,0);
	private JLabel icon;
	private ArrayList<Block> blockList;
	private ArrayList<Block> keyList;
	private Block nextStage;
	public boolean playFinish = false, isGameover = false, threadCtrl = true;
	public int keySum;
	private int xSpeed, ySpeed;
	private JPanel panel;
	private int animeState = 0;//0 = wait, 1 = jump, 2 = left, 3 = right
	private boolean isJump = false, isGround = true;
	private int jumpHigh;
	
	Player(int x, int y, JPanel _panel, ArrayList<Block> _blockList, ArrayList<Block> _keyList, Block next)
	{
		threadCtrl = true;
		position.setLocation(x, y);
		playFinish = false;
		isGameover = false;
		panel = _panel;
		blockList = _blockList;
		keyList = _keyList;
		keySum = keyList.size();
		nextStage = next;
		nextStage.icon.setVisible(false);
		icon = new JLabel();
		drawIcon("wait.png");
		icon.setBounds(position.x, position.y, 50, 50);
		panel.add(icon);
	}
	
	public void run()
	{
		while(threadCtrl)
		{
			move();
			gravity();
			jump();
			getKey();
			position.y += ySpeed;
			icon.setBounds(position.x, position.y, 50, 50);
			
			if(position.y > 500)
			{
				isGameover = true;
				threadCtrl = false;
			}
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void drawIcon(String fileName)
	{
		ImageIcon img = new ImageIcon(Game.path + "\\images\\" + fileName);
		icon.setIcon(img);
	}
	
	public boolean collision(Rectangle _rect)
	{
		Block block;
		if(new Rectangle(-2,-100,2,550).intersects(_rect) || new Rectangle(900,-100,2,550).intersects(_rect))
		{
			return true;
		}
		for(int i = 0; i < blockList.size(); i++)
		{
			block = blockList.get(i);
			if(block.rect.intersects(_rect))
			{
				return true;
			}
		}
		return false;
	}
	
	public void move()
	{
		if(Game.LEFT)
		{
			xSpeed = -2;
			if(animeState != 2 && animeState != 1)
			{
				drawIcon("walk_L.gif");
				animeState = 2;
			}else if(animeState == 1)
			{
				drawIcon("jump_L.png");
			}
			if(collision(new Rectangle(position.x + 15, position.y + 5, 2, 40)))
			{
				xSpeed = 0;
			}
			
		}
		else if(Game.RIGHT)
		{
			xSpeed = 2;
			if(animeState != 3 && animeState != 1)
			{
				drawIcon("walk_R.gif");
				animeState = 3;
			}else if(animeState == 1)
			{
				drawIcon("jump_R.png");
			}
			if(collision(new Rectangle(position.x + 35, position.y + 5, 2, 40)))
			{
				xSpeed = 0;
			}
		}
		else
		{
			if(animeState != 0 || animeState != 1)
			{
				drawIcon("wait.png");
				animeState = 0;
			}
			xSpeed = 0;
		}
		position.x += xSpeed;
		
		if(Game.UP && isGround)
		{
			isJump = true;
			jumpHigh = 0;
		}
	}
	public void jump()
	{
		if(!isJump)
			return;
		ySpeed = -2;
		jumpHigh += Math.abs(ySpeed);
		isGround = false;
		animeState = 1;
		if(collision(new Rectangle(position.x + 17, position.y + 5, 16, 2)) || jumpHigh >= 75)
		{
			isJump = false;
		}
		
	}
	private void gravity()
	{
		if(!isJump)
		{
			ySpeed = 2;
			isGround = false;
			if(collision(new Rectangle(position.x + 17, position.y + 45, 16, 2)))
			{
				ySpeed = 0;
				isGround = true;
				animeState = 0;
			}
		}
	}
	
	private void getKey()
	{
		if(!Game.GET)
		{
			return;
		}
		Rectangle character = new Rectangle(position.x + 15, position.y + 5, 20, 40);
		for(int i = 0; i < keyList.size(); i++)
		{
			if(keyList.get(i).rect.intersects(character))
			{
				keyList.get(i).icon.setVisible(false);
				keyList.remove(i);
			}
		}
		if(keyList.isEmpty())
		{
			nextStage.icon.setVisible(true);
			if(nextStage.rect.intersects(character))
			{
				playFinish = true;
				threadCtrl = false;
			}
		}
	}
}
