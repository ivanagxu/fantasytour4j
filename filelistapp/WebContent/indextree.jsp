<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.ivan.buildindexsheet.IndexFile" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags" %>
<% 
String path = request.getParameter("path");
String strLv = request.getParameter("level");
int lv = 3;

if(path == null)
	path = "C:/Users/Administrator/Desktop/Note/History";

if(strLv == null)
	lv = 3;
else{
	try{
		lv = Integer.parseInt(strLv);
	}catch(Exception e){
		lv = 3;
	}
}

IndexFile indexFile = new IndexFile(path,lv);
session.setAttribute("rootFile", indexFile);
session.setAttribute("iconMap", indexFile.getIdMap());
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Index Tree</title>
	<link rel="stylesheet" href="jquery/jquery-ui.css" />
	<script src="jquery/jquery-1.9.1.js"></script>
	<script src="jquery/jquery-ui.js"></script>
	
	<style>
		.ui-menu { width: 250px; }
		a { white-space: nowrap } 
		body {
			font-family: "Trebuchet MS", "Helvetica", "Arial",  "Verdana", "sans-serif";
			font-size: 62.5%;
		}
	</style>
</head>
<body>
	<form action="indextree.jsp" method = "Post">
	<!-- String path, int level, String iconFolder, String iconRelativePath, String ignoreFolder, String saveHtml -->
	<table>
		<tr>
			<td>Path:</td>
			<td><input name="path" value="C:\Users\Administrator\Desktop\Note\History"/></td>
			<td>Level:</td>
			<td><input name="level" value="3"/></td>
			<td><input type="submit" value="List"/></td>
		</tr>
	</table>
	</form>
	
	<ul id="menu">
		<li><a href='#' title='<%=indexFile.getName() %>' <%if(!indexFile.isDirectory()) {%> onclick="window.open('DownloadFileServlet?Id=<%=indexFile.getId().toString() %>')" <%} %>><img src="IndexFileIconServlet?Id=<%=indexFile.getId().toString() %>"/><%=indexFile.getName()%></a>
			<% if(indexFile.childFiles() != null) { %>
			<ul>
			<% for(int i = 0; i < indexFile.childFiles().length; i++) { %>
					<template:nodeTree node="<%=indexFile.childFiles()[i] %>" />
			<% } %>
			</ul>
			<% } %>
		</li>
	</ul>
	<script>
		$(function() {
		  $( "#menu" ).menu();
		});
	</script>
</body>
</html>