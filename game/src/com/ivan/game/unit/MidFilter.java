package com.ivan.game.unit;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class MidFilter  extends FileFilter{

	public boolean accept(File f)
	{
		return f.getName().toLowerCase().endsWith(".mid")
			|| f.isDirectory();
	}
	public String getDescription()
	{
		return "MIDI data";
	}
}
