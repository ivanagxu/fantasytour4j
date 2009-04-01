package com.ivan.dbm;

public class DMField {
	private String column;
	private int type;
	private int size = 0;
	
	public final static int INT_TYPE = 0;
	public final static int STRING_TYPE = 1;
	public final static int DATETIME_TYPE = 2;
	public final static int BLOB_TYPE = 3;
	
	public static int getTypeCode(String type)
	{
		if(type.equals("int"))
			return INT_TYPE;
		else if(type.equals("datetime"))
		{
			return DATETIME_TYPE;
		}
		else if(type.equals("String"))
		{
			return STRING_TYPE;
		}
		else if(type.equals("blob"))
		{
			return BLOB_TYPE;
		}
		return -1; //Unsupport
	}
	public DMField(String column, int type , String size) {
		super();
		this.column = column;
		this.type = type;
		this.size = Integer.parseInt(size);
	}
	public String getColumn() {
		return column;
	}
	public int getType() {
		return type;
	}
	public int getSize()
	{
		return size;
	}
}
