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
		filenamelabel = new JLabel("�ļ�����:");
		itemnamelabel = new JLabel("��Ʒ����:");
		itemtypelabel = new JLabel("��Ʒ���:");
		oneofflabel = new JLabel("�Ƿ�һ����:");
		acteventlabel = new JLabel("�����¼��ļ�:");
		hplabel = new JLabel("�ظ�HPֵ:");
		mplabel = new JLabel("�ظ�MPֵ:");
		antidotallabel = new JLabel("�Ƿ�ⶾ:");
		imagenamelabel = new JLabel("ͼ���ļ�(Ĭ��Ϊdefault.gif):");
		instructionlabel = new JLabel("��Ʒ˵��(��˫���޸�):");
		sellablelabel = new JLabel("�ܷ����:");
		itempricelabel = new JLabel("��Ʒ�۸�(���ܳ���Ĭ��Ϊ0):");

		filenametext = new JTextField(10);
		itemnametext = new JTextField(10);
		itemtypecombo = new JComboBox();
		itemtypecombo.addItem("һ����Ʒ");
		itemtypecombo.addItem("ս����Ʒ");
		itemtypecombo.addItem("�¼���Ʒ");
		oneoffcombo = new JComboBox();
		oneoffcombo.addItem("��");
		oneoffcombo.addItem("��");
		acteventtext = new JTextField("˫�����ļ�", 10);
		acteventtext.setEditable(false);
		hptext = new JTextField(10);
		mptext = new JTextField(10);
		antidotalcombo = new JComboBox();
		antidotalcombo.addItem("��");
		antidotalcombo.addItem("��");
		imagenametext = new JTextField("˫�����ļ�", 10);
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
		instructiontext = new JTextField("Ч������Ʒ������˵.");
		instructiontext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() > 1)
					instructiontext.setText("");
			}
		});
		sellablecombo = new JComboBox();
		sellablecombo.addItem("��");
		sellablecombo.addItem("��");

		itempricetext = new JTextField("0", 10);

		//��ӱ����¼�
		JButton savebutton = new JButton("������Ʒ");
		savebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//����
				save();
			}
		});
		//��������¼�
		JButton resetbutton = new JButton("��������");
		resetbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//����
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
	 * ��ʼ��
	 */
	public void init() {
		track("��ʼ��...");

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
		acteventtext.setText("˫�����ļ�");
		hptext.setText(hp);
		mptext.setText(mp);
		antidotalcombo.setSelectedIndex(0);
		imagenametext.setText("˫�����ļ�");
		instructiontext.setText("Ч������Ʒ������˵");
		sellablecombo.setSelectedIndex(0);
		itempricetext.setText(itemprice);
	}

	/*
	 * ���ļ�
	 * @param f Ҫ�����ļ�
	 */
	public void readFile(File f) {
		track("��ȡ�ļ�...");
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
	 * �½��ļ�
	 */
	public void newFile() {
		track("�ȴ��������ļ�...");
		int result = JOptionPane.showConfirmDialog(ItemCreaterPanel.this,
				"�Ƿ񱣴浱ǰ�¼�?", "�ȴ�ȷ��", JOptionPane.YES_NO_CANCEL_OPTION);
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
		track("��������...");
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
		if (sellable.equals("��"))
			itemprice = itempricetext.getText();
		else
			itemprice = "0";

		if (hp.length() == 0)
			hp = "0";
		if (mp.length() == 0)
			mp = "0";

		if (actevent.equals("˫�����ļ�") || actevent.length() == 0)
			actevent = "data/default.dat";
		if (imagename.equals("˫�����ļ�"))
			imagename = "";

		if (filename.length() == 0) {
			errormsg += "�������ļ���\n";
			error = true;
		}
		if (filename.length() > 0 && !filename.endsWith(".dat")) {
			errormsg += "�ļ�����ʽ����(.dat)\n";
			error = true;
		}
		if (itemname.length() == 0) {
			errormsg += "��������Ʒ��\n";
			error = true;
		}
		if (actevent.length() != 0 && !actevent.endsWith(".dat")) {
			errormsg += "�����¼���ʽ����(.dat)\n";
			error = true;
		}
		NumberTester tester = new NumberTester();
		if (hp.length() != 0 && !tester.test(hp)) {
			errormsg += "�ظ�HP����������\n";
			error = true;
		}
		if (mp.length() != 0 && !tester.test(mp)) {
			errormsg += "�ظ�MP����������\n";
			error = true;
		}
		if (imagename.length() == 0) {
			errormsg += "��Ʒͼ���Զ�����Ϊdefaultitem.gif\n";
			imagename = "data/images/defaultitem.gif";
		}
		if (!imagename.endsWith(".gif")) {
			errormsg += "��ͼ�ļ���ʽ����(.gif)\n";
			error = true;
		}
		if (instruction.length() == 0) {
			errormsg += "��������Ʒ˵��\n";
			error = true;
		}
		if (!tester.test(itemprice)) {
			errormsg += "��Ʒ�۸��Ϊ����\n";
			error = true;
		}
		if (error) {
			JOptionPane.showMessageDialog(ItemCreaterPanel.this, errormsg,
					"����", JOptionPane.ERROR_MESSAGE);
			return error;
		}
		return error;
	}

	/*
	 * ���湤��
	 */
	public void save() {
		if (check()) {
			return;
		}

		track("�����ļ�...");
		String saveData = "";
		savefile = new File("data/item/" + filename);
		int save = 0;
		if(savefile.exists())
		{
			save = JOptionPane.showConfirmDialog(ItemCreaterPanel.this,
				"����Ʒ�Ѿ�����,�Ƿ񸲸�?", "�ȴ�ȷ��", JOptionPane.YES_NO_CANCEL_OPTION);
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
			JOptionPane.showMessageDialog(ItemCreaterPanel.this, "�ļ�����ɹ�",
					"����", JOptionPane.INFORMATION_MESSAGE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}

	}

	/*
	 * ���¿ؼ�
	 */
	public void update() {
		track("���¿ؼ�...");

		filenametext.setText(filename);
		itemnametext.setText(itemname);
		if (actevent.length() > 0)
			acteventtext.setText(actevent);
		else
			acteventtext.setText("˫�����ļ�");
		hptext.setText(hp);
		mptext.setText(mp);
		imagenametext.setText(imagename);

		if (oneoff.equals("��"))
			oneoffcombo.setSelectedIndex(0);
		else
			oneoffcombo.setSelectedIndex(1);

		if (antidotal.equals("��"))
			antidotalcombo.setSelectedIndex(0);
		else
			antidotalcombo.setSelectedIndex(1);

		if (itemtype.equals("һ����Ʒ"))
			itemtypecombo.setSelectedIndex(0);
		else if (itemtype.equals("ս����Ʒ"))
			itemtypecombo.setSelectedIndex(1);
		else if (itemtype.equals("�¼���Ʒ"))
			itemtypecombo.setSelectedIndex(2);

		if (instruction.length() == 0)
			instructiontext.setText("Ч������Ʒ������˵");
		else
			instructiontext.setText(instruction);

		if (sellable.equals("��"))
			sellablecombo.setSelectedIndex(0);
		else
			sellablecombo.setSelectedIndex(1);

		itempricetext.setText(itemprice);
	}

	/*
	 * ������
	 * @param s ����̨�����Ϣ
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
