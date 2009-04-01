package com.ivan.dbm;

import java.util.HashMap;

public class Mysql2MssqlTypeMap {
	public static HashMap<String,String> map;
	static{
		map = new HashMap<String,String>();
		map.put(""+DMField.STRING_TYPE, "nvarchar");
		map.put(""+DMField.INT_TYPE, "int");
		map.put(""+DMField.DATETIME_TYPE, "datetime");
		map.put(""+DMField.BLOB_TYPE, "image");
	}
}
