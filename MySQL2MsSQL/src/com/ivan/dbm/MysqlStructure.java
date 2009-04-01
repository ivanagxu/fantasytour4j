package com.ivan.dbm;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class MysqlStructure {

	/**
	 * @param args
	 */
	protected static Document document;
	protected static File jobfile;
	protected static SAXReader saxReader;
	static Connection con;
	public static void createJob(Connection conn,String filename) {
		try{
			con = conn;

			saxReader = new SAXReader();
			
			document = saxReader.getDocumentFactory().createDocument();
			document.addElement("jobs");
			
			
			ArrayList<String> tables = new ArrayList<String>();
			DatabaseMetaData md = con.getMetaData();
			ResultSet rs = md.getTables("vrms", "vrms", null, null);
			while(rs.next())
			{
				//System.out.println(rs.getString(3) + " " + rs.getString("TABLE_TYPE"));
				if(rs.getString("TABLE_TYPE").equals("TABLE"))
					tables.add(rs.getString(3));
			}
			rs.close();
			
			
			for(int i = 0; i < tables.size(); i++)
			{
				addJob(tables.get(i));
			}
			
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			format.setSuppressDeclaration(false);
			format.setIndent(true);
			format.setIndent("   ");
			format.setNewlines(true);

			XMLWriter output = new XMLWriter(new FileWriter(new File(
					filename)), format);
			output.write(document);
			output.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void addJob(String table) throws SQLException
	{
		DatabaseMetaData md = con.getMetaData();
		ResultSet rs = md.getColumns("vrms", "vrms", table, null);
		
		
		Element rootE = document.getRootElement();
		Element jobE = rootE.addElement("job");
		jobE.addElement("fromTable").addText(table);
		jobE.addElement("toTable").addText(table);
		
		Element fieldE;
		String type;

		while(rs.next())
		{
			type = rs.getString(6);
			if(type.equalsIgnoreCase("varchar") || type.equalsIgnoreCase("text"))
				type = "String";
			else if(type.equalsIgnoreCase("int") || type.equalsIgnoreCase("int unsigned") || type.equalsIgnoreCase("tinyint unsigned"))
				type = "int";
			else if(type.equalsIgnoreCase("datetime") || type.equalsIgnoreCase("date") || type.equalsIgnoreCase("timestamp"))
				type = "datetime";
			else if(type.equalsIgnoreCase("blob"))
				type = "blob";
			else{
				System.out.println("UnsupportType:" + type);
			}
			
			fieldE = jobE.addElement("field");
			fieldE.addElement("column").addText(rs.getString(4));
			fieldE.addElement("type").addText(type);
			int size = Integer.parseInt(rs.getString(7));
			if(size > 8000)
				size  = 1000;
			fieldE.addElement("size").addText("" + size);
		}
		rs.close();
	}
}
