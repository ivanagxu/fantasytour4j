package com.ivan.filelistapp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.Icon;

import com.ivan.buildindexsheet.IndexFile;

/**
 * Servlet implementation class IndexFileIconServlet
 */
public class IndexFileIconServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexFileIconServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpg");
		
		Object obj = request.getSession().getAttribute("iconMap");
		String strId = request.getParameter("Id");
		
		
		if(obj != null && strId != null){
			HashMap<Long, IndexFile> iconId = (HashMap<Long, IndexFile>) obj;
			
			Object idxFile = iconId.get(Long.parseLong(strId));
			
			if(idxFile != null){
			
				IndexFile file = (IndexFile)idxFile;
				Icon icon = file.getIcon();
				
				if(icon != null){
					BufferedImage bi = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
				    Graphics g = bi.createGraphics();
				    g.setColor(Color.WHITE);
				    g.fillRect(0, 0, icon.getIconWidth(),icon.getIconHeight());
				    icon.paintIcon(null, g, 0, 0);
				    OutputStream out = response.getOutputStream();
				    ImageIO.write(bi, "jpg", out);
					out.close();
				}
				else
				{
					generateDummayIcon(response);
				}
			}
			else
			{
				generateDummayIcon(response);
			}
		    
		}
		else
		{
			generateDummayIcon(response);
		}
	}
	
	private void generateDummayIcon(HttpServletResponse response) throws ServletException, IOException{
		String pathToWeb = getServletContext().getRealPath(File.separator);
		File f = new File((pathToWeb + "icons/dummy.jpg").replace("\\.\\", "\\"));
		BufferedImage bi = ImageIO.read(f);
		OutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.close();
	}

}
