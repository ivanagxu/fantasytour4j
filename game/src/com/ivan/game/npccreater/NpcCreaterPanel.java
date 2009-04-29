package com.ivan.game.npccreater;

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
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ivan.game.magiccreater.MagicCreaterPanel;
import com.ivan.game.unit.DatFilter;
import com.ivan.game.unit.GifFilter;

public class NpcCreaterPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8732746769165916470L;

	public NpcCreaterPanel() {
		setBackground(Color.ORANGE);
		imagename = new String[4];
		//images = new Image[4];
		sellitemlist = new ArrayList();

		setLayout(new GridLayout(15, 2));
		add(new JLabel("Npc名称:"));
		add(nametext = new JTextField(""));

		add(new JLabel("Npc类别:"));
		npctypecombo = new JComboBox();
		npctypecombo.addItem("信息");
		npctypecombo.addItem("治疗");
		npctypecombo.addItem("出售");
		add(npctypecombo);

		add(new JLabel("Npc消息:"));
		msgtext = new JTextField();
		add(msgtext);

		add(new JLabel("Npc最后消息:"));
		add(lmsgtext = new JTextField(""));

		add(new JLabel("Npc事件:"));
		add(eventtext = new JTextField("双击打开文件"));
		eventtext.setEditable(false);
		eventtext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File(".//data//event//"));
					chooser.setFileFilter(new DatFilter());
					int result = chooser.showOpenDialog(NpcCreaterPanel.this);
					if (result == 0) {
						//String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						eventtext.setText(path);
						event = path;
					}
				}
			}
		});

		add(frontimagelabel = new JLabel("正面贴图:"));
		add(imagefronttext = new JTextField("双击打开文件"));
		imagefronttext.setEditable(false);
		imagefronttext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File(".//data//images//"));
					chooser.setFileFilter(new GifFilter());
					int result = chooser.showOpenDialog(NpcCreaterPanel.this);
					if (result == 0) {
						String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						ImageIcon image = new ImageIcon(filepath);
						if (image != null) {
							frontimagelabel.setIcon(image);
							imagefronttext.setText(filename);
							imagename[0] = path;
							track("正面图片添加成功");
						}

					}
				}
			}
		});

		add(backimagelabel = new JLabel("背面贴图:"));
		add(imagebacktext = new JTextField("双击打开文件"));
		imagebacktext.setEditable(false);
		imagebacktext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File(".//data//images//"));
					chooser.setFileFilter(new GifFilter());
					int result = chooser.showOpenDialog(NpcCreaterPanel.this);
					if (result == 0) {
						String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						ImageIcon image = new ImageIcon(filepath);
						if (image != null) {
							backimagelabel.setIcon(image);
							imagebacktext.setText(filename);
							imagename[1] = path;
							track("背面图片添加成功");
						}

					}
				}
			}
		});

		add(leftimagelabel = new JLabel("左面贴图:"));
		add(imagelefttext = new JTextField("双击打开文件"));
		imagelefttext.setEditable(false);
		imagelefttext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File(".//data//images//"));
					chooser.setFileFilter(new GifFilter());
					int result = chooser.showOpenDialog(NpcCreaterPanel.this);
					if (result == 0) {
						String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						ImageIcon image = new ImageIcon(filepath);
						if (image != null) {
							leftimagelabel.setIcon(image);
							imagelefttext.setText(filename);
							imagename[2] = path;
							track("左面图片添加成功");
						}

					}
				}
			}
		});

		add(rightimagelabel = new JLabel("右面贴图:"));
		add(imagerighttext = new JTextField("双击打开文件"));
		imagerighttext.setEditable(false);
		imagerighttext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File(".//data//images//"));
					chooser.setFileFilter(new GifFilter());
					int result = chooser.showOpenDialog(NpcCreaterPanel.this);
					if (result == 0) {
						String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						ImageIcon image = new ImageIcon(filepath);
						if (image != null) {
							rightimagelabel.setIcon(image);
							imagerighttext.setText(filename);
							imagename[3] = path;
							track("右面图片添加成功");
						}

					}
				}
			}
		});

		add(new JLabel("可否撤离:"));
		leaveablecombo = new JComboBox();
		leaveablecombo.addItem("否");
		leaveablecombo.addItem("是");
		add(leaveablecombo);

		add(new JLabel("撤离留言:"));
		add(leavemsgtext = new JTextField(""));

		add(new JLabel("撤离需要物品:"));
		add(leaveitemtext = new JTextField("双击打开文件"));
		leaveitemtext.setEditable(false);
		leaveitemtext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File(".//data//item//"));
					chooser.setFileFilter(new DatFilter());
					int result = chooser.showOpenDialog(NpcCreaterPanel.this);
					if (result == 0) {
						//String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						leaveitemtext.setText(path);
						leaveitem = path;
					}
				}
			}
		});

		add(new JLabel("撤离需要事件:"));
		add(leaveeventtext = new JTextField("双击打开文件"));
		leaveeventtext.setEditable(false);
		leaveeventtext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File(".//data//event//"));
					chooser.setFileFilter(new DatFilter());
					int result = chooser.showOpenDialog(NpcCreaterPanel.this);
					if (result == 0) {
						//String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						leaveeventtext.setText(path);
						leaveevent = path;
					}
				}
			}
		});

		JButton additembutton = new JButton("添加出售物品:");
		additembutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("./data/item/"));
				chooser.setFileFilter(new DatFilter());
				int result = chooser.showOpenDialog(NpcCreaterPanel.this);
				if (result == 0) {
					//String filename = chooser.getSelectedFile().getName();
					String filepath = chooser.getSelectedFile().getPath();
					String path = filepath.substring(filepath
							.lastIndexOf("data"));
					sellitemlist.add(path);
					sellitemcombo.addItem(path);
				}
			}
		});
		add(additembutton);
		sellitemcombo = new JComboBox();
		add(sellitemcombo);

		JButton deleteitembutton = new JButton("删除已添加物品");
		deleteitembutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sellitemlist.clear();
				sellitemcombo.removeAllItems();
			}
		});
		add(deleteitembutton);

		init();
	}

	public void init() {
		name = "";
		npctype = "";
		msg = "";
		lmsg = "";
		event = "";
		imagename[0] = "";
		imagename[1] = "";
		imagename[2] = "";
		imagename[3] = "";
		//images[0] = null;
		leaveable = "";
		leavemsg = "";
		leaveitem = "";
		leaveevent = "";
		sellitemlist.clear();

		frontimagelabel.setIcon(null);
		backimagelabel.setIcon(null);
		leftimagelabel.setIcon(null);
		rightimagelabel.setIcon(null);

		nametext.setText(name);
		npctypecombo.setSelectedIndex(0);
		msgtext.setText(msg);
		lmsgtext.setText(lmsg);
		eventtext.setText("双击打开文件");
		imagefronttext.setText("双击打开文件");
		imagebacktext.setText("双击打开文件");
		imagelefttext.setText("双击打开文件");
		imagerighttext.setText("双击打开文件");
		leaveablecombo.setSelectedIndex(0);
		leavemsgtext.setText(leavemsg);
		leaveitemtext.setText("双击打开文件");
		leaveeventtext.setText("双击打开文件");
		sellitemcombo.removeAllItems();
	}

	public void readFile(File f) {
		track("读取文件...");
		init();
		try {
			//FileInputStream in = new FileInputStream(f);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(f),"GBK"));

			name = in.readLine();
			npctype = in.readLine();
			msg = in.readLine();
			lmsg = in.readLine();
			event = in.readLine();
			imagename[0] = in.readLine();
			imagename[1] = in.readLine();
			imagename[2] = in.readLine();
			imagename[3] = in.readLine();
			leaveable = in.readLine();
			leavemsg = in.readLine();
			leaveitem = in.readLine();
			leaveevent = in.readLine();
			String temp = in.readLine();
			while (!temp.equals("#")) {
				sellitemlist.add(temp);
				track("add item!");
				temp = in.readLine();
			}
			in.close();
			update();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		track("更新控件...");

		frontimagelabel.setIcon(new ImageIcon(imagename[0]));
		backimagelabel.setIcon(new ImageIcon(imagename[1]));
		leftimagelabel.setIcon(new ImageIcon(imagename[2]));
		rightimagelabel.setIcon(new ImageIcon(imagename[3]));

		nametext.setText(name);
		if (npctype.equals("信息"))
			npctypecombo.setSelectedIndex(0);
		else if (npctype.equals("治疗"))
			npctypecombo.setSelectedIndex(1);
		else if (npctype.equals("出售"))
			npctypecombo.setSelectedIndex(2);
		msgtext.setText(msg);
		lmsgtext.setText(lmsg);
		eventtext.setText(event);
		imagefronttext.setText(imagename[0].substring(imagename[0]
				.lastIndexOf("images")));
		imagebacktext.setText(imagename[1].substring(imagename[1]
				.lastIndexOf("images")));
		imagelefttext.setText(imagename[2].substring(imagename[2]
				.lastIndexOf("images")));
		imagerighttext.setText(imagename[3].substring(imagename[3]
				.lastIndexOf("images")));
		if (leaveable.equals("否"))
			leaveablecombo.setSelectedIndex(0);
		else if (leaveable.equals("是"))
			leaveablecombo.setSelectedIndex(1);
		leavemsgtext.setText(leavemsg);
		leaveitemtext.setText(leaveitem);
		leaveeventtext.setText(leaveevent);
		for (int i = 0; i < sellitemlist.size(); i++) {
			sellitemcombo.addItem((String) sellitemlist.get(i));
		}
	}

	public void newFile() {
		track("等待建立新文件...");
		int result = JOptionPane.showConfirmDialog(NpcCreaterPanel.this,
				"是否保存当前Npc?", "等待确认", JOptionPane.YES_NO_CANCEL_OPTION);
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

	public boolean checkfalse() {
		track("检测数据...");

		boolean error = false;
		String errormsg = "";

		name = nametext.getText();
		npctype = (String) npctypecombo.getSelectedItem();
		msg = msgtext.getText();
		lmsg = lmsgtext.getText();
		event = eventtext.getText();
		//imagename[0] = "";
		//imagename[1] = "";
		//imagename[2] = "";
		//imagename[3] = "";
		leaveable = (String) leaveablecombo.getSelectedItem();
		leavemsg = leavemsgtext.getText();
		leaveitem = leaveitemtext.getText();
		leaveevent = leaveeventtext.getText();

		if (msg.length() == 0) {
			msg = "...";
		}
		if (lmsg.length() == 0) {
			lmsg = "...";
		}
		if (event.equals("双击打开文件") || event.length() == 0) {
			event = "default.dat";
		}
		if (leaveitem.equals("双击打开文件") || leaveitem.length() == 0) {
			leaveitem = "default.dat";
		}
		if (leaveevent.equals("双击打开文件") || leaveevent.length() == 0) {
			leaveevent = "default.dat";
		}
		if (leavemsg.length() == 0) {
			leavemsg = "再见.";
		}

		if (name.length() == 0) {
			errormsg += "请输入Npc名称\n";
			error = true;
		}
		if (imagename[0].length() == 0) {
			errormsg += "请添加正面贴图\n";
			error = true;
		}
		if (imagename[1].length() == 0) {
			errormsg += "请添加背面贴图\n";
			error = true;
		}
		if (imagename[2].length() == 0) {
			errormsg += "请添加左面贴图\n";
			error = true;
		}
		if (imagename[3].length() == 0) {
			errormsg += "请添加右面贴图\n";
			error = true;
		}
		if (npctype.equals("出售")) {
			if (sellitemlist.size() == 0) {
				errormsg += "缺少出售物品\n";
				error = true;
			}
		}
		if (error) {
			JOptionPane.showMessageDialog(NpcCreaterPanel.this, errormsg, "错误",
					JOptionPane.ERROR_MESSAGE);
			return error;
		}

		return error;
	}

	public void save() {
		if (!checkfalse()) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(".//data//npc//"));
			chooser.setFileFilter(new DatFilter());
			int result = chooser.showSaveDialog(NpcCreaterPanel.this);
			if (result == 0) {
				String filename = chooser.getSelectedFile().getName();
				String filepath = chooser.getSelectedFile().getPath();
				if (!filename.endsWith(".dat"))
					filename += ".dat";
				if (!filepath.endsWith(".dat"))
					filepath += ".dat";
				File f = new File(filepath);
				int save = 0;
				if (f.exists()) {
					save = JOptionPane.showConfirmDialog(NpcCreaterPanel.this,
							"此NPC已经存在,是否覆盖?", "等待确认",
							JOptionPane.YES_NO_CANCEL_OPTION);
				}
				if (save == 0) {
					String saveData = "";
					saveData = name + "\n" + npctype + "\n" + msg + "\n" + lmsg
							+ "\n" + event + "\n" + imagename[0] + "\n"
							+ imagename[1] + "\n" + imagename[2] + "\n"
							+ imagename[3] + "\n" + leaveable + "\n" + leavemsg
							+ "\n" + leaveitem + "\n" + leaveevent + "\n";
					for (int i = 0; i < sellitemlist.size(); i++) {
						saveData += (String) sellitemlist.get(i);
						saveData += "\n";
					}
					saveData += "#";
					saveData += "\n";
					saveData += "                                                                ";
					try {
						FileOutputStream out = new FileOutputStream(f);
						out.write(saveData.getBytes("GBK"), 0, saveData.length());
						out.close();
						track("保存成功...");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void track(String s) {
		System.out.println(s);
	}

	private String name;

	private String npctype;

	private String msg;

	private String lmsg;

	private String event;

	private String[] imagename;

	//private Image[] images;
	private String leaveable;

	private String leavemsg;

	private String leaveitem;

	private String leaveevent;

	private ArrayList sellitemlist;

	private JLabel frontimagelabel;

	private JLabel backimagelabel;

	private JLabel leftimagelabel;

	private JLabel rightimagelabel;

	private JTextField nametext;

	private JComboBox npctypecombo;

	private JTextField msgtext;

	private JTextField lmsgtext;

	private JTextField eventtext;

	private JTextField imagefronttext;

	private JTextField imagebacktext;

	private JTextField imagelefttext;

	private JTextField imagerighttext;

	private JComboBox leaveablecombo;

	private JTextField leavemsgtext;

	private JTextField leaveitemtext;

	private JTextField leaveeventtext;

	private JComboBox sellitemcombo;

}
