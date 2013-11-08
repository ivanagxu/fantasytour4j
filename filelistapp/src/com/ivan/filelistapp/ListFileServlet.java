package com.ivan.filelistapp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ivan.buildindexsheet.IndexSheetBuilder;

/**
 * Servlet implementation class ListFileServlet
 */
public class ListFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ListFileServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
		
		String path = request.getParameter("path");
		String strLv = request.getParameter("level");
		
		int level = 1;
		
		try{
			level = Integer.parseInt(strLv);
		}catch(Exception e)
		{
			level = 1;
		}
		
		File file = new File(path);
		if(!file.exists() || !file.isDirectory()){
			response.getOutputStream().println("Directory not found: " + path);
			return;
		}
		else{
			IndexSheetBuilder bder = new IndexSheetBuilder();
			
			String iconPath = getServletContext().getRealPath("/") + "icons/";
			
			iconPath = iconPath.replace("\\.\\", "\\");
			
			try {
				System.out.println("Start to generate index");
				String html = bder.generateHtml(path, level, iconPath, "icons/", "icons", null);
				html = html.replace("</body>", "<a href='index.jsp'>Back</a></body>");
				response.getOutputStream().write(html.getBytes());
				System.out.println("Finish generate index");
			} catch (Exception e) {
				PrintWriter out = response.getWriter();
				e.printStackTrace(out);
			}
		}
	}

}
