package com.liamning.itext;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StudentBean student=new StudentBean();
		student.setName("liamning");
		student.setAge(24);
		student.setSex("man");
		StudentImpl stuImpl=new StudentImpl();
		stuImpl.setStudent(student);
		stuImpl.pdfReport();

	}

}
