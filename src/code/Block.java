package game;

import java.awt.*;

import javax.swing.*;

class Block {
	public Rectangle rect;
	public JLabel icon;
	private JPanel panel;
	public int kind;
	Block(int x, int y, int _kind, JPanel _panel)
	{
		rect = new Rectangle(x, y, 50, 50);
		kind = _kind;
		panel = _panel;
		icon = new JLabel();
		switch(kind)
		{
		case 1:
			drawIcon("basic.png");
			break;
		case 2:
			drawIcon("key.png");
			break;
		case 3:
			drawIcon("next.png");
			break;
		}
		icon.setBounds(x, y, 50, 50);
		panel.add(icon);
	}
	private void drawIcon(String fileName)
	{
		ImageIcon img = new ImageIcon(Game.path + "\\images\\" + fileName);
		icon.setIcon(img);
	}
}
