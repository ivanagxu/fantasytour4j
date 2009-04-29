package com.ivan.game.magiccreater;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ivan.game.eventcreater.CreaterPanel;
import com.ivan.game.logger.GameLogger;
import com.ivan.game.unit.DatFilter;
import com.ivan.game.unit.NumberTester;

public class MagicCreaterPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2075076563728420793L;

	public MagicCreaterPanel() {
		setBackground(Color.GREEN);
		setLayout(new GridLayout(34, 2));

		add(new JLabel("----------基本信息----------"));
		add(new JLabel(""));

		add(new JLabel("技能名称:"));
		magicnametext = new JTextField(10);
		add(magicnametext);

		add(new JLabel("技能类型:"));
		magictypecombo = new JComboBox();
		magictypecombo.addItem("攻击");
		magictypecombo.addItem("治疗");
		magictypecombo.addItem("buff");
		magictypecombo.addItem("debuff");
		add(magictypecombo);

		add(new JLabel("动画文件:"));
		magicmotiontext = new JTextField("双击打开文件");
		magicmotiontext.setEditable(false);
		magicmotiontext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File(".//data//motion//"));
					chooser.setFileFilter(new DatFilter());
					int result = chooser.showOpenDialog(MagicCreaterPanel.this);
					if (result == 0) {
						// String filename =
						// chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						magicmotiontext.setText(path);
						magicmotion = path;
					}
				}
			}
		});
		add(magicmotiontext);

		add(new JLabel("需要法力:"));
		needmptext = new JTextField("0");
		add(needmptext);

		add(new JLabel("需要物品文件:"));
		needitemtext = new JTextField("双击打开文件");
		needitemtext.setEditable(false);
		needitemtext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File(".//data//item//"));
					chooser.setFileFilter(new DatFilter());
					int result = chooser.showOpenDialog(MagicCreaterPanel.this);
					if (result == 0) {
						// String filename =
						// chooser.getSelectedFile().getName();
						String filepath = chooser.getSelectedFile().getPath();
						String path = filepath.substring(filepath
								.lastIndexOf("data"));
						needitemtext.setText(path);
						needitem = path;
					}
				}
			}
		});
		add(needitemtext);

		add(new JLabel("持续回合:"));
		magicdelaytext = new JTextField("1");
		add(magicdelaytext);

		add(new JLabel("技能说明"));
		magicinfotext = new JTextField(10);
		add(magicinfotext);

		add(new JLabel("----------攻击效果----------"));
		add(new JLabel(""));

		add(new JLabel("攻击类型:"));
		attacktypecombo = new JComboBox();
		attacktypecombo.addItem("普通攻击");
		attacktypecombo.addItem("魔法攻击");
		add(attacktypecombo);

		add(new JLabel("技能属性:"));
		magicpropertycombo = new JComboBox();
		magicpropertycombo.addItem("普");
		magicpropertycombo.addItem("全");
		magicpropertycombo.addItem("火");
		magicpropertycombo.addItem("冰");
		magicpropertycombo.addItem("水");
		add(magicpropertycombo);

		add(new JLabel("技能伤害:"));
		dmgtext = new JTextField("0");
		add(dmgtext);

		add(new JLabel("----------增益效果----------"));
		add(new JLabel(""));

		add(new JLabel("对HP增益:"));
		add(buffhptext = new JTextField("0"));

		add(new JLabel("对MP增益:"));
		add(buffmptext = new JTextField("0"));

		add(new JLabel("对防御增益:"));
		add(buffdeftext = new JTextField("0"));

		add(new JLabel("对魔防增益:"));
		add(buffmdeftext = new JTextField("0"));

		add(new JLabel("对力量增益:"));
		add(buffstrtext = new JTextField("0"));

		add(new JLabel("对魔法力量增益:"));
		add(buffmstrtext = new JTextField("0"));

		add(new JLabel("对致命一击增益:"));
		add(buffhstext = new JTextField("0"));

		add(new JLabel("对躲闪的增益:"));
		add(buffjouktext = new JTextField("0"));

		add(new JLabel("----------恢复效果----------"));
		add(new JLabel(""));

		add(new JLabel("治疗效果:"));
		add(healhptext = new JTextField("0"));

		add(new JLabel("是否能解毒:"));
		antidotalcombo = new JComboBox();
		antidotalcombo.addItem("否");
		antidotalcombo.addItem("是");
		add(antidotalcombo);

		add(new JLabel("是否清debuff:"));
		cleardebuffcombo = new JComboBox();
		cleardebuffcombo.addItem("否");
		cleardebuffcombo.addItem("是");
		add(cleardebuffcombo);

		add(new JLabel("----------减益效果----------"));
		add(new JLabel(""));

		add(new JLabel("对HP减益:"));
		add(debuffhptext = new JTextField("0"));

		add(new JLabel("对MP减益:"));
		add(debuffmptext = new JTextField("0"));

		add(new JLabel("对防御减益:"));
		add(debuffdeftext = new JTextField("0"));

		add(new JLabel("对魔防减益:"));
		add(debuffmdeftext = new JTextField("0"));

		add(new JLabel("对力量减益:"));
		add(debuffstrtext = new JTextField("0"));

		add(new JLabel("对魔法力量减益:"));
		add(debuffmstrtext = new JTextField("0"));

		add(new JLabel("对致命一击减益:"));
		add(debuffhstext = new JTextField("0"));

		add(new JLabel("对躲闪的减益:"));
		add(debuffjouktext = new JTextField("0"));
		init();
	}

	public void init() {
		magicname = "";
		magictype = "";
		attacktype = "";
		magicproperty = "";
		magicmotion = "";
		needitem = "";
		magicdelay = "1";
		magicinfo = "";

		// attack magic
		dmg = "0";
		needmp = "0";

		// buff magic
		buffhp = "0";
		buffmp = "0";
		buffdef = "0";
		buffmdef = "0";
		buffstr = "0";
		buffmstr = "0";
		buffhs = "0";
		buffjouk = "0";

		// heal magic
		healhp = "0";
		antidotal = "";
		cleardebuff = "";
		// debuff magic
		debuffhp = "0";
		debuffmp = "0";
		debuffdef = "0";
		debuffmdef = "0";
		debuffstr = "0";
		debuffmstr = "0";
		debuffhs = "0";
		debuffjouk = "0";
		// ////////////////////////////////////////////////////////////////////////////////////////
		magicnametext.setText(magicname);
		magictypecombo.setSelectedIndex(0);
		attacktypecombo.setSelectedIndex(0);
		magicpropertycombo.setSelectedIndex(0);
		magicmotiontext.setText("双击打开文件");
		needitemtext.setText("双击打开文件");
		magicdelaytext.setText("1");
		magicinfotext.setText("");

		// attack magic
		dmgtext.setText("0");
		needmptext.setText("0");

		// buff magic
		buffhptext.setText("0");
		buffmptext.setText("0");
		buffdeftext.setText("0");
		buffmdeftext.setText("0");
		buffstrtext.setText("0");
		buffmstrtext.setText("0");
		buffhstext.setText("0");
		buffjouktext.setText("0");

		// heal magic
		healhptext.setText("0");
		antidotalcombo.setSelectedIndex(0);
		cleardebuffcombo.setSelectedIndex(0);
		// debuff magic
		debuffhptext.setText("0");
		debuffmptext.setText("0");
		debuffdeftext.setText("0");
		debuffmdeftext.setText("0");
		debuffstrtext.setText("0");
		debuffmstrtext.setText("0");
		debuffhstext.setText("0");
		debuffjouktext.setText("0");
	}

	public void newFile() {
		track("等待建立新文件...");
		int result = JOptionPane.showConfirmDialog(MagicCreaterPanel.this,
				"是否保存当前技能?", "等待确认", JOptionPane.YES_NO_CANCEL_OPTION);
		// System.out.println(result);
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
			// FileInputStream in = new FileInputStream(f);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(f),"GBK"));
			magicname = in.readLine();
			magictype = in.readLine();
			attacktype = in.readLine();
			magicproperty = in.readLine();
			magicmotion = in.readLine();
			needitem = in.readLine();
			magicdelay = in.readLine();
			magicinfo = in.readLine();
			dmg = in.readLine();
			needmp = in.readLine();
			buffhp = in.readLine();
			buffmp = in.readLine();
			buffdef = in.readLine();
			buffmdef = in.readLine();
			buffstr = in.readLine();
			buffmstr = in.readLine();
			buffhs = in.readLine();
			buffjouk = in.readLine();
			healhp = in.readLine();
			antidotal = in.readLine();
			cleardebuff = in.readLine();
			debuffhp = in.readLine();
			debuffmp = in.readLine();
			debuffdef = in.readLine();
			debuffmdef = in.readLine();
			debuffstr = in.readLine();
			debuffmstr = in.readLine();
			debuffhs = in.readLine();
			debuffjouk = in.readLine();

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
		magicnametext.setText(magicname);
		if (magictype.equals("攻击"))
			magictypecombo.setSelectedIndex(0);
		else if (magictype.equals("治疗"))
			magictypecombo.setSelectedIndex(1);
		else if (magictype.equals("buff"))
			magictypecombo.setSelectedIndex(2);
		else if (magictype.equals("debuff"))
			magictypecombo.setSelectedIndex(3);
		if (attacktype.equals("普通攻击"))
			attacktypecombo.setSelectedIndex(0);
		else if (attacktype.equals("魔法攻击"))
			attacktypecombo.setSelectedIndex(1);
		if (magicproperty.equals("普"))
			magicpropertycombo.setSelectedIndex(0);
		else if (magicproperty.equals("全"))
			magicpropertycombo.setSelectedIndex(1);
		else if (magicproperty.equals("火"))
			magicpropertycombo.setSelectedIndex(2);
		else if (magicproperty.equals("冰"))
			magicpropertycombo.setSelectedIndex(3);
		else if (magicproperty.equals("水"))
			magicpropertycombo.setSelectedIndex(4);

		magicmotiontext.setText(magicmotion);
		needitemtext.setText(needitem);
		magicdelaytext.setText(magicdelay);
		magicinfotext.setText(magicinfo);

		// attack magic
		dmgtext.setText(dmg);
		needmptext.setText(needmp);

		// buff magic
		buffhptext.setText(buffhp);
		buffmptext.setText(buffmp);
		buffdeftext.setText(buffdef);
		buffmdeftext.setText(buffmdef);
		buffstrtext.setText(buffstr);
		buffmstrtext.setText(buffmstr);
		buffhstext.setText(buffhs);
		buffjouktext.setText(buffjouk);

		// heal magic
		healhptext.setText(healhp);
		if (antidotal.equals("否"))
			antidotalcombo.setSelectedIndex(0);
		else if (antidotal.equals("是"))
			antidotalcombo.setSelectedIndex(1);
		if (cleardebuff.equals("否"))
			cleardebuffcombo.setSelectedIndex(0);
		else if (cleardebuff.equals("是"))
			cleardebuffcombo.setSelectedIndex(1);
		// debuff magic
		debuffhptext.setText(debuffhp);
		debuffmptext.setText(debuffmp);
		debuffdeftext.setText(debuffdef);
		debuffmdeftext.setText(debuffmdef);
		debuffstrtext.setText(debuffstr);
		debuffmstrtext.setText(debuffmstr);
		debuffhstext.setText(debuffhs);
		debuffjouktext.setText(debuffjouk);
	}

	public boolean checkfalse() {
		track("检测数据...");
		boolean error = false;
		String errormsg = "";
		magicname = magicnametext.getText();
		magictype = (String) magictypecombo.getSelectedItem();
		attacktype = (String) attacktypecombo.getSelectedItem();
		magicproperty = (String) magicpropertycombo.getSelectedItem();
		magicmotion = magicmotiontext.getText();
		needitem = needitemtext.getText();
		if (needitem.equals("双击打开文件"))
			needitem = "data/default.dat";
		magicdelay = magicdelaytext.getText();
		magicinfo = magicinfotext.getText();
		dmg = dmgtext.getText();
		if (dmg.length() == 0)
			dmg = "0";
		needmp = needmptext.getText();
		if (needmp.length() == 0)
			needmp = "0";
		buffhp = buffhptext.getText();
		if (buffhp.length() == 0)
			buffhp = "0";
		buffmp = buffmptext.getText();
		if (buffmp.length() == 0)
			buffmp = "0";
		buffdef = buffdeftext.getText();
		if (buffdef.length() == 0)
			buffdef = "0";
		buffmdef = buffmdeftext.getText();
		if (buffmdef.length() == 0)
			buffmdef = "0";
		buffstr = buffstrtext.getText();
		if (buffstr.length() == 0)
			buffstr = "0";
		buffmstr = buffmstrtext.getText();
		if (buffmstr.length() == 0)
			buffmstr = "0";
		buffhs = buffhstext.getText();
		if (buffhs.length() == 0)
			buffhs = "0";
		buffjouk = buffjouktext.getText();
		if (buffjouk.length() == 0)
			buffjouk = "0";
		healhp = healhptext.getText();
		if (healhp.length() == 0)
			healhp = "0";
		antidotal = (String) antidotalcombo.getSelectedItem();
		cleardebuff = (String) cleardebuffcombo.getSelectedItem();
		debuffhp = debuffhptext.getText();
		if (debuffhp.length() == 0)
			debuffhp = "0";
		debuffmp = debuffmptext.getText();
		if (debuffmp.length() == 0)
			debuffmp = "0";
		debuffdef = debuffdeftext.getText();
		if (debuffdef.length() == 0)
			debuffdef = "0";
		debuffmdef = debuffmdeftext.getText();
		if (debuffmdef.length() == 0)
			debuffmdef = "0";
		debuffstr = debuffstrtext.getText();
		if (debuffstr.length() == 0)
			debuffstr = "0";
		debuffmstr = debuffmstrtext.getText();
		if (debuffmstr.length() == 0)
			debuffmstr = "0";
		debuffhs = debuffhstext.getText();
		if (debuffhs.length() == 0)
			debuffhs = "0";
		debuffjouk = debuffjouktext.getText();
		if (debuffjouk.length() == 0)
			debuffjouk = "0";

		NumberTester tester = new NumberTester();
		if (magicname.length() == 0) {
			errormsg += "请输入技能名称\n";
			error = true;
		}
		if (magicmotion.length() == 0 || !magicmotion.endsWith(".dat")) {
			errormsg += "技能动画文件错误\n";
			error = true;
		}
		if (needitem.length() != 0) {
			if (!needitem.equals("双击打开文件") && !needitem.endsWith(".dat")) {
				errormsg += "需要物品文件错误\n";
				error = true;
			}
		}
		if (!tester.test(magicdelay) || tester.getInt(magicdelay) < 1) {
			errormsg += "持续回合数据不正确\n";
			error = true;
		}
		if (magicinfo.length() == 0) {
			magicinfo = "无相关说明\n";
		}
		if (magictype.endsWith("攻击")) {
			if (!tester.test(dmg)) {
				errormsg += "伤害数据不正确\n";
				error = true;
			}
		}
		if (magictype.equals("治疗")) {
			if (!tester.test(healhp)) {
				errormsg += "治疗效果数据不正确\n";
				error = true;
			}
		}
		if (magictype.equals("buff")) {
			if (!tester.test(buffhp)) {
				errormsg += "增益HP数据不正确\n";
				error = true;
			}
			if (!tester.test(buffmp)) {
				errormsg += "增益MP数据不正确\n";
				error = true;
			}
			if (!tester.test(buffdef)) {
				errormsg += "增益防御数据不正确\n";
				error = true;
			}
			if (!tester.test(buffmdef)) {
				errormsg += "增益魔防数据不正确\n";
				error = true;
			}
			if (!tester.test(buffstr)) {
				errormsg += "增益力量数据不正确\n";
				error = true;
			}
			if (!tester.test(buffmstr)) {
				errormsg += "增益魔力数据不正确\n";
				error = true;
			}
			if (!tester.test(buffhs)) {
				errormsg += "增益致命一击数据不正确\n";
				error = true;
			}
			if (!tester.test(buffjouk)) {
				errormsg += "增益躲闪数据不正确\n";
				error = true;
			}
		}

		if (magictype.equals("debuff")) {
			if (!tester.test(debuffhp)) {
				errormsg += "减益HP数据不正确\n";
				error = true;
			}
			if (!tester.test(debuffmp)) {
				errormsg += "减益MP数据不正确\n";
				error = true;
			}
			if (!tester.test(debuffdef)) {
				errormsg += "减益防御数据不正确\n";
				error = true;
			}
			if (!tester.test(debuffmdef)) {
				errormsg += "减益魔防数据不正确\n";
				error = true;
			}
			if (!tester.test(debuffstr)) {
				errormsg += "减益力量数据不正确\n";
				error = true;
			}
			if (!tester.test(debuffmstr)) {
				errormsg += "减益魔力数据不正确\n";
				error = true;
			}
			if (!tester.test(debuffhs)) {
				errormsg += "减益致命一击数据不正确\n";
				error = true;
			}
			if (!tester.test(debuffjouk)) {
				errormsg += "减益躲闪数据不正确\n";
				error = true;
			}
		}
		if (error) {
			JOptionPane.showMessageDialog(MagicCreaterPanel.this, errormsg,
					"错误", JOptionPane.ERROR_MESSAGE);
			return error;
		}
		return error;
	}

	public void save() {
		if (!checkfalse()) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(".//data//magic//"));
			chooser.setFileFilter(new DatFilter());
			int result = chooser.showSaveDialog(MagicCreaterPanel.this);
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
							MagicCreaterPanel.this, "此技能已经存在,是否覆盖?", "等待确认",
							JOptionPane.YES_NO_CANCEL_OPTION);
				}
				if (save == 0) {

					String saveData = "";
					saveData = magicname
							+ "\n"
							+ magictype
							+ "\n"
							+ attacktype
							+ "\n"
							+ magicproperty
							+ "\n"
							+ magicmotion
							+ "\n"
							+ needitem
							+ "\n"
							+ magicdelay
							+ "\n"
							+ magicinfo
							+ "\n"
							+ dmg
							+ "\n"
							+ needmp
							+ "\n"
							+ buffhp
							+ "\n"
							+ buffmp
							+ "\n"
							+ buffdef
							+ "\n"
							+ buffmdef
							+ "\n"
							+ buffstr
							+ "\n"
							+ buffmstr
							+ "\n"
							+ buffhs
							+ "\n"
							+ buffjouk
							+ "\n"
							+ healhp
							+ "\n"
							+ antidotal
							+ "\n"
							+ cleardebuff
							+ "\n"
							+ debuffhp
							+ "\n"
							+ debuffmp
							+ "\n"
							+ debuffdef
							+ "\n"
							+ debuffmdef
							+ "\n"
							+ debuffstr
							+ "\n"
							+ debuffmstr
							+ "\n"
							+ debuffhs
							+ "\n"
							+ debuffjouk
							+ "\n"
							+ "                                                                            ";

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

	public void track(String s) {
		GameLogger.logger.info("MagicCreater|" + s);
	}

	private String magicname;

	private String magictype;

	private String attacktype;

	private String magicproperty;

	private String magicmotion;

	private String needitem;

	private String magicdelay;

	private String magicinfo;

	// attack magic
	private String dmg;

	private String needmp;

	// buff magic
	private String buffhp;

	private String buffmp;

	private String buffdef;

	private String buffmdef;

	private String buffstr;

	private String buffmstr;

	private String buffhs;

	private String buffjouk;

	// heal magic
	private String healhp;

	private String antidotal;

	private String cleardebuff;

	// debuff magic
	private String debuffhp;

	private String debuffmp;

	private String debuffdef;

	private String debuffmdef;

	private String debuffstr;

	private String debuffmstr;

	private String debuffhs;

	private String debuffjouk;

	// ////////////////////////////////////////////////////////////////////////////////////////
	private JTextField magicnametext;

	private JComboBox magictypecombo;

	private JComboBox attacktypecombo;

	private JComboBox magicpropertycombo;

	private JTextField magicmotiontext;

	private JTextField needitemtext;

	private JTextField magicdelaytext;

	private JTextField magicinfotext;

	// attack magic
	private JTextField dmgtext;

	private JTextField needmptext;

	// buff magic
	private JTextField buffhptext;

	private JTextField buffmptext;

	private JTextField buffdeftext;

	private JTextField buffmdeftext;

	private JTextField buffstrtext;

	private JTextField buffmstrtext;

	private JTextField buffhstext;

	private JTextField buffjouktext;

	// heal magic
	private JTextField healhptext;

	private JComboBox antidotalcombo;

	private JComboBox cleardebuffcombo;

	// debuff magic
	private JTextField debuffhptext;

	private JTextField debuffmptext;

	private JTextField debuffdeftext;

	private JTextField debuffmdeftext;

	private JTextField debuffstrtext;

	private JTextField debuffmstrtext;

	private JTextField debuffhstext;

	private JTextField debuffjouktext;
}
