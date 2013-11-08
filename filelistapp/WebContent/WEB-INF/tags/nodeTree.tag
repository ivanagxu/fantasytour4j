<%@tag description="display the whole nodeTree" pageEncoding="UTF-8"%>
<%@attribute name="node" type="com.ivan.buildindexsheet.IndexFile" required="true" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags" %>
<li>
	<a href='#' title='<%=node.getName() %>' 
		<%if(!node.isDirectory()) {%> onclick="window.open('DownloadFileServlet?Id=<%=node.getId().toString() %>')" <% } else if(node.getParentFile() != null) { %> onclick="location.href='indextree.jsp?path=<%=java.net.URLEncoder.encode(node.getAbsolutePath()) %>&level=3'" <% } %>>
		<img src="IndexFileIconServlet?Id=<%=node.getId().toString() %>"/>
		<%=node.getName() %>
	</a>
<% if(node.childFiles() != null){ %>
<ul>
<% for(int i = 0; i < node.childFiles().length; i++) { %>
<template:nodeTree node="<%=node.childFiles()[i] %>" />
<% } %>
</ul>
<% } %>
</li>