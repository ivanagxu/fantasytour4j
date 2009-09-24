package com.ivan.game.managers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import com.ivan.game.unit.Hero;


public class DisplayManager {
	public DisplayManager()
	{
		
	}
	public void setCanvas(Graphics g)
	{
		canvas = g;
	}
	public void playStartMovie()
	{
		for(int i = 0; i < 256; i++)
		{
			canvas.setColor(new Color(i,i,i));
			canvas.fillRect(0,0,260,260);
			sleep(10);
		}
		play("data/sound/bakegxuan.mid");
		
	}
	public void playFailMovie()
	{
		canvas.setColor(Color.WHITE);
		canvas.fillRect(0,0,260,260);
		for(int i = 255; i >=0 ; i--)
		{
			canvas.setColor(new Color(i,i,i));
			canvas.fillRect(0,0,260,260);
			sleep(10);
		}
	}
	public void playMetEnemyMovie()
	{
		
		canvas.setColor(Color.BLACK);
		canvas.fillRect(0,0,260,260);
		canvas.setColor(Color.WHITE);
		Graphics2D g2 = (Graphics2D)canvas;
		for(int i = 0; i < 130; i++)
		{
			g2.drawLine(130-i,0,130+i,260);
			g2.drawLine(130+i,0,130-i,260);
			g2.drawLine(0,130-i,260,130+i);
			g2.drawLine(0,130+i,260,130-i);
			sleep(10);
		}
		
		play("data/sound/圣斗士星矢.mid");
	}
	public void changeMapMovie(String mapname,String music)
	{
		Color c;
		c = new Color(0, 0, 0, 1);
		canvas.setColor(c);
		canvas.fillRect(0, 0, 260, 260);

		c = new Color(0, 0, 0, 255);
		canvas.setColor(c);
		canvas.fillRect(0, 0, 260, 260);
		sleep(10);
		/*
		canvas.setColor(Color.BLACK);
		canvas.fillRect(0,0,260,260);
		canvas.setColor(Color.WHITE);
		
		for(int i = 0; i <= 130; i++)
		{
			canvas.setColor(Color.BLACK);
			canvas.drawString(mapname,120,130);
			canvas.setColor(Color.WHITE);
			canvas.fillRect(130-i,0,1,260);
			canvas.fillRect(130+i,0,1,260);
			sleep(10);
		}
		canvas.setColor(Color.BLACK);
		canvas.fillRect(0,0,260,260);
		*/
		if(!musicfile.equals(music))
		{
			musicfile = music;
			play(musicfile);
		}
		
	}
	public void playExitMovie()
	{
		
	}
	public void DrawHero(Hero hero)
	{
		Image heroimage = hero.getCurrentImage();
		canvas.drawImage(heroimage,120,120,null);
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
	public void setMsgMgr(MessageManager m)
	{
		msgmgr = m;
	}
	public void setSoundManager(SoundManager m)
	{
		sndmgr = m;
	}
	public void play(String s)
	{
		sndmgr.stop();
		sndmgr.play(s);
	}
	public void setDisplay(int d)
	{
		display = d;
	}
	public int getDisplay()
	{
		return display;
	}
	private int display;
	private Graphics canvas;
	private MessageManager msgmgr;
	private SoundManager sndmgr;
	private String musicfile = "";
}
