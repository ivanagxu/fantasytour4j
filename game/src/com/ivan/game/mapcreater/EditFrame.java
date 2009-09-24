package com.ivan.game.mapcreater;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;

import com.ivan.game.unit.MapPoint;

public class EditFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4308377318892248328L;
	public EditFrame(JTable a,MapPoint p,MapPoint[][] map,int x,int y)
	{
		setBounds(x,300,200,200);
		setResizable(false);
		mappoint = p;
		Container content = getContentPane();
		content.add(panel = new EditPanel(a,mappoint,map));
		JButton submit = new JButton("提交修改");
		submit.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						panel.save();
						System.out.println(mappoint.imagepath);
						dispose();
					}
				});
		content.add(submit,BorderLayout.SOUTH);
	}
	private MapPoint mappoint;
	private EditPanel panel;
}
