package com.ivan.game.mapcreater;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.ivan.game.managers.ItemManager;
import com.ivan.game.managers.NpcManager;
import com.ivan.game.unit.DatFilter;
import com.ivan.game.unit.MapPoint;
import com.ivan.game.unit.MidFilter;
import com.ivan.game.unit.Npc;
import com.ivan.game.unit.NumberTester;


public class MapCreaterFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2670465050582015834L;
	public MapCreaterFrame(String filename,boolean b)
	{
		setTitle("地图制作器");
		//setResizable(false);
		this.setBounds(0,0,DEFAULT_WIDTH,DEFAULT_HEIGHT);
		runonce = b;
		content = getContentPane();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu editmenu = new JMenu("Edit");
		JMenu helpmenu = new JMenu("Help");
		
		menuBar.add(editmenu);
		menuBar.add(helpmenu);

		JMenuItem newitem = new JMenuItem("New",'N');
		JMenuItem openitem = new JMenuItem("Open",'O');
		JMenuItem saveitem = new JMenuItem("Save",'S');
		JMenuItem exititem = new JMenuItem("Exit",'E');
		JMenuItem aboutitem = new JMenuItem("About");
		
		newitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
		openitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
		saveitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
		exititem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
		//aboutitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
		
		editmenu.add(newitem);
		editmenu.addSeparator();
		editmenu.add(openitem);
		editmenu.add(saveitem);
		editmenu.addSeparator();
		editmenu.add(exititem);
		helpmenu.add(aboutitem);
		
		exititem.addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						int NotExit = 0;
						NotExit = JOptionPane.showConfirmDialog(
								MapCreaterFrame.this,
								"是否退出?",
								"等待确认",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE);
						if(NotExit == 0)
							dispose();
						
					}
				});
		
		aboutitem.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						/*
						 * JOptionPane.showMessageDialog(MainFrame.this, "love IvAn",
						 * "关于", JOptionPane.INFORMATION_MESSAGE);
						 * 
						 */
						try
						{
							Runtime.getRuntime().exec(
									"cmd.exe /c start data/MyGameDoc/index.htm");
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				});
		
		openitem.addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						JFileChooser chooser = new JFileChooser();
						chooser.setCurrentDirectory(new File(".//data//map//"));
						chooser.setFileFilter(new DatFilter());
						int result = chooser.showOpenDialog(MapCreaterFrame.this);
						if(result == 0)
						{
							readFile(chooser.getSelectedFile());
						}
					}
				});
		
		saveitem.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						save();
					}
				});
		
		newitem.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						newFile();
					}
				});
		
		addWindowListener(new 
				WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{
						dispose();
						if(frame != null)
						{
							frame.dispose();
						}
					}
				});
		if(filename.length() == 0)
			init();
		else
			init(filename);
		content.add(new JScrollPane(table));
	}
	public void init()
	{
		String tablex = "0";
		String tabley = "0";
		NumberTester tester = new NumberTester();
		int x = 0;
		int y = 0;
		if(runonce == true) //always true
		{
			tablex = "50";
			tabley = "50";
			y = Integer.parseInt(tabley);
			x = Integer.parseInt(tablex);
			runonce = false;
		}
		else
		{
			while(true)
			{
				mapname = JOptionPane.showInputDialog(MapCreaterFrame.this,"输入地图名称");
				tablex = JOptionPane.showInputDialog(MapCreaterFrame.this,"输入地图宽度");
				tabley = JOptionPane.showInputDialog(MapCreaterFrame.this,"输入地图高度");

				if(tablex != null && tabley != null)
				{
					if(tester.test(tablex) && tester.test(tabley))
					{
						y = Integer.parseInt(tabley);
						x = Integer.parseInt(tablex);
				
						if(y <= 50 && y>0 && x>0 && x<100)
						{
							break;
						}
					}
				}
				JOptionPane.showMessageDialog(
						MapCreaterFrame.this,
						"地图size错误","错误",
						JOptionPane.ERROR_MESSAGE);
			}
			runonce = false;
		}
		setSize(x*20,600);
		
		columnNames = new String[Integer.parseInt(tabley)];
		for(int i = 0 ; i < Integer.parseInt(tabley); i ++)
		{
			Integer a = new Integer(i);
			columnNames[i] = a.toString();
		}
		cells = new ImageIcon[Integer.parseInt(tabley)][Integer.parseInt(tablex)];
		for(int i = 0 ; i < Integer.parseInt(tabley); i ++)
		{
			for(int j = 0; j<Integer.parseInt(tablex); j++)
				cells[i][j] = new ImageIcon("data/images/world/ground.gif");
		}
		map = new MapPoint[Integer.parseInt(tabley)][Integer.parseInt(tablex)];
		for(int i = 0 ; i < Integer.parseInt(tabley); i ++)
		{
			for(int j = 0; j<Integer.parseInt(tablex); j++)
			{
				map[i][j] = new MapPoint(mapname);
				map[i][j].y = new Integer(i).toString();
				map[i][j].x = new Integer(j).toString();
			}
		}

		TableModel model = new MapTableModel(cells,columnNames);

		table = new JTable(model);
		table.setCellSelectionEnabled(true);
		table.setRowHeight(20);
		table.addMouseListener(new MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)
					{
						if(e.getButton() == MouseEvent.BUTTON1)
						{
							int mx = (int)(e.getX()/20+2)*20 + getPosition().x;
							int my = e.getY() + getPosition().y + 60;
							int x = table.getSelectedColumn();
							int y = table.getSelectedRow();
							System.out.println("select:"+x+","+y + " map:" + 
									map[y][x].x + "," + map[y][x].y);
							if(frame == null)
							{
								frame = new EditFrame(table,map[y][x],map,mx,my);
								frame.show();
							}
							else
							{
								frame.dispose();
								frame = new EditFrame(table,map[y][x],map,mx,my);
								frame.show();
							}
						}
						else if(e.getButton() == MouseEvent.BUTTON3)
						{
							int mx = (int)(e.getX()/20+2)*20 + getPosition().x;
							int my = e.getY() + getPosition().y + 60;
							int x = 0;
							int y = 0;
							boolean find = false;
							for(int i = 0; i< table.getRowCount(); i++)
							{
								for(int j = 0; j < table.getColumnCount(); j++)
								{
									if(table.isCellSelected(i,j))
									{
										find = true;
										x = i;
										y = j;
										System.out.println("select:"+y+","+x + " map:" + 
												map[x][y].x + "," + map[x][y].y);
										break;
									}
								}
								if(find == true)
									break;
							}
							if(frame == null)
							{
								frame = new EditFrame(table,map[x][y],map,mx,my);
								frame.show();
							}
							else
							{
								frame.dispose();
								frame = new EditFrame(table,map[x][y],map,mx,my);
								frame.show();
							}
						}
						else
						{
							if(frame != null)
								frame.dispose();
						}
					}
				});
	}
	public void init(String filename)
	{
		try
		{
			//FileInputStream in = new FileInputStream(f);
			//System.out.println(filename);
			File f = new File(filename);
			BufferedReader in = new 
			BufferedReader(new 
					InputStreamReader(new 
							FileInputStream(f)
							));
			soundfile = in.readLine();
			soundfile.replace(' ','\0');
			//System.out.println(soundfile + "asdasdasd");
			String tablex = in.readLine();
			String tabley = in.readLine();
			//System.out.println(tablex+tabley);
			int x = Integer.parseInt(tablex);
			setSize(x*20,600);
			map = new MapPoint[Integer.parseInt(tabley)][Integer.parseInt(tablex)];
			MapPoint temp;
			String mapstring;
			for(int i = 0 ; i < Integer.parseInt(tabley); i ++)
			{
				for(int j = 0; j<Integer.parseInt(tablex); j++)
				{
					temp = new MapPoint("");
					mapstring = in.readLine();
					//System.out.println(mapstring);
					temp.readFromString(mapstring);
					map[i][j] = temp;
					map[i][j].y = new Integer(i).toString();
					map[i][j].x = new Integer(j).toString();
				}
			}
			mapname = map[0][0].mapname;
			columnNames = new String[Integer.parseInt(tabley)];
			for(int i = 0 ; i < Integer.parseInt(tabley); i ++)
			{
				Integer a = new Integer(i);
				columnNames[i] = a.toString();
			}
			cells = new ImageIcon[Integer.parseInt(tabley)][Integer.parseInt(tablex)];
			NpcManager npcmgr = new NpcManager(new ItemManager());
			for(int i = 0 ; i < Integer.parseInt(tabley); i ++)
			{
				for(int j = 0; j<Integer.parseInt(tablex); j++)
				{
					if(map[i][j].hasNpc())
					{
						cells[i][j] = new ImageIcon(new Npc(new File(map[i][j].npc)).getImagesName()[0]);
					}
					else
					{
						cells[i][j] = new ImageIcon(map[i][j].imagepath);
					}
				}
			}

			TableModel model = new MapTableModel(cells,columnNames);

			table = new JTable(model);
			table.setRowHeight(20);
			table.setCellSelectionEnabled(true);
			table.addMouseListener(new MouseAdapter()
					{
						public void mouseClicked(MouseEvent e)
						{
							if(e.getButton() == MouseEvent.BUTTON1)
							{
								int mx = (int)(e.getX()/20+2)*20 + getPosition().x;
								int my = 300;//e.getY() + getPosition().y + 60;
								int x = table.getSelectedColumn();
								int y = table.getSelectedRow();
								System.out.println("select:"+x+","+y + " map:" + 
										map[y][x].x + "," + map[y][x].y);
								if(frame == null)
								{
									frame = new EditFrame(table,map[y][x],map,mx,my);
									frame.show();
								}
								else
								{
									frame.dispose();
									frame = new EditFrame(table,map[y][x],map,mx,my);
									frame.show();
								}
							}
							else if(e.getButton() == MouseEvent.BUTTON3)
							{
								int mx = (int)(e.getX()/20+2)*20 + getPosition().x;
								int my = e.getY() + getPosition().y + 60;
								int x = 0;
								int y = 0;
								boolean find = false;
								for(int i = 0; i< table.getRowCount(); i++)
								{
									for(int j = 0; j < table.getColumnCount(); j++)
									{
										if(table.isCellSelected(i,j))
										{
											find = true;
											x = i;
											y = j;
											System.out.println("select:"+y+","+x + " map:" + 
													map[x][y].x + "," + map[x][y].y);
											break;
										}
									}
									if(find == true)
										break;
								}
								if(frame == null)
								{
									frame = new EditFrame(table,map[x][y],map,mx,my);
									frame.show();
								}
								else
								{
									frame.dispose();
									frame = new EditFrame(table,map[x][y],map,mx,my);
									frame.show();
								}
							}
							else
							{
								if(frame != null)
									frame.dispose();
							}
						}
					});
			in.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void newFile()
	{
		//track("等待建立新文件...");
		int result = JOptionPane.showConfirmDialog(
				MapCreaterFrame.this,
				"是否保存当前地图?",
				"等待确认",
				JOptionPane.YES_NO_CANCEL_OPTION);
		if(result == 0)
		{
			save();
			dispose();
			new MapCreaterFrame("",runonce).show();
		}
		else if(result == 1)
		{
			dispose();
			new MapCreaterFrame("",runonce).show();
		}
		else if(result == 2)
		{
			return;
		}
	}
	public void readFile(File f)
	{
		dispose();
		new MapCreaterFrame(f.getPath(),false).show();
	}
	public void save()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(".//data//sound//"));
		chooser.setFileFilter(new MidFilter());
		soundfile = "";
		int result = chooser.showOpenDialog(MapCreaterFrame.this);
		if(result == 0)
		{
			String filepath = chooser.getSelectedFile().getPath();
			soundfile = filepath.substring(filepath.lastIndexOf("data"));
		}
		else
		{
			return;
		}
		
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(".//data//map//"));
		chooser.setFileFilter(new DatFilter());
		result = chooser.showSaveDialog(MapCreaterFrame.this);
		if(result == 0)
		{
			String filepath = chooser.getSelectedFile().getPath();
			if(!filepath.endsWith(".dat"))
				filepath += ".dat";
			File savefile = new File(filepath);
			if(savefile.exists())
			{
				int save = JOptionPane.showConfirmDialog(
						MapCreaterFrame.this,
						"此地图已经存在,是否覆盖?",
						"等待确认",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if(save == 0)
				{
					try
					{
						System.out.println("saving map:" + mapname);
						FileOutputStream out = new FileOutputStream(savefile);
						String saveData = "";
						saveData = soundfile + "\n" + map.length + "\n" + map[0].length + "\n" + "                        ";
						out.write(saveData.getBytes(),0,saveData.length());
						
						for(int i = 0; i < map.length; i++)
						{
							for(int j = 0; j< map[0].length; j++)
							{
								saveData = null;
								//saveData = new String();
								saveData = map[i][j].toString();
								out.write(saveData.getBytes(),0,saveData.length());
							}
						}
						System.out.println("has saved!");
						out.close();
					}
					catch(FileNotFoundException e)
					{
						e.printStackTrace();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	public Point getPosition()
	{
		return this.getLocation();
	}
	private EditFrame frame;
	private JTable table;
	private String[] columnNames;
	private Object[][] cells;
	
	private boolean runonce;
	private Container content;
	private MapPoint[][] map;
	private String mapname;
	private final static int  DEFAULT_WIDTH = 800;
	private final static int DEFAULT_HEIGHT = 600;
	private String soundfile;
}

class MapTableModel extends AbstractTableModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8218028121817314333L;
	public MapTableModel(Object[][] x,String[] y)
	{
		super();
		cells = x;
		columnNames = y;
	}
	
	public String getColumnNames(int c)
	{
		return columnNames[c];
	}
	public Class getColumnClass(int c)
	{
		return cells[0][c].getClass();
	}
	public int getColumnCount()
	{
		return cells[0].length;
	}
	public int getRowCount()
	{
		return cells.length;
	}
	public Object getValueAt(int r,int c)
	{
		return cells[r][c];
	}
	public void setValueAt(Object obj,int r,int c)
	{
		cells[r][c] = obj;
	}
	public boolean isCellEditable(int r,int c)
	{
		return true;
	}
	private Object[][] cells; 
	private String[] columnNames;
}