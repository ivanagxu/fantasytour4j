package com.ivan.game.itemcreater;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ivan.game.eventcreater.CreaterPanel;
import com.ivan.game.logger.GameLogger;
import com.ivan.game.unit.DatFilter;
import com.ivan.game.unit.GifFilter;
import com.ivan.game.unit.NumberTester;

public class ItemCreaterPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4973985267205512230L;

	public ItemCreaterPanel() {
		setBackground(Color.YELLOW);
		filenamelabel = new JLabel("文件名称:");
		itemnamelabel = new JLabel("物品名称:");
		itemtypelabel = new JLabel("物品类别:");
		oneofflabel = new JLabel("是否一次性:");
		acteventlabel = new JLabel("激活事件文件:");
		hplabel = new JLabel("回复HP值:");
		mplabel = new JLabel("回复MP值:");
		antidotallabel = new JLabel("是否解毒:");
		imagenamelabel = new JLabel("图标文件(默认为default.gif):");
		instructionlabel = new JLabel("物品说明(请双击修改):");
		sellablelabel = new JLabel("能否出售:");
		itempricelabel = new JLabel("物品价格(不能出售默认为0):");

		filenametext = new JTextField(10);
		itemnametext = new JTextField(10);
		itemtypecombo = new JComboBox();
		itemtypecombo.addItem("一般物品");
		itemtypecombo.addItem("战斗物品");
		itemtypecombo.addItem("事件物品");
		oneoffcombo = new JComboBox();
		oneoffcombo.addItem("是");
		oneoffcombo.addItem("否");
		acteventtext = new JTextField("双击打开文件", 10);
		acteventtext.setEditable(false);
		hptext = new JTextField(10);
		mptext = new JTextField(10);
		antidotalcombo = new JComboBox();
		antidotalcombo.addItem("否");
		antidotalcombo.addItem("是");
		imagenametext = new JTextField("双击打开文件", 10);
		imagenametext.setEditable(false);

		acteventtext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File(".//data//event//"));
					chooser.setFileFilter(new DatFilter());
					int result = chooser.showOpenDialog(ItemCreaterPanel.this);
					if (result == 0) {
						//String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						acteventtext.setText(path);
						actevent = path;
					}
				}
			}
		});

		imagenametext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File(".//data//images//"));
					chooser.setFileFilter(new GifFilter());
					int result = chooser.showOpenDialog(ItemCreaterPanel.this);
					if (result == 0) {
						filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						imagenametext.setText(path);
						imagename = path;
					}
				}
			}
		});
		instructiontext = new JTextField("效果如物品名称所说.");
		instructiontext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() > 1)
					instructiontext.setText("");
			}
		});
		sellablecombo = new JComboBox();
		sellablecombo.addItem("是");
		sellablecombo.addItem("否");

		itempricetext = new JTextField("0", 10);

		//添加保存事件
		JButton savebutton = new JButton("保存物品");
		savebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//保存
				save();
			}
		});
		//添加重置事件
		JButton resetbutton = new JButton("重置数据");
		resetbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//重置
				init();
			}
		});

		setLayout(new GridLayout(13, 2));
		add(filenamelabel);
		add(filenametext);

		add(itemnamelabel);
		add(itemnametext);

		add(itemtypelabel);
		add(itemtypecombo);

		add(oneofflabel);
		add(oneoffcombo);

		add(acteventlabel);
		add(acteventtext);

		add(hplabel);
		add(hptext);

		add(mplabel);
		add(mptext);

		add(antidotallabel);
		add(antidotalcombo);

		add(imagenamelabel);
		add(imagenametext);

		add(instructionlabel);
		add(instructiontext);

		add(sellablelabel);
		add(sellablecombo);

		add(itempricelabel);
		add(itempricetext);

		add(savebutton);
		add(resetbutton);

	}

	/*
	 * 初始化
	 */
	public void init() {
		track("初始化...");

		filename = "";
		itemname = "";
		itemtype = "";
		oneoff = "";
		actevent = "";
		hp = "";
		mp = "";
		antidotal = "";
		imagename = "";
		instruction = "";
		sellable = "";
		itemprice = "0";

		filenametext.setText(filename);
		itemnametext.setText(itemname);
		itemtypecombo.setSelectedIndex(0);
		oneoffcombo.setSelectedIndex(0);
		acteventtext.setText("双击打开文件");
		hptext.setText(hp);
		mptext.setText(mp);
		antidotalcombo.setSelectedIndex(0);
		imagenametext.setText("双击打开文件");
		instructiontext.setText("效果如物品名称所说");
		sellablecombo.setSelectedIndex(0);
		itempricetext.setText(itemprice);
	}

	/*
	 * 读文件
	 * @param f 要读的文件
	 */
	public void readFile(File f) {
		track("读取文件...");
		init();
		filename = f.getName();
		//String filepath = f.getPath();
		//openfile = new File(filepath);
		try {
			/*
			 FileInputStream in = new FileInputStream(openfile);
			 byte[] readbuff = new byte[(int)openfile.length()];
			 in.read(readbuff,0,(int)openfile.length());
			 String readData = new String(readbuff);
			 int i = 0;
			 while(readData.charAt(i) != '\n')
			 {
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 itemname += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 itemtype += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 oneoff += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 actevent += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 hp += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 mp += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 antidotal += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 imagename += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 instruction += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 sellable += readData.charAt(i);
			 i++;
			 }
			 i++;
			 itemprice = "";
			 while(readData.charAt(i) != '\n')
			 {
			 itemprice += readData.charAt(i);
			 i++;
			 }
			 in.close();
			 */

			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(f),"GBK"));

			filename = in.readLine();
			itemname = in.readLine();
			itemtype = in.readLine();
			oneoff = in.readLine();
			actevent = in.readLine();
			hp = in.readLine();
			mp = in.readLine();
			antidotal = in.readLine();
			imagename = in.readLine();
			instruction = in.readLine();
			sellable = in.readLine();
			itemprice = in.readLine();

			in.close();
			update();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 新建文件
	 */
	public void newFile() {
		track("等待建立新文件...");
		int result = JOptionPane.showConfirmDialog(ItemCreaterPanel.this,
				"是否保存当前事件?", "等待确认", JOptionPane.YES_NO_CANCEL_OPTION);
		//System.out.println(result);
		if (result == 0) {
			save();
			init();
		} else if (result == 1) {
			init();
		} else if (result == 2) {
			return;
		}
	}

	public boolean check() {
		boolean error = false;
		track("处理数据...");
		String errormsg = "";

		filename = filenametext.getText();
		itemname = itemnametext.getText();
		itemtype = (String) itemtypecombo.getSelectedItem();
		oneoff = (String) oneoffcombo.getSelectedItem();
		actevent = acteventtext.getText();
		hp = hptext.getText();
		mp = mptext.getText();
		antidotal = (String) antidotalcombo.getSelectedItem();
		imagename = imagenametext.getText();
		instruction = instructiontext.getText();
		sellable = (String) sellablecombo.getSelectedItem();
		if (sellable.equals("是"))
			itemprice = itempricetext.getText();
		else
			itemprice = "0";

		if (hp.length() == 0)
			hp = "0";
		if (mp.length() == 0)
			mp = "0";

		if (actevent.equals("双击打开文件") || actevent.length() == 0)
			actevent = "data/default.dat";
		if (imagename.equals("双击打开文件"))
			imagename = "";

		if (filename.length() == 0) {
			errormsg += "请输入文件名\n";
			error = true;
		}
		if (filename.length() > 0 && !filename.endsWith(".dat")) {
			errormsg += "文件名格式不对(.dat)\n";
			error = true;
		}
		if (itemname.length() == 0) {
			errormsg += "请输入物品名\n";
			error = true;
		}
		if (actevent.length() != 0 && !actevent.endsWith(".dat")) {
			errormsg += "激活事件格式不对(.dat)\n";
			error = true;
		}
		NumberTester tester = new NumberTester();
		if (hp.length() != 0 && !tester.test(hp)) {
			errormsg += "回复HP请输入整数\n";
			error = true;
		}
		if (mp.length() != 0 && !tester.test(mp)) {
			errormsg += "回复MP请输入整数\n";
			error = true;
		}
		if (imagename.length() == 0) {
			errormsg += "物品图标自动设置为defaultitem.gif\n";
			imagename = "data/images/defaultitem.gif";
		}
		if (!imagename.endsWith(".gif")) {
			errormsg += "地图文件格式不对(.gif)\n";
			error = true;
		}
		if (instruction.length() == 0) {
			errormsg += "请输入物品说明\n";
			error = true;
		}
		if (!tester.test(itemprice)) {
			errormsg += "物品价格该为整数\n";
			error = true;
		}
		if (error) {
			JOptionPane.showMessageDialog(ItemCreaterPanel.this, errormsg,
					"错误", JOptionPane.ERROR_MESSAGE);
			return error;
		}
		return error;
	}

	/*
	 * 保存工作
	 */
	public void save() {
		if (check()) {
			return;
		}

		track("保存文件...");
		String saveData = "";
		savefile = new File("data/item/" + filename);
		int save = 0;
		if(savefile.exists())
		{
			save = JOptionPane.showConfirmDialog(ItemCreaterPanel.this,
				"此物品已经存在,是否覆盖?", "等待确认", JOptionPane.YES_NO_CANCEL_OPTION);
		}
		if (save == 0) {
			
		saveData = filename
				+ "\n"
				+ itemname
				+ "\n"
				+ itemtype
				+ "\n"
				+ oneoff
				+ "\n"
				+ actevent
				+ "\n"
				+ hp
				+ "\n"
				+ mp
				+ "\n"
				+ antidotal
				+ "\n"
				+ imagename
				+ "\n"
				+ instruction
				+ "\n"
				+ sellable
				+ "\n"
				+ itemprice
				+ "\n"
				+ "                                                                 ";
		try {
			FileOutputStream out = new FileOutputStream(savefile);
			out.write(saveData.getBytes("GBK"), 0, saveData.length());
			out.close();
			JOptionPane.showMessageDialog(ItemCreaterPanel.this, "文件保存成功",
					"保存", JOptionPane.INFORMATION_MESSAGE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}

	}

	/*
	 * 更新控件
	 */
	public void update() {
		track("更新控件...");

		filenametext.setText(filename);
		itemnametext.setText(itemname);
		if (actevent.length() > 0)
			acteventtext.setText(actevent);
		else
			acteventtext.setText("双击打开文件");
		hptext.setText(hp);
		mptext.setText(mp);
		imagenametext.setText(imagename);

		if (oneoff.equals("是"))
			oneoffcombo.setSelectedIndex(0);
		else
			oneoffcombo.setSelectedIndex(1);

		if (antidotal.equals("否"))
			antidotalcombo.setSelectedIndex(0);
		else
			antidotalcombo.setSelectedIndex(1);

		if (itemtype.equals("一般物品"))
			itemtypecombo.setSelectedIndex(0);
		else if (itemtype.equals("战斗物品"))
			itemtypecombo.setSelectedIndex(1);
		else if (itemtype.equals("事件物品"))
			itemtypecombo.setSelectedIndex(2);

		if (instruction.length() == 0)
			instructiontext.setText("效果如物品名称所说");
		else
			instructiontext.setText(instruction);

		if (sellable.equals("是"))
			sellablecombo.setSelectedIndex(0);
		else
			sellablecombo.setSelectedIndex(1);

		itempricetext.setText(itemprice);
	}

	/*
	 * 跟踪器
	 * @param s 控制台输出信息
	 */
	public void track(String s) {
		GameLogger.logger.info("ItemCreater|" + s);
	}

	private String filename;

	private String itemname;

	private String itemtype;

	private String oneoff;

	private String actevent;

	private String hp;

	private String mp;

	private String antidotal;

	private String imagename;

	private String instruction;

	private String sellable;

	private String itemprice;

	private JTextField filenametext;

	private JTextField itemnametext;

	private JComboBox itemtypecombo;

	private JComboBox oneoffcombo;

	private JTextField acteventtext;

	private JTextField hptext;

	private JTextField mptext;

	private JComboBox antidotalcombo;

	private JTextField imagenametext;

	private JTextField instructiontext;

	private JComboBox sellablecombo;

	private JTextField itempricetext;

	private JLabel filenamelabel;

	private JLabel itemnamelabel;

	private JLabel itemtypelabel;

	private JLabel oneofflabel;

	private JLabel acteventlabel;

	private JLabel hplabel;

	private JLabel mplabel;

	private JLabel antidotallabel;

	private JLabel imagenamelabel;

	private JLabel instructionlabel;

	private JLabel itempricelabel;

	private JLabel sellablelabel;

	//private File openfile;
	private File savefile;

}
