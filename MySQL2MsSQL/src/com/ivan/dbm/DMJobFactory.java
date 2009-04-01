package com.ivan.dbm;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DMJobFactory {
	protected static Document document;
	protected static SAXReader saxReader;
	

	@SuppressWarnings("unchecked")
	public static ArrayList<DMJob> configure(String jobfile)throws DocumentException
	{
		DMLogger.getLogger().info("Configuring jobs");
		
		ArrayList<DMJob> jobs = new ArrayList<DMJob>();
		File jobFile = new File(jobfile);
		saxReader = new SAXReader();
		document = saxReader.read(jobFile);
		
		Element rootE = document.getRootElement();
		Element jobE;
		Element querySqlE;
		Element fieldE;
		Element columnE;
		Element typeE;
		Element sizeE;
		boolean containBlob = false;
		ArrayList<DMField> fields = new ArrayList<DMField>();
		ArrayList<DMField> keyList = new ArrayList<DMField>();
		DMField field;
		Element executeSqlE;
		List<Element> joblist = rootE.elements("job");
		
		if(joblist.size() == 0)
		{
			DMLogger.getLogger().info("no job");
			return jobs;
		}

		Iterator<Element> it = joblist.iterator();
		while(it.hasNext())
		{
			jobE = (Element)it.next();
			querySqlE = jobE.element("fromTable");
			executeSqlE = jobE.element("toTable");
			List<Element> fieldlist = jobE.elements("field");
			//System.out.println(fieldlist.size());
			Iterator<Element> fit = fieldlist.iterator();
			
			while(fit.hasNext())
			{
				fieldE = (Element)fit.next();
				columnE = fieldE.element("column");
				typeE = fieldE.element("type");
				sizeE = fieldE.element("size");
				
				field = new DMField(columnE.getText(),DMField.getTypeCode(typeE.getText()),sizeE.getText());
				if(fieldE.element("key") != null)
				{
					keyList.add(field);
				}
				fields.add(field);
				if(typeE.getText().equals("blob"))
					containBlob = true;
			}
			if(keyList.size() == 0)
				keyList = null;
			jobs.add(new DMJob(querySqlE.getText(),executeSqlE.getText(),fields,keyList,containBlob));
			
			fields = new ArrayList<DMField>();
			keyList = new ArrayList<DMField>();
			containBlob = false;
		}
		
		return jobs;
	}
}
