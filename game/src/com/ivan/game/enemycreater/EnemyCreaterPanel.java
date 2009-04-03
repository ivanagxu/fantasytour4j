package com.ivan.game.enemycreater;

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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ivan.game.logger.GameLogger;
import com.ivan.game.unit.DatFilter;
import com.ivan.game.unit.GifFilter;
import com.ivan.game.unit.NumberTester;

public class EnemyCreaterPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8535031675496217263L;

	public EnemyCreaterPanel() {
		this.setBackground(Color.CYAN);
		setLayout(new GridLayout(18, 2));

		add(new JLabel("名称:"));
		nametext = new JTextField(10);
		add(nametext);

		add(new JLabel("属性:"));
		propertycombo = new JComboBox();
		propertycombo.addItem("普");
		propertycombo.addItem("全");
		propertycombo.addItem("火");
		propertycombo.addItem("冰");
		propertycombo.addItem("水");
		add(propertycombo);

		add(new JLabel("HP:"));
		hptext = new JTextField("1");
		add(hptext);

		add(new JLabel("MP:"));
		mptext = new JTextField("1");
		add(mptext);

		add(new JLabel("攻击力:"));
		strtext = new JTextField("1");
		add(strtext);

		add(new JLabel("防御力:"));
		deftext = new JTextField("1");
		add(deftext);

		add(new JLabel("魔力:"));
		mstrtext = new JTextField("1");
		add(mstrtext);

		add(new JLabel("魔防:"));
		mdeftext = new JTextField("1");
		add(mdeftext);

		add(new JLabel("致命率:"));
		hstext = new JTextField("0");
		add(hstext);

		add(new JLabel("躲闪率:"));
		jouktext = new JTextField("0");
		add(jouktext);

		JButton addmagic = new JButton("添加技能");
		magiclist = new ArrayList();
		magiccombo = new JComboBox();
		addmagic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("./data/magic/"));
				chooser.setFileFilter(new DatFilter());
				int result = chooser.showOpenDialog(EnemyCreaterPanel.this);
				if (result == 0) {
					//String filename = chooser.getSelectedFile().getName();
					String filepath = chooser.getSelectedFile().getPath();
					String path = filepath.substring(filepath
							.lastIndexOf("data"));
					magiccombo.addItem(path);
					magiclist.add(path);
				}
			}
		});
		add(addmagic);

		add(magiccombo);

		add(new JLabel("金钱:"));
		moneytext = new JTextField("0");
		add(moneytext);

		add(new JLabel("物品:"));
		itemtext = new JTextField("双击打开文件");
		itemtext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("./data/item/"));
					chooser.setFileFilter(new DatFilter());
					int result = chooser.showOpenDialog(EnemyCreaterPanel.this);
					if (result == 0) {
						//String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						itemtext.setText(path);
						item = path;
					}
				}
			}
		});
		itemtext.setEditable(false);
		add(itemtext);

		add(new JLabel("物品掉率:"));
		dropprobabilitytext = new JTextField("0");
		add(dropprobabilitytext);

		add(new JLabel("贴图:"));
		imagetext = new JTextField("双击打开文件");
		imagetext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("./data/images/"));
					chooser.setFileFilter(new GifFilter());
					int result = chooser.showOpenDialog(EnemyCreaterPanel.this);
					if (result == 0) {
						//String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						imagetext.setText(path);
						image = path;
					}
				}
			}
		});
		imagetext.setEditable(false);
		add(imagetext);

		add(new JLabel("受伤贴图:"));
		hurtimagetext = new JTextField("双击打开文件");
		hurtimagetext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("./data/images/"));
					chooser.setFileFilter(new GifFilter());
					int result = chooser.showOpenDialog(EnemyCreaterPanel.this);
					if (result == 0) {
						//String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						hurtimagetext.setText(path);
						hurtimage = path;
					}
				}
			}
		});
		hurtimagetext.setEditable(false);
		add(hurtimagetext);

		add(new JLabel("攻击贴图:"));
		attackimagetext = new JTextField("双击打开文件");
		attackimagetext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("./data/images/"));
					chooser.setFileFilter(new GifFilter());
					int result = chooser.showOpenDialog(EnemyCreaterPanel.this);
					if (result == 0) {
						//String filename = chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						attackimagetext.setText(path);
						attackimage = path;
					}
				}
			}
		});
		attackimagetext.setEditable(false);
		add(attackimagetext);
	}

	public void init() {
		name = "";
		property = "普";
		hp = "1";
		mp = "1";
		str = "1";
		def = "1";
		mstr = "1";
		mdef = "1";
		hs = "0";
		jouk = "0";
		magiclist.clear();
		money = "0";
		item = "";
		dropprobability = "0";
		image = "";
		hurtimage = "";
		attackimage = "";

		nametext.setText(name);
		propertycombo.setSelectedIndex(0);
		hptext.setText(hp);
		mptext.setText(mp);
		strtext.setText(str);
		deftext.setText(def);
		mstrtext.setText(mstr);
		mdeftext.setText(mdef);
		hstext.setText(hs);
		jouktext.setText(jouk);
		magiccombo.removeAllItems();
		moneytext.setText(money);
		itemtext.setText("双击打开文件");
		dropprobabilitytext.setText(dropprobability);
		imagetext.setText("双击打开文件");
		hurtimagetext.setText("双击打开文件");
		attackimagetext.setText("双击打开文件");
	}

	public void newFile() {
		track("等待建立新文件...");
		int result = JOptionPane.showConfirmDialog(EnemyCreaterPanel.this,
				"是否保存当前敌人?", "等待确认", JOptionPane.YES_NO_CANCEL_OPTION);
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

	public void readFile(File f) {
		track("读取文件...");
		init();
		try {
			//FileInputStream in = new FileInputStream(f);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(f)));

			name = in.readLine();
			property = in.readLine();
			hp = in.readLine();
			mp = in.readLine();
			str = in.readLine();
			def = in.readLine();
			mstr = in.readLine();
			mdef = in.readLine();
			hs = in.readLine();
			jouk = in.readLine();
			money = in.readLine();
			item = in.readLine();
			dropprobability = in.readLine();
			image = in.readLine();
			hurtimage = in.readLine();
			attackimage = in.readLine();
			String magic = "";
			magic = in.readLine();
			while (!magic.equals("#")) {
				magiclist.add(magic);
				magiccombo.addItem(magic);
				magic = in.readLine();
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
		nametext.setText(name);
		propertycombo.setSelectedItem(property);
		hptext.setText(hp);
		mptext.setText(mp);
		strtext.setText(str);
		deftext.setText(def);
		mstrtext.setText(mstr);
		mdeftext.setText(mdef);
		hstext.setText(hs);
		jouktext.setText(jouk);
		magiccombo.setSelectedIndex(0);
		moneytext.setText(money);
		itemtext.setText(item);
		dropprobabilitytext.setText(dropprobability);
		imagetext.setText(image);
		hurtimagetext.setText(hurtimage);
		attackimagetext.setText(attackimage);
	}

	public boolean checkfalse() {
		track("检测数据...");
		boolean error = false;
		String errormsg = "";

		name = nametext.getText();
		property = (String) propertycombo.getSelectedItem();
		hp = hptext.getText();
		mp = mptext.getText();
		str = strtext.getText();
		def = deftext.getText();
		mstr = mstrtext.getText();
		mdef = mdeftext.getText();
		hs = hstext.getText();
		jouk = jouktext.getText();
		money = moneytext.getText();
		item = itemtext.getText();
		dropprobability = dropprobabilitytext.getText();
		image = imagetext.getText();
		hurtimage = hurtimagetext.getText();
		attackimage = attackimagetext.getText();

		if (hp.length() == 0)
			hp = "1";
		if (mp.length() == 0)
			mp = "1";
		if (str.length() == 0)
			str = "1";
		if (def.length() == 0)
			def = "1";
		if (mstr.length() == 0)
			mstr = "1";
		if (mdef.length() == 0)
			mdef = "1";
		if (hs.length() == 0)
			hs = "0";
		if (jouk.length() == 0)
			jouk = "0";
		if (money.length() == 0)
			money = "0";
		if (dropprobability.length() == 0)
			dropprobability = "0";
		if (item.length() == 0 || item.equals("双击打开文件"))
			item = "data/item/default.dat";

		if (image.length() == 0 || image.equals("双击打开文件"))
			image = "";
		if (hurtimage.length() == 0 || image.equals("双击打开文件"))
			hurtimage = "";
		if (attackimage.length() == 0 || image.equals("双击打开文件"))
			attackimage = "";

		NumberTester test = new NumberTester();
		if (name.length() == 0) {
			errormsg += "请输入名称\n";
			error = true;
		}
		if (!test.test(hp) || test.getInt(hp) < 1) {
			errormsg += "HP数据不正确(大于1)\n";
			error = true;
		}
		if (!test.test(mp) || test.getInt(mp) < 1) {
			errormsg += "Mp数据不正确(大于1)\n";
			error = true;
		}
		if (!test.test(str) || test.getInt(str) < 1) {
			errormsg += "攻击力数据不正确(大于1)\n";
			error = true;
		}
		if (!test.test(def) || test.getInt(def) < 1) {
			errormsg += "防御力数据不正确(大于1)\n";
			error = true;
		}
		if (!test.test(mstr) || test.getInt(mstr) < 1) {
			errormsg += "魔力数据不正确(大于1)\n";
			error = true;
		}
		if (!test.test(mdef) || test.getInt(mdef) < 1) {
			errormsg += "魔防数据不正确(大于1)\n";
			error = true;
		}
		if (!test.test(hs) || test.getInt(hs) > 100) {
			errormsg += "致命率数据不正确(0~100)\n";
			error = true;
		}
		if (!test.test(jouk) || test.getInt(jouk) > 100) {
			errormsg += "躲闪率数据不正确(0~100)\n";
			error = true;
		}
		if (!test.test(dropprobability) || test.getInt(dropprobability) > 100) {
			errormsg += "掉率数据不正确(0~100)\n";
			error = true;
		}
		if (image.length() == 0 || !image.endsWith(".gif")) {
			errormsg += "贴图不正确\n";
			error = true;
		}
		if (hurtimage.length() == 0 || !hurtimage.endsWith(".gif")) {
			errormsg += "受伤贴图不正确\n";
			error = true;
		}
		if (attackimage.length() == 0 || !attackimage.endsWith(".gif")) {
			errormsg += "攻击贴图不正确\n";
			error = true;
		}
		if (!test.test(money)) {
			errormsg += "金钱数据不正确(0~100)\n";
			error = true;
		}
		if (!item.endsWith(".dat")) {
			errormsg += "物品格式不正确(.dat)\n";
			error = true;
		}
		if (magiclist.size() == 0) {
			errormsg += "没添加技能!\n";
			error = true;
		}
		if (error) {
			JOptionPane.showMessageDialog(EnemyCreaterPanel.this, errormsg,
					"错误", JOptionPane.ERROR_MESSAGE);
			return error;
		}

		return error;
	}

	public void save() {
		if (!checkfalse()) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("./data/enemy/"));
			chooser.setFileFilter(new DatFilter());
			int result = chooser.showSaveDialog(EnemyCreaterPanel.this);
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
					save = JOptionPane.showConfirmDialog(
							EnemyCreaterPanel.this, "此怪物已经存在,是否覆盖?", "等待确认",
							JOptionPane.YES_NO_CANCEL_OPTION);
				}
				if (save == 0) {
					String saveData = "";
					saveData = name + "\n" + property + "\n" + hp + "\n" + mp
							+ "\n" + str + "\n" + def + "\n" + mstr + "\n"
							+ mdef + "\n" + hs + "\n" + jouk + "\n" + money
							+ "\n" + item + "\n" + dropprobability + "\n"
							+ image + "\n" + hurtimage + "\n" + attackimage
							+ "\n";
					for (int i = 0; i < magiclist.size(); i++) {
						saveData += (String) magiclist.get(i);
						saveData += "\n";
					}
					saveData += "#\n                                                              \n";
					try {
						FileOutputStream out = new FileOutputStream(f);
						out.write(saveData.getBytes(), 0, saveData.length());
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

	public void track(String s) {
		GameLogger.logger.info("EnemyCreater|" + s);
	}

	private String name;

	private String property;

	private String hp;

	private String mp;

	private String str;

	private String def;

	private String mstr;

	private String mdef;

	private String hs;

	private String jouk;

	private ArrayList magiclist; //file

	private String money;

	private String item; //file

	private String dropprobability;

	private String image; //file

	private String hurtimage; //file

	private String attackimage; //file

	private JTextField nametext;

	private JComboBox propertycombo;

	private JTextField hptext;

	private JTextField mptext;

	private JTextField strtext;

	private JTextField deftext;

	private JTextField mstrtext;

	private JTextField mdeftext;

	private JTextField hstext;

	private JTextField jouktext;

	private JComboBox magiccombo;

	private JTextField moneytext;

	private JTextField itemtext;

	private JTextField dropprobabilitytext;

	private JTextField imagetext;

	private JTextField hurtimagetext;

	private JTextField attackimagetext;
}
