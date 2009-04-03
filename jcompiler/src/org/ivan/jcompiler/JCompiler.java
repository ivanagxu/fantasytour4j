package org.ivan.jcompiler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class JCompiler {
	private JavaCompiler jc;
	public JCompiler()
	{
		jc = ToolProvider.getSystemJavaCompiler();
	}
	public void execute(String cmd) throws Exception
	{
		Runtime run = Runtime.getRuntime();
		Process p = run.exec(cmd);

		BufferedInputStream in = new BufferedInputStream(p.getInputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String s;
		while ((s = br.readLine()) != null)
		System.out.println(s);
	}
	
	public DiagnosticCollector compile(File[] javaSrcFiles, String d, String classpath)
	{
		boolean success = false;
		DiagnosticCollector diagnostics = new DiagnosticCollector();
		try {
			StandardJavaFileManager sjfm = jc.getStandardFileManager(diagnostics, null, null);
			Iterable< String> options = Arrays.asList("-d", d,"-cp",classpath);
			Iterable fileObjects = sjfm.getJavaFileObjectsFromFiles(Arrays.asList(javaSrcFiles));
			success = jc.getTask(null, sjfm, diagnostics, options, null, fileObjects).call();
			sjfm.close();
			if (!success) {
				for (int i = 0; i < (diagnostics.getDiagnostics().size()); i++) {
					Diagnostic diagnostic = (Diagnostic) diagnostics.getDiagnostics().get(i);
					System.out.printf("Code: %s%n" + "Kind: %s%n"
									+ "Position: %s%n" + "Start Position: %s%n"
									+ "End Position: %s%n" + "Source: %s%n"
									+ "Message: %s%n", 
									diagnostic.getCode(),
									diagnostic.getKind(), 
									diagnostic.getPosition(), 
									diagnostic.getStartPosition(), 
									diagnostic.getEndPosition(), 
									diagnostic.getSource(), 
									diagnostic.getMessage(null)
					);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diagnostics;
	}
}
