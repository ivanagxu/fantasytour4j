package com.ivan.game.mapcreater;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.ivan.game.unit.DatFilter;
import com.ivan.game.unit.GifFilter;
import com.ivan.game.unit.MapPoint;
import com.ivan.game.unit.Npc;
import com.ivan.game.unit.NumberTester;


public class EditPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8941295108009981589L;
	public EditPanel(JTable a,MapPoint p,MapPoint[][] mp)
	{
		//mappoint = p;
		table = a;
		map = mp;
		
		setLayout(new GridLayout(7,2));
		
		add(new JLabel("��ͼ:"+ p.mapname));
		add(new JLabel("����:("+p.x+","+p.y+")"));
		
		add(new JLabel("��ͼ�¼�:"));
		eventtext = new JTextField(p.event);
		eventtext.addMouseListener(new
				MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)
					{
						if(e.getClickCount() >= 1)
						{
							JFileChooser chooser = new JFileChooser();
							chooser.setCurrentDirectory(new File(".//data//event//"));
							chooser.setFileFilter(new DatFilter());
							int result = chooser.showOpenDialog(EditPanel.this);
							if(result == 0)
							{
								String filepath = chooser.getSelectedFile().getPath();
								String path = filepath.substring(filepath.lastIndexOf("data"));
								eventtext.setText(path);
								event = path;
							}
						}
					}
				});
		add(eventtext);
		
		add(new JLabel("��ͼNpc:"));
		npctext = new JTextField(p.npc);
		npctext.addMouseListener(new
				MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)
					{
						if(e.getClickCount() >= 1)
						{
							JFileChooser chooser = new JFileChooser();
							chooser.setCurrentDirectory(new File(".//data//npc//"));
							chooser.setFileFilter(new DatFilter());
							int result = chooser.showOpenDialog(EditPanel.this);
							if(result == 0)
							{
								String filepath = chooser.getSelectedFile().getPath();
								String path = filepath.substring(filepath.lastIndexOf("data"));
								npctext.setText(path);
								npc = path;
							}
						}
					}
				});
		add(npctext);
		
		add(new JLabel("��ͼ����:"));
		enemytext = new JTextField(p.enemy);
		enemytext.addMouseListener(new
				MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)
					{
						if(e.getClickCount() >= 1)
						{
							JFileChooser chooser = new JFileChooser();
							chooser.setCurrentDirectory(new File(".//data//enemy//"));
							chooser.setFileFilter(new DatFilter());
							int result = chooser.showOpenDialog(EditPanel.this);
							if(result == 0)
							{
								String filepath = chooser.getSelectedFile().getPath();
								String path = filepath.substring(filepath.lastIndexOf("data"));
								enemytext.setText(path);
								enemy = path;
							}
						}
					}
				});
		add(enemytext);
		
		add(new JLabel("Ϯ������:"));
		probabilitytext = new JTextField(new Integer(p.probability).toString());
		add(probabilitytext);
		
		add(new JLabel("��ͼ�ļ�:"));
		imagepathtext = new JTextField(p.imagepath);
		imagepathtext.addMouseListener(new
				MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)
					{
						if(e.getClickCount() >= 1)
						{
							JFileChooser chooser = new JFileChooser();
							chooser.setCurrentDirectory(new File(".//data//images//"));
							chooser.setFileFilter(new GifFilter());
							int result = chooser.showOpenDialog(EditPanel.this);
							if(result == 0)
							{
								String filepath = chooser.getSelectedFile().getPath();
								String path = filepath.substring(filepath.lastIndexOf("data"));
								imagepathtext.setText(path);
								imagepath = path;
							}
						}
					}
				});
		add(imagepathtext);
		
		add(new JLabel("�ܷ�����:"));
		walkablecombo = new JComboBox();
		walkablecombo.addItem("��");
		walkablecombo.addItem("��");
		if(p.walkable.equals("��"))
			walkablecombo.setSelectedIndex(0);
		else if(p.walkable.equals("��"))
			walkablecombo.setSelectedIndex(1);
		add(walkablecombo);
	}
	public void update()
	{
		
	}
	public void save()
	{
		event = eventtext.getText();
		npc = npctext.getText();
		enemy = enemytext.getText();
		probability = probabilitytext.getText();
		imagepath = imagepathtext.getText();
		walkable = (String)walkablecombo.getSelectedItem();
		
		if(event.length() == 0)
		{
			event = "data/default.dat";
		}
		if(npc.length() == 0)
		{
			npc = "data/default.dat";
		}
		if(enemy.length() == 0)
		{
			enemy = "data/default.dat";
		}
		
		String errormsg = "";
		boolean wrong = false;
		NumberTester tester = new NumberTester();
		
		if(!event.endsWith(".dat"))
		{
			errormsg += "�¼��ļ�����\n";
			wrong = true;
		}
		if(!npc.endsWith(".dat"))
		{
			errormsg += "Npc�ļ�����\n";
			wrong = true;
		}
		if(!enemy.endsWith(".dat"))
		{
			errormsg += "�����ļ�����\n";
			wrong = true;
		}
		if(!tester.test(probability) || tester.getInt(probability) > 100)
		{
			errormsg += "��������(0~100)\n";
			wrong = true;
		}
		if(imagepath.length() == 0 || !imagepath.endsWith(".gif"))
		{
			errormsg += "��ͼ�ļ�����\n";
			wrong = true;
		}
		
		if(wrong)
		{
			JOptionPane.showMessageDialog(
					EditPanel.this,
					errormsg,"����",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		for(int i = 0; i< table.getRowCount(); i++)
		{
			for(int j = 0; j < table.getColumnCount(); j++)
			{
				if(table.isCellSelected(i,j))
				{
					if(!npc.endsWith("default.dat"))
					{
						table.setValueAt(new ImageIcon(new Npc(new File(npc)).getImagesName()[0]),i,j);
					}
					else
					{
						table.setValueAt(new ImageIcon(imagepath),i,j);
					}
					map[i][j].enemy = enemy;
					map[i][j].event = event;
					map[i][j].imagepath = imagepath;
					map[i][j].npc = npc;
					map[i][j].probability = probability;
					map[i][j].walkable = walkable;
				}
			}
		}
		table.repaint();
	}
	private JTable table;
	private MapPoint[][] map;
	
	private String event;
	private String npc;
	private String enemy;
	private String probability;
	private String imagepath;
	private String walkable;
	
	private JTextField eventtext;
	private JTextField npctext;
	private JTextField enemytext;
	private JTextField probabilitytext;
	private JTextField imagepathtext;
	private JComboBox walkablecombo;
}
