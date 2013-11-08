package com.ivan.buildindexsheet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.Icon;

public class IndexSheetBuilder {
	
	private int maxLevel = 0;
	private long iconNumber = System.currentTimeMillis() * 10000;
	private StringBuffer html = null;
	private String iconFolder = "C:\\icons\\";
	private String iconRelativePath = "/icons/";
	private String saveHtml = "C:\\index.html";
	private String ignoreFolder = "icons";

	public static void main(String[] args) {
		
		IndexSheetBuilder bder = new IndexSheetBuilder();
		String path = System.getProperty("user.dir");
		int level = 0;

		try{
			
			if(args == null || args.length == 0){
				System.out.println("Generate index in current directory");
				bder.generateHtml(path, level, "C:\\icons\\", "/icons/", "icons","C:\\index.html");
			}
			else
			{
				if(args.length > 1){
					try{
						level = Integer.parseInt(args[1]);
					}catch(Exception e)
					{
						System.out.println("Please provide path and level, e.g \"C:/temp\" 0");
						return;
					}
				}
				path = args[0];//.replace("/", "").replace("\\", "").replace("\"", "").trim();
				if(path.equals(""))
					path = System.getProperty("user.dir");
				System.out.println("Generate index for directory: " + path);
				System.out.println("Generate index level: " + level);
				
				File file = new File(path);
				if(!file.exists() || !file.isDirectory()){
					System.out.println("Directory not found: " + path);
					return;
				}
				else{
					bder.generateHtml(path, level, "C:\\icons\\", "/icons/", "icons","C:\\index.html");
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String generateHtml(String path, int level, String iconFolder, String iconRelativePath, String ignoreFolder, String saveHtml) throws Exception{
		this.iconFolder = iconFolder;
		this.iconRelativePath = iconRelativePath;
		this.ignoreFolder = ignoreFolder;
		this.saveHtml = saveHtml;
		
		boolean save = saveHtml != null;
		
		generateIndex(new File(path), level);
		html.append("</table></body></html>");
		
		if(save){
			FileOutputStream fous = new FileOutputStream(this.saveHtml);
			fous.write(html.toString().getBytes());
			fous.close();
		}
		System.out.println(html.toString());
		return html.toString();
	}
	
	public IndexFile generateIndexFile(String path, int level){
		return new IndexFile(path, level);
	}
	
	private void generateIndex(File path, int level) throws Exception{
		
		if(html == null){
			html = new StringBuffer();
			html.append("<html><head><title>Index</title></head><body><table>").append("\n");
		}
		
		if(maxLevel < level)
			maxLevel = level;
		
		if(path.isDirectory() && path.getName().equals(ignoreFolder))
			return;
		
		Icon icon = javax.swing.filechooser.FileSystemView.getFileSystemView().getSystemIcon( path );
		BufferedImage bi = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
	    Graphics g = bi.createGraphics();
	    g.setColor(Color.WHITE);
	    g.fillRect(0, 0, icon.getIconWidth(),icon.getIconHeight());
	    icon.paintIcon(null, g, 0, 0);
	    File iconFile = new File(iconFolder + (iconNumber++) + ".jpg");
	    ImageIO.write(bi, "PNG", iconFile);
	    g.dispose();
		
		if(path.isDirectory()){
			System.out.println(getPaddingSpace(level) + "[" + path.getName() + "]");
			appendHtmlTable(path, iconFile, level);
			
			File[] fileList = path.listFiles();

			Arrays.sort(fileList);
			
			for(int i = 0; i < fileList.length; i++)
			{
				if(level > 0 && fileList[i].getName().indexOf(".") != 0)
				{
					generateIndex(fileList[i], level - 1);
				}
			}
		}
		else
		{
			System.out.println(getPaddingSpace(level) + path.getName());
			appendHtmlTable(path, iconFile, level);
		}
		
		
	}
	
	private String getPaddingSpace(int lv){
		String result = "";
		while(lv < maxLevel){
			result += " ";
			lv++;
		}
		return result;
	}
	
	private String getPaddingCharacter(int lv){
		String result = "";
		while(lv < maxLevel){
			result += "-";
			lv++;
		}
		return result;
	}
	
	private void appendHtmlTable(File path, File iconFile, int level)
	{
		html.append("\n");
		html.append("<tr><td><img src='" + iconRelativePath + iconFile.getName() + "'");
		html.append("</td><td>");
		html.append("\n");
		html.append(encodeHTML(getPaddingCharacter(level) + (path.isDirectory() ? "[" : "") + path.getName() + (path.isDirectory() ? "]" : "")));
		html.append("</td></tr>");
	}
	
	private String encodeHTML(String s)
	{
	    StringBuffer out = new StringBuffer();
	    for(int i=0; i<s.length(); i++)
	    {
	        char c = s.charAt(i);
	        if(c > 127 || c=='"' || c=='<' || c=='>')
	        {
	           out.append("&#"+(int)c+";");
	        }
	        else
	        {
	            out.append(c);
	        }
	    }
	    return out.toString();
	}
}
