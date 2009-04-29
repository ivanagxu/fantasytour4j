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

		add(new JLabel("----------������Ϣ----------"));
		add(new JLabel(""));

		add(new JLabel("��������:"));
		magicnametext = new JTextField(10);
		add(magicnametext);

		add(new JLabel("��������:"));
		magictypecombo = new JComboBox();
		magictypecombo.addItem("����");
		magictypecombo.addItem("����");
		magictypecombo.addItem("buff");
		magictypecombo.addItem("debuff");
		add(magictypecombo);

		add(new JLabel("�����ļ�:"));
		magicmotiontext = new JTextField("˫�����ļ�");
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

		add(new JLabel("��Ҫ����:"));
		needmptext = new JTextField("0");
		add(needmptext);

		add(new JLabel("��Ҫ��Ʒ�ļ�:"));
		needitemtext = new JTextField("˫�����ļ�");
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

		add(new JLabel("�����غ�:"));
		magicdelaytext = new JTextField("1");
		add(magicdelaytext);

		add(new JLabel("����˵��"));
		magicinfotext = new JTextField(10);
		add(magicinfotext);

		add(new JLabel("----------����Ч��----------"));
		add(new JLabel(""));

		add(new JLabel("��������:"));
		attacktypecombo = new JComboBox();
		attacktypecombo.addItem("��ͨ����");
		attacktypecombo.addItem("ħ������");
		add(attacktypecombo);

		add(new JLabel("��������:"));
		magicpropertycombo = new JComboBox();
		magicpropertycombo.addItem("��");
		magicpropertycombo.addItem("ȫ");
		magicpropertycombo.addItem("��");
		magicpropertycombo.addItem("��");
		magicpropertycombo.addItem("ˮ");
		add(magicpropertycombo);

		add(new JLabel("�����˺�:"));
		dmgtext = new JTextField("0");
		add(dmgtext);

		add(new JLabel("----------����Ч��----------"));
		add(new JLabel(""));

		add(new JLabel("��HP����:"));
		add(buffhptext = new JTextField("0"));

		add(new JLabel("��MP����:"));
		add(buffmptext = new JTextField("0"));

		add(new JLabel("�Է�������:"));
		add(buffdeftext = new JTextField("0"));

		add(new JLabel("��ħ������:"));
		add(buffmdeftext = new JTextField("0"));

		add(new JLabel("����������:"));
		add(buffstrtext = new JTextField("0"));

		add(new JLabel("��ħ����������:"));
		add(buffmstrtext = new JTextField("0"));

		add(new JLabel("������һ������:"));
		add(buffhstext = new JTextField("0"));

		add(new JLabel("�Զ���������:"));
		add(buffjouktext = new JTextField("0"));

		add(new JLabel("----------�ָ�Ч��----------"));
		add(new JLabel(""));

		add(new JLabel("����Ч��:"));
		add(healhptext = new JTextField("0"));

		add(new JLabel("�Ƿ��ܽⶾ:"));
		antidotalcombo = new JComboBox();
		antidotalcombo.addItem("��");
		antidotalcombo.addItem("��");
		add(antidotalcombo);

		add(new JLabel("�Ƿ���debuff:"));
		cleardebuffcombo = new JComboBox();
		cleardebuffcombo.addItem("��");
		cleardebuffcombo.addItem("��");
		add(cleardebuffcombo);

		add(new JLabel("----------����Ч��----------"));
		add(new JLabel(""));

		add(new JLabel("��HP����:"));
		add(debuffhptext = new JTextField("0"));

		add(new JLabel("��MP����:"));
		add(debuffmptext = new JTextField("0"));

		add(new JLabel("�Է�������:"));
		add(debuffdeftext = new JTextField("0"));

		add(new JLabel("��ħ������:"));
		add(debuffmdeftext = new JTextField("0"));

		add(new JLabel("����������:"));
		add(debuffstrtext = new JTextField("0"));

		add(new JLabel("��ħ����������:"));
		add(debuffmstrtext = new JTextField("0"));

		add(new JLabel("������һ������:"));
		add(debuffhstext = new JTextField("0"));

		add(new JLabel("�Զ����ļ���:"));
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
		magicmotiontext.setText("˫�����ļ�");
		needitemtext.setText("˫�����ļ�");
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
		track("�ȴ��������ļ�...");
		int result = JOptionPane.showConfirmDialog(MagicCreaterPanel.this,
				"�Ƿ񱣴浱ǰ����?", "�ȴ�ȷ��", JOptionPane.YES_NO_CANCEL_OPTION);
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
		track("��ȡ�ļ�...");
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
		track("���¿ؼ�...");
		magicnametext.setText(magicname);
		if (magictype.equals("����"))
			magictypecombo.setSelectedIndex(0);
		else if (magictype.equals("����"))
			magictypecombo.setSelectedIndex(1);
		else if (magictype.equals("buff"))
			magictypecombo.setSelectedIndex(2);
		else if (magictype.equals("debuff"))
			magictypecombo.setSelectedIndex(3);
		if (attacktype.equals("��ͨ����"))
			attacktypecombo.setSelectedIndex(0);
		else if (attacktype.equals("ħ������"))
			attacktypecombo.setSelectedIndex(1);
		if (magicproperty.equals("��"))
			magicpropertycombo.setSelectedIndex(0);
		else if (magicproperty.equals("ȫ"))
			magicpropertycombo.setSelectedIndex(1);
		else if (magicproperty.equals("��"))
			magicpropertycombo.setSelectedIndex(2);
		else if (magicproperty.equals("��"))
			magicpropertycombo.setSelectedIndex(3);
		else if (magicproperty.equals("ˮ"))
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
		if (antidotal.equals("��"))
			antidotalcombo.setSelectedIndex(0);
		else if (antidotal.equals("��"))
			antidotalcombo.setSelectedIndex(1);
		if (cleardebuff.equals("��"))
			cleardebuffcombo.setSelectedIndex(0);
		else if (cleardebuff.equals("��"))
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
		track("�������...");
		boolean error = false;
		String errormsg = "";
		magicname = magicnametext.getText();
		magictype = (String) magictypecombo.getSelectedItem();
		attacktype = (String) attacktypecombo.getSelectedItem();
		magicproperty = (String) magicpropertycombo.getSelectedItem();
		magicmotion = magicmotiontext.getText();
		needitem = needitemtext.getText();
		if (needitem.equals("˫�����ļ�"))
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
			errormsg += "�����뼼������\n";
			error = true;
		}
		if (magicmotion.length() == 0 || !magicmotion.endsWith(".dat")) {
			errormsg += "���ܶ����ļ�����\n";
			error = true;
		}
		if (needitem.length() != 0) {
			if (!needitem.equals("˫�����ļ�") && !needitem.endsWith(".dat")) {
				errormsg += "��Ҫ��Ʒ�ļ�����\n";
				error = true;
			}
		}
		if (!tester.test(magicdelay) || tester.getInt(magicdelay) < 1) {
			errormsg += "�����غ����ݲ���ȷ\n";
			error = true;
		}
		if (magicinfo.length() == 0) {
			magicinfo = "�����˵��\n";
		}
		if (magictype.endsWith("����")) {
			if (!tester.test(dmg)) {
				errormsg += "�˺����ݲ���ȷ\n";
				error = true;
			}
		}
		if (magictype.equals("����")) {
			if (!tester.test(healhp)) {
				errormsg += "����Ч�����ݲ���ȷ\n";
				error = true;
			}
		}
		if (magictype.equals("buff")) {
			if (!tester.test(buffhp)) {
				errormsg += "����HP���ݲ���ȷ\n";
				error = true;
			}
			if (!tester.test(buffmp)) {
				errormsg += "����MP���ݲ���ȷ\n";
				error = true;
			}
			if (!tester.test(buffdef)) {
				errormsg += "����������ݲ���ȷ\n";
				error = true;
			}
			if (!tester.test(buffmdef)) {
				errormsg += "����ħ�����ݲ���ȷ\n";
				error = true;
			}
			if (!tester.test(buffstr)) {
				errormsg += "�����������ݲ���ȷ\n";
				error = true;
			}
			if (!tester.test(buffmstr)) {
				errormsg += "����ħ�����ݲ���ȷ\n";
				error = true;
			}
			if (!tester.test(buffhs)) {
				errormsg += "��������һ�����ݲ���ȷ\n";
				error = true;
			}
			if (!tester.test(buffjouk)) {
				errormsg += "����������ݲ���ȷ\n";
				error = true;
			}
		}

		if (magictype.equals("debuff")) {
			if (!tester.test(debuffhp)) {
				errormsg += "����HP���ݲ���ȷ\n";
				error = true;
			}
			if (!tester.test(debuffmp)) {
				errormsg += "����MP���ݲ���ȷ\n";
				error = true;
			}
			if (!tester.test(debuffdef)) {
				errormsg += "����������ݲ���ȷ\n";
				error = true;
			}
			if (!tester.test(debuffmdef)) {
				errormsg += "����ħ�����ݲ���ȷ\n";
				error = true;
			}
			if (!tester.test(debuffstr)) {
				errormsg += "�����������ݲ���ȷ\n";
				error = true;
			}
			if (!tester.test(debuffmstr)) {
				errormsg += "����ħ�����ݲ���ȷ\n";
				error = true;
			}
			if (!tester.test(debuffhs)) {
				errormsg += "��������һ�����ݲ���ȷ\n";
				error = true;
			}
			if (!tester.test(debuffjouk)) {
				errormsg += "����������ݲ���ȷ\n";
				error = true;
			}
		}
		if (error) {
			JOptionPane.showMessageDialog(MagicCreaterPanel.this, errormsg,
					"����", JOptionPane.ERROR_MESSAGE);
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
							MagicCreaterPanel.this, "�˼����Ѿ�����,�Ƿ񸲸�?", "�ȴ�ȷ��",
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
