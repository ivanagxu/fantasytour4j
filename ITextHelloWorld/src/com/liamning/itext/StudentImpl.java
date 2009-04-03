package com.liamning.itext;

import java.io.BufferedOutputStream;
import java.io.IOException;

import com.lowagie.text.DocumentException;

public class StudentImpl implements PersonalDataReport {
	
	private StudentBean student;

	public void setStudent(StudentBean student) {
		this.student = student;
	}

	public BufferedOutputStream pdfReport() {
		// TODO Auto-generated method stub
		PdfUtility pdfHandler=new PdfUtility();
		try {
			pdfHandler.setPdfFiled("Name",student.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

}
