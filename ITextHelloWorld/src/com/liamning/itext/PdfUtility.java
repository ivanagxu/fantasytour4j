package com.liamning.itext;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class PdfUtility {
	
	
	public BufferedOutputStream setPdfFiled(String field,String fileValue) throws IOException, DocumentException
	{
		
		PdfReader reader=new PdfReader("PdfTemplate/StudentTemplate.pdf");
		BufferedOutputStream buffer=new BufferedOutputStream(new FileOutputStream("PdfReport/output.pdf"));
		PdfStamper stamper=new PdfStamper(reader,buffer);
		AcroFields fields=stamper.getAcroFields();
		fields.setField(field, fileValue);
		stamper.close();
		return buffer;		
	}
	
	
	
	public BufferedOutputStream setPdfFiled(String inputFile,String outputFile,String field,String fileValue) throws IOException, DocumentException
	{
		
		PdfReader reader=new PdfReader(inputFile);
		BufferedOutputStream buffer=new BufferedOutputStream(new FileOutputStream(outputFile));
		PdfStamper stamper=new PdfStamper(reader,buffer);
		AcroFields fields=stamper.getAcroFields();
		fields.setField(field, fileValue);
		stamper.close();
		return buffer;		
	}
}
