package org.ivan.jcompiler.test;

import java.io.File;

import javax.tools.DiagnosticCollector;

import junit.framework.TestCase;

import org.ivan.jcompiler.JCompiler;

public class JCompilerTest extends TestCase{
	public JCompilerTest(String fName)
	{
		super("testJCompiler");
	}
	public void testJCompiler()
	{
		JCompiler jc = new JCompiler();
		DiagnosticCollector diagnostics = jc.compile(new File[]{new File("source/Hello.java")}, "./build", "./source");
		assertTrue(diagnostics.getDiagnostics().size() == 0);
		try {
			jc.execute("java -cp ./build com.ivan.hello.test.Hello");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
