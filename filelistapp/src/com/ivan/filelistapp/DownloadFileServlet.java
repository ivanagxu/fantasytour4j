package com.ivan.filelistapp;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ivan.buildindexsheet.IndexFile;

/**
 * Servlet implementation class DownloadFileServlet
 */
public class DownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadFileServlet() {
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
		
		Object obj = request.getSession().getAttribute("iconMap");
		String strId = request.getParameter("Id");
		
		if(obj != null && strId != null){
			HashMap<Long, IndexFile> iconId = (HashMap<Long, IndexFile>) obj;
			Object idxFile = iconId.get(Long.parseLong(strId));
			
			if(idxFile != null){
		
				int BUFSIZE = 4096;
			    
			    IndexFile file = (IndexFile)idxFile;
			    
		        int length   = 0;
		        ServletOutputStream outStream = response.getOutputStream();
		        ServletContext context  = getServletConfig().getServletContext();
		        String mimetype = context.getMimeType(file.getAbsolutePath());
		       
		        // sets response content type
		        if (mimetype == null) {
		            mimetype = "application/octet-stream";
		        }
		        response.setContentType(mimetype);
		        response.setContentLength((int)file.length());
		        String fileName = file.getName();
		       
		        // sets HTTP header
		        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		       
		        byte[] byteBuffer = new byte[BUFSIZE];
		        DataInputStream in = new DataInputStream(new FileInputStream(file));
		       
		        // reads the file's bytes and writes them to the response stream
		        while ((in != null) && ((length = in.read(byteBuffer)) != -1))
		        {
		            outStream.write(byteBuffer,0,length);
		        }
		       
		        in.close();
		        outStream.close();
			}
			else{
				response.getOutputStream().write("File is not found".getBytes());
			}
		}
		else
		{
			response.getOutputStream().write("File is not found".getBytes());
		}
	}

}
