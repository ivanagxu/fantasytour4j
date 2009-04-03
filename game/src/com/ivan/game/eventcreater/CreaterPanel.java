package com.ivan.game.eventcreater;

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

import com.ivan.game.itemcreater.ItemCreaterPanel;
import com.ivan.game.logger.GameLogger;
import com.ivan.game.mapcreater.MapCreaterFrame;
import com.ivan.game.unit.DatFilter;
import com.ivan.game.unit.NumberTester;

public class CreaterPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3781918923414002919L;

	public CreaterPanel() {
		this.setBackground(Color.CYAN);

		//�¼�����
		JLabel filenamelabel = new JLabel("�ļ���:");
		JLabel eventnamelabel = new JLabel("�¼���:");
		JLabel eventinfolabel = new JLabel("�¼�����:");
		//�¼���������
		JLabel needeventlabel = new JLabel("ǰ���¼��ļ�:");
		JLabel followeventlabel = new JLabel("�����¼��ļ�");
		JLabel needitemlabel = new JLabel("��Ҫ��Ʒ�ļ�:");
		//�¼�����
		JLabel eventtypelabel = new JLabel("�¼�����:");
		//�¼��������
		JLabel enemynamelabel = new JLabel("�����ļ�:");
		JLabel itemnamelabel = new JLabel("��Ʒ�ļ�:");
		JLabel moneylabel = new JLabel("��Ǯ����");
		JLabel targetmaplabel = new JLabel("Ŀ���ͼ�ļ�:");
		JLabel targetxlabel = new JLabel("����X:");
		JLabel targetylabel = new JLabel("����Y:");
		JLabel magiclabel = new JLabel("ϰ�ü���:");

		filenametext = new JTextField(10);
		eventnametext = new JTextField(10);
		eventinfotext = new JTextField(10);

		needeventtext = new JTextField("˫�����ļ�", 10);
		needeventtext.setEditable(false);
		//���"ǰ���¼�"�ı���˫���¼�
		needeventtext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("./data/event/"));
					chooser.setFileFilter(new DatFilter());
					int result = chooser.showOpenDialog(CreaterPanel.this);
					if (result == 0) {
						//String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						needeventtext.setText(path);
						needevent = path;
					}
				}
			}
		});

		needitemtext = new JTextField("˫�����ļ�", 10);
		needitemtext.setEditable(false);
		//���"��Ҫ��Ʒ"�ı���˫���¼�
		needitemtext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("./data/item/"));
					chooser.setFileFilter(new DatFilter());
					int result = chooser.showOpenDialog(CreaterPanel.this);
					if (result == 0) {
						//String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						needitemtext.setText(path);
						needitem = path;
					}
				}
			}
		});
		followeventtext = new JTextField("˫�����ļ�", 10);
		followeventtext.setEditable(false);
		//���"��Ҫ��Ʒ"�ı���˫���¼�
		followeventtext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("./data/event/"));
					chooser.setFileFilter(new DatFilter());
					int result = chooser.showOpenDialog(CreaterPanel.this);
					if (result == 0) {
						//String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						followeventtext.setText(path);
						followevent = path;
					}
				}
			}
		});

		eventtypecombo = new JComboBox();
		//���"�¼�����"��Ͽ�ı��¼�
		/*
		 eventtypecombo.addActionListener(new
		 ActionListener()
		 {
		 public void actionPerformed(ActionEvent event)
		 {
		 //eventtype = (String)eventtypecombo.getSelectedItem();
		 //System.out.println(
		 //"ѡ����¼�������:" + 
		 //(String)eventtypecombo.getSelectedItem());
		 }
		 });
		 */
		eventtypecombo.addItem("�����¼�");
		eventtypecombo.addItem("ս���¼�");
		eventtypecombo.addItem("��Ʒ�¼�");
		eventtypecombo.addItem("��Ǯ�¼�");
		eventtypecombo.addItem("ѧϰ�¼�");

		enemynametext = new JTextField("˫�����ļ�", 10);
		enemynametext.setEditable(false);
		enemynametext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("./data/enemy/"));
					chooser.setFileFilter(new DatFilter());
					int result = chooser.showOpenDialog(CreaterPanel.this);
					if (result == 0) {
						//String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						enemynametext.setText(path);
						enemyname = path;
					}
				}
			}
		});
		itemnametext = new JTextField("˫�����ļ�", 10);
		itemnametext.setEditable(false);
		itemnametext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("./data/item/"));
					chooser.setFileFilter(new DatFilter());
					int result = chooser.showOpenDialog(CreaterPanel.this);
					if (result == 0) {
						//String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						itemnametext.setText(path);
						itemname = path;
					}
				}
			}
		});

		moneytext = new JTextField("0", 10);

		targetmaptext = new JTextField("˫�����ļ�", 10);
		targetmaptext.setEditable(false);
		targetmaptext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("./data/map/"));
					chooser.setFileFilter(new DatFilter());
					int result = chooser.showOpenDialog(CreaterPanel.this);
					if (result == 0) {
						//String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						targetmaptext.setText(path);
						targetmapname = path;
					}
				}
			}
		});
		targetxtext = new JTextField(10);
		targetytext = new JTextField(10);

		magictext = new JTextField("˫�����ļ�");
		magictext.setEditable(false);
		magictext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("./data/magic/"));
					chooser.setFileFilter(new DatFilter());
					int result = chooser.showOpenDialog(CreaterPanel.this);
					if (result == 0) {
						//String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						magictext.setText(path);
						magic = path;
					}
				}
			}
		});
		//��ӱ����¼�
		JButton savebutton = new JButton("�����¼�");
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
		//��Ӹ��¿ؼ��¼�
		JButton updatebutton = new JButton("��������");
		updatebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//����
				update();
			}
		});

		setLayout(new GridLayout(16, 2));
		add(filenamelabel);
		add(filenametext);

		add(eventnamelabel);
		add(eventnametext);

		add(eventinfolabel);
		add(eventinfotext);

		add(needeventlabel);
		add(needeventtext);

		add(followeventlabel);
		add(followeventtext);

		add(needitemlabel);
		add(needitemtext);

		add(eventtypelabel);
		add(eventtypecombo);

		add(enemynamelabel);
		add(enemynametext);

		add(itemnamelabel);
		add(itemnametext);

		add(moneylabel);
		add(moneytext);

		add(targetmaplabel);
		add(targetmaptext);

		add(targetxlabel);
		add(targetxtext);

		add(targetylabel);
		add(targetytext);

		add(magiclabel);
		add(magictext);

		add(savebutton);
		add(resetbutton);
		//add(updatebutton);
		//updatebutton.hide();
	}

	/*
	 * ��ʼ��
	 */
	public void init() {
		track("��ʼ��...");
		openfile = null;

		filename = "";
		eventname = "";
		eventinfo = "";
		needevent = "";
		followevent = "";
		needitem = "";
		eventtype = "";
		enemyname = "";
		itemname = "";
		targetmapname = "";
		targetx = "";
		targety = "";
		money = "0";
		magic = "";

		filenametext.setText(filename);
		eventnametext.setText(eventname);
		eventinfotext.setText(eventinfo);
		needeventtext.setText("˫�����ļ�");
		needitemtext.setText("˫�����ļ�");
		followeventtext.setText("˫�����ļ�");
		enemynametext.setText("˫�����ļ�");
		itemnametext.setText("˫�����ļ�");
		targetmaptext.setText("˫�����ļ�");
		targetxtext.setText(targetx);
		targetytext.setText(targety);
		eventtypecombo.setSelectedIndex(0);
		moneytext.setText(money);
		magictext.setText("˫�����ļ�");
	}

	/*
	 * �½��ļ�
	 */
	public void newFile() {
		track("�ȴ��������ļ�...");
		int result = JOptionPane.showConfirmDialog(CreaterPanel.this,
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

	/*
	 * ��ȡ�ļ�
	 * @param f Ҫ��ȡ���ļ�
	 */
	public void readFile(File f) {
		track("��ȡ�ļ�...");
		init();
		money = "";
		filename = f.getName();
		openfile = new File(f.getPath());
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
			 eventname += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 needevent += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 followevent += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 needitem += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 eventtype += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 enemyname += readData.charAt(i);
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
			 targetmapname += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 targetx += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 targety += readData.charAt(i);
			 i++;
			 }
			 i++;
			 while(readData.charAt(i) != '\n')
			 {
			 money += readData.charAt(i);
			 i++;
			 }
			 in.close();
			 */
			/*
			 if(needevent.endsWith("NONE"))
			 {
			 needevent = "";
			 }
			 if(needitem.endsWith("NONE"))
			 {
			 needitem = "";
			 }
			 */

			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(f)));

			filename = in.readLine();
			eventname = in.readLine();
			eventinfo = in.readLine();
			needevent = in.readLine();
			followevent = in.readLine();
			needitem = in.readLine();
			eventtype = in.readLine();
			enemyname = in.readLine();
			itemname = in.readLine();
			targetmapname = in.readLine();
			targetx = in.readLine();
			targety = in.readLine();
			money = in.readLine();
			magic = in.readLine();

			in.close();
			update();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * ���ݼ��,���ݲ��Է���true
	 */
	public boolean check() {
		track("�������...");

		String errormsg = "";
		boolean error = false;

		filename = filenametext.getText();
		eventname = eventnametext.getText();
		eventinfo = eventinfotext.getText();
		needevent = needeventtext.getText();
		followevent = followeventtext.getText();
		needitem = needitemtext.getText();
		enemyname = enemynametext.getText();
		itemname = itemnametext.getText();
		targetmapname = targetmaptext.getText();
		targetx = targetxtext.getText();
		targety = targetytext.getText();
		eventtype = (String) eventtypecombo.getSelectedItem();
		money = moneytext.getText();
		magic = magictext.getText();

		if (eventinfo.length() == 0) {
			eventinfo = "����" + eventname + "�¼�.";
		}

		if (needevent.equals(new String("˫�����ļ�")))
			needevent = "data/default.dat";
		if (followevent.equals(new String("˫�����ļ�")))
			followevent = "data/default.dat";
		if (needitem.equals(new String("˫�����ļ�")))
			needitem = "data/default.dat";
		if (itemname.equals(new String("˫�����ļ�")))
			itemname = "data/default.dat";
		if (enemyname.equals(new String("˫�����ļ�")))
			enemyname = "data/default.dat";
		if (targetmapname.equals(new String("˫�����ļ�")))
			targetmapname = "data/default.dat";
		if (magic.equals("˫�����ļ�"))
			magic = "data/default.dat";

		if (money.length() == 0)
			money = "0";

		if (targetx.length() == 0)
			targetx = "0";
		if (targety.length() == 0)
			targety = "0";

		NumberTester tester = new NumberTester();
		if (filename.length() == 0) {
			errormsg += "�������ļ���\n";
			error = true;
		}
		if (filename.length() > 0 && !filename.endsWith(".dat")) {
			errormsg += "�ļ�����ʽ����(.dat)\n";
			error = true;
		}
		if (eventname.length() == 0) {
			errormsg += "�������¼���\n";
			error = true;
		}
		if (needevent.length() != 0 && !needevent.endsWith(".dat")) {
			errormsg += "ǰ���¼���ʽ����(.dat)\n";
			error = true;
		}
		if (followevent.length() != 0 && !followevent.endsWith(".dat")) {
			errormsg += "�����¼���ʽ����(.dat)\n";
			error = true;
		}
		if (magic.length() != 0 && !magic.endsWith(".dat")) {
			errormsg += "�����ļ���ʽ����(.dat)\n";
			error = true;
		}
		if (eventtype.equals("��Ʒ�¼�") && itemname.equals("default.dat")) {
			errormsg += "��Ʒ�¼���������Ʒ\n";
			error = true;
		}
		if (eventtype.equals("�����¼�") && targetmapname.equals("default.dat")) {
			errormsg += "�����¼�������Ŀ���ͼ\n";
			error = true;
		}
		if (eventtype.equals("ս���¼�") && enemyname.equals("default.dat")) {
			errormsg += "ս���¼������е���\n";
			error = true;
		}
		if (eventtype.equals("��Ǯ�¼�")) {
			if (!tester.test(money)) {
				errormsg += "��Ǯ�¼�����ָ����Ǯ����\n";
				error = true;
			}
		}
		if (eventtype.equals("ѧϰ�¼�") && magic.equals("default.dat")) {
			errormsg += "ѧϰ�¼������м���\n";
			error = true;
		}
		if (needitem.length() != 0 && !needitem.endsWith(".dat")) {
			errormsg += "��Ҫ��Ʒ�ļ���ʽ����(.dat)\n";
			error = true;
		}
		if (itemname.length() != 0 && !itemname.endsWith(".dat")) {
			errormsg += "��Ʒ�ļ���ʽ����(.dat)\n";
			error = true;
		}
		if (enemyname.length() != 0 && !enemyname.endsWith(".dat")) {
			errormsg += "�����ļ���ʽ����(.dat)\n";
			error = true;
		}
		if (!targetmapname.equals("data/default.dat")) {
			if (!targetmapname.endsWith(".dat")) {
				errormsg += "��ͼ��ʽ����(.dat)\n";
				error = true;
			}
			if (targetx.length() == 0 || targety.length() == 0) {
				errormsg += "�����봫������\n";
				error = true;
			}
			if (!tester.test(targetx) || !tester.test(targety)) {
				errormsg += "��������������\n";
				error = true;
			}
		}
		if (error) {
			JOptionPane.showMessageDialog(CreaterPanel.this, errormsg, "����",
					JOptionPane.ERROR_MESSAGE);
			return error;
		}
		return error;
	}

	/*
	 * �����湤��
	 */
	public void save() {
		if (check()) {
			return;
		}
			track("�����ļ�...");
			String saveData = "";
			savefile = new File("data/event/" + filename);
			int save = 0;
			if(savefile.exists())
			{
				save = JOptionPane.showConfirmDialog(CreaterPanel.this,
					"���¼��Ѿ�����,�Ƿ񸲸�?", "�ȴ�ȷ��", JOptionPane.YES_NO_CANCEL_OPTION);
			}
			if (save == 0) {
			saveData = filename + "\n" + eventname + "\n" + eventinfo + "\n"
					+ needevent + "\n" + followevent + "\n" + needitem + "\n"
					+ eventtype + "\n" + enemyname + "\n" + itemname + "\n"
					+ targetmapname + "\n" + targetx + "\n" + targety + "\n"
					+ money + "\n" + magic + "\n"
					+ "                                                   #\n";
			try {
				FileOutputStream out = new FileOutputStream(savefile);
				out.write(saveData.getBytes(), 0, saveData.length());
				out.close();
				JOptionPane.showMessageDialog(CreaterPanel.this, "�ļ�����ɹ�",
						"����", JOptionPane.INFORMATION_MESSAGE);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * �����¹���
	 */
	public void update() {
		track("���¿ؼ�...");
		filenametext.setText(filename);
		eventnametext.setText(eventname);
		eventinfotext.setText(eventinfo);
		if (needevent.length() > 0)
			needeventtext.setText(needevent);
		else
			needeventtext.setText("˫�����ļ�");

		if (followevent.length() > 0)
			followeventtext.setText(followevent);
		else
			followeventtext.setText("˫�����ļ�");

		if (needitem.length() > 0)
			needitemtext.setText(needitem);
		else
			needitemtext.setText("˫�����ļ�");

		if (itemname.length() > 0)
			itemnametext.setText(itemname);
		else
			itemnametext.setText("˫�����ļ�");

		if (enemyname.length() > 0)
			enemynametext.setText(enemyname);
		else
			enemynametext.setText("˫�����ļ�");

		if (targetmapname.length() > 0)
			targetmaptext.setText(targetmapname);
		else
			targetmaptext.setText("˫�����ļ�");
		magictext.setText(magic);
		targetxtext.setText(targetx);
		targetytext.setText(targety);
		if (eventtype.equals("�����¼�"))
			eventtypecombo.setSelectedIndex(0);
		else if (eventtype.equals("ս���¼�"))
			eventtypecombo.setSelectedIndex(1);
		else if (eventtype.equals("��Ʒ�¼�"))
			eventtypecombo.setSelectedIndex(2);
		else if (eventtype.equals("��Ǯ�¼�"))
			eventtypecombo.setSelectedIndex(3);
		else if (eventtype.equals("ѧϰ�¼�"))
			eventtypecombo.setSelectedIndex(4);

		moneytext.setText(money);
	}

	/*
	 * ������,���ڵ���
	 * @param s ����̨�����Ϣ
	 */
	public void track(String s) {
		GameLogger.logger.info("EventCreater|" + s);
	}

	private String filename;

	private String eventname;

	private String eventinfo;

	private String needevent;

	private String followevent;

	private String needitem;

	private String eventtype;

	private String enemyname;

	private String itemname;

	private String targetmapname;

	private String targetx;

	private String targety;

	private String money;

	private String magic;

	JTextField filenametext;

	JTextField eventnametext;

	JTextField eventinfotext;

	JTextField needeventtext;

	JTextField followeventtext;

	JTextField needitemtext;

	JTextField enemynametext;

	JTextField itemnametext;

	JTextField targetmaptext;

	JTextField targetxtext;

	JTextField targetytext;

	JTextField moneytext;

	JTextField magictext;

	File openfile;

	File savefile;

	private JComboBox eventtypecombo;
}
