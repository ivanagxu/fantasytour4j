package com.ivan.game.unit;
import javax.swing.filechooser.FileFilter;
import java.io.*;

public class DatFilter extends FileFilter{
	public boolean accept(File f)
	{
		return f.getName().toLowerCase().endsWith(".dat")
			|| f.isDirectory();
	}
	public String getDescription()
	{
		return "DAT data";
	}
}
