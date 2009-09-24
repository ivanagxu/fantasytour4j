package com.ivan.game.motioncreater;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MotionPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -323626494714371830L;
	public MotionPanel()
	{
		setBackground(Color.WHITE);
		image = null;
		imagelist = new ArrayList();
		index = 0;
		timespace = 1000;
		//delaytime = 1000;
	}
	/*
	 * 开始播放动画
	 * @param ts 时间间隔
	 * @param dt 停留时间
	 * @param ty 动画类型
	 * @param list 图片组
	 */
	public void start(int ts,int dt,String ty,ArrayList list)
	{
		timespace = ts;
		//delaytime = dt;
		imagelist = list;
		motiontype = ty;
		if(motiontype.equals("变换"))
		{
			t = new Timer(timespace,new ActionListener()
					{
					public void actionPerformed(ActionEvent e)
						{
							if(imagelist.size() != 0)
							{
								image = (Image)imagelist.get(index);
								repaint();
								index++;
								if (index >= imagelist.size())
								{
									index = 0;
								}
							}
						}
					});
			t.start();
		}
		else
		{
			t = new Timer(timespace,new ActionListener()
					{
					public void actionPerformed(ActionEvent e)
						{
							if(imagelist.size() != 0)
							{
								image = (Image)imagelist.get(0);
								repaint();
								index += 10;
								if (index > 300)
								{
									index = 0;
								}
							}
						}
					});
			t.start();
		}
			
	}
	/*
	 * 停止播放动画
	 */
	public void stop()
	{
		t.stop();
		image = null;
		imagelist = new ArrayList();
		index = 0;
		repaint();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(image != null)
		{
			if(motiontype.equals("变换"))
			{
				track("draw...");
				int i = this.getWidth()/2-50;
				g.drawImage(image,i,50,null);
			}
			else
			{
				track("move...");
				g.drawImage(image,index,50,null);
			}
		}
	}
	/*
	 * 调试
	 * param s 用信息框输出的信息
	 */
	public void track(String s)
	{
		System.out.println(s);
	}
	
	private int index;
	private Image image;
	private ArrayList imagelist;
	private int timespace;
	//private int delaytime;
	private String motiontype;
	private Timer t;
}
