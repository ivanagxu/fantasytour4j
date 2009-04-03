package com.ivan.game.unit;
import javax.swing.filechooser.FileFilter;
import java.io.*;

public class GifFilter extends FileFilter{
	public boolean accept(File f)
	{
		return f.getName().toLowerCase().endsWith(".gif")
			|| f.isDirectory();
	}
	public String getDescription()
	{
		return "Image GIF";
	}
}