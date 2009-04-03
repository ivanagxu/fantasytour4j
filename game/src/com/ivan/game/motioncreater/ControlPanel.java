package com.ivan.game.motioncreater;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ivan.game.unit.DatFilter;
import com.ivan.game.unit.GifFilter;
import com.ivan.game.unit.NumberTester;

public class ControlPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1416520547112921960L;

	public ControlPanel(MotionPanel p) {
		setBackground(Color.ORANGE);

		motionpanel = p;
		motiontype = "变换";
		timespacetext = new JTextField("1000", 10);
		delaytimetext = new JTextField("1000", 10);
		imagelistcombo = new JComboBox();
		imagelist = new ArrayList();
		imagenamelist = new ArrayList();

		JButton addimagebutton = new JButton("增加图片");
		addimagebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addImage();
			}
		});
		JButton deleteimagebutton = new JButton("删除图片");
		deleteimagebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteImage();
			}
		});

		JButton startbutton = new JButton("演示动画");
		startbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		JButton closebutton = new JButton("停止动画");
		closebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});

		this.setLayout(new GridLayout(5, 2));
		add(new JLabel("间隔时间:"));
		add(timespacetext);
		//add(new JLabel("停留时间:"));
		//add(delaytimetext);

		add(new JLabel("动画类型:"));
		motiontypecombo = new JComboBox();
		motiontypecombo.addItem("变换");
		motiontypecombo.addItem("平移");
		motiontypecombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				motiontype = (String) motiontypecombo.getSelectedItem();
			}
		});
		add(motiontypecombo);

		add(addimagebutton);
		add(deleteimagebutton);
		add(new JLabel("现有图片:"));
		add(imagelistcombo);
		add(startbutton);
		add(closebutton);

	}

	/*
	 * 添加图片
	 */
	public void addImage() {
		if (started == true) {
			JOptionPane.showMessageDialog(ControlPanel.this, "请先停止动画", "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("./data/images/"));
		chooser.setFileFilter(new GifFilter());
		int result = chooser.showOpenDialog(ControlPanel.this);
		if (result == 0) {
			String filename = chooser.getSelectedFile().getName();
			String filepath = chooser.getSelectedFile().getPath();
			//转换为相对路径
			String path = filepath.substring(filepath.lastIndexOf("data"));

			Image image;
			image = new ImageIcon(path).getImage();
			if (image != null) {

				imagelistcombo.addItem(filename);
				imagelist.add(image);
				imagenamelist.add(path);
				track("添加图片成功...");
			}
		}
	}

	/*
	 * 删除所有加载的图片
	 */
	public void deleteImage() {
		if (started == true) {
			JOptionPane.showMessageDialog(ControlPanel.this, "请先停止动画", "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (imagelistcombo.getItemCount() == 0) {
			JOptionPane.showMessageDialog(ControlPanel.this, "现在没有图片", "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		track("删除图片...");
		imagelistcombo.removeAllItems();
		imagelistcombo.repaint();
		imagelist.clear();
		imagenamelist.clear();
	}

	/*
	 * 开始播放动画,该函数调用MotionPanel的start()
	 */
	public void start() {
		if (checkfalse()) {
			return;
		}
		if (started == false) {
			started = true;
			track("动画开始...");
			motionpanel.start(Integer.parseInt(timespace), Integer
					.parseInt(delaytime), motiontype, imagelist);
		} else {
			JOptionPane.showMessageDialog(ControlPanel.this, "动画已经开始", "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

	}

	/*
	 * 停止动画
	 */
	public void stop() {
		if (started == false) {
			JOptionPane.showMessageDialog(ControlPanel.this, "动画还没启动", "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			started = false;
			track("动画停止...");
			motionpanel.stop();
		}
	}

	/*
	 * 初始化私有成员变量
	 */
	public void init() {
		imagelist.clear();
		imagenamelist.clear();
		motiontypecombo.setSelectedIndex(0);
		motiontype = "变换";
		motionname = "";
		imagelistcombo.removeAllItems();
		imagelistcombo.repaint();

		timespace = "1000";
		delaytime = "1000";
		timespacetext.setText(timespace);
	}

	/*
	 * 判断如何建立新文件
	 */
	public void newFile() {
		if (started == true) {
			JOptionPane.showMessageDialog(ControlPanel.this, "动画没停止", "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		track("等待建立新文件...");
		int result = JOptionPane.showConfirmDialog(ControlPanel.this,
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

	/*
	 * 检测数据,数据通过则返回false
	 */
	public boolean checkfalse() {
		timespace = timespacetext.getText();
		delaytime = delaytimetext.getText();
		String errormsg = "";
		boolean error = false;
		NumberTester tester = new NumberTester();
		if (timespace.length() == 0 || !tester.test(timespace)) {
			errormsg += "间隔时间不对\n";
			error = true;
		}
		if (delaytime.length() == 0 || !tester.test(delaytime)) {
			errormsg += "停留时间不对\n";
			error = true;
		}
		if (imagelistcombo.getItemCount() == 0) {
			errormsg += "请先添加图片\n";
			error = true;
		}
		if (error) {
			JOptionPane.showMessageDialog(ControlPanel.this, errormsg, "错误",
					JOptionPane.ERROR_MESSAGE);
		}
		return error;
	}

	/*
	 * 保存文件
	 */
	public void save() {
		if (!checkfalse()) {
			motionname = JOptionPane.showInputDialog(ControlPanel.this,
					"请输入该动画名称");
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(".//data//motion//"));
			chooser.setFileFilter(new DatFilter());
			int result = chooser.showSaveDialog(ControlPanel.this);
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
					save = JOptionPane.showConfirmDialog(ControlPanel.this,
							"此动画已经存在,是否覆盖?", "等待确认",
							JOptionPane.YES_NO_CANCEL_OPTION);
				}
				if (save == 0) {
					String saveData = "";
					saveData = motionname + "\n" + timespace + "\n" + delaytime
							+ "\n" + motiontype + "\n";
					for (int i = 0; i < imagenamelist.size(); i++) {
						saveData += (String) imagenamelist.get(i);
						saveData += "\n";
					}
					saveData += "#";
					saveData += "\n";
					saveData += "                                                                  ";
					try {
						FileOutputStream out = new FileOutputStream(f);
						out.write(saveData.getBytes(), 0, saveData.length());
						out.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/*
	 * 读取文件
	 * @param f 要读取的文件
	 */
	public void readFile(File f) {
		track("读取文件...");
		init();
		timespace = "";
		delaytime = "";
		motiontype = "";
		String filepath = f.getPath();
		File openfile = new File(filepath);
		try {
			FileInputStream in = new FileInputStream(openfile);
			byte[] readbuff = new byte[(int) openfile.length()];
			in.read(readbuff, 0, (int) openfile.length());
			String readData = new String(readbuff);
			int i = 0;
			while (readData.charAt(i) != '\n') {
				motionname += readData.charAt(i);
				i++;
			}
			i++;
			while (readData.charAt(i) != '\n') {
				timespace += readData.charAt(i);
				i++;
			}
			i++;
			while (readData.charAt(i) != '\n') {
				delaytime += readData.charAt(i);
				i++;
			}
			i++;
			while (readData.charAt(i) != '\n') {
				motiontype += readData.charAt(i);
				i++;
			}
			i++;
			String imagename = "";
			while (readData.charAt(i) != '#') {
				if (readData.charAt(i) != '\n') {
					imagename += readData.charAt(i);
					i++;
				} else {
					i++;
					imagenamelist.add(imagename);
					File imagefile = new File(imagename);
					Image image = new ImageIcon(imagefile.getPath()).getImage();
					if (image != null) {

						imagelistcombo.addItem(imagefile.getName());
						imagelist.add(image);
						track("添加图片成功...");
						imagename = "";
					}
				}
			}
			timespacetext.setText(timespace);
			if (motiontype.equals("变换")) {
				motiontypecombo.setSelectedIndex(0);
			} else if (motiontype.equals("平移")) {
				motiontypecombo.setSelectedIndex(1);
			} else {
				JOptionPane.showMessageDialog(ControlPanel.this, "wrong", "错误",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 调试函数
	 * @param s 信息框输出的信息
	 */
	public void track(String s) {
		System.out.println(s);
	}

	private JTextField timespacetext;

	private JTextField delaytimetext;

	private String timespace = "1000";

	private String delaytime = "1000";

	private String motiontype;

	private String motionname;

	private ArrayList imagelist;

	private ArrayList imagenamelist;

	private boolean started = false;

	private JComboBox imagelistcombo;

	private JComboBox motiontypecombo;

	private MotionPanel motionpanel;
}
