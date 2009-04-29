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
		add(new JLabel("Npc����:"));
		add(nametext = new JTextField(""));

		add(new JLabel("Npc���:"));
		npctypecombo = new JComboBox();
		npctypecombo.addItem("��Ϣ");
		npctypecombo.addItem("����");
		npctypecombo.addItem("����");
		add(npctypecombo);

		add(new JLabel("Npc��Ϣ:"));
		msgtext = new JTextField();
		add(msgtext);

		add(new JLabel("Npc�����Ϣ:"));
		add(lmsgtext = new JTextField(""));

		add(new JLabel("Npc�¼�:"));
		add(eventtext = new JTextField("˫�����ļ�"));
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

		add(frontimagelabel = new JLabel("������ͼ:"));
		add(imagefronttext = new JTextField("˫�����ļ�"));
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
							track("����ͼƬ��ӳɹ�");
						}

					}
				}
			}
		});

		add(backimagelabel = new JLabel("������ͼ:"));
		add(imagebacktext = new JTextField("˫�����ļ�"));
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
							track("����ͼƬ��ӳɹ�");
						}

					}
				}
			}
		});

		add(leftimagelabel = new JLabel("������ͼ:"));
		add(imagelefttext = new JTextField("˫�����ļ�"));
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
							track("����ͼƬ��ӳɹ�");
						}

					}
				}
			}
		});

		add(rightimagelabel = new JLabel("������ͼ:"));
		add(imagerighttext = new JTextField("˫�����ļ�"));
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
							track("����ͼƬ��ӳɹ�");
						}

					}
				}
			}
		});

		add(new JLabel("�ɷ���:"));
		leaveablecombo = new JComboBox();
		leaveablecombo.addItem("��");
		leaveablecombo.addItem("��");
		add(leaveablecombo);

		add(new JLabel("��������:"));
		add(leavemsgtext = new JTextField(""));

		add(new JLabel("������Ҫ��Ʒ:"));
		add(leaveitemtext = new JTextField("˫�����ļ�"));
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

		add(new JLabel("������Ҫ�¼�:"));
		add(leaveeventtext = new JTextField("˫�����ļ�"));
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

		JButton additembutton = new JButton("��ӳ�����Ʒ:");
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

		JButton deleteitembutton = new JButton("ɾ���������Ʒ");
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
		eventtext.setText("˫�����ļ�");
		imagefronttext.setText("˫�����ļ�");
		imagebacktext.setText("˫�����ļ�");
		imagelefttext.setText("˫�����ļ�");
		imagerighttext.setText("˫�����ļ�");
		leaveablecombo.setSelectedIndex(0);
		leavemsgtext.setText(leavemsg);
		leaveitemtext.setText("˫�����ļ�");
		leaveeventtext.setText("˫�����ļ�");
		sellitemcombo.removeAllItems();
	}

	public void readFile(File f) {
		track("��ȡ�ļ�...");
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
		track("���¿ؼ�...");

		frontimagelabel.setIcon(new ImageIcon(imagename[0]));
		backimagelabel.setIcon(new ImageIcon(imagename[1]));
		leftimagelabel.setIcon(new ImageIcon(imagename[2]));
		rightimagelabel.setIcon(new ImageIcon(imagename[3]));

		nametext.setText(name);
		if (npctype.equals("��Ϣ"))
			npctypecombo.setSelectedIndex(0);
		else if (npctype.equals("����"))
			npctypecombo.setSelectedIndex(1);
		else if (npctype.equals("����"))
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
		if (leaveable.equals("��"))
			leaveablecombo.setSelectedIndex(0);
		else if (leaveable.equals("��"))
			leaveablecombo.setSelectedIndex(1);
		leavemsgtext.setText(leavemsg);
		leaveitemtext.setText(leaveitem);
		leaveeventtext.setText(leaveevent);
		for (int i = 0; i < sellitemlist.size(); i++) {
			sellitemcombo.addItem((String) sellitemlist.get(i));
		}
	}

	public void newFile() {
		track("�ȴ��������ļ�...");
		int result = JOptionPane.showConfirmDialog(NpcCreaterPanel.this,
				"�Ƿ񱣴浱ǰNpc?", "�ȴ�ȷ��", JOptionPane.YES_NO_CANCEL_OPTION);
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
		track("�������...");

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
		if (event.equals("˫�����ļ�") || event.length() == 0) {
			event = "default.dat";
		}
		if (leaveitem.equals("˫�����ļ�") || leaveitem.length() == 0) {
			leaveitem = "default.dat";
		}
		if (leaveevent.equals("˫�����ļ�") || leaveevent.length() == 0) {
			leaveevent = "default.dat";
		}
		if (leavemsg.length() == 0) {
			leavemsg = "�ټ�.";
		}

		if (name.length() == 0) {
			errormsg += "������Npc����\n";
			error = true;
		}
		if (imagename[0].length() == 0) {
			errormsg += "�����������ͼ\n";
			error = true;
		}
		if (imagename[1].length() == 0) {
			errormsg += "����ӱ�����ͼ\n";
			error = true;
		}
		if (imagename[2].length() == 0) {
			errormsg += "�����������ͼ\n";
			error = true;
		}
		if (imagename[3].length() == 0) {
			errormsg += "�����������ͼ\n";
			error = true;
		}
		if (npctype.equals("����")) {
			if (sellitemlist.size() == 0) {
				errormsg += "ȱ�ٳ�����Ʒ\n";
				error = true;
			}
		}
		if (error) {
			JOptionPane.showMessageDialog(NpcCreaterPanel.this, errormsg, "����",
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
							"��NPC�Ѿ�����,�Ƿ񸲸�?", "�ȴ�ȷ��",
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
						track("����ɹ�...");
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
