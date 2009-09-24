package com.ivan.game.unit;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.ivan.game.logger.GameLogger;
import com.ivan.game.managers.EventManager;
import com.ivan.game.managers.ItemManager;
import com.ivan.game.managers.MagicManager;

public class Hero {
	/*
	 * 以文件创建主角(读取记录用)
	 * @param f 存档文件
	 */
	public Hero(File f) {
		track("正在读取主角存档...");
		if (f == null) {
			abort("读取存档出错!找不到存档!");
		}
		itemlist = new ArrayList();
		itemnumlist = new ArrayList();
		eventlist = new ArrayList();
		magiclist = new ArrayList();
		leavenpclist = new ArrayList();
		battlemagic = new Magic[4];
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(f),"utf-8"));
			name = in.readLine();
			property = in.readLine();
			hp = Integer.parseInt(in.readLine());
			hpmx = Integer.parseInt(in.readLine());
			mp = Integer.parseInt(in.readLine());
			mpmx = Integer.parseInt(in.readLine());
			str = Integer.parseInt(in.readLine());
			def = Integer.parseInt(in.readLine());
			mstr = Integer.parseInt(in.readLine());
			mdef = Integer.parseInt(in.readLine());
			hs = Integer.parseInt(in.readLine());
			jouk = Integer.parseInt(in.readLine());
			money = Integer.parseInt(in.readLine());
			level = Integer.parseInt(in.readLine());
			exp = Integer.parseInt(in.readLine());
			expnext = Integer.parseInt(in.readLine());
			mapfile = in.readLine();
			x = Integer.parseInt(in.readLine());
			y = Integer.parseInt(in.readLine());

			in.close();

			File elf = new File(f.getPath() + "el");
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					elf),"utf-8"));
			String event = "";
			event = in.readLine();
			while (event.length() != 0) {
				eventlist.add(event);
				event = in.readLine();
			}
			in.close();

			File ilf = new File(f.getPath() + "il");
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					ilf),"utf-8"));
			String item = "";
			item = in.readLine();
			while (item.length() != 0) {
				itemnumlist.add(item.substring(0, 2));
				itemlist.add(item.substring(2));
				item = in.readLine();
			}
			in.close();

			File mlf = new File(f.getPath() + "ml");
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					mlf),"utf-8"));
			String magic = "";
			magic = in.readLine();
			int i = 0;
			while (magic.length() != 0) {
				if (magic.startsWith("@")) {
					magic = magic.substring(1);
					if (i > 3)
						abort("战斗技能超过4个!!");
					battlemagic[i] = new Magic(new File(magic));
					i++;
				}
				magiclist.add(magic);
				magic = in.readLine();
			}
			in.close();

			File nlf = new File(f.getPath() + "nl");
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					nlf),"utf-8"));
			String npcname = "";
			npcname = in.readLine();
			while (npcname.length() != 0) {
				leavenpclist.add(npcname);
				npcname = in.readLine();
			}
			in.close();

			mapimage = new Image[8];
			mapimage[0] = new ImageIcon("data/images/hero/hero-front.gif")
					.getImage();
			mapimage[1] = new ImageIcon("data/images/hero/hero-back.gif")
					.getImage();
			mapimage[2] = new ImageIcon("data/images/hero/hero-left.gif")
					.getImage();
			mapimage[3] = new ImageIcon("data/images/hero/hero-right.gif")
					.getImage();
			mapimage[4] = new ImageIcon("data/images/hero/hero-front2.gif")
					.getImage();
			mapimage[5] = new ImageIcon("data/images/hero/hero-back2.gif")
					.getImage();
			mapimage[6] = new ImageIcon("data/images/hero/hero-left2.gif")
					.getImage();
			mapimage[7] = new ImageIcon("data/images/hero/hero-right2.gif")
					.getImage();

			if (mapimage[0] == null || mapimage[1] == null
					|| mapimage[2] == null || mapimage[3] == null) {
				abort("主角地图贴图丢失!");
			}

			battleimage = new Image[6];
			/*
			 * for no more image,the 6 images are the same,I will add more later.
			 */
			battleimage[0] = new ImageIcon("data/images/hero/hero-stand.gif")
					.getImage();
			battleimage[1] = new ImageIcon("data/images/hero/hero-stand.gif")
					.getImage();
			battleimage[2] = new ImageIcon(
					"data/images/hero/hero-stand2.gif").getImage();
			battleimage[3] = new ImageIcon("data/images/hero/hero-stand.gif")
					.getImage();
			battleimage[4] = new ImageIcon("data/images/hero/hero-stand.gif")
					.getImage();
			battleimage[5] = new ImageIcon("data/images/hero/hero-stand.gif")
					.getImage();

			if (battleimage[0] == null || battleimage[1] == null
					|| battleimage[2] == null || battleimage[3] == null
					|| battleimage[4] == null || battleimage[5] == null) {
				abort("主角战斗贴图丢失!");
			}

			state = new BattleState(name, property, hp, hpmx, mp, mpmx, str,
					def, mstr, mdef, hs, jouk);
			track("主角存档读取完成.\n");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 以默认值创建主角
	 */
	public Hero(String name) {
		track("创建新主角...");
		if (name == null) {
			name = "defaultplayer";
			track("主角名字为空!以默认名defaultplayer创建默认主角...");
		}
		if (name.length() == 0) {
			name = "defaultplayer";
			track("主角名字为空!以默认名defaultplayer创建默认主角...");
		}
		this.name = name;
		property = "普";
		hp = 10;
		hpmx = 10;
		mp = 10;
		mpmx = 10;
		str = 10;
		def = 10;
		mstr = 10;
		mdef = 10;
		hs = 1;
		jouk = 1;
		money = 100;
		level = 1;
		exp = 0;
		expnext = 20 + (int) Math.pow(1.3, (level + 1))
				- (int) Math.pow(1.3, (level));

		eventlist = new ArrayList();
		itemlist = new ArrayList();
		magiclist = new ArrayList();
		itemnumlist = new ArrayList();
		leavenpclist = new ArrayList();
		battlemagic = new Magic[4];
		for (int i = 0; i < 4; i++) {
			battlemagic[i] = null;
		}

		mapimage = new Image[8];
		mapimage[0] = new ImageIcon("data/images/hero/hero-front.gif")
				.getImage();
		mapimage[1] = new ImageIcon("data/images/hero/hero-back.gif")
				.getImage();
		mapimage[2] = new ImageIcon("data/images/hero/hero-left.gif")
				.getImage();
		mapimage[3] = new ImageIcon("data/images/hero/hero-right.gif")
				.getImage();
		mapimage[4] = new ImageIcon("data/images/hero/hero-front2.gif")
				.getImage();
		mapimage[5] = new ImageIcon("data/images/hero/hero-back2.gif")
				.getImage();
		mapimage[6] = new ImageIcon("data/images/hero/hero-left2.gif")
				.getImage();
		mapimage[7] = new ImageIcon("data/images/hero/hero-right2.gif")
				.getImage();
		if (mapimage[0] == null || mapimage[1] == null || mapimage[2] == null
				|| mapimage[3] == null) {
			abort("主角地图贴图丢失!");
		}

		battleimage = new Image[6];
		/*
		 * for no more image,the 6 images are the same,I will add more later.
		 */
		battleimage[0] = new ImageIcon("data/images/hero/hero-stand.gif")
				.getImage();
		battleimage[1] = new ImageIcon("data/images/hero/hero-stand.gif")
				.getImage();
		battleimage[2] = new ImageIcon("data/images/hero/hero-stand.gif")
				.getImage();
		battleimage[3] = new ImageIcon("data/images/hero/hero-stand.gif")
				.getImage();
		battleimage[4] = new ImageIcon("data/images/hero/hero-stand.gif")
				.getImage();
		battleimage[5] = new ImageIcon("data/images/hero/hero-stand.gif")
				.getImage();

		if (battleimage[0] == null || battleimage[1] == null
				|| battleimage[2] == null || battleimage[3] == null
				|| battleimage[4] == null || battleimage[5] == null) {
			abort("主角战斗贴图丢失!");
		}

		mapfile = "data/map/home.dat";
		x = 1;
		y = 2;
		magiclist.add("data/magic/handattack.dat");
		battlemagic[0] = new Magic(new File("data/magic/handattack.dat"));

		state = new BattleState(name, property, hp, hpmx, mp, mpmx, str, def,
				mstr, mdef, hs, jouk);
		track("主角创建完成.\n");
	}

	/*
	 * 获取主角战斗数据
	 */
	public BattleState getBattleState() {
		return state;
	}

	/*
	 * 保存主角资料
	 */
	public void save() {
		track("正在保存...");
		File f = new File("data/save/" + name + ".sav");
		String saveData = "";
		saveData = name
				+ "\n"
				+ property
				+ "\n"
				+ hp
				+ "\n"
				+ hpmx
				+ "\n"
				+ mp
				+ "\n"
				+ mpmx
				+ "\n"
				+ str
				+ "\n"
				+ def
				+ "\n"
				+ mstr
				+ "\n"
				+ mdef
				+ "\n"
				+ hs
				+ "\n"
				+ jouk
				+ "\n"
				+ money
				+ "\n"
				+ level
				+ "\n"
				+ exp
				+ "\n"
				+ expnext
				+ "\n"
				+ mapfile
				+ "\n"
				+ x
				+ "\n"
				+ y
				+ "\n"
				+ "                                                                        ";
		try {
			/*
			 * 保存基本数据
			 */
			FileOutputStream out = new FileOutputStream(f);
			out.write(saveData.getBytes("utf-8"), 0, saveData.length());
			out.close();
			/*
			 * 保存事件列表
			 */
			f = new File("data/save/" + name + ".savel");
			FileOutputStream out1 = new FileOutputStream(f);
			for (int i = 0; i < eventlist.size(); i++) {
				saveData = (String) eventlist.get(i) + "\n";
				out1.write(saveData.getBytes("utf-8"), 0, saveData.length());
			}
			saveData = "\n                                                                                                                                         ";
			out1.write(saveData.getBytes("utf-8"), 0, saveData.length());
			out1.close();
			/*
			 * 保存物品列表
			 */
			f = new File("data/save/" + name + ".savil");
			FileOutputStream out2 = new FileOutputStream(f);
			for (int i = 0; i < itemlist.size(); i++) {
				saveData = (String) itemnumlist.get(i)
						+ (String) itemlist.get(i) + "\n";
				out2.write(saveData.getBytes("utf-8"), 0, saveData.length());
			}
			saveData = "\n                                                                                                                                         ";
			out2.write(saveData.getBytes("utf-8"), 0, saveData.length());
			out2.close();
			/*
			 * 保存技能列表
			 */
			f = new File("data/save/" + name + ".savml");
			FileOutputStream out3 = new FileOutputStream(f);

			boolean use = false;
			for (int i = 0; i < magiclist.size(); i++) {
				use = false;
				for (int j = 0; j < 4; j++) {
					if (battlemagic[j] != null) {
						if (battlemagic[j].getName().equals(
								(String) magicnamelist.get(i))) {
							use = true;
							//magiclist.set(i,"@"+(String)magiclist.get(i));
						}
						System.out.println(battlemagic[j].getName());
					}
				}
				if (use == true) {
					saveData = "@" + (String) magiclist.get(i) + "\n";
				} else {
					saveData = (String) magiclist.get(i) + "\n";
				}
				out3.write(saveData.getBytes("utf-8"), 0, saveData.length());
			}
			saveData = "\n                                                                                                                                                                       ";
			out3.write(saveData.getBytes("utf-8"), 0, saveData.length());
			out3.close();

			/*
			 * 保存Npc列表
			 */
			f = new File("data/save/" + name + ".savnl");
			FileOutputStream out4 = new FileOutputStream(f);

			for (int i = 0; i < leavenpclist.size(); i++) {
				saveData = (String) leavenpclist.get(i) + "\n";
				out4.write(saveData.getBytes("utf-8"), 0, saveData.getBytes("utf-8").length);
			}
			saveData = "\n                                                                                                                                                                       ";
			out4.write(saveData.getBytes("utf-8"), 0, saveData.length());
			out4.close();

			track("主角记录保存完毕.\n");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 获取主角当前贴图
	 * face = 0 正面
	 * 1		背面
	 * 2		左
	 * 3		右
	 */
	public Image getCurrentImage() {
		if (face > -1 && face < 4)
			return mapimage[face];
		else {
			abort("主角方向错误! now face = " + face);
			return null;
		}
	}

	public Image getCurrentImage2() {
		if (face > -1 && face < 4)
			return mapimage[face + 4];
		else {
			abort("主角方向错误! now face = " + face);
			return null;
		}
	}

	/*
	 * 主角向上移动
	 * @param map 移动所在的地图
	 */
	public void moveUp(Map map) {
		setFace(1);
		if (y > 0) {
			if (map.getMapPoint(x, y - 1).IsWalkabel()) {
				if (map.getMapPoint(x, y - 1).hasNpc()) {
					if (map.getMapPoint(x, y - 1).anpc.isLeave())
						y--;
				} else {
					y--;
				}
			}
		}
		if (map.getMapPoint(x, y).hasEnemy()) {
			double met = Math.random() * 100;
			if (met < map.getMapPoint(x, y).getEnemyAttackProbability()) {
				enemy = new Enemy(new File(map.getMapPoint(x, y).enemy));
				setBattle(enemy, true);
			}
		}

	}

	/*
	 * 主角向下移动
	 * @param map 移动所在的地图
	 */
	public void moveDown(Map map) {
		setFace(0);
		if (y < map.getMapHeight() - 1) {
			if (map.getMapPoint(x, y + 1).IsWalkabel()) {
				if (map.getMapPoint(x, y + 1).hasNpc()) {
					if (map.getMapPoint(x, y + 1).anpc.isLeave())
						y++;
				} else {
					y++;
				}
			}
		}
		if (map.getMapPoint(x, y).hasEnemy()) {
			double met = Math.random() * 100;
			if (met < map.getMapPoint(x, y).getEnemyAttackProbability()) {
				enemy = new Enemy(new File(map.getMapPoint(x, y).enemy));
				setBattle(enemy, true);
			}
		}
	}

	/*
	 * 主角向左移动
	 * @param map 移动所在的地图
	 */
	public void moveLeft(Map map) {
		setFace(2);
		if (x > 0) {
			if (map.getMapPoint(x - 1, y).IsWalkabel()) {
				if (map.getMapPoint(x - 1, y).hasNpc()) {
					if (map.getMapPoint(x - 1, y).anpc.isLeave())
						x--;
				} else {
					x--;
				}
			}
		}
		if (map.getMapPoint(x, y).hasEnemy()) {
			double met = Math.random() * 100;
			if (met < map.getMapPoint(x, y).getEnemyAttackProbability()) {
				enemy = new Enemy(new File(map.getMapPoint(x, y).enemy));
				setBattle(enemy, true);
			}
		}
	}

	/*
	 * 主角向右移动
	 * @param map 移动所在的地图
	 */
	public void moveRight(Map map) {
		setFace(3);
		if (x < map.getMapWidth() - 1) {
			if (map.getMapPoint(x + 1, y).IsWalkabel()) {
				if (map.getMapPoint(x + 1, y).hasNpc()) {
					if (map.getMapPoint(x + 1, y).anpc.isLeave())
						x++;
				} else {
					x++;
				}
			}
		}
		if (map.getMapPoint(x, y).hasEnemy()) {
			double met = Math.random() * 100;
			if (met < map.getMapPoint(x, y).getEnemyAttackProbability()) {
				enemy = new Enemy(new File(map.getMapPoint(x, y).enemy));
				setBattle(enemy, true);
			}
		}
	}

	/*
	 * @param f 方向
	 */
	private void setFace(int f) {
		if (f > -1 && f < 4)
			face = f;
		else {
			abort("主角方向设置遇到非法变量!f = " + f);
		}
	}

	/*
	 * @param e 增加的经验
	 */
	public boolean increaseExp(int e) {
		boolean b = false;
		exp += e;
		while (exp - expnext > 0) {
			exp = exp - expnext;
			levelup();
			b = true;
		}
		return b;
	}

	/*
	 * @return 返回主角方向
	 */
	public int getFace() {
		return face;
	}

	/*
	 * @return 返回主角经验值
	 */
	public int getExp() {
		return exp;
	}

	/*
	 * @return 返回主角下一级所需经验值
	 */
	public int getExpNext() {
		return expnext;
	}

	/*
	 * @return 返回主角等级
	 */
	public int getLevel() {
		return level;
	}

	/*
	 * @return 返回主角所处的地图文件名
	 */
	public String getMapFile() {
		return mapfile;
	}

	/*
	 * @return 返回主角横坐标
	 */
	public int getX() {
		return x;
	}

	/*
	 * @return 返回主角纵坐标
	 */
	public int getY() {
		return y;
	}

	/*
	 * @param s 设置主角战斗数据,一般战斗后调用;
	 */
	public void setState(BattleState s) {
		state = s;
		this.name = state.getName();
		this.property = state.getPropertyString();
		this.str = state.getStr();
		this.def = state.getDef();
		this.hp = state.getHp();
		this.hpmx = state.getHpmx();
		this.mp = state.getMp();
		this.mpmx = state.getMpmx();
		this.jouk = state.getJouk();
		this.hs = state.getHs();
		this.mstr = state.getMstr();
		this.mdef = state.getMdef();

		if (property == null) {
			abort("从battle state得来的state出现属性变异!");
		}
	}

	/*
	 * 让主角回复一点生命和法力
	 */
	public void recover() {
		hp += 1;
		mp += 1;
		if (hp > hpmx)
			hp = hpmx;
		if (mp > mpmx)
			mp = mpmx;
	}

	/*
	 * 让主角回复指定生命和法力
	 */
	public void recover(int ahp, int amp) {
		hp += ahp;
		mp += amp;
		if (hp > hpmx)
			hp = hpmx;
		if (mp > mpmx)
			mp = mpmx;
		state.recover(ahp, amp, false);
	}

	/*
	 * 让主角生命和法力完全回复
	 */
	public void fullyRecover() {
		hp = hpmx;
		mp = mpmx;
		state.fullyRecover();
	}

	/*
	 * 提升主角等级
	 */
	private void levelup() {
		expnext = 20 + (int) Math.pow(1.3, (level + 1))
				- (int) Math.pow(1.3, (level));
		level++;
		hp += 1 + 4 * Math.random();
		hpmx += 1 + 4 * Math.random();
		mp += 1 + 3 * Math.random();
		mpmx += 1 + 3 * Math.random();
		str += 1 + 3 * Math.random();
		def += 1 + 2 * Math.random();
		mstr += 1 + 3 * Math.random();
		mdef += 1 + 2 * Math.random();
		fullyRecover();
		state = new BattleState(name, property, hp, hpmx, mp, mpmx, str, def,
				mstr, mdef, hs, jouk);
	}

	/*
	 * 让主角执行事件
	 * @param e 要执行的事件
	 * @return 
	 */
	public boolean executeEvent(Event e) {
		boolean canexecute = true;
		if (e.isNeedEvent()) {
			canexecute = false;
			for (int i = 0; i < eventlist.size(); i++) {
				if (e.getNeedEvent().equals((String) eventlist.get(i))) {
					//System.out.println("!!!");
					canexecute = true;
					break;
				}
			}
		}
		if (e.isNeedItem()) {
			canexecute = false;
			for (int i = 0; i < itemlist.size(); i++) {
				if (e.getNeedItem().equals((String) itemlist.get(i))) {
					canexecute = true;
					break;
				}
			}
		}
		if (canexecute) {
			if (!e.getFollowEvent().endsWith("default.dat")) {
				nextevent = new Event(new File(e.getFollowEvent()));
			} else {
				nextevent = null;
			}
			if (e.getType() == Event.TRANS_EVENT) {
				if (!e.getMapFileName().endsWith("default.dat")) {
					GameLogger.logger.info("执行事件:" + e.getName() + "|"
							+ e.getFilename());
					mapfile = e.getMapFileName();
					x = e.getTargetMapX();
					y = e.getTargetMapY();
				}
			} else if (e.getType() == Event.ITEM_EVENT) {
				for (int i = 0; i < eventnamelist.size(); i++) {
					//物品事件已经完成则不执行
					if (e.getName().equals((String) eventnamelist.get(i)))
						return false;
				}
				GameLogger.logger.info("执行事件:" + e.getName() + "|"
						+ e.getFilename());
				Item item = new Item(new File(e.getItem()));
				addItem(item);
			} else if (e.getType() == Event.BATTLE_EVENT) {
				GameLogger.logger.info("执行事件:" + e.getName() + "|"
						+ e.getFilename());
				Enemy aenemy = new Enemy(new File(e.getEnemy()));
				setBattle(aenemy, true);
			} else if (e.getType() == Event.MONEY_EVENT) {
				GameLogger.logger.info("执行事件:" + e.getName() + "|"
						+ e.getFilename());
				money += e.getMoney();
			} else if (e.getType() == Event.LEARN_EVENT) {
				//在learnMagic函数里面判断是否已经发生
				Magic magic = new Magic(new File(e.getMagic()));
				if (!learnMagic(e.getMagic(), magic.getName())) {
					return false;
				}
				GameLogger.logger.info("执行事件:" + e.getName() + "|"
						+ e.getFilename());
			}
			for (int i = 0; i < eventlist.size(); i++) {
				if (eventlist.get(i).equals("data/event/" + e.getFilename()))
					return canexecute;
			}
			eventlist.add("data/event/" + e.getFilename());
			eventnamelist.add(e.getName());
			return canexecute;
		}
		return canexecute;
	}

	/*
	 * 让主角学会一个技能
	 * @param magicfile 技能文件
	 * @param magicname 技能名称
	 */
	public boolean learnMagic(String magicfile, String magicname) {
		for (int i = 0; i < magiclist.size(); i++) {
			if (magicname.equals((String) magicnamelist.get(i)))
				return false;
		}
		magiclist.add(magicfile);
		magicnamelist.add(magicname);
		return true;
	}

	public int hasItem(Item item) {
		for (int i = 0; i < itemnamelist.size(); i++) {
			if (item.getName().equals((String) itemnamelist.get(i))) {
				return i;
			}
		}
		return -1;
	}

	/*
	 * 让主角获得一个物品
	 * @param item 要获得的物品
	 */
	public void addItem(Item item) {
		for (int i = 0; i < itemlist.size(); i++) {
			if (item.getName().equals((String) itemnamelist.get(i))) {
				int num = Integer.parseInt((String) itemnumlist.get(i));
				if (num < 99)
					num++;
				if (num < 10)
					itemnumlist.set(i, "0" + (new Integer(num).toString()));
				else
					itemnumlist.set(i, new Integer(num).toString());
				return;
			}
		}
		itemlist.add("data/item/" + item.getFileName());
		itemnamelist.add(item.getName());
		itemnumlist.add("01");
	}

	public void removeItem(int index, int num) {
		int hasnum = Integer.parseInt((String) itemnumlist.get(index));
		if (num >= hasnum) {
			itemlist.remove(index);
			itemnamelist.remove(index);
			itemnumlist.remove(index);
		} else {
			hasnum = hasnum - num;
			if (hasnum < 10)
				itemnumlist.set(index, "0" + (new Integer(hasnum).toString()));
			else
				itemnumlist.set(index, new Integer(hasnum).toString());
		}
	}

	/*
	 * 让主角使用一个指定的物品
	 * @param e 使用的物品
	 * @param index 物品在物品列表的位置
	 */
	public boolean useItem(Item e, int index) {
		
			if (e.getItemType() == Item.NORMAL_ITEM && !inbattle) {
				recover(e.getHealHp(), e.getRecoverMp());
			} else if (e.getItemType() == Item.BATTLE_ITEM && inbattle) {
				state.recover(e.getHealHp(), e.getRecoverMp(), e.IsAntidotal());
			} else if(e.getItemType() == Item.BATTLE_ITEM && !inbattle){
				return false;
			} else if(e.getItemType() != Item.NORMAL_ITEM && inbattle)
			{
				return false;
			}
			if (e.getItemType() == Item.EVENT_ITEM) {
				executeEvent(new Event(new File(e.getEvent())));
				//后续事件被忽略!
			}
			if (e.IsOneOff()) {
				int num = Integer.parseInt((String) itemnumlist.get(index));
				num--;
				if (num == 0) {
					itemlist.remove(index);
					itemnamelist.remove(index);
					itemnumlist.remove(index);
					return true;
				}
				String newnum = new Integer(num).toString();
				if (newnum.length() == 1)
					newnum = "0" + newnum;
				itemnumlist.set(index, newnum);
			}
			return true;
	
	}

	public boolean useItem(Item e, int index, BattleState bt) {

		if (e.getItemType() == Item.NORMAL_ITEM && !inbattle) {
			recover(e.getHealHp(), e.getRecoverMp());
		} else if (e.getItemType() == Item.BATTLE_ITEM && inbattle) {
			bt.setHp(bt.getHp() + e.getHealHp());
			bt.setMp(bt.getMp() + e.getRecoverMp());
		} else if(e.getItemType() == Item.BATTLE_ITEM && !inbattle){
			return false;
		} else if((e.getItemType() == Item.NORMAL_ITEM || e.getItemType() == Item.BATTLE_ITEM)  && inbattle)
		{
			return false;
		}
		if (e.getItemType() == Item.EVENT_ITEM) {
			executeEvent(new Event(new File(e.getEvent())));
			//后续事件被忽略!
		}
		//如果是一次性用品,使用后减少
		if (e.IsOneOff()) {
			int num = Integer.parseInt((String) itemnumlist.get(index));
			num--;
			if (num == 0) {
				itemlist.remove(index);
				itemnamelist.remove(index);
				itemnumlist.remove(index);
				return true;
			}
			String newnum = new Integer(num).toString();
			if (newnum.length() == 1)
				newnum = "0" + newnum;
			itemnumlist.set(index, newnum);
		}
		
		return true;
	}

	/*
	 * 设置主角战斗状态
	 * @param e 怪物文件
	 * @param b 为真时主角进入战斗,假时退出
	 */
	public void setBattle(Enemy e, boolean b) {
		enemy = e;
		inbattle = b;
	}

	/*
	 * @return 主角的战斗状态
	 */
	public Enemy getEnemy() {
		if (inbattle) {
			return enemy;
		} else
			return null;
	}

	public boolean inBattle() {
		return inbattle;
	}

	public void initEventName(EventManager manager) {
		if (eventnamelist == null) {
			track("正在初始化主角事件列表...");
			eventnamelist = new ArrayList();
			for (int i = 0; i < eventlist.size(); i++) {
				Event event = manager.getEvent((String) eventlist.get(i));
				if (event == null)
					abort("主角事件:" + eventlist.get(i) + "丢失!");
				eventnamelist.add(event.getName());
			}
			track("主角事件列表初始化完成.\n");
		}
	}

	public void initItemName(ItemManager manager) {
		if (itemnamelist == null) {
			track("正在初始化主角物品列表...");
			itemnamelist = new ArrayList();
			for (int i = 0; i < itemlist.size(); i++) {
				Item item = manager.getItem((String) itemlist.get(i));
				if (item == null)
					abort("主角物品:" + itemlist.get(i) + "丢失!");
				for (int j = 0; j < itemnamelist.size(); j++) {
					if (item.getName().equals((String) itemnamelist.get(j))) {
						item.reName();
					}
				}
				itemnamelist.add(item.getName());
			}
			track("主角物品列表初始化完成.\n");
		}
	}

	public ArrayList getItemNameList() {
		return itemnamelist;
	}

	public ArrayList getItemNumList() {
		return itemnumlist;
	}

	public ArrayList getItemList() {
		return itemlist;
	}

	public ArrayList getEventlist() {
		return eventlist;
	}

	public ArrayList getMagicList() {
		return magiclist;
	}

	public ArrayList getMagicNameList() {
		return magicnamelist;
	}

	public Magic[] getBattleMagic() {
		return battlemagic;
	}

	public void addLeaveNpc(String npcname) {
		leavenpclist.add(npcname);
	}

	public boolean findNpc(String npcname) {
		for (int i = 0; i < leavenpclist.size(); i++) {
			if (npcname.equals((String) leavenpclist.get(i))) {
				return true;
			}
		}
		return false;
	}

	public void initMagicName(MagicManager manager) {
		if (magicnamelist == null) {
			track("正在初始化主角技能列表...");
			magicnamelist = new ArrayList();
			for (int i = 0; i < magiclist.size(); i++) {
				Magic magic = manager.getMagic((String) magiclist.get(i));
				if (magic == null)
					abort("主角技能:" + magiclist.get(i) + "丢失!");
				for (int j = 0; j < magicnamelist.size(); j++) {
					if (magic.getName().equals((String) magicnamelist.get(j))) {
						magic.reName();
					}
				}
				magicnamelist.add(magic.getName());
			}
			track("主角技能列表初始化完成.\n");
		}
	}

	public int getMoney() {
		return money;
	}

	public void addMoney(int m) {
		if (m < 0) {
			return;
		}
		money += m;
	}

	public boolean useMoney(int m) {
		if (m < 0) {
			return false;
		}
		if ((money - m) >= 0) {
			money -= m;
			return true;
		}
		return false;
	}

	public Image[] getBattleImage() {
		return battleimage;
	}

	public Event getNextEvent() {
		return nextevent;
	}

	public void delete() {
		eventlist = null;
		itemlist = null;
		magiclist = null;

		eventnamelist = null;
		itemnamelist = null;
		itemnumlist = null;
		magicnamelist = null;
		leavenpclist = null;
	}

	private void abort(String s) {
		System.out.println(s);
		System.exit(0);
	}

	private void track(String s) {
		System.out.println(s);
	}

	private String name;

	private String property;

	private int hp;

	private int hpmx;

	private int mp;

	private int mpmx;

	private int str;

	private int def;

	private int mstr;

	private int mdef;

	private int hs;

	private int jouk;

	private int money;

	private int level;

	private int exp;

	private int expnext;

	private String mapfile;

	int x;

	int y;

	private int face = 0; /*
	 * 0正面
	 * 1背面
	 * 2左面
	 * 3右面
	 */

	private Image[] mapimage;

	private Image[] battleimage;

	private Magic[] battlemagic;

	private Event nextevent = null;

	private ArrayList eventlist;

	private ArrayList itemlist;

	private ArrayList magiclist;

	private ArrayList eventnamelist;

	private ArrayList itemnamelist;

	private ArrayList itemnumlist;

	private ArrayList magicnamelist;

	private ArrayList leavenpclist;

	private Enemy enemy = null;

	private boolean inbattle = false;

	private BattleState state;

}
