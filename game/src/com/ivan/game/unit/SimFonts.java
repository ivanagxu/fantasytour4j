package com.ivan.game.unit;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SimFonts {
	private Font font = null;
	private static class Holder {
		private static final SimFonts instance = new SimFonts();
	}
	public SimFonts()
	{
		InputStream in;
		try {
			in = new FileInputStream("./data/fonts/simfang.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT,in).deriveFont(13f);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static Font getFont()
	{
		return Holder.instance.font;
	}
}
