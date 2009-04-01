package com.ivan.dbm;

import java.util.ArrayList;
import java.util.HashMap;

public class DMJob {
	private String fromTable;
	private String toTable;
	private ArrayList<DMField> fields;
	private long size = 0;
	private ArrayList<DMField> keyList;
	private boolean containBlob = false;
	
	private String createTableSql;
	
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	private String executeSql;
	
	public DMJob(String fromTable, String toTable, ArrayList<DMField> fields, ArrayList<DMField> keyList,boolean containBlob) {
		super();
		this.toTable = toTable;
		this.fields = fields;
		this.keyList = keyList;
		this.fromTable = fromTable;
		this.containBlob = containBlob;
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ").append("[" + toTable + "]").append("(");
		DMField field;
		int i = 0;
		for(i = 0; i < fields.size() - 1; i++)
		{
			field = (DMField)fields.get(i);
			sql.append(("[" + field.getColumn() + "]")).append(",");
		}
		field = (DMField)fields.get(i);
		sql.append("[" + field.getColumn() + "]").append(") values (");
		for(i = 0; i < fields.size() - 1; i++)
		{
			sql.append("?,");
		}
		sql.append("?)");
		executeSql = sql.toString();
		//System.out.println(querySql);
		//System.out.println(executeSql);
		
		createTableSql = "create table [" + toTable + "] (";
		for(i = 0; i < fields.size(); i++)
		{
			createTableSql = createTableSql + "[" + fields.get(i).getColumn() + "] ";
			if(fields.get(i).getType() == DMField.STRING_TYPE)
			{
				createTableSql = createTableSql + Mysql2MssqlTypeMap.map.get(""+fields.get(i).getType()) + "(" + fields.get(i).getSize() + ") ";
			}
			else
			{
				createTableSql = createTableSql + Mysql2MssqlTypeMap.map.get(""+fields.get(i).getType());
			}
			if(i < fields.size() - 1)
			{
				createTableSql = createTableSql + ",";
			}
			else
			{
				createTableSql = createTableSql + ");";
			}
		}
		System.out.println(createTableSql);
	}
	public String getFromTable() {
		return fromTable;
	}
	public String getToTable() {
		return toTable;
	}
	public ArrayList<DMField> getFields() {
		return fields;
	}
	public String getExecuteSql() {
		return executeSql;
	}
	public ArrayList<DMField> getKeyList()
	{
		return keyList;
	}
	public boolean isContainBlob() {
		return containBlob;
	}
	public String getCreateTableSql() {
		return createTableSql;
	}
}
