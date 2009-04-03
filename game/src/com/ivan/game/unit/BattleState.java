package com.ivan.game.unit;


/* 战斗中人物的状态信息
 * 在战斗中用于技能使用后做数据分析处理
 */
public class BattleState {

	/*
	 * 以基础数据进行构造
	 */
	public BattleState(
			String name,
			String property,		//属性
			int hp, 				//血
			int	hpmx, 				//血上限
			int mp, 				//法力
			int mpmx, 				//法力上限
			int ap,					//攻击力
			int d, 					//防御力
			int mstr,				//魔力
			int mdef,				//魔防
			int hs,					//致命率
			int jouk				//躲闪率
			)
	{
		this.name = name.substring(0);
		if(property.equals("普"))
			this.property = NORMAL_PROPERTY;
		else if(property.equals("全"))
			this.property = ALL_PROPERTY;
		else if(property.equals("火"))
			this.property = FIRE_PROPERTY;
		else if(property.equals("冰"))
			this.property = ICE_PROPERTY;
		else if(property.equals("水"))
			this.property = WATER_PROPERTY;
		
		this.str = ap;
		this.def = d;
		this.hp = hp;
		this.hpmx = hpmx;
		this.mp = mp;
		this.mpmx = mpmx;
		this.mstr = mstr;
		this.mdef = mdef;
		this.jouk = jouk;
		this.hs = hs;
		
		win = true;
		
	}
	/*
	 * 以已有对象构造,有点似clone
	 */
	public BattleState(BattleState state)
	{
		this.name = state.getName();
		this.property = state.getProperty();
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
		
		this.win =state.getWin();
	}
	/*
	 * 默认构造
	 */
	public BattleState()
	{
		property = 0;
		hp = 1;
		hpmx = 1;
		mp = 1;
		mpmx = 1;
		str = 1;
		def = 1;
		mstr = 1;
		mdef = 1;
		hs = 0;
		jouk = 0;
		win = true;
	}
	/*
	 * @return 名字
	 */
	public String getName()
	{
		return name;
	}
	/*
	 * @return 魔力
	 */
	public int getMstr()
	{
		return mstr;
	}
	/*
	 * @return 魔防
	 */
	public int getMdef()
	{
		return mdef;
	}
	/*
	 * @return 属性名称
	 */
	public String getPropertyString()
	{
		if(property == 0)
			return new String("普");
		else if(property == 1)
			return new String("全");
		else if(property == 2)
			return new String("火");
		else if(property == 3)
			return new String("冰");
		else if(property == 4)
			return new String("水");
		else
			return null;
	}
	/*
	 * @return 属性值
	 */
	public int getProperty()
	{
		return property;
	}
	/*
	 * @return 力量
	 */
	public int getStr()
	{
		return str;
	}
	/*
	 * @return 防御
	 */
	public int getDef()
	{
		return def;
	}
	/*
	 * @return 生命
	 */
	public int getHp()
	{
		return hp;
	}
	/*
	 * @return 生命上限
	 */
	public int getHpmx()
	{
		return hpmx;
	}
	/*
	 * @return 法力
	 */
	public int getMp()
	{
		return mp;
	}
	/*
	 * @return 法力上限
	 */
	public int getMpmx()
	{
		return mpmx;
	}
	/*
	 * @return 躲闪
	 */
	public int getJouk()
	{
		return jouk;
	}
	/*
	 * @return 致命率
	 */
	public int getHs()
	{
		return hs;
	}
	/*
	 * @return 获胜
	 */
	public boolean getWin()
	{
		return win;
	}
	/*
	 * @param ap 攻击力
	 */
	public void setstr(int ap)
	{
		this.str = ap;
		if(this.str <= 0)
			this.str = 0;
	}
	/*
	 * @param d 防御力
	 */
	public void setDef(int d)
	{
		this.def = d;
		if(this.def <= 0)
			this.def = 0;
	}
	/*
	 * @param hp 生命
	 */
	public void setHp(int hp)
	{
		this.hp = hp;
		if(this.hp > hpmx)
			this.hp = hpmx;
		if(this.hp <= 0)
		{
			this.hp = 0;
			this.win = false;
		}
	}
	public void fullyRecover()
	{
		hp = hpmx;
		mp = mpmx;
	}
	/*
	 * @param hpmx 生命上限
	 */
	public void setHpmx(int hpmx)
	{
		this.hpmx = hpmx;
		if(hp > hpmx)
			hp = hpmx;
		if(this.hpmx <= 0)
			this.hpmx = 0;
	}
	/*
	 * @param mp 法力
	 */
	public void setMp(int mp)
	{
		this.mp = mp;
		if(this.mp > mpmx)
			this.mp = mpmx;
		if(this.mp <= 0)
			this.mp = 0;
	}
	/*
	 * @param mpmx 法力上限
	 */
	public void setMpmx(int mpmx)
	{
		this.mpmx = mpmx;
		if(mp > mpmx)
			mp = mpmx;
		if(this.mpmx <= 0)
			this.mpmx =0;
	}
	/*
	 * @param jouk 躲闪
	 */
	public void setHitp(int jouk)
	{
		this.jouk = jouk;
		if(this.jouk < 0)
			this.jouk = 0;
		else if(jouk > 100)
			this.jouk = 100;
	}
	/*
	 * @param hs 致命率
	 */
	public void setHs(int hs)
	{
		this.hs = hs;
		if(this.hs < 0)
			this.hs = 0;
		else if(hs > 100)
			this.hs = 100;
	}
	/*
	 * @param win 胜利
	 */
	public void setWin(boolean win)
	{
		this.win = win;
	}
	/*
	 * 回复
	 * @param ahp 回复的生命值
	 * @param amp 回复的法力值
	 * @param an 解毒
	 */
	public void recover(int ahp,int amp,boolean an)
	{
		hp += ahp;
		mp += amp;
		if(hp > hpmx)
			hp = hpmx;
		if(mp > mpmx)
			mp = mpmx;
		setAntidotal(an);
	}
	public void setMstr(int m)
	{
		mstr = m;
		if(mstr < 0)
			mstr = 0;
	}
	public void setMdef(int m)
	{
		mdef = m;
		if(mdef < 0)
			mdef = 0;
	}
	/*
	 * @param b 中毒
	 */
	public void setAntidotal(boolean b)
	{
		antidotal = b;
	}
	/*
	 * @param b 中debuff
	 * @param m debuff的技能
	 */
	public void setDebuffed(boolean b,Magic m)
	{
		debuffed = b;
	}
	/*
	 * @return 中毒状态
	 */
	public boolean isAntidotal()
	{
		return antidotal;
	}
	/*
	 * @return 中debuff状态
	 */
	public boolean isDebuffed()
	{
		return debuffed;
	}
	/////////////////////////////////////////////////////////
	private String name;
	private int property;
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
	
	private boolean antidotal = false;
	private boolean debuffed = false;
	
	private boolean win = true;
	
	/*
	 * 属性
	 * 0	普通
	 * 1	全部
	 * 2	火
	 * 3	冰
	 * 4	水
	 */
	public final static int NORMAL_PROPERTY = 0;
	public final static int ALL_PROPERTY = 1;
	public final static int FIRE_PROPERTY = 2;
	public final static int ICE_PROPERTY = 3;
	public final static int WATER_PROPERTY = 4;
}
