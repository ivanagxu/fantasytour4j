<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<title>Index</title>
</head>
<body>
<form action="ListFileServlet" method = "Post">
	<!-- String path, int level, String iconFolder, String iconRelativePath, String ignoreFolder, String saveHtml -->
	<table>
		<tr>
			<td>Path:</td>
			<td><input name="path" value="C:\Users\Administrator\Desktop\Note\History" width="600px"/></td>
			<td>Level:</td>
			<td><input name="level" value="2"/></td>
			<td><input type="submit" value="List"/></td>
		</tr>
	</table>

</form>
</body>
</html>