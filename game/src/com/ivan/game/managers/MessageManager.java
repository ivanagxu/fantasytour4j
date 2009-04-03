package com.ivan.game.managers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.ivan.game.game.GameCanvas;
import com.ivan.game.game.MainFrame;
import com.ivan.game.unit.Msg;


public class MessageManager {
	public MessageManager(JFrame frame)
	{
		canvas = ((MainFrame)frame).getCanvas().getGraphics();
		gamecanvas = ((MainFrame)frame).getCanvas();
		cursor = new ImageIcon("data/images/cursor-2.gif").getImage();
		doubleBuffer = frame.createImage(260,260);
	}
	public void showMessage(Msg msg)
	{
		String s = msg.getLine();
		//Graphics g2 = doubleBuffer.getGraphics();
		//g2 = canvas;
		//canvas.setFont(new Font("ËÎÌו",Font.PLAIN,14));
		while(true)
		{
			if(s != "" && s != null)
			{
				canvas.setColor(Color.ORANGE);
				canvas.fillRect(0,190,260,70);
				canvas.setColor(Color.WHITE);
				canvas.fillRect(6,196,248,53);
				canvas.setColor(Color.BLACK);
				canvas.drawRect(5,195,250,55);
				canvas.drawString(s,20,230);
				if(!s.endsWith(" "))
					canvas.drawImage(cursor,240,240,null);
				sleep(200);
				while(gamecanvas.getInput() != 'j' && s != null)
				{
					sleep(1);
				}
				s = msg.getLine();
			}
			else
			{
				//canvas = g2;
				break;
			}
			
		}
	}
	public void sleep(int ms)
	{
		try{
			Thread.sleep(ms);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	private Image doubleBuffer;
	private Graphics canvas;
	private GameCanvas gamecanvas;
	private Image cursor;
}
