package com.ivan.game.unit;


/* ս���������״̬��Ϣ
 * ��ս�������ڼ���ʹ�ú������ݷ�������
 */
public class BattleState {

	/*
	 * �Ի������ݽ��й���
	 */
	public BattleState(
			String name,
			String property,		//����
			int hp, 				//Ѫ
			int	hpmx, 				//Ѫ����
			int mp, 				//����
			int mpmx, 				//��������
			int ap,					//������
			int d, 					//������
			int mstr,				//ħ��
			int mdef,				//ħ��
			int hs,					//������
			int jouk				//������
			)
	{
		this.name = name.substring(0);
		if(property.equals("��"))
			this.property = NORMAL_PROPERTY;
		else if(property.equals("ȫ"))
			this.property = ALL_PROPERTY;
		else if(property.equals("��"))
			this.property = FIRE_PROPERTY;
		else if(property.equals("��"))
			this.property = ICE_PROPERTY;
		else if(property.equals("ˮ"))
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
	 * �����ж�����,�е���clone
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
	 * Ĭ�Ϲ���
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
	 * @return ����
	 */
	public String getName()
	{
		return name;
	}
	/*
	 * @return ħ��
	 */
	public int getMstr()
	{
		return mstr;
	}
	/*
	 * @return ħ��
	 */
	public int getMdef()
	{
		return mdef;
	}
	/*
	 * @return ��������
	 */
	public String getPropertyString()
	{
		if(property == 0)
			return new String("��");
		else if(property == 1)
			return new String("ȫ");
		else if(property == 2)
			return new String("��");
		else if(property == 3)
			return new String("��");
		else if(property == 4)
			return new String("ˮ");
		else
			return null;
	}
	/*
	 * @return ����ֵ
	 */
	public int getProperty()
	{
		return property;
	}
	/*
	 * @return ����
	 */
	public int getStr()
	{
		return str;
	}
	/*
	 * @return ����
	 */
	public int getDef()
	{
		return def;
	}
	/*
	 * @return ����
	 */
	public int getHp()
	{
		return hp;
	}
	/*
	 * @return ��������
	 */
	public int getHpmx()
	{
		return hpmx;
	}
	/*
	 * @return ����
	 */
	public int getMp()
	{
		return mp;
	}
	/*
	 * @return ��������
	 */
	public int getMpmx()
	{
		return mpmx;
	}
	/*
	 * @return ����
	 */
	public int getJouk()
	{
		return jouk;
	}
	/*
	 * @return ������
	 */
	public int getHs()
	{
		return hs;
	}
	/*
	 * @return ��ʤ
	 */
	public boolean getWin()
	{
		return win;
	}
	/*
	 * @param ap ������
	 */
	public void setstr(int ap)
	{
		this.str = ap;
		if(this.str <= 0)
			this.str = 0;
	}
	/*
	 * @param d ������
	 */
	public void setDef(int d)
	{
		this.def = d;
		if(this.def <= 0)
			this.def = 0;
	}
	/*
	 * @param hp ����
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
	 * @param hpmx ��������
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
	 * @param mp ����
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
	 * @param mpmx ��������
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
	 * @param jouk ����
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
	 * @param hs ������
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
	 * @param win ʤ��
	 */
	public void setWin(boolean win)
	{
		this.win = win;
	}
	/*
	 * �ظ�
	 * @param ahp �ظ�������ֵ
	 * @param amp �ظ��ķ���ֵ
	 * @param an �ⶾ
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
	 * @param b �ж�
	 */
	public void setAntidotal(boolean b)
	{
		antidotal = b;
	}
	/*
	 * @param b ��debuff
	 * @param m debuff�ļ���
	 */
	public void setDebuffed(boolean b,Magic m)
	{
		debuffed = b;
	}
	/*
	 * @return �ж�״̬
	 */
	public boolean isAntidotal()
	{
		return antidotal;
	}
	/*
	 * @return ��debuff״̬
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
	 * ����
	 * 0	��ͨ
	 * 1	ȫ��
	 * 2	��
	 * 3	��
	 * 4	ˮ
	 */
	public final static int NORMAL_PROPERTY = 0;
	public final static int ALL_PROPERTY = 1;
	public final static int FIRE_PROPERTY = 2;
	public final static int ICE_PROPERTY = 3;
	public final static int WATER_PROPERTY = 4;
}
